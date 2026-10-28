import java.util.*;

public class Parser {

    private List<Token> tokens;
    private int position;
    public List<String> trace;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.trace = new ArrayList<>();
    }

    public void log(String message) {
        trace.add(message);
    }

    private Token currentToken() {
        if (position < tokens.size()) {
            return tokens.get(position);
        }
        return null;
    }

    private Token eat(String expectedType, String expectedValue) {
        Token token = currentToken();

        if (token == null) {
            throw new RuntimeException("Parser Error: Unexpected end of input");
        }

        if (expectedType != null && !token.type.equals(expectedType)) {
            throw new RuntimeException(
                "Parser Error: Expected " + expectedType + " but got " + token.type + " at line " + token.line
            );
        }

        if (expectedValue != null && !token.value.equals(expectedValue)) {
            throw new RuntimeException(
                "Parser Error: Expected '" + expectedValue + "' but got '" + token.value + "' at line " + token.line
            );
        }

        position++;
        return token;
    }

    public ParseResult parse() {
        log("Starting parsing process");

        Pair result = parseSolveBlock();

        log("Parsing completed successfully");

        return new ParseResult(result.parseTree, result.ast);
    }

    private Pair parseSolveBlock() {
        ParseTreeNode root = new ParseTreeNode("SolveStmt");

        // solve
        eat("KEYWORD", "solve");
        root.addChild(new ParseTreeNode("solve"));

        // formula
        Token formulaToken = eat("IDENTIFIER", null);
        root.addChild(new ParseTreeNode(formulaToken.value));

        // where
        eat("KEYWORD", "where");
        root.addChild(new ParseTreeNode("where"));

        ParseTreeNode assignmentsNode = new ParseTreeNode("Assignments");
        List<AssignmentNode> assignments = new ArrayList<>();

        // first assignment
        AssignmentPair first = parseAssignment();
        assignmentsNode.addChild(first.ptNode);
        assignments.add(first.astNode);

        // more assignments
        while (currentToken() != null && currentToken().type.equals("COMMA")) {
            eat("COMMA", null);
            AssignmentPair next = parseAssignment();
            assignmentsNode.addChild(next.ptNode);
            assignments.add(next.astNode);
        }

        root.addChild(assignmentsNode);

        // find
        eat("KEYWORD", "find");
        root.addChild(new ParseTreeNode("find"));

        // target
        Token targetToken = eat("IDENTIFIER", null);
        root.addChild(new ParseTreeNode(targetToken.value));

        SolveNode ast = new SolveNode(
            formulaToken.value,
            assignments,
            targetToken.value
        );

        return new Pair(root, ast);
    }

    private AssignmentPair parseAssignment() {
        Token varToken = eat("IDENTIFIER", null);
        eat("OPERATOR", "=");
        Token valueToken = eat("NUMBER", null);

        ParseTreeNode ptNode = new ParseTreeNode("Assignment");
        ptNode.addChild(new ParseTreeNode(varToken.value));
        ptNode.addChild(new ParseTreeNode("="));
        ptNode.addChild(new ParseTreeNode(valueToken.value));

        AssignmentNode astNode = new AssignmentNode(varToken.value, Integer.parseInt(valueToken.value));

        return new AssignmentPair(ptNode, astNode);
    }
}