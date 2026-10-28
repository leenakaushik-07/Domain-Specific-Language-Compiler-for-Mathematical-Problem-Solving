import java.util.*;

public class Solver {

    public String solve(SolveNode ast) {
        if (ast.formula.equals("pythagoras")) {
            return solvePythagoras(ast);
        } else if (Arrays.asList("sin","cos","tan","sec","cosec","cot").contains(ast.formula)) {
            return solveTrigonometry(ast);
        } else {
            throw new RuntimeException("Unsupported operation: " + ast.formula);
        }
    }

    private String solvePythagoras(SolveNode ast) {
    Map<String, Integer> values = new HashMap<>();

    for (AssignmentNode a : ast.assignments) {
        values.put(a.variable, a.value);
    }

    String target = ast.target;

    StringBuilder explanation = new StringBuilder();
    explanation.append("Given:\n");

    for (String k : values.keySet()) {
        explanation.append(k).append(" = ").append(values.get(k)).append("\n");
    }

    explanation.append("\nUsing Pythagoras theorem:\n");

    // ------------------ FIND c ------------------
    if (target.equals("c") && values.containsKey("a") && values.containsKey("b")) {
        int a = values.get("a");
        int b = values.get("b");

        int total = a * a + b * b;
        double result = Math.sqrt(total);

        explanation.append("c = sqrt(a² + b²)\n");
        explanation.append("c = sqrt(").append(a).append("² + ").append(b).append("²)\n");
        explanation.append("c = sqrt(").append(total).append(")\n");
        explanation.append("c = ").append(result).append("\n\nHence,\nc = ").append(result);

        return explanation.toString();
    }

    // ------------------ FIND a ------------------
    if (target.equals("a") && values.containsKey("b") && values.containsKey("c")) {
        int b = values.get("b");
        int c = values.get("c");

        int total = c * c - b * b;
        double result = Math.sqrt(total);

        explanation.append("a = sqrt(c² - b²)\n");
        explanation.append("a = sqrt(").append(c).append("² - ").append(b).append("²)\n");
        explanation.append("a = sqrt(").append(total).append(")\n");
        explanation.append("a = ").append(result).append("\n\nHence,\na = ").append(result);

        return explanation.toString();
    }

    // ------------------ FIND b ------------------
    if (target.equals("b") && values.containsKey("a") && values.containsKey("c")) {
        int a = values.get("a");
        int c = values.get("c");

        int total = c * c - a * a;
        double result = Math.sqrt(total);

        explanation.append("b = sqrt(c² - a²)\n");
        explanation.append("b = sqrt(").append(c).append("² - ").append(a).append("²)\n");
        explanation.append("b = sqrt(").append(total).append(")\n");
        explanation.append("b = ").append(result).append("\n\nHence,\nb = ").append(result);

        return explanation.toString();
    }

    throw new RuntimeException("Insufficient or invalid data for pythagoras");
}
        private String solveTrigonometry(SolveNode ast) {
        Map<String, Integer> values = new HashMap<>();

        for (AssignmentNode a : ast.assignments) {
            values.put(a.variable, a.value);
        }

        if (!values.containsKey("angle")) {
            throw new RuntimeException("Angle required");
        }

        double radians = Math.toRadians(values.get("angle"));

        double result = 0;

        switch (ast.formula) {
            case "sin": result = Math.sin(radians); break;
            case "cos": result = Math.cos(radians); break;
            case "tan": result = Math.tan(radians); break;
            case "sec": result = 1 / Math.cos(radians); break;
            case "cosec": result = 1 / Math.sin(radians); break;
            case "cot": result = 1 / Math.tan(radians); break;
        }

        result = Math.round(result * 10000.0) / 10000.0;

        return "Result: " + result;
    }
}