package compilers.visitor.kxi.typecheck;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.*;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.exceptions.TypeCheckException;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.result.ResultFlag;
import compilers.visitor.kxi.result.ResultType;
import compilers.visitor.kxi.symboltable.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class TypeCheckerVisitor extends KxiVisitorBase {
    private ScopeHandler scopeHandler;
    private Stack<ResultType> resultTypeStack;
    private boolean staticContext;

    public TypeCheckerVisitor(ScopeHandler scopeHandler, Stack<ResultType> resultTypeStack) {
        this.scopeHandler = scopeHandler;
        this.resultTypeStack = resultTypeStack;
        staticContext = false;
    }

    @Override
    public void dumpErrorStack() {
        System.err.println("TypeCheck Errors");
        super.dumpErrorStack();
    }


    private void matchId(ResultType resultL, ResultType resultR, String codeLine) {

        ScalarType left = resultL.getTypeData().getType().getScalarType();
        ScalarType right = resultR.getTypeData().getType().getScalarType();

        if (left == ScalarType.ID && right == ScalarType.ID) {
            String IdL = resultL.getTypeData().getType().getKxiType().getIdName().getValue();
            String IdR = resultR.getTypeData().getType().getKxiType().getIdName().getValue();


            if (!IdL.equals(IdR))
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + IdR + " expected: " + IdL));
        }

    }

    private void matchResults(String codeLine) {
        if (resultTypeStack.size() >= 2) {
            ResultType resultR = resultTypeStack.pop();
            ResultType resultL = resultTypeStack.pop();

            int arrayDepthL = resultL.getTypeData().getType().getArrayDepth();
            int arrayDepthR = resultR.getTypeData().getType().getArrayDepth();

            ScalarType left = resultL.getTypeData().getType().getScalarType();
            ScalarType right = resultR.getTypeData().getType().getScalarType();

            matchId(resultL, resultR, codeLine);
            //if has flags
            if (resultR.containsFlag(ResultFlag.Method))
                exceptionStack.push(new TypeCheckException(codeLine, "invalid method in expression " + resultR.getReferenceId()));
                //check if left is pointer
            else if (resultL.getTypeData().isStatic() && resultR.containsFlag(ResultFlag.This)) {
                exceptionStack.push(new TypeCheckException(codeLine, "Can't access non-static member " + resultR.getReferenceId() + " in a static context"));
            } else if (!resultL.getTypeData().isStatic() && staticContext) {
                exceptionStack.push(new TypeCheckException(codeLine, "Can't assign to instance member in a static context"));
            } else if (right == ScalarType.NULL) {
                if (left != ScalarType.ID && arrayDepthL == 0 && left != ScalarType.STRING) {
                    exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected pointer"));
                }
            } else if (left != right)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: " + left));
            else if (arrayDepthL != arrayDepthR)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched ArrayDim providedDimension: " + arrayDepthR + " expectedDimension: " + arrayDepthL));

            resultTypeStack.push(resultR);
        }
    }

    private void matchResultsOnType(String codeLine, ScalarType scalarType) {
        if (resultTypeStack.size() >= 2) {
            ResultType resultR = resultTypeStack.pop();
            ResultType resultL = resultTypeStack.pop();

            int arrayDepthL = resultL.getTypeData().getType().getArrayDepth();
            int arrayDepthR = resultR.getTypeData().getType().getArrayDepth();

            ScalarType left = resultL.getTypeData().getType().getScalarType();
            ScalarType right = resultR.getTypeData().getType().getScalarType();

            matchId(resultL, resultR, codeLine);
            //if has flags
            if (resultR.containsFlag(ResultFlag.Method))
                exceptionStack.push(new TypeCheckException(codeLine, "invalid method in expression " + resultR.getReferenceId()));
                //check if left is pointer
            else if (resultL.getTypeData().isStatic() && resultR.containsFlag(ResultFlag.This)) {
                exceptionStack.push(new TypeCheckException(codeLine, "Can't access non-static member " + resultR.getReferenceId() + " in a static context"));
            } else if (right == ScalarType.NULL) {
                if (left != ScalarType.ID && arrayDepthL == 0 && left != ScalarType.STRING) {
                    exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected pointer"));
                }
            } else if (left != scalarType && right != scalarType)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: " + scalarType));
            else if (arrayDepthL != arrayDepthR)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched ArrayDim providedDimension: " + arrayDepthR + " expectedDimension: " + arrayDepthL));

            resultTypeStack.push(resultR);
        }
    }

    private void matchParamArg(SymbolData left, SymbolData right, String codeLine) {

        int arrayDepthL = right.getType().getArrayDepth();
        int arrayDepthR = left.getType().getArrayDepth();

        ScalarType leftType = left.getType().getScalarType();
        ScalarType rightType = right.getType().getScalarType();

        if (leftType == ScalarType.ID && rightType == ScalarType.ID) {
            String IdL = left.getType().getKxiType().getIdName().getValue();
            String IdR = right.getType().getKxiType().getIdName().getValue();

            if (!IdL.equals(IdR))
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types arg: " + IdR + " param: " + IdL));
        }
        if (leftType != rightType)
            exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types arg: " + rightType + " param: " + leftType));
        else if (arrayDepthL != arrayDepthR)
            exceptionStack.push(new TypeCheckException(codeLine, "Mismatched ArrayDim arg Dimension: " + arrayDepthR + " param Dimension: " + arrayDepthL));

    }

    private void matchResultsOnInt(String codeLine) {
        if (resultTypeStack.size() >= 2) {
            ResultType resultR = resultTypeStack.pop();
            ResultType resultL = resultTypeStack.pop();
            ScalarType left = resultL.getTypeData().getType().getScalarType();
            ScalarType right = resultR.getTypeData().getType().getScalarType();
            ScalarType expected = ScalarType.INT;
            if (right != expected)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: " + expected));
            else if (left != expected)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + left + " expected: " + expected));

            resultTypeStack.push(resultR);
        }
    }

    private void matchResultsOnRelational(String codeLine) {
        if (resultTypeStack.size() >= 2) {
            ResultType resultR = resultTypeStack.pop();
            ResultType resultL = resultTypeStack.pop();
            ScalarType left = resultL.getTypeData().getType().getScalarType();
            ScalarType right = resultR.getTypeData().getType().getScalarType();
            matchId(resultL, resultR, codeLine);
            if (right != ScalarType.INT && right != ScalarType.CHAR)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: INT or CHAR"));
            else if (left != ScalarType.INT && left != ScalarType.CHAR)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + left + " expected: INT or CHAR"));
            else if (left != right)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: " + left));

            resultTypeStack.push(resultR);
            updatedResultScalarType(ScalarType.BOOL);
        }
    }

    private void matchResultOnType(String codeLine, ScalarType matchType) {
        ResultType resultType = resultTypeStack.pop();
        ScalarType type = resultType.getTypeData().getType().getScalarType();
        if (type != matchType)
            exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Type provided: " + type + " expected: " + matchType));
        resultTypeStack.push(resultType);
    }

    private void updatedResultScalarType(ScalarType scalarType) {
        //update result to bool
        ResultType resultType = resultTypeStack.peek();
        KxiType updatedType = new KxiType(scalarType, null);
        resultType.setTypeData(new SymbolData(false, null, updatedType));
    }

    private List<ResultType> popList(int size) {
        List<ResultType> resultTypeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            resultTypeList.add(resultTypeStack.pop());
        }
        Collections.reverse(resultTypeList);
        return resultTypeList;
    }


    private ResultType pushNewResult(String id, SymbolData typeData, SymbolTable scope) {
        return resultTypeStack.push(new ResultType(id, typeData, scope));
    }

    private ResultType pushFailedResult() {
        String id = "Failed";
        SymbolData typeData = new SymbolData(false, null, new KxiType(ScalarType.UNKNOWN, null));
        return resultTypeStack.push(new ResultType(id, typeData, currentScope));
    }

    private KxiType getLiteralAsDataType(ExpressionLiteral expressionLiteral, IdentifierToken id) {
        KxiType kxiType = new KxiType(expressionLiteral.getTokenLiteral().getScalarType(), id);
        kxiType.setLineInfo(expressionLiteral.getLineInfo());
        return kxiType;
    }

    /*
    EXPRESSION LITERALS
     */
    @Override
    public void visit(ExpressionBoolLit expressionLiteral) {
        pushNewResult(null, new SymbolData(false, null, getLiteralAsDataType(expressionLiteral, null)), currentScope);
    }

    @Override
    public void visit(ExpressionCharLit expressionLiteral) {
        pushNewResult(null, new SymbolData(false, null, getLiteralAsDataType(expressionLiteral, null)), currentScope);

    }

    @Override
    public void visit(ExpressionIdLit expressionLiteral) {
        String id = expressionLiteral.getTokenLiteral().getValue();
        SymbolData typeData = scopeHandler.Identify(currentScope, id);

        //not found, check for class level
        if (typeData == null) {
            ClassScope classScope = scopeHandler.getClassScope(id);
            if (classScope != null) {
                typeData = classScope.getClassData();
                typeData.getType().setLineInfo(expressionLiteral.getLineInfo());
                pushNewResult(id, typeData, currentScope).getResultFlagList().add(ResultFlag.ClassLevel);
            } else {
                exceptionStack.push(new TypeCheckException(expressionLiteral.getLineInfo(), "Variable not declared: " + id));
                pushFailedResult();
            }
        } else {
            typeData.getType().setLineInfo(expressionLiteral.getLineInfo());
            ResultType resultType = new ResultType(id, typeData, currentScope);
            if (typeData.getModifier() != null && !typeData.isStatic()) //has to be a data member with non-static context
                resultType.getResultFlagList().add(ResultFlag.This);
            if (scopeHandler.bubbleToMethodScope(currentScope, id) != null) {
                resultType.getResultFlagList().add(ResultFlag.Method);
            }

            resultTypeStack.push(resultType);
        }

    }

    @Override
    public void visit(ExpressionIntLit expressionLiteral) {
        pushNewResult(null, new SymbolData(false, null, getLiteralAsDataType(expressionLiteral, null)), currentScope);

    }

    @Override
    public void visit(ExpressionNullLit expressionLiteral) {
        pushNewResult(null, new SymbolData(false, null, getLiteralAsDataType(expressionLiteral, null)), currentScope);

    }

    @Override
    public void visit(ExpressionStringLit expressionLiteral) {
        pushNewResult(null, new SymbolData(false, null, getLiteralAsDataType(expressionLiteral, null)), currentScope);

    }

    @Override
    public void visit(ExpressionThisLit expressionLiteral) {
        ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
        if (classScope == null) {
            exceptionStack.push(new TypeCheckException(expressionLiteral.getLineInfo(), "'this' must be used in a class"));
            pushFailedResult();
        } else {
            SymbolData typeData = classScope.getClassData();
            pushNewResult(null, typeData, currentScope).getResultFlagList().add(ResultFlag.This);
        }
    }

    @Override
    public void preVisit(KxiMethod kxiMethod) {
        if (kxiMethod.isStatic())
            staticContext = true;
    }

    @Override
    public void visit(KxiMethod kxiMethod) {
        if (kxiMethod.isStatic())
            staticContext = false;
    }

    /*
    EXPRESSIONS
     */

        /*
    EXPRESSIONS ARITHMIC ASSIGNMENT
     */

    @Override
    public void visit(KxiVariableDeclaration variableDeclaration) {
        //this handles thar var = exp case (instead of exp = exp)

        if (variableDeclaration.getInitializer() != null) {
            ResultType leftOver = resultTypeStack.pop(); // pop to preserve order
            pushNewResult(variableDeclaration.getIdToken().getValue()
                    , new SymbolData(staticContext, null, variableDeclaration.getType())
                    , currentScope);
            resultTypeStack.push(leftOver);
            matchResults(variableDeclaration.getLineInfo());

            if (!variableDeclaration.isPartOfDataMember())
                resultTypeStack.pop(); //don't keep results if not part of member

        } else if (variableDeclaration.getType().getScalarType() == ScalarType.ID) {
            ClassScope classScope = scopeHandler.getClassScope(variableDeclaration.getType().getKxiType().getIdName().getValue());
            if (classScope == null)
                exceptionStack.push(new TypeCheckException(variableDeclaration.getLineInfo(), variableDeclaration.getIdToken().getValue() + " does not refer to a valid class"));
        } else if (variableDeclaration.getType().getScalarType() == ScalarType.VOID) {
            exceptionStack.push(new TypeCheckException(variableDeclaration.getLineInfo(), variableDeclaration.getIdToken().getValue() + " can't be void"));
        }

    }

    @Override
    public void visit(KxiDivEquals expression) {
        matchResultsOnInt(expression.getLineInfo());
    }

    @Override
    public void visit(KxiEquals expression) {
        matchResults(expression.getLineInfo());
    }

    @Override
    public void visit(KxiMultEquals expression) {
        matchResultsOnInt(expression.getLineInfo());

    }

    @Override
    public void visit(KxiPlusEquals expression) {
        matchResultsOnInt(expression.getLineInfo());

    }

    @Override
    public void visit(KxiSubtractEquals expression) {
        matchResultsOnInt(expression.getLineInfo());

    }

      /*
    EXPRESSIONS ASSIGNMENT
     */

    @Override
    public void visit(KxiDiv expression) {
        matchResultsOnInt(expression.getLineInfo());
    }

    @Override
    public void visit(KxiMult expression) {
        matchResultsOnInt(expression.getLineInfo());
    }

    @Override
    public void visit(KxiPlus expression) {
        matchResultsOnInt(expression.getLineInfo());
    }

    @Override
    public void visit(KxiSubtract expression) {
        matchResultsOnInt(expression.getLineInfo());
    }

    /*
 EXPRESSIONS CONDITIONAL
  */
    @Override
    public void visit(KxiAnd expression) {
        matchResultsOnType(expression.getLineInfo(), ScalarType.BOOL);
        //updatedResultScalarType(ScalarType.BOOL);
    }

    @Override
    public void visit(KxiEqualsEquals expression) {
        matchResults(expression.getLineInfo());
        updatedResultScalarType(ScalarType.BOOL);
    }

    @Override
    public void visit(KxiGreaterEqualsThen expression) {
        matchResultsOnRelational(expression.getLineInfo());
    }

    @Override
    public void visit(KxiGreaterThen expression) {
        matchResultsOnRelational(expression.getLineInfo());
    }

    @Override
    public void visit(KxiLessEqualsThen expression) {
        matchResultsOnRelational(expression.getLineInfo());
    }

    @Override
    public void visit(KxiLessThen expression) {
        matchResultsOnRelational(expression.getLineInfo());
    }

    @Override
    public void visit(KxiNotEquals expression) {
        matchResults(expression.getLineInfo());
        updatedResultScalarType(ScalarType.BOOL);
    }

    @Override
    public void visit(KxiOr expression) {
        matchResultsOnType(expression.getLineInfo(), ScalarType.BOOL);
        //updatedResultScalarType(ScalarType.BOOL);
    }

    /*
EXPRESSIONS UNARY
*/
    @Override
    public void visit(KxiNot expression) {
        matchResultOnType(expression.getLineInfo(), ScalarType.BOOL);
    }

    @Override
    public void visit(KxiUniPlus expression) {
        matchResultOnType(expression.getLineInfo(), ScalarType.CHAR);
        updatedResultScalarType(ScalarType.INT);
    }

    @Override
    public void visit(KxiUniSubtract expression) {
        matchResultOnType(expression.getLineInfo(), ScalarType.INT);
    }

    /*
EXPRESSIONS DOT
*/
    @Override
    public void visit(KxiDotExpression expression) {
        ResultType resultType = resultTypeStack.pop();
        //check if type is ID
        if (resultType.getTypeData().getType().getScalarType() == ScalarType.ID) {
            //check for  array
            if (resultType.getTypeData().getType() instanceof KxiType) {
                //get its type
                KxiType kxiType = (KxiType) resultType.getTypeData().getType();

                //set result class scope
                ClassScope resultClassScope = scopeHandler.getClassScope(kxiType.getIdName().getValue());
                resultType.setScope(resultClassScope);

                //get class scope from current scope
                //compare to make sure we are out of scope
                ClassScope currentClassScope = scopeHandler.bubbleToClassScope(currentScope);
                if (currentClassScope == null || !currentClassScope.equals(resultClassScope))
                    resultType.getResultFlagList().add(ResultFlag.OutOfScope);

                String childID = expression.getId().getValue();

                if (resultClassScope.getMethodScopeMap().get(childID) != null) {
                    resultType.getResultFlagList().add(ResultFlag.Method);
                }

                //get dataType from result scope using childID
                SymbolData resultSymbolData = scopeHandler.Identify(resultClassScope, childID);

                if (resultSymbolData != null) {
                    resultType.setTypeData(resultSymbolData);
                    resultType.setReferenceId(childID);

                    //check for static and public modifier
                    if (resultType.containsFlag(ResultFlag.ClassLevel)) {
                        resultType.getResultFlagList().remove(ResultFlag.ClassLevel);
                        if (!resultSymbolData.isStatic())
                            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't access non-static member " + childID));
                    }

                    if (resultType.containsFlag(ResultFlag.OutOfScope))
                        if (resultSymbolData.getModifier() == null || resultSymbolData.getModifier() == Modifier.PRIVATE)
                            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't access private or local member " + childID));
                } else {
                    exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "invalid member access " + childID));

                }

            } else {
                exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "DOT expression cant access arrayType"));
            }
        } else {
            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "DOT expression must start with ID"));
        }

        //result has updated scope, refId and dataType
        resultTypeStack.push(resultType);
    }

    @Override
    public void preVisit(KxiNewExpressionArgument expression) {
        //previsit so we arguments can have the context needed
        expression.getArguments().setLineInfo(expression.getLineInfo()); //cuz
        KxiType kxiType = new KxiType(ScalarType.ID, expression.getId());
        kxiType.setLineInfo(expression.getLineInfo());
        String id = kxiType.getIdName().getValue();
        ClassScope classScope = scopeHandler.getClassScope(id);

        //childId needs to be a class
        if (classScope == null)
            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Class does not exist for new " + id));

        pushNewResult(id, new SymbolData(false, null, kxiType), classScope);

    }

    @Override
    public void visit(KxiMethodExpression expression) {
        //check if it's a method, no changes
        expression.getArguments().setLineInfo(expression.getLineInfo()); //cuz
        ResultType result = resultTypeStack.pop();

        if (!result.containsFlag(ResultFlag.Method))
            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't find method "));
        else
            result.getResultFlagList().remove(ResultFlag.Method);
//        if (result.getReferenceId() == null || scopeHandler.bubbleToMethodScope(result.getScope(), result.getReferenceId()) == null)
//            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't find method "));
        resultTypeStack.push(result);
    }

    @Override
    public void visit(KxiArguments expression) {
        List<ResultType> rightList = popList(expression.getArguments().size());
        ResultType left = resultTypeStack.pop();
        //left result has to have ref id
        if (left.getReferenceId() != null) {
            //access method from result scope
            MethodScope methodScope = scopeHandler.bubbleToMethodScope(left.getScope(), left.getReferenceId());
            if (methodScope != null) {
                //make sure number of args match number of params
                int leastSize = 0;
                int argSize = expression.getArguments().size();
                int paramSize = methodScope.getParams().size();
                if (argSize != paramSize) {
                    exceptionStack.push(new TypeCheckException(expression.getLineInfo()
                            , "Number of args and params don't match. args: " + argSize + " params: " + paramSize));
                    if (argSize < paramSize) leastSize = argSize;
                    else leastSize = paramSize;

                } else
                    leastSize = paramSize;

                //compare params and args
                for (int i = 0; i < leastSize; i++) {
                    SymbolData param = methodScope.getParams().get(i);
                    SymbolData arg = rightList.get(i).getTypeData();

                    //argument can't be class level and expressionLit. Only dot expression will work with class level
                    if (rightList.get(i).containsFlag(ResultFlag.ClassLevel))
                        exceptionStack.push(new TypeCheckException(rightList.get(i).getTypeData().getType().getLineInfo(), "Can't use class as argument"));
                    matchParamArg(param, arg, expression.getLineInfo());
                }
            }
        } else
            exceptionStack.push(new TypeCheckException(left.getTypeData().getType().getLineInfo(), "Invalid Id for method access "));
        resultTypeStack.push(left);
    }

    @Override
    public void visit(KxiNewExpressionIndex expression) {
        KxiArrayType kxiArrayType = new KxiArrayType(expression.getType().getScalarType(), expression.getType());

        //if ID needs to be class
        String id = null;
        if (expression.getType().getScalarType() == ScalarType.ID) {
            id = expression.getType().getKxiType().getIdName().getValue();
            ClassScope classScope = scopeHandler.getClassScope(id);
            if (classScope == null) {
                exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Class does not exist for new " + id));
            }
        }
        pushNewResult(id, new SymbolData(false, null, kxiArrayType), currentScope);
    }

    @Override
    public void visit(KxiIndex expression) {
        matchResultOnType(expression.getIndex().getLineInfo(), ScalarType.INT);
        resultTypeStack.pop();
    }

    @Override
    public void visit(KxiExpressionIndex expression) {
        //has to have reference
        ResultType resultType = resultTypeStack.pop();
        if (resultType.getReferenceId() != null) {
            //has to be an array
            if (resultType.getTypeData().getType() instanceof KxiArrayType) {
                KxiAbstractType kxiAbstractType = ((KxiArrayType) resultType.getTypeData().getType()).getInsideType();
                resultType.setTypeData(new SymbolData(false, null, kxiAbstractType));
            } else
                exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "index expression must have an array " + resultType.getReferenceId()));
        } else
            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "invalid array access member"));

        //changed typeData, has one array unwrapped
        resultTypeStack.push(resultType);
    }

    @Override
    public void visit(KxiExpressionStatement statement) {
        ResultType resultType = resultTypeStack.pop();
        if (resultType.containsFlag(ResultFlag.Method))
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "invalid method in expression"));
    }

    @Override
    public void visit(KxiIfStatement statement) {
        if (resultTypeStack.pop().getScalarType() != ScalarType.BOOL)
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "If statement requires bool expression"));

    }

    @Override
    public void visit(KxiWhileStatement statement) {
        if (resultTypeStack.pop().getScalarType() != ScalarType.BOOL)
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "While statement requires bool expression"));

    }

    @Override
    public void visit(KxiPostForExpression node) {
        if (resultTypeStack.pop().getScalarType() != ScalarType.INT)
            exceptionStack.push(new TypeCheckException(node.getLineInfo(), "For statement PostExpression requires INT"));
    }

    @Override
    public void visit(KxiPreForExpression node) {
        if (resultTypeStack.pop().getScalarType() != ScalarType.INT)
            exceptionStack.push(new TypeCheckException(node.getLineInfo(), "For statement PreExpression requires INT"));
    }


    @Override
    public void visit(KxiForStatement statement) {
        if (resultTypeStack.pop().getScalarType() != ScalarType.BOOL)
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "For statement requires bool expression"));
    }

    @Override
    public void visit(KxiReturnStatement statement) {
        //has to be in a method scope
        MethodScope methodScope = scopeHandler.bubbleToNearestMethodScope(currentScope);
        if (methodScope != null) {
            //if method return type is not void and return does not have expression
            ScalarType methodReturnType = methodScope.getReturnType().getScalarType();
            if (methodReturnType != ScalarType.VOID && statement.getExpression() != null) {
                //if return had expression then result will be on stack
                //use that to match on method type
                SymbolData symbolData = methodScope.getReturnType();

                //fix the order
                ResultType resultType = resultTypeStack.pop();
                resultTypeStack.push(new ResultType(methodScope.getBlockScope().getMethodId(), symbolData, currentScope));
                resultTypeStack.push(resultType);

                matchResults(statement.getLineInfo());
                //method is void, but return is not
            } else if (methodReturnType == ScalarType.VOID && statement.getExpression() != null) {
                exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "Invalid Return Type. Expecting VOID"));
                resultTypeStack.pop();

            } else if (methodReturnType != ScalarType.VOID && statement.getExpression() == null) {
                exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "Invalid Return Type. Expecting NON-VOID"));
            }
        } else
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "Return statement must be used in a method context"));
    }

    @Override
    public void visit(KxiCinStatement statement) {
        ResultType result = resultTypeStack.pop();
        //check if int, char, or string
        ScalarType resultType = result.getScalarType();
        statement.setScalarType(resultType);
        if (resultType != ScalarType.CHAR && resultType != ScalarType.INT)
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "CIN does not support type: " + resultType));

    }

    @Override
    public void visit(KxiCoutStatement statement) {
        ResultType result = resultTypeStack.pop();
        //check if int, char, or string
        ScalarType resultType = result.getScalarType();
        if (resultType != ScalarType.CHAR && resultType != ScalarType.INT && resultType != ScalarType.STRING && resultType != ScalarType.NULL)
            exceptionStack.push(new TypeCheckException(statement.getLineInfo(), "COUT does not support type: " + resultType));
        //useful for desugar
        statement.setScalarType(resultType);

    }

    @Override
    public void visit(KxiSwitchStatement switchStatement) {
        ResultType resultType = resultTypeStack.pop();
        if (resultType.getScalarType() != ScalarType.INT && resultType.getScalarType() != ScalarType.CHAR)
            exceptionStack.push(new TypeCheckException(switchStatement.getLineInfo(), "Switch does not support type: " + resultType.getScalarType()));

    }

    @Override
    public void visit(KxiCaseInt statement) {
        matchResultOnType(statement.getLineInfo(), ScalarType.INT);
    }

    @Override
    public void visit(KxiCaseChar statement) {
        matchResultOnType(statement.getLineInfo(), ScalarType.CHAR);
    }

    @Override
    public void visit(KxiParameter parameter) {
        if (parameter.getType().getKxiType().getScalarType() == ScalarType.ID) {
            String id = parameter.getType().getKxiType().getIdName().getValue();
            if (scopeHandler.getClassScope(id) == null)
                exceptionStack.push(new TypeCheckException(parameter.getLineInfo(), "Invalid Parameter: (missing class) " + parameter.getIdToken().getValue()));


        }
    }

    @Override
    public void visit(KxiDataMember dataMember) {
        //make sure we aren't writing to non-static members
        if (dataMember.getVariableDeclaration().getInitializer() != null) {
            ResultType resultType = resultTypeStack.pop();
            if (dataMember.isStatic()) {
                if (resultType.containsFlag(ResultFlag.This)) {
                    exceptionStack.push(new TypeCheckException(dataMember.getLineInfo()
                            , "Non-static field '"
                            + dataMember.getVariableDeclaration().getIdToken().getValue()
                            + "' cannot be referenced from a static context"));
                }
            }
        }
    }
}

