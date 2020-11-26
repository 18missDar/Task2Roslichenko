public class PairforFSM {
    private final String input;
    private final String state;

    public PairforFSM(String input, String state) {
        this.input = input;
        this.state = state;
    }

    public String getInput() {
        return input;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "FsmPair{" +
                "input='" + input + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}