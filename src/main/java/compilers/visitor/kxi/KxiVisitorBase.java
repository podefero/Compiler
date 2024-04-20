package compilers.visitor.kxi;

import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.*;
import compilers.ast.kxi_nodes.*;
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
import compilers.ast.kxi_nodes.helper.KxiFordSemi;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiElseStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.*;
import compilers.exceptions.CompilerException;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.Getter;

import java.util.Stack;

@Getter
public  class KxiVisitorBase implements VisitKxi {

    protected Stack<CompilerException> exceptionStack;
    protected SymbolTable currentScope;

    public KxiVisitorBase() {
        exceptionStack = new Stack<>();
    }

    public boolean hasErrors() {
        return !exceptionStack.empty();
    }

    public void dumpErrorStack() {
        // StringBuilder body = new StringBuilder();
        while (hasErrors()) {
            //body.append(exceptionStack.pop().getMessage() + '\n');
            System.out.println(exceptionStack.pop().getMessage());
        }
    }

    public void visitAssignment(AbstractBinaryAssignmentExpression assignmentExpression) {
    }

    public void visitStatement(AbstractKxiStatement abstractKxiStatement) {
    }


    @Override
    public void visit(KxiMain kxiMain) {

    }


    @Override
    public void visit(KxiClass kxiClass) {
        currentScope = kxiClass.getScope().getParent();
    }

    @Override
    public void visit(KxiMethod kxiMethod) {

    }

    @Override
    public void visit(KxiDataMember kxiDataMember) {

    }

    @Override
    public void visit(KxiConstructor kxiConstructor) {

    }

    @Override
    public void visit(KxiVariableDeclaration kxiVariableDeclaration) {

    }



    @Override
    public void visit(KxiWhileStatement kxiWhileStatement) {

    }

    @Override
    public void visit(KxiForStatement kxiForStatement) {

    }

    @Override
    public void visit(KxiReturnStatement kxiReturnStatement) {

    }

    @Override
    public void visit(KxiCoutStatement kxiCoutStatement) {

    }

    @Override
    public void visit(KxiCinStatement kxiCinStatement) {

    }


    @Override
    public void visit(KxiBreakStatement kxiBreakStatement) {

    }

    @Override
    public void visit(KxiExpressionStatement kxiExpressionStatement) {

    }

    @Override
    public void visit(KxiBlockStatement kxiBlockStatement) {

    }

    @Override
    public void visit(KxiVariableDeclarationStatement kxiVariableDeclarationStatement) {

    }

    @Override
    public void visit(KxiBlock kxiBlock) {
        currentScope = kxiBlock.getScope().getParent();
    }


    @Override
    public void visit(KxiCaseInt kxiCaseInt) {

    }

    @Override
    public void visit(KxiCaseChar kxiCaseChar) {

    }

    @Override
    public void visit(KxiEquals kxiEquals) {

    }

    @Override
    public void visit(KxiPlusEquals kxiPlusEquals) {

    }

    @Override
    public void visit(KxiSubtractEquals kxiSubtractEquals) {

    }

    @Override
    public void visit(KxiMultEquals kxiMultEquals) {

    }

    @Override
    public void visit(KxiDivEquals kxiDivEquals) {

    }

    @Override
    public void visit(KxiMult kxiMult) {

    }

    @Override
    public void visit(KxiDiv kxiDiv) {

    }

    @Override
    public void visit(KxiPlus kxiPlus) {

    }

    @Override
    public void visit(KxiSubtract kxiSubtract) {

    }

    @Override
    public void visit(KxiEqualsEquals kxiEqualsEquals) {

    }

    @Override
    public void visit(KxiNotEquals kxiNotEquals) {

    }

    @Override
    public void visit(KxiLessThen kxiLessThen) {

    }

    @Override
    public void visit(KxiGreaterThen kxiGreaterThen) {

    }

    @Override
    public void visit(KxiLessEqualsThen kxiLessEqualsThen) {

    }

    @Override
    public void visit(KxiGreaterEqualsThen kxiGreaterEqualsThen) {

    }

    @Override
    public void visit(KxiAnd kxiAnd) {

    }

    @Override
    public void visit(KxiOr kxiOr) {

    }

    @Override
    public void visit(KxiNot kxiNot) {

    }

    @Override
    public void visit(KxiUniPlus kxiUniPlus) {

    }

    @Override
    public void visit(KxiUniSubtract kxiUniSubtract) {

    }

    @Override
    public void visit(KxiParenthExpression kxiParenthExpression) {

    }

    @Override
    public void visit(IntLitToken intLitToken) {

    }

    @Override
    public void visit(CharLitToken charLitToken) {

    }

    @Override
    public void visit(StringLitToken stringLitToken) {

    }

    @Override
    public void visit(BoolToken boolToken) {

    }

    @Override
    public void visit(NullToken nullToken) {

    }

    @Override
    public void visit(ThisToken thisToken) {

    }

    @Override
    public void visit(IdentifierToken identifierToken) {

    }

    @Override
    public void visit(KxiNewExpressionIndex kxiNewExpressionIndex) {

    }

    @Override
    public void visit(KxiNewExpressionArgument kxiNewExpressionArgument) {

    }

    @Override
    public void visit(KxiDotExpression kxiDotExpression) {

    }

    @Override
    public void visit(KxiExpressionIndex kxiExpressionIndex) {

    }

    @Override
    public void visit(KxiMethodExpression kxiMethodExpression) {

    }

    @Override
    public void preVisit(KxiMain kxiMain) {

    }

    @Override
    public void preVisit(KxiClass kxiClass) {
        currentScope = kxiClass.getScope();
    }

    @Override
    public void preVisit(KxiMethod kxiMethod) {

    }

    @Override
    public void preVisit(KxiDataMember kxiDataMember) {

    }

    @Override
    public void preVisit(KxiConstructor kxiConstructor) {

    }

    @Override
    public void preVisit(KxiVariableDeclaration kxiVariableDeclaration) {

    }

    @Override
    public void preVisit(KxiIfStatement kxiIfStatement) {

    }

    @Override
    public void preVisit(KxiWhileStatement kxiWhileStatement) {

    }

    @Override
    public void preVisit(KxiForStatement kxiForStatement) {

    }

    @Override
    public void preVisit(KxiReturnStatement kxiReturnStatement) {

    }

    @Override
    public void preVisit(KxiCoutStatement kxiCoutStatement) {

    }

    @Override
    public void preVisit(KxiCinStatement kxiCinStatement) {

    }


    @Override
    public void preVisit(KxiBreakStatement kxiBreakStatement) {

    }

    @Override
    public void preVisit(KxiExpressionStatement kxiExpressionStatement) {

    }

    @Override
    public void preVisit(KxiBlockStatement kxiBlockStatement) {

    }

    @Override
    public void preVisit(KxiVariableDeclarationStatement kxiVariableDeclarationStatement) {

    }

    @Override
    public void preVisit(KxiBlock kxiBlock) {
        currentScope = kxiBlock.getScope();

    }


    @Override
    public void preVisit(KxiCaseInt kxiCaseInt) {

    }

    @Override
    public void preVisit(KxiCaseChar kxiCaseChar) {

    }

    @Override
    public void preVisit(KxiEquals kxiEquals) {

    }

    @Override
    public void preVisit(KxiPlusEquals kxiPlusEquals) {

    }

    @Override
    public void preVisit(KxiSubtractEquals kxiSubtractEquals) {

    }

    @Override
    public void preVisit(KxiMultEquals kxiMultEquals) {

    }

    @Override
    public void preVisit(KxiDivEquals kxiDivEquals) {

    }

    @Override
    public void preVisit(KxiMult kxiMult) {

    }

    @Override
    public void preVisit(KxiDiv kxiDiv) {

    }

    @Override
    public void preVisit(KxiPlus kxiPlus) {

    }

    @Override
    public void preVisit(KxiSubtract kxiSubtract) {

    }

    @Override
    public void preVisit(KxiEqualsEquals kxiEqualsEquals) {

    }

    @Override
    public void preVisit(KxiNotEquals kxiNotEquals) {

    }

    @Override
    public void preVisit(KxiLessThen kxiLessThen) {

    }

    @Override
    public void preVisit(KxiGreaterThen kxiGreaterThen) {

    }

    @Override
    public void preVisit(KxiLessEqualsThen kxiLessEqualsThen) {

    }

    @Override
    public void preVisit(KxiGreaterEqualsThen kxiGreaterEqualsThen) {

    }

    @Override
    public void preVisit(KxiAnd kxiAnd) {

    }

    @Override
    public void preVisit(KxiOr kxiOr) {

    }

    @Override
    public void preVisit(KxiNot kxiNot) {

    }

    @Override
    public void preVisit(KxiUniPlus kxiUniPlus) {

    }

    @Override
    public void preVisit(KxiUniSubtract kxiUniSubtract) {

    }

    @Override
    public void preVisit(KxiParenthExpression kxiParenthExpression) {

    }

    @Override
    public void preVisit(IntLitToken intLitToken) {

    }

    @Override
    public void preVisit(CharLitToken charLitToken) {

    }

    @Override
    public void preVisit(StringLitToken stringLitToken) {

    }

    @Override
    public void preVisit(BoolToken boolToken) {

    }

    @Override
    public void preVisit(NullToken nullToken) {

    }

    @Override
    public void preVisit(ThisToken thisToken) {

    }

    @Override
    public void preVisit(IdentifierToken identifierToken) {

    }

    @Override
    public void preVisit(KxiNewExpressionIndex kxiNewExpressionIndex) {

    }

    @Override
    public void preVisit(KxiNewExpressionArgument kxiNewExpressionArgument) {

    }

    @Override
    public void preVisit(KxiDotExpression kxiDotExpression) {

    }

    @Override
    public void preVisit(KxiExpressionIndex kxiExpressionIndex) {

    }

    @Override
    public void preVisit(KxiMethodExpression kxiMethodExpression) {

    }

    @Override
    public void preVisit(KxiSwitchStatement kxiSwitchStatementChar) {

    }

    @Override
    public void visit(KxiSwitchStatement kxiSwitchStatementChar) {

    }

    @Override
    public void preVisit(ExpressionBoolLit expressionLit) {

    }

    @Override
    public void visit(ExpressionBoolLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionCharLit expressionLit) {

    }

    @Override
    public void visit(ExpressionCharLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionIntLit expressionLit) {

    }

    @Override
    public void visit(ExpressionIntLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionIdLit expressionLit) {

    }

    @Override
    public void visit(ExpressionIdLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionNullLit expressionLit) {

    }

    @Override
    public void visit(ExpressionNullLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionStringLit expressionLit) {

    }

    @Override
    public void visit(ExpressionStringLit expressionLit) {

    }

    @Override
    public void preVisit(ExpressionThisLit expressionLit) {

    }

    @Override
    public void visit(ExpressionThisLit expressionLit) {

    }

    @Override
    public void preVisit(KxiArguments kxiArguments) {

    }

    @Override
    public void visit(KxiArguments kxiArguments) {

    }

    @Override
    public void preVisit(KxiIndex kxiIndex) {

    }

    @Override
    public void visit(KxiIndex kxiIndex) {

    }

    @Override
    public void preVisit(KxiParameter node) {

    }

    @Override
    public void visit(KxiParameter node) {

    }

    @Override
    public void visit(KxiFordSemi kxiFordSemi) {

    }

    @Override
    public void preVisit(KxiCaseBlock kxiCaseBlock) {
        currentScope = kxiCaseBlock.getScope();
    }

    @Override
    public void visit(KxiCaseBlock kxiCaseBlock) {
        currentScope = kxiCaseBlock.getScope().getParent();
    }

    public void visit(InterGlobal interGlobal) {
    }

    public void preVisit(InterGlobal interGlobal) {
    }

    public void visit(InterVariable interVariable) {
    }

    public void visit(InterFunctionNode interFunctionNode) {
    }

    public void preVisit(InterFunctionNode interFunctionNode) {
    }

    public void visit(InterBinaryPlus interBinaryPlus) {
    }

    public void visit(LeftVariableStack leftVariableStack) {
    }

    public void visit(LeftOperandLit leftOperandLit) {
    }

    public void visit(RightVariableStack rightVariableStack) {
    }

    public void visit(RightOperandLit rightOperandLit) {
    }

    public void preVisit(InterVariable interVariable) {
    }

    public void visit(InterFunctionalCall interFunctionalCall) {
    }

    public void visit(AssemblyMain assemblyMain) {
    }

    public void visit(AssemblyCode assemblyCode) {
    }

    public void visit(AssemblyComment assemblyComment) {
    }

    public void visit(InterAssignment interAssignment) {
    }

    public void visit(OperandLabelWrapper operandLabelWrapper) {
    }

    public void visit(InterActivationRecord interActivationRecord) {
    }

    public void visit(InterReturn interReturn) {
    }

    public void visit(InterCoutStatement interCoutStatement) {
    }

    public void visit(InterExpressionStatement interExpressionStatement) {
    }

    public void visit(InterBinarySubtract interBinarySubtract) {
    }

    public void visit(InterBinaryDivide interBinaryDivide) {
    }

    public void visit(InterBinaryMult interBinaryMult) {
    }

    public void visit(InterBinaryAssignmentStatement interBinaryAssignmentStatement) {
    }

    public void visit(InterLogicalLessThen interLogicalLessThen) {
    }

    public void visit(InterLogicalGreaterThen interLogicalGreaterThen) {
    }

    public void visit(InterLogicalEqualsEquals interLogicalEqualsEquals) {
    }

    public void visit(InterLogicalLessEqualThen interLogicalLessEqualThen) {
    }

    public void visit(InterLogicalGreaterEqualThen interLogicalGreaterEqualThen) {
    }

    public void visit(InterLogicalOr interLogicalOr) {
    }

    public void visit(InterLogicalAnd interLogicalAnd) {
    }

    public void visit(InterLogicalNot interLogicalNot) {
    }

    public void visit(InterLogicalNotEquals interLogicalNotEquals) {
    }

    public void visit(RightPtr rightPtr) {
    }

    public void visit(LeftPtr leftPtr) {
    }

    public void preVisit(InterGlobalVariable interGlobalVariable) {
    }

    public void visit(InterGlobalVariable interGlobalVariable) {
    }

    public void visit(InterPtrAssignment interPtrAssignment) {
    }

    public void visit(AssemblyNewLine assemblyNewLine) {
    }

    public void visit(InterDirAssignment interDirAssignment) {
    }

    public void visit(AssemblyDirective AssemblyDirective) {
    }


    public void visit(InterUnarySubOperator interUnarySubOperator) {
    }

    public void visit(InterIfStatement interIfStatement) {
    }

    public void preVisit(InterIfStatement interIfStatement) {
    }

    public void visit(InterDerefStatement interDerefStatement) {
    }

    public void preVisit(InterElseStatement interElseStatement) {
    }

    public void visit(InterElseStatement interElseStatement) {
    }

    public void preVisit(KxiElseStatement kxiElseStatement) {
    }

    public void visit(KxiIfStatement kxiIfStatement){}

    public void visit(KxiElseStatement kxiElseStatement) {
    }

    public void visit(InterWhileStatement interWhileStatement) {
    }

    public void visit(InterWhileLoop interWhileLoop) {
    }

    public void preVisit(InterWhileStatement interWhileStatement) {
    }

    public void visit(InterBreak interBreak) {
    }

    public void preVisit(KxiForWhileExpression kxiForWhileExpression) {
    }

    public void visit(KxiForWhileExpression kxiForWhileExpression) {
    }

    public void visit(InterCinStatement interCinStatement) {
    }

    public void preVisit(InterCinStatement interCinStatement) {
    }

    public void preVisit(InterSwitch interSwitch) {
    }

    public void visit(InterSwitch interSwitch) {
    }

    public void visit(InterCase interCase) {
    }

    public void preVisit(InterCase interCase) {
    }

    public void visit(EndPog endPog) {
    }

    public void visit(OperandReturn operandReturn) {
    }
}
