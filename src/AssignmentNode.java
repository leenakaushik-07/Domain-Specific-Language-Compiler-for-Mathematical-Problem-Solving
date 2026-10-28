public class AssignmentNode {
    public String variable;
    public int value;

    public AssignmentNode(String variable, int value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public String toString() {
        return variable + " = " + value;
    }
}