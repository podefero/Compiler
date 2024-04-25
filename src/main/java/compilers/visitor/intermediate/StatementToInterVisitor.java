//package compilers.visitor.intermediate;
//
//import compilers.ast.GenericListNode;
//import compilers.ast.intermediate.InterArgs;
//import compilers.ast.intermediate.InterId;
//import compilers.ast.intermediate.InterOperand.LeftVariableStack;
//import compilers.ast.intermediate.InterValue;
//import compilers.ast.intermediate.expression.InterExpression;
//import compilers.ast.intermediate.expression.InterVariable;
//import compilers.ast.intermediate.expression.operation.InterAssignment;
//import compilers.ast.intermediate.expression.operation.InterOperation;
//import compilers.ast.intermediate.statements.*;
//import compilers.ast.kxi_nodes.KxiMain;
//import compilers.ast.kxi_nodes.KxiVariableDeclaration;
//import compilers.ast.kxi_nodes.class_members.KxiMethod;
//import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
//import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
//import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
//import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
//import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
//import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
//import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
//import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
//import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
//import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
//import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
//import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
//import compilers.ast.kxi_nodes.scope.KxiBlock;
//import compilers.ast.kxi_nodes.statements.*;
//import compilers.ast.kxi_nodes.statements.conditional.KxiElseStatement;
//import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
//import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
//import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
//import compilers.util.HashString;
//import compilers.visitor.kxi.KxiVisitorBase;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Stack;
//
//public class StatementToInterVisitor extends KxiVisitorBase {
//    Stack<InterStatement> interStatementStack;
//
//    public StatementToInterVisitor() {
//        interStatementStack = new Stack<>();
//    }
//
//    private void pushInterStatement(InterStatement interStatement) {
//        interStatementStack.push(interStatement);
//    }
//
//    private List<InterStatement> getStatements(int size) {
//        List<InterStatement> interStatements = new ArrayList<>();
//        while (size > 0) {
//            interStatements.add(interStatementStack.pop());
//            size--;
//        }
//        Collections.reverse(interStatements);
//        return interStatements;
//    }
//
//    private InterStatement pop(String error) {
//        if (interStatementStack.empty()) {
//            System.out.println("Empty interstatement stack Line: " + error);
//            return null;
//        }
//        return interStatementStack.pop();
//    }
//
//    private List<InterStatement> dumpStatements() {
//        List<InterStatement> interStatements = new ArrayList<>();
//        while (!interStatementStack.empty()) {
//            interStatements.add(interStatementStack.pop());
//        }
//        return interStatements;
//    }
//
//    @Override
//    public void visit(KxiElseStatement node) {
//        if (node.getStatement() instanceof KxiBlock) {
//            interStatementStack.push(((KxiBlock) node.getStatement()).getInterBlock());
//        } else if (node.getStatement() instanceof KxiBlockStatement) {
//            interStatementStack.push(((KxiBlockStatement) node.getStatement()).getBlock().getInterBlock());
//        }
//
//        InterStatement interStatement = pop(node.getLineInfo());
//        InterElseStatement interElseStatement = new InterElseStatement(interStatement);
//        node.setInterElseStatement(interElseStatement);
//    }
//
//    @Override
//    public void visit(KxiIfStatement node) {
//        InterElseStatement interElseStatement = null;
//        if (node.getElseStatement() != null) interElseStatement = node.getElseStatement().getInterElseStatement();
//
//        if (node.getStatement() instanceof KxiBlock) {
//            interStatementStack.push(((KxiBlock) node.getStatement()).getInterBlock());
//        } else if (node.getStatement() instanceof KxiBlockStatement) {
//            interStatementStack.push(((KxiBlockStatement) node.getStatement()).getBlock().getInterBlock());
//        }
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        InterStatement interStatement = pop(node.getLineInfo());
//        InterIfStatement interIfStatement =
//                new InterIfStatement(expression, interStatement, interElseStatement, "if_" + HashString.updateStringHash(), node.getInterOperand());
//
//        if (node.getElseStatement() != null) {
//            interElseStatement.setIfNot(interIfStatement.getIfNot());
//            interElseStatement.setDone(interIfStatement.getDone());
//        }
//        interStatementStack.push(interIfStatement);
//
//    }
//
//    @Override
//    public void visit(KxiWhileStatement node) {
//        GenericListNode empty = new GenericListNode(new ArrayList<>());
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        InterWhileStatement interWhileStatement = new InterWhileStatement(empty, expression, pop(node.getLineInfo()), empty, node.getLoopLabel(), node.getExitLoop());
//        interWhileStatement.setInterOperand(node.getInterOperand());
//        interStatementStack.push(interWhileStatement);
//    }
//
//
//    @Override
//    public void visit(KxiForStatement node) {
//        KxiPreForExpression kxiPreForExpression = node.getPreExpression();
//        KxiPostForExpression kxiPostForExpression = node.getPostExpression();
//        GenericListNode preExpression;
//        GenericListNode postExpression;
//        if (kxiPreForExpression != null)
//            preExpression = new GenericListNode(kxiPreForExpression.getInterExpressions());
//        else
//            preExpression = new GenericListNode(new ArrayList<>());
//        if (kxiPostForExpression != null)
//            postExpression = new GenericListNode(kxiPostForExpression.getInterExpressions());
//        else
//            postExpression = new GenericListNode(new ArrayList<>());
//
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//
//        InterWhileStatement interWhileStatement = new InterWhileStatement(preExpression, expression, pop(node.getLineInfo()), postExpression, node.getLoopLabel(), node.getExitLoop());
//        interWhileStatement.setInterOperand(node.getInterOperand());
//        interStatementStack.push(interWhileStatement);
//    }
//
//    @Override
//    public void visit(KxiReturnStatement node) {
//        GenericListNode expression;
//        if (node.getExpression() != null) {
//            expression = new GenericListNode(node.getInterExpressions());
//        } else
//            expression = new GenericListNode(new ArrayList<>());
//        InterReturn interReturn = new InterReturn(expression);
//        interReturn.setInterOperand(node.getInterOperand());
//        interStatementStack.push(interReturn);
//    }
//
//    @Override
//    public void visit(KxiCoutStatement node) {
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        InterCoutStatement interCoutStatement = new InterCoutStatement(expression, node.getInterOperand());
//        interStatementStack.push(interCoutStatement);
//    }
//
//    @Override
//    public void visit(KxiCinStatement node) {
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        InterCinStatement interCinStatement = new InterCinStatement(expression);
//        interCinStatement.setInterOperand(node.getInterOperand());
//        interStatementStack.push(interCinStatement);
//    }
//
//    @Override
//    public void visit(KxiSwitchStatement node) {
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        int defSize = node.getCaseBlock().getDefaultStatements().size();
//        int caseSize = node.getCaseBlock().getCases().size();
//        GenericListNode defaultStatements = new GenericListNode(getStatements(defSize));
//        GenericListNode cases = new GenericListNode(getStatements(caseSize));
//        InterSwitch interSwitch = new InterSwitch(expression, cases, defaultStatements, node.getExitLoop());
//        interSwitch.setInterOperand(node.getInterOperand());
//        interStatementStack.push(interSwitch);
//    }
//
//    @Override
//    public void visit(KxiExpressionStatement node) {
//        GenericListNode expression = new GenericListNode(node.getInterExpressions());
//        InterExpressionStatement interExpressionStatement = new InterExpressionStatement(expression);
//        interStatementStack.push(interExpressionStatement);
//    }
//
//    @Override
//    public void visit(KxiMain node) {
//        node.getBlock().getInterBlock().getInterStatementList().add(new InterReturn(new GenericListNode(new ArrayList<>())));
//    }
//
//    @Override
//    public void visit(KxiBlock node) {
//        GenericListNode statements = new GenericListNode(getStatements(interStatementStack.size()));
//        InterBlock interBlock = new InterBlock(statements);
//        node.setInterBlock(interBlock);
//        //interStatementStack.push(interBlock);
//        super.visit(node);
//    }
//
//
//    public void visit(KxiVariableDeclaration node) {
//        List<InterExpression> expressions = new ArrayList<>();
//        InterOperation interOperation = null;
//        InterValue interValue = new InterId(node.getId(), node.getType().getScalarType());
//        if (node.getInitializer() != null) {
//            for (InterExpression exp : node.getInterStatements()) {
//                expressions.add(exp);
//            }
//            interOperation = new InterAssignment(node.getInterInit(), new LeftVariableStack(interValue)); //may need to set to expression op
//        }
//        InterVariable interVariable = new InterVariable(new InterId(node.getId(), node.getType().getScalarType()), interOperation);
//        node.setInterVariable(interVariable);
//        expressions.add(interVariable);
//        InterVariableDec interVariableDec = new InterVariableDec(interVariable);
//        interStatementStack.push(interVariableDec);
//    }
//}
