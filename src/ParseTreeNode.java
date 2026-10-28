import java.util.*;

public class ParseTreeNode {
    public String name;
    public List<ParseTreeNode> children;

    public ParseTreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(ParseTreeNode node) {
        children.add(node);
    }

    @Override
    public String toString() {
        return reprTree("", true);
    }

    private String reprTree(String prefix, boolean isLast) {
        StringBuilder result = new StringBuilder();

        if (!prefix.isEmpty()) {
            result.append(prefix);
            result.append(isLast ? "└── " : "├── ");
        }

        result.append(name).append("\n");

        String newPrefix = prefix + (isLast ? "    " : "│   ");

        for (int i = 0; i < children.size(); i++) {
            boolean last = (i == children.size() - 1);
            result.append(children.get(i).reprTree(newPrefix, last));
        }

        return result.toString();
    }
}