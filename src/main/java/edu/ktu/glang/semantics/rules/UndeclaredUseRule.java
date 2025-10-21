package edu.ktu.glang.semantics.rules;

import edu.ktu.glang.syntax.GLangParser;

import java.util.HashSet;
import java.util.Set;

public class UndeclaredUseRule extends AbstractSemanticRule {
    private final Set<String> declared = new HashSet<>();

    @Override public String id() { return "undeclared-use"; }
    @Override public String description() { return "ID used before 'let' declaration."; }

    @Override
    protected void onVisitLetDecl(GLangParser.LetDeclContext ctx) {
        declared.add(ctx.ID().getText());
    }

    @Override
    protected void onVisitExpr(GLangParser.ExprContext ctx) {
        if (ctx.ID() != null && !declared.contains(ctx.ID().getText())) {
            error(ctx, "Use of undeclared variable '" + ctx.ID().getText() + "'.");
        }
    }
}
