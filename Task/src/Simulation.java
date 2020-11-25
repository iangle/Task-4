import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//=============================================================================================================================================================
/**
 * Sets up and runs the model, collects the results, and passes them to the analyzer to report.
 */
public class Simulation
{
   //----------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Runs the simulation.
    *
    * @param arguments there are no arguments
    */
   public static void main(final String[] arguments)
   {
      Simulation simulation = new Simulation();

      simulation.run();
   }

   //----------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    *  Run a simulation. Make as many methods like this as necessary for our Qs and As.
    */
   private void run()
   {
      Random randomizer = new Random(0); // provide a value to get the same random sequence each time; omit it for a different one each time

      // set up the states
      State s1 = new State("s1", randomizer); // start
      State s2 = new State("s2"); // final
      State s3 = new State("s3", randomizer);
      State s4 = new State("s4"); // final

      // set up the transitions
      s1.addTransition(new Transition(s2, 0.25));
      s1.addTransition(new Transition(s3, 0.75));
      s3.addTransition(new Transition(s4, 1.00));

      // finalize the tree
      s1.commit();

      // generate the Dot output. Import it into Graphviz to verify your tree
      System.out.println("dot=\n" + s1.generateDot());

      // run the model a number of times, recording the results each time
      List<List<String>> results = new ArrayList<>();

      for (int iRun = 1; iRun <= 100; iRun++)
      {
         List<String> result = s1.visit();

         results.add(result);

         System.out.println(iRun + " " + result);
      }

      // run the analysis to report how many times s3 appears among the runs
      Analyzer analyzer = new Analyzer(results);

      analyzer.analyze("s3");
   }
}
