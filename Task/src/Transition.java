import java.math.BigDecimal;

public class Transition {

    private double _probabilityThreshold;

    private State _toState;

    public Transition(State toState, double probabilityThreshold){ _toState = toState; _probabilityThreshold = probabilityThreshold; }

    public int compareTo(Transition transition){

        if(Double.compare(_probabilityThreshold,transition.getProbabilityThreshold()) == 0){
            return 0;
        }else if(Double.compare(_probabilityThreshold, transition.getProbabilityThreshold()) < 0){
            return 1;
        }else{
            return -1;
        }
    }

    public double getProbabilityThreshold(){ return _probabilityThreshold; }

    public State getToState(){ return _toState; }
}
