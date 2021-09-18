import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n = 100;//(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        int maxV = 2;
        double l = 6;
        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,maxV,true);

        List<List<Particle>> particlesEvolution = new ArrayList<>();
        particlesEvolution.add(test.getParticlesForLogging());

        test.calculateEvents();
        int iterations = 3;
        String runConfig = "_it=" + iterations + "_n=" + n + "_v=" + maxV + "_run:" + 2;
        for (int i = 0; i < iterations; i++) {
            if (i % 500 == 0){
                System.out.println("Events executed: " + i);
            }

            test.executeEvent(i >= iterations - 0.3 * iterations);
            particlesEvolution.add(test.getParticlesForLogging());

            // System.out.println(test.getTotalTime());
            // System.out.println(test.getTotalCollisions());

        }

        double avgCf = test.getTotalCollisions()/test.getTotalTime();
        double avgCt = test.getTotalTime()/test.getTotalCollisions();

        System.out.println("n: " + n);
        System.out.println("total collisions: " + test.getTotalCollisions());
        System.out.println("total time: " + test.getTotalTime());
        System.out.println("Average collision frequency: " + avgCf);
        System.out.println("Average collision time: " + avgCt);

        FileManager.writeOutputFile("test",particlesEvolution,l);
        FileManager.writeCsv("velocidades" + runConfig, test.getOutputData().getParticlesVelocities(), Object::toString,"v");
        FileManager.writeCsv("trayectorias" + runConfig, test.getOutputData().getParticlesTrajectories(), OutputData.TrajectoryData::toString,"id;x;y;t0");
        FileManager.writeCsv("tiempos_colision" + runConfig, test.getOutputData().getTimes(), Object::toString, "dt");
    }
}
