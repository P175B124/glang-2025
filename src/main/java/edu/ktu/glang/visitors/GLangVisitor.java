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
        int value = Integer.parseInt(ctx.INT().getText());
        symbolTable.put(name, value); // NOTE - we allow re-declaring for now
        return null;
    }

    @Override
    public Object visitPrintStmt(GLangParser.PrintStmtContext ctx) {
        if (ctx.INT() != null) {
            out.println(ctx.INT().getText());
        } else {
            String name = ctx.ID().getText();
            Integer v = symbolTable.get(name);
            out.println(v); // NOTE - we assume the variable was declared
        }
        return null;
    }
}
