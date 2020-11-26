import java.util.List;

public class ManyFSM {

    private final List<FSM> finiteStateMachines;
    private final int priority;
    private final String name;

    public ManyFSM(List<FSM> finiteStateMachines,
                   String name) {
        this.finiteStateMachines = finiteStateMachines;
        this.priority = 1;
        this.name = name;
    }

    public ManyFSM(List<FSM> finiteStateMachines,
                   String name,
                   int priority) {
        this.finiteStateMachines = finiteStateMachines;
        this.name = name;
        this.priority = priority;
    }

    public List<FSM> getFiniteStateMachines() {
        return finiteStateMachines;
    }

    public void addFiniteStateMachine(FSM finiteStateMachine) {
        finiteStateMachines.add(finiteStateMachine);
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FsmFamily{" +
                "finiteStateMachines=" + finiteStateMachines +
                ", priority=" + priority +
                ", name='" + name + '\'' +
                '}';
    }
}