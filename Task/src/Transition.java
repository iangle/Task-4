public class Transition {

    private double _probabilityThreshold;

    private State _toState;

    public Transition(State toState, double probabilityThreshold){ _toState = toState; _probabilityThreshold = probabilityThreshold; }

    public int compareTo(Transition transition){ return 1; }

    public double getProbabilityThreshold(){ return _probabilityThreshold; }

    public State getToState(){ return _toState; }
}
