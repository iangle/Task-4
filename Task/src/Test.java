import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//=============================================================================================================================================================
public class Test
{
   //----------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] arguments)
   {
      Test test = new Test();

      test.doAnalysis();
   }

   //----------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doAnalysis()
   {
      Random randomizer = new Random(0);

      State a1 = new State("steering_wheel_turns", randomizer);

      State b1 = new State("linkage_ok", randomizer);
      State b2 = new State("linkage_slips", randomizer); // error

      State c1 = new State("sensor_ok", randomizer);
      State c2 = new State("sensor_error_low", randomizer); // error
      State c3 = new State("sensor_error_high", randomizer); // error

      State d1 = new State("network_ok", randomizer);
      State d2 = new State("network_error", randomizer); // error

      State e1 = new State("computer_ok", randomizer);
      State e2 = new State("computer_error", randomizer); // error
      State e3 = new State("computer_explodes"); // big error

      State f1 = new State("motor_ok", randomizer);
      State f2 = new State("motor_short", randomizer); // error
      State f3 = new State("motor_long", randomizer); // error
      State f4 = new State("motor_reverses", randomizer); // error

      State g1 = new State("gears_ok", randomizer);
      State g2 = new State("gears_slip", randomizer); // error

      State h1 = new State("done");

      a1.addTransition(new Transition(b1, 0.95));
      a1.addTransition(new Transition(b2, 0.05));

      b1.addTransition(new Transition(c1, 0.90));
      b1.addTransition(new Transition(c2, 0.04));
      b1.addTransition(new Transition(c3, 0.06));

      b2.addTransition(new Transition(c1, 0.87));
      b2.addTransition(new Transition(c2, 0.10));
      b2.addTransition(new Transition(c3, 0.03));

      c1.addTransition(new Transition(d1, 0.95));
      c1.addTransition(new Transition(d2, 0.05));

      c2.addTransition(new Transition(d1, 0.90));
      c2.addTransition(new Transition(d2, 0.10));

      c3.addTransition(new Transition(d1, 0.88));
      c3.addTransition(new Transition(d2, 0.12));

      d1.addTransition(new Transition(e1, 0.90));
      d1.addTransition(new Transition(e2, 0.08));
      d1.addTransition(new Transition(e3, 0.02));

      d2.addTransition(new Transition(e1, 0.89));
      d2.addTransition(new Transition(e2, 0.02));
      d2.addTransition(new Transition(e3, 0.09));

      e1.addTransition(new Transition(f1, 0.05));
      e1.addTransition(new Transition(f2, 0.05));
      e1.addTransition(new Transition(f3, 0.85));
      e1.addTransition(new Transition(f4, 0.05));

      e2.addTransition(new Transition(f1, 0.05));
      e2.addTransition(new Transition(f2, 0.05));
      e2.addTransition(new Transition(f3, 0.88));
      e2.addTransition(new Transition(f4, 0.02));

      f1.addTransition(new Transition(g1, 0.87));
      f1.addTransition(new Transition(g2, 0.13));

      f2.addTransition(new Transition(g1, 0.85));
      f2.addTransition(new Transition(g2, 0.15));

      f3.addTransition(new Transition(g1, 0.97));
      f3.addTransition(new Transition(g2, 0.03));

      f4.addTransition(new Transition(g1, 0.80));
      f4.addTransition(new Transition(g2, 0.20));

      g1.addTransition(new Transition(h1, 1.00));

      g2.addTransition(new Transition(h1, 1.00));

      a1.commit();

      System.out.println("dot=\n" + a1.generateDot());

      List<List<String>> results = new ArrayList<>();

      int numIterations = 1;

      for (int iRun = 1; iRun <= numIterations; iRun++)
      {
         List<String> result = a1.visit();

         results.add(result);
      }

      Analyzer analyzer = new Analyzer(results);

      System.out.println("\nGOOD THINGS");
      analyzer.analyze("steering_wheel_turns");
      analyzer.analyze("linkage_ok");
      analyzer.analyze("sensor_ok");
      analyzer.analyze("network_ok");
      analyzer.analyze("computer_ok");
      analyzer.analyze("motor_ok");
      analyzer.analyze("gears_ok");

      System.out.println("\nBAD THINGS");
      analyzer.analyze("linkage_slips");
      analyzer.analyze("sensor_error_low");
      analyzer.analyze("sensor_error_high");
      analyzer.analyze("network_error");
      analyzer.analyze("computer_error");
      analyzer.analyze("computer_explodes");
      analyzer.analyze("motor_short");
      analyzer.analyze("motor_long");
      analyzer.analyze("motor_reverses");
      analyzer.analyze("gears_slip");
   }
}