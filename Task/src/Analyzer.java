import java.util.List;

public class Analyzer {

    private List<List<String>> _results;

    public Analyzer(List<List<String>> results){ _results = results; }

    public void analyze(String id){

        int counter = 0;

        int numResults = _results.size();

        for(int i = 0; i < _results.size(); i++){

            if (_results.get(i).contains(id))
                counter++;

        }

        double percentage = (double) counter / numResults;

        System.out.println("\nid " + id + " appeared " + counter + " times");

        System.out.println("number of results: " +  numResults);
        System.out.println("percentage: " + percentage * 100 + "%");

    }
}
