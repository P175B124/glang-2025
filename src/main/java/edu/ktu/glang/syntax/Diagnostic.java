package edu.ktu.glang.syntax;

public record Diagnostic(Severity severity, String message, int line, int col) {}
