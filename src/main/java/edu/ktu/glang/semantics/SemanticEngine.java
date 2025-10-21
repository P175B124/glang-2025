package edu.ktu.glang.semantics;

import edu.ktu.glang.semantics.rules.AbstractSemanticRule;
import edu.ktu.glang.semantics.rules.Rule;
import edu.ktu.glang.syntax.DiagnosticReporter;
import edu.ktu.glang.syntax.GLangBaseVisitor;
import edu.ktu.glang.syntax.GLangParser;
import org.antlr.v4.runtime.TokenStream;

import java.util.List;

public class SemanticEngine {

    private final List<Rule> rules;
    private final SemanticContext sharedCtx;

    public SemanticEngine(List<Rule> rules, SemanticContext sharedCtx) {
        this.rules = rules;
        this.sharedCtx = sharedCtx;
    }

    public void analyze(GLangParser.ProgramContext program,
                        DiagnosticReporter reporter,
                        TokenStream tokens) {
        for (Rule r : rules) {
            r.init(reporter, tokens);
            if (r instanceof AbstractSemanticRule tr) {
                tr.setContext(sharedCtx);
                tr.visit(program);
            } else {
                ((GLangBaseVisitor<Void>) r).visit(program);
            }
            r.finish();
        }
    }
}
