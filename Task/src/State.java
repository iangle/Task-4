import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State
{

    private String _id;

    private Random _randomizer;

    private List<Transition> _transitions = new ArrayList<>();



    public State(String id) { _id = id;}

    public State(String id, Random randomizer) { _id = id; _randomizer = randomizer;}



    public void addTransition(Transition transition) { _transitions.add(transition); }

    public void commit(){ }

    public String generateDot(){return "";}

    public String getID(){return _id;}

    public List<String> visit()
    {
        for(int i = 0; i < _transitions.size(); i++)
        {
            if(_randomizer.nextDouble() <= _transitions.get(i).getProbabilityThreshold())
                _transitions.get(i).getToState().visit();
        }


    }

}