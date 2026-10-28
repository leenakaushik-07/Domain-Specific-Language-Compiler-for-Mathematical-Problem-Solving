public class ParseResult {
    public ParseTreeNode parseTree;
    public SolveNode ast;

    public ParseResult(ParseTreeNode parseTree, SolveNode ast) {
        this.parseTree = parseTree;
        this.ast = ast;
    }
}