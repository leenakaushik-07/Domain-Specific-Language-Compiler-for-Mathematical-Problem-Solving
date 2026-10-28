import java.util.*;

public class SolveNode {
    public String formula;
    public List<AssignmentNode> assignments;
    public String target;

    public SolveNode(String formula, List<AssignmentNode> assignments, String target) {
        this.formula = formula;
        this.assignments = assignments;
        this.target = target;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("SolveNode\n");
        result.append("├── Formula: ").append(formula).append("\n");
        result.append("├── Target: ").append(target).append("\n");
        result.append("└── Assignments\n");

        for (int i = 0; i < assignments.size(); i++) {
            if (i == assignments.size() - 1) {
                result.append("    └── ").append(assignments.get(i)).append("\n");
            } else {
                result.append("    ├── ").append(assignments.get(i)).append("\n");
            }
        }

        return result.toString();
    }
}