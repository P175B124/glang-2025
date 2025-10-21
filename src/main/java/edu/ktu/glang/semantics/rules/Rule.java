package edu.ktu.glang.semantics.rules;

import edu.ktu.glang.syntax.DiagnosticReporter;
import org.antlr.v4.runtime.TokenStream;

public interface Rule {
    String id();
    String description();

    void init(DiagnosticReporter reporter, TokenStream tokens); // set up shared state
    default void finish() {}                                    // finalize if needed
}
