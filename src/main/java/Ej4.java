import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Ej4 {

    public static void main( String[] args ) {
        int n = 135;
        int maxV = 2;
        double l = 6;
        List<OutputData> simulations = new LinkedList<>();
        int iterations = 5000;

        for (int i = 0; i < 50; i++) {
            Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,maxV,false);
            System.out.println("New board created");
            test.calculateEvents();
            for (int j = 0; j < iterations; j++) {
                if(j % 1000 == 0){
                    System.out.println("Events executed: " + j);
                }
                test.executeEvent(j >= iterations - 0.3 * iterations);
            }
            System.out.println("Simulation: " + i);
            simulations.add(test.getOutputData());
        }

        List<List<OutputData.TrajectoryData>> data = new LinkedList<>();
        for (OutputData sim : simulations){
            data.add(sim.getParticlesTrajectories().stream().filter(td -> td.id == 0).collect(Collectors.toList()));
        }
        FileManager.writeMSDBigParticleCalcCsv("msd-big-particle-calc", data, iterations,"id;x;y;t0");
    }
}
