package edu.ktu.glang.visitors;

import edu.ktu.glang.syntax.GLangBaseVisitor;
import edu.ktu.glang.syntax.GLangParser;

/**
 * Handles only if/else semantics and delegates expression evaluation
 * and statement execution back to the root interpreter.
 */
public class IfVisitor extends GLangBaseVisitor<Object> {
    private final GLangVisitor root; // we reuse root's symbol table, output, and helpers

    public IfVisitor(GLangVisitor root) {
        this.root = root;
    }

    @Override
    public Object visitIfStmt(GLangParser.IfStmtContext ctx) {
        int cond = (Integer) root.visit(ctx.expr()); // evaluate condition with root
        if (cond != 0) {
            root.visit(ctx.statement(0));                  // NOTE - statement 0
        } else if (ctx.statement().size() > 1) {
            root.visit(ctx.statement(1));                  // NOTE - statement 1
        }
        return null;
    }
}
