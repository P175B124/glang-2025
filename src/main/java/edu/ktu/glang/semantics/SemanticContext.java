package edu.ktu.glang.semantics;

import edu.ktu.glang.syntax.DiagnosticReporter;
import org.antlr.v4.runtime.TokenStream;

import java.util.HashMap;
import java.util.Map;

public class SemanticContext {

    private final Map<String, Integer> symbolTable = new HashMap<>();
    public final DiagnosticReporter reporter;
    public final TokenStream tokens;

    public SemanticContext(DiagnosticReporter reporter, TokenStream tokens) {
        this.reporter = reporter;
        this.tokens = tokens;
    }
}