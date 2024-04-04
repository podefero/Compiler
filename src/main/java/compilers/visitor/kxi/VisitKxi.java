package compilers.visitor.kxi;

import compilers.ast.kxi_nodes.KxiCaseChar;
import compilers.ast.kxi_nodes.KxiCaseInt;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
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
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlockChar;
import compilers.ast.kxi_nodes.scope.KxiCaseBlockInt;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.*;

public interface VisitKxi {
    void visit(KxiMain kxiMain);
    void visit(KxiClass kxiClass);
    void visit(KxiMethod kxiMethod);
    void visit(KxiDataMember kxiDataMember);
    void visit(KxiConstructor kxiConstructor);
    void visit(KxiVariableDeclaration kxiVariableDeclaration);

    void visit(KxiIfStatement kxiIfStatement);
    void visit(KxiWhileStatement kxiWhileStatement);
    void visit(KxiForStatement kxiForStatement);
    void visit(KxiReturnStatement kxiReturnStatement);
    void visit(KxiCoutStatement kxiCoutStatement);
    void visit(KxiCinStatement kxiCinStatement);
    void visit(KxiSwitchStatementInt kxiSwitchStatementInt);
    void visit(KxiBreakStatement kxiBreakStatement);
    void visit(KxiExpressionStatement kxiExpressionStatement);
    void visit(KxiBlockStatement kxiBlockStatement);
    void visit(KxiVariableDeclarationStatement kxiVariableDeclarationStatement);

    void visit(KxiBlock kxiBlock);
    void visit(KxiCaseBlockInt kxiCaseBlockInt);
    void visit(KxiCaseInt kxiCaseInt);
    void visit(KxiCaseChar kxiCaseChar);


    void visit(KxiEquals kxiEquals);
    void visit(KxiPlusEquals kxiPlusEquals);
    void visit(KxiSubtractEquals kxiSubtractEquals);
    void visit(KxiMultEquals kxiMultEquals);
    void visit(KxiDivEquals kxiDivEquals);
    void visit(KxiMult kxiMult);
    void visit(KxiDiv kxiDiv);
    void visit(KxiPlus kxiPlus);
    void visit(KxiSubtract kxiSubtract);
    void visit(KxiEqualsEquals kxiEqualsEquals);
    void visit(KxiNotEquals kxiNotEquals);
    void visit(KxiLessThen kxiLessThen);
    void visit(KxiGreaterThen kxiGreaterThen);
    void visit(KxiLessEqualsThen kxiLessEqualsThen);
    void visit(KxiGreaterEqualsThen kxiGreaterEqualsThen);
    void visit(KxiAnd kxiAnd);
    void visit(KxiOr kxiOr);
    void visit(KxiNot kxiNot);
    void visit(KxiUniPlus kxiUniPlus);
    void visit(KxiUniSubtract kxiUniSubtract);
    void visit(KxiParenthExpression kxiParenthExpression);
    void visit(IntLitToken intLitToken);
    void visit(CharLitToken charLitToken);
    void visit(StringLitToken stringLitToken);
    void visit(BoolToken boolToken);
    void visit(NullToken nullToken);
    void visit(ThisToken thisToken);
    void visit(IdentifierToken identifierToken);
    void visit(KxiNewExpressionIndex kxiNewExpressionIndex);
    void visit(KxiNewExpressionArgument kxiNewExpressionArgument);
    void visit(KxiDotExpression kxiDotExpression);
    void visit(KxiExpressionIndex kxiExpressionIndex);
    void visit(KxiExpressionArguments kxiExpressionArguments);


    //Previsit

    void preVisit(KxiMain kxiMain);
    void preVisit(KxiClass kxiClass);
    void preVisit(KxiMethod kxiMethod);
    void preVisit(KxiDataMember kxiDataMember);
    void preVisit(KxiConstructor kxiConstructor);
    void preVisit(KxiVariableDeclaration kxiVariableDeclaration);
    void preVisit(KxiIfStatement kxiIfStatement);
    void preVisit(KxiWhileStatement kxiWhileStatement);
    void preVisit(KxiForStatement kxiForStatement);
    void preVisit(KxiReturnStatement kxiReturnStatement);
    void preVisit(KxiCoutStatement kxiCoutStatement);
    void preVisit(KxiCinStatement kxiCinStatement);
    void preVisit(KxiSwitchStatementInt kxiSwitchStatementInt);
    void preVisit(KxiBreakStatement kxiBreakStatement);
    void preVisit(KxiExpressionStatement kxiExpressionStatement);
    void preVisit(KxiBlockStatement kxiBlockStatement);
    void preVisit(KxiVariableDeclarationStatement kxiVariableDeclarationStatement);
    void preVisit(KxiBlock kxiBlock);
    void preVisit(KxiCaseBlockInt kxiCaseBlockInt);
    void preVisit(KxiCaseInt kxiCaseInt);
    void preVisit(KxiCaseChar kxiCaseChar);
    void preVisit(KxiEquals kxiEquals);
    void preVisit(KxiPlusEquals kxiPlusEquals);
    void preVisit(KxiSubtractEquals kxiSubtractEquals);
    void preVisit(KxiMultEquals kxiMultEquals);
    void preVisit(KxiDivEquals kxiDivEquals);
    void preVisit(KxiMult kxiMult);
    void preVisit(KxiDiv kxiDiv);
    void preVisit(KxiPlus kxiPlus);
    void preVisit(KxiSubtract kxiSubtract);
    void preVisit(KxiEqualsEquals kxiEqualsEquals);
    void preVisit(KxiNotEquals kxiNotEquals);
    void preVisit(KxiLessThen kxiLessThen);
    void preVisit(KxiGreaterThen kxiGreaterThen);
    void preVisit(KxiLessEqualsThen kxiLessEqualsThen);
    void preVisit(KxiGreaterEqualsThen kxiGreaterEqualsThen);
    void preVisit(KxiAnd kxiAnd);
    void preVisit(KxiOr kxiOr);
    void preVisit(KxiNot kxiNot);
    void preVisit(KxiUniPlus kxiUniPlus);
    void preVisit(KxiUniSubtract kxiUniSubtract);
    void preVisit(KxiParenthExpression kxiParenthExpression);
    void preVisit(IntLitToken intLitToken);
    void preVisit(CharLitToken charLitToken);
    void preVisit(StringLitToken stringLitToken);
    void preVisit(BoolToken boolToken);
    void preVisit(NullToken nullToken);
    void preVisit(ThisToken thisToken);
    void preVisit(IdentifierToken identifierToken);
    void preVisit(KxiNewExpressionIndex kxiNewExpressionIndex);
    void preVisit(KxiNewExpressionArgument kxiNewExpressionArgument);
    void preVisit(KxiDotExpression kxiDotExpression);
    void preVisit(KxiExpressionIndex kxiExpressionIndex);
    void preVisit(KxiExpressionArguments kxiExpressionArguments);

    void preVisit(KxiCaseBlockChar kxiCaseBlockChar);

    // The forgotten Ones

    void visit(KxiCaseBlockChar kxiCaseBlockChar);

    void preVisit(KxiSwitchStatementChar kxiSwitchStatementChar);
    void visit(KxiSwitchStatementChar kxiSwitchStatementChar);

    void preVisit(ExpressionBoolLit expressionLit);
    void visit(ExpressionBoolLit expressionLit);

    void preVisit(ExpressionCharLit expressionLit);
    void visit(ExpressionCharLit expressionLit);

    void preVisit(ExpressionIntLit expressionLit);
    void visit(ExpressionIntLit expressionLit);

    void preVisit(ExpressionIdLit expressionLit);
    void visit(ExpressionIdLit expressionLit);

    void preVisit(ExpressionNullLit expressionLit);
    void visit(ExpressionNullLit expressionLit);

    void preVisit(ExpressionStringLit expressionLit);
    void visit(ExpressionStringLit expressionLit);

    void preVisit(ExpressionThisLit expressionLit);
    void visit(ExpressionThisLit expressionLit);

}
