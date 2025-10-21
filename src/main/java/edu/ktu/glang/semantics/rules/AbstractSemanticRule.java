package edu.ktu.glang.semantics.rules;

import edu.ktu.glang.semantics.SemanticContext;
import edu.ktu.glang.syntax.DiagnosticReporter;
import edu.ktu.glang.syntax.GLangBaseVisitor;
import edu.ktu.glang.syntax.GLangParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;

public abstract class AbstractSemanticRule extends GLangBaseVisitor<Void> implements Rule {
    protected DiagnosticReporter reporter;
    protected TokenStream tokens;
    protected SemanticContext ctx; // optional shared context (symbol table, etc.)

    @Override
    public void init(DiagnosticReporter reporter, TokenStream tokens) {
        this.reporter = reporter;
        this.tokens = tokens;
    }
    public void setContext(SemanticContext ctx) { this.ctx = ctx; }

    /* ---------- traverse tree ---------- */

    @Override
    public Void visitProgram(GLangParser.ProgramContext ctx) {
        onVisitProgram(ctx);
        for (var s : ctx.statement()) visit(s);
        return null;
    }

    @Override
    public Void visitStatement(GLangParser.StatementContext ctx) {
        onVisitStatement(ctx);
        return visitChildren(ctx);
    }

    @Override
    public Void visitLetDecl(GLangParser.LetDeclContext ctx) {
        onVisitLetDecl(ctx);
        if (ctx.expr() != null) visit(ctx.expr());
        return null;
    }

    @Override
    public Void visitAssignment(GLangParser.AssignmentContext ctx) {
        onVisitAssignment(ctx);
        visit(ctx.expr());
        return null;
    }

    @Override
    public Void visitPrintStmt(GLangParser.PrintStmtContext ctx) {
        onVisitPrintStmt(ctx);
        visit(ctx.expr());
        return null;
    }

    @Override
    public Void visitIfStmt(GLangParser.IfStmtContext ctx) {
        onVisitIfStmt(ctx);
        visit(ctx.expr());
        visit(ctx.statement(0));
        if (ctx.statement().size() > 1) visit(ctx.statement(1));
        return null;
    }

    @Override
    public Void visitExpr(GLangParser.ExprContext ctx) {
        onVisitExpr(ctx);
        return null; // expr: INT | ID – no children
    }

    /* ---------- helpers ---------- */

    protected void error(ParserRuleContext n, String msg) {
        var t = n.getStart();
        reporter.error(msg, t.getLine(), t.getCharPositionInLine());
    }
    protected void warn(ParserRuleContext n, String msg) {
        var t = n.getStart();
        reporter.warn(msg, t.getLine(), t.getCharPositionInLine());
    }

    /* ---------- hooks  ---------- */

    protected void onVisitProgram(GLangParser.ProgramContext ctx) {}
    protected void onVisitStatement(GLangParser.StatementContext ctx) {}
    protected void onVisitLetDecl(GLangParser.LetDeclContext ctx) {}
    protected void onVisitAssignment(GLangParser.AssignmentContext ctx) {}
    protected void onVisitPrintStmt(GLangParser.PrintStmtContext ctx) {}
    protected void onVisitIfStmt(GLangParser.IfStmtContext ctx) {}
    protected void onVisitExpr(GLangParser.ExprContext ctx) {}
}