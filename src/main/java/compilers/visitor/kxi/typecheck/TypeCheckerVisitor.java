package compilers.visitor.kxi.typecheck;

import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
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


    private void matchResults(String codeLine) {
        if (resultTypeStack.size() >= 2) {
            ResultType resultR = resultTypeStack.pop();
            ResultType resultL = resultTypeStack.pop();

            int arrayDepthL = resultL.getTypeData().getType().getArrayDepth();
            int arrayDepthR = resultR.getTypeData().getType().getArrayDepth();

            ScalarType left = resultL.getTypeData().getType().getScalarType();
            ScalarType right = resultR.getTypeData().getType().getScalarType();

            if (left != right)
                exceptionStack.push(new TypeCheckException(codeLine, "provided: " + right + " expected: " + left));
            else if (arrayDepthL != arrayDepthR)
                exceptionStack.push(new TypeCheckException(codeLine, "providedDimension: " + arrayDepthR + " expectedDimension: " + arrayDepthL));

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
                exceptionStack.push(new TypeCheckException(codeLine, "provided: " + right + " expected: " + expected));
            else if (left != expected)
                exceptionStack.push(new TypeCheckException(codeLine, "provided: " + left + " expected: " + expected));

            resultTypeStack.push(resultR);
        }
    }

    private ResultType pushNewResult(String id, SymbolData typeData, SymbolTable scope) {
        return resultTypeStack.push(new ResultType(id, typeData, scope));
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
        if (classScope == null)
            exceptionStack.push(new TypeCheckException(expressionLiteral.getLineInfo(), "'this' must be used in a class"));
        else {
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
    EXPRESSIONS ASSIGNMENT
     */

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
}

