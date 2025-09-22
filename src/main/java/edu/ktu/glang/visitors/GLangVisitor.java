package edu.ktu.glang.visitors;

import edu.ktu.glang.syntax.GLangBaseVisitor;
import edu.ktu.glang.syntax.GLangParser;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class GLangVisitor extends GLangBaseVisitor<Object> {

    private final PrintStream out;
    private final Map<String, Integer> symbolTable = new HashMap<>();

    public GLangVisitor(PrintStream out) {
        this.out = out;
    }

    @Override
    public Object visitProgram(GLangParser.ProgramContext ctx) {
        // visit every statement in order
        for (GLangParser.StatementContext stmt : ctx.statement()) {
            visit(stmt);
        }
        return null;
    }

    @Override
    public Object visitStatement(GLangParser.StatementContext ctx) {
        // just delegate to child
        return visitChildren(ctx);
    }

    @Override
    public Object visitLetDecl(GLangParser.LetDeclContext ctx) {
        String name = ctx.ID().getText();
        int value = 0; // NOTE - default if no initializer
        if (ctx.expr() != null) {
            value = (Integer) visit(ctx.expr());
        }
        symbolTable.put(name, value); // NOTE - we allow re-declaring for now
        return null;
    }

    @Override
    public Object visitAssignment(GLangParser.AssignmentContext ctx) {
        String name = ctx.ID().getText();
        int value = (Integer) visit(ctx.expr());
        symbolTable.put(name, value); // NOTE - we assume the variable was declared
        return null;
    }

    @Override
    public Object visitPrintStmt(GLangParser.PrintStmtContext ctx) {
        int value = (Integer) visit(ctx.expr());
        out.println(value);
        return null;
    }

    @Override
    public Object visitExpr(GLangParser.ExprContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        } else {
            String name = ctx.ID().getText();
            return symbolTable.get(name); // NOTE - we assume the variable was declared
        }
        // NOTE - this method return value, instead of null
    }
}
