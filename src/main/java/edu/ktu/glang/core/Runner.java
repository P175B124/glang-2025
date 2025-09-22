package edu.ktu.glang.core;

import edu.ktu.glang.syntax.GLangLexer;
import edu.ktu.glang.syntax.GLangParser;
import edu.ktu.glang.syntax.SyntaxErrorCollector;
import edu.ktu.glang.visitors.GLangVisitor;
import org.antlr.v4.runtime.*;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public final class Runner {

    private Runner() {}

    /** Parse + execute from a CharStream (used by Main). */
    public static void run(CharStream input, PrintStream out) {
        GLangLexer lexer = new GLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GLangParser parser = new GLangParser(tokens);

        SyntaxErrorCollector err = new SyntaxErrorCollector();
        parser.removeErrorListeners();  // NOTE - remove default
        parser.addErrorListener(err);   // NOTE - register ours

        ParseTree tree = parser.program(); // NOTE - entry point

        List<String> errors = err.getErrors();
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Syntax errors: " + errors);
        }

        new GLangVisitor(out).visit(tree); // NOTE - evaluate the tree
    }
}

