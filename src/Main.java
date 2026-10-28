import java.util.*;
// import java.io.*;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Main <source_file.mdsl>");
            return;
        }

        String sourceFile = args[0];

        if (!sourceFile.endsWith(".mdsl")) {
            System.out.println("Error: Only .mdsl files are supported");
            return;
        }

        String content = FileReaderUtil.readSourceFile(sourceFile);

        if (content == null) {
            return;
        }

        // LEXER
        Lexer lexer = new Lexer(content);
        List<Token> tokens = lexer.tokenize();

        StringBuilder tokenOutput = new StringBuilder();
        for (Token token : tokens) {
            tokenOutput.append(token.toString()).append("\n");
        }

        FileWriterUtil.writeToFile("tokens.txt", tokenOutput.toString());

        // PARSER
        Parser parser = new Parser(tokens);
        ParseResult result = parser.parse();

        ParseTreeNode parseTree = result.parseTree;
        SolveNode ast = result.ast;

        // VISUALIZATION
        // ASTVisualizer.visualizeParseTree(parseTree);
        // ASTVisualizer.visualizeAST(ast);

        FileWriterUtil.writeToFile("parse_tree.txt", parseTree.toString());
        FileWriterUtil.writeToFile("ast.txt", ast.toString());

        // TRACE
        StringBuilder traceOutput = new StringBuilder();
        for (String line : parser.trace) {
            traceOutput.append(line).append("\n");
        }

        FileWriterUtil.writeToFile("parse_trace.txt", traceOutput.toString());

        // SOLVER
        Solver solver = new Solver();
        String finalResult = solver.solve(ast);

        System.out.println(finalResult);

        // PIPELINE
        StringBuilder pipeline = new StringBuilder();

        pipeline.append("===== DSL INPUT =====\n");
        pipeline.append(content).append("\n\n");

        pipeline.append("===== TOKENS =====\n");
        for (Token token : tokens) {
            pipeline.append(token.toString()).append("\n");
        }

        pipeline.append("\n===== PARSER TRACE =====\n");
        for (String line : parser.trace) {
            pipeline.append(line).append("\n");
        }

        pipeline.append("\n===== AST =====\n");
        pipeline.append(ast.toString()).append("\n");

        pipeline.append("\n===== RESULT =====\n");
        pipeline.append(finalResult).append("\n");

        FileWriterUtil.writeToFile("pipeline.txt", pipeline.toString());
    }
}