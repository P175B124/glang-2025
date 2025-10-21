package edu.ktu.glang.semantics;

import edu.ktu.glang.syntax.*;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;

public class SemanticAnalyzer extends GLangBaseVisitor<Void> {

    private final DiagnosticReporter reporter;
    private final Set<String> declared = new HashSet<>();

    public SemanticAnalyzer(DiagnosticReporter reporter) {
        this.reporter = reporter;
    }

    @Override public Void visitProgram(GLangParser.ProgramContext ctx) {
        for (var s : ctx.statement()) visit(s);
        return null;
    }

    @Override public Void visitLetDecl(GLangParser.LetDeclContext ctx) {
        declared.add(ctx.ID().getText());
        if (ctx.expr() != null) visit(ctx.expr());
        return null;
    }

    @Override public Void visitAssignment(GLangParser.AssignmentContext ctx) {
        if (!declared.contains(ctx.ID().getText())) {
            error(ctx, "Assignment to undeclared variable '" + ctx.ID().getText() + "'.");
        }
        visit(ctx.expr());
        return null;
    }

    @Override public Void visitPrintStmt(GLangParser.PrintStmtContext ctx) {
        visit(ctx.expr());
        return null;
    }

    @Override public Void visitIfStmt(GLangParser.IfStmtContext ctx) {
        visit(ctx.expr());
        visit(ctx.statement(0));
        if (ctx.statement().size() > 1) visit(ctx.statement(1));
        return null;
    }

    @Override public Void visitExpr(GLangParser.ExprContext ctx) {
        return null;
    }

    private void error(ParserRuleContext n, String msg) {
        var t = n.getStart();
        reporter.error(msg, t.getLine(), t.getCharPositionInLine());
    }
}
