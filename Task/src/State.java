import java.util.*;
import java.math.BigDecimal;

public class State
{

    private String _id;

    private Random _randomizer;

    private List<Transition> _transitions = new ArrayList<>();



    public State(String id) { _id = id;}

    public State(String id, Random randomizer) { _id = id; _randomizer = randomizer;}



    public void addTransition(Transition transition) { _transitions.add(transition); }


    //code for using the collections from: https://www.techiedelight.com/sort-list-of-objects-using-comparator-java/
    public void commit(){

        double sum = 0;
        BigDecimal amount = BigDecimal.ONE;

        for(int i = 0; i < _transitions.size(); i++){
            sum += _transitions.get(i).getProbabilityThreshold();
        }

        if(amount.subtract(BigDecimal.valueOf(sum)).equals(BigDecimal.ZERO)) {
            System.out.println("look like one of the Transitions has the wrong probability threshold");
            System.exit(0);
        }

        Collections.sort(_transitions, new Comparator<>() {

            @Override
            public int compare(Transition t1, Transition t2) {

                if(t1.compareTo(t2) == 0) {
                    return 0;
                }else if(t1.compareTo(t2) == -1) {
                    return -1;
                }else if(t1.compareTo(t2) == 1){
                    return 1;
                }

                return 0;
            }
        });

        if(_transitions.size() != 0){
            for(int i = 0; i < _transitions.size(); i++){
                _transitions.get(i).getToState().commit();
            }
        }

    }

    public String generateDot(){

        String dot = "";



        if(_transitions.size() != 0) {
            for (int i = 0; i < _transitions.size(); i++) {

                dot = dot.concat(getID() + " -> " + _transitions.get(i).getToState().getID() + " [label = " + "\"" + _transitions.get(i).getProbabilityThreshold() + "\"]\n");
                dot = dot.concat(_transitions.get(i).getToState().generateDot());
                //dot = "digraph my_graph{\n" + dot;



            }
        }else{

            //dot = "digraph my_graph{\n" + dot;
            dot += getID() + " [shape = doublecircle]\n";
           // dot = dot + "}\n";
        }

        return dot;
                //dot.substring(18);
    }

    public String getID(){return _id;}

    /**
     *
     * @return a list of strings that contains the state id that we visited
     */
    public List<String> visit()
    {

        List<String> myList = new ArrayList<>();
        myList.add(_id);

        //make sure this state isn't final
        if(_transitions.size() != 0){

            float rand = _randomizer.nextFloat();

            //go through each transition and pick one based on the random number
            for (int i = 0; i < _transitions.size() - 1; i++) {

                if (rand > _transitions.get(i).getProbabilityThreshold() && rand < _transitions.get(i + 1).getProbabilityThreshold()) {

                    myList.addAll(_transitions.get(i + 1).getToState().visit());

                } else if (rand < _transitions.get(i).getProbabilityThreshold()) {

                    myList.addAll(_transitions.get(i).getToState().visit());

                } else if (rand > _transitions.get(_transitions.size() - 1).getProbabilityThreshold()) {

                    myList.addAll(_transitions.get(_transitions.size() - 1).getToState().visit());

                }
            }

            //checking to see if this state leads to only one other state
            if(_transitions.size() == 1){
                myList.addAll(_transitions.get(0).getToState().visit());
            }
        }

        return myList;
    }

}