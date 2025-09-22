package edu.ktu.glang.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfStatementTest {

    @Test
    void execute_ifWithTrueCondition_executesThenBranch() {
        String src = """
            let x = 1;
            if (x) print(42);
            """;

        String expected = """
            42
            """;

        String out = Runner.runToString(src);
        assertEquals(expected, out);
    }

    @Test
    void execute_ifWithFalseCondition_skipsThenBranch() {
        String src = """
            let x = 0;
            if (x) print(42);
            """;

        String expected = """
            """;

        String out = Runner.runToString(src);
        assertEquals(expected, out);
    }

    @Test
    void execute_ifElse_true_executesThenNotElse() {
        String src = """
            let x = 7;
            if (x) print(1); else print(2);
            """;

        String expected = """
            1
            """;

        String out = Runner.runToString(src);
        assertEquals(expected, out);
    }

    @Test
    void execute_ifElse_false_executesElse() {
        String src = """
            let x = 0;
            if (x) print(1); else print(2);
            """;

        String expected = """
            2
            """;

        String out = Runner.runToString(src);
        assertEquals(expected, out);
    }
}

