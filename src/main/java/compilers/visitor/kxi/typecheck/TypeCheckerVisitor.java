package compilers.visitor.kxi.typecheck;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiNewExpressionArgument;
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
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.exceptions.TypeCheckException;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.result.ResultFlag;
import compilers.visitor.kxi.result.ResultType;
import compilers.visitor.kxi.symboltable.ClassScope;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolData;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.AllArgsConstructor;

import java.util.Stack;


@AllArgsConstructor
public class TypeCheckerVisitor extends KxiVisitorBase {
    private ScopeHandler scopeHandler;
    private Stack<ResultType> resultTypeStack;
    private SymbolTable currentScope;


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

            if (left != right)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched Types provided: " + right + " expected: " + left));
            else if (arrayDepthL != arrayDepthR)
                exceptionStack.push(new TypeCheckException(codeLine, "Mismatched ArrayDim providedDimension: " + arrayDepthR + " expectedDimension: " + arrayDepthL));

            resultTypeStack.push(resultR);
        }
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
        KxiType updatedType = new KxiType(ScalarType.BOOL, null);
        resultType.getTypeData().setType(updatedType);
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
        return new KxiType(expressionLiteral.getTokenLiteral().getScalarType(), id);
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
                pushNewResult(id, typeData, currentScope).getResultFlagList().add(ResultFlag.ClassLevel);
            } else {
                exceptionStack.push(new TypeCheckException(expressionLiteral.getLineInfo(), "ID not found: " + id));
                pushFailedResult();
            }
        } else {
            pushNewResult(id, typeData, currentScope);
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
            pushNewResult(null, typeData, currentScope);
        }
    }

    /*
    SCOPE TRAVERSAL
     */

    @Override
    public void preVisit(KxiClass scopeNode) {
        currentScope = scopeNode.getScope();
    }

    @Override
    public void preVisit(KxiBlock scopeNode) {
        currentScope = scopeNode.getScope();
    }

      /*
    EXPRESSIONS
     */

        /*
    EXPRESSIONS ARITHMIC ASSIGNMENT
     */

    @Override
    public void visit(KxiVariableDeclaration expression) {
        //this handles thar var = exp case (instead of exp = exp)
        if (expression.getInitializer() != null) {
            ResultType leftOver = resultTypeStack.pop(); // pop to preserve order
            pushNewResult(expression.getId().getValue(), new SymbolData(false, null, expression.getType()), currentScope);
            resultTypeStack.push(leftOver);
            matchResults(expression.getLineInfo());
        } else if (expression.getType().getScalarType() == ScalarType.ID) {
            ClassScope classScope = scopeHandler.getClassScope(expression.getType().getKxiType().getIdName().getValue());
            if (classScope == null)
                exceptionStack.push(new TypeCheckException(expression.getLineInfo(), expression.getId().getValue() + " does not refer to a valid class"));
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
        matchResultsOnRelational(expression.getLineInfo());
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
        matchResultsOnRelational(expression.getLineInfo());
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

                //get dataType from result scope using childID
                String childID = expression.getId().getValue();
                SymbolData resultSymbolData = scopeHandler.Identify(resultClassScope, childID);
                resultType.setTypeData(resultSymbolData);

                //check for static and public modifier
                if (resultType.containsFlag(ResultFlag.ClassLevel)) {
                    if (!resultSymbolData.isStatic())
                        exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't access non-static member " + childID));
                } else if (resultSymbolData.isStatic())
                    exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Instance object can't access static member " + childID));


                if (resultType.containsFlag(ResultFlag.OutOfScope))
                    if (resultSymbolData.getModifier() == null || resultSymbolData.getModifier() == Modifier.PRIVATE)
                        exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "Can't access private or local member " + childID));


            } else {
                exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "DOT expression cant access arrayType"));
            }
        } else {
            exceptionStack.push(new TypeCheckException(expression.getLineInfo(), "DOT expression must start with ID"));
        }

        //result has updated scope and dataType
        resultTypeStack.push(resultType);
    }

    @Override
    public void preVisit(KxiNewExpressionArgument expression) {
        pushNewResult(null, new SymbolData(false, null, new KxiType(ScalarType.ID, expression.getId())), currentScope);
    }
}

