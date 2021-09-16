import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n = 100; //(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        String run = "_run=" + 2;
        double l = 6;

        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,1,true);
        test.calculateEvents();
        List<List<Particle>> particlesEvolution = new ArrayList<>();
        particlesEvolution.add(test.getParticles());

        int iterations = 10000;
        for (int i = 0; i < iterations; i++) {
            if (i % 500 == 0){
                System.out.println("Events executed: " + i);
            }
            test.executeEvent(i >= iterations - 0.3 * iterations);
            //System.out.println(test.getTotalTime());
            //System.out.println(test.getTotalCollisions());
            particlesEvolution.add(test.getParticles());
        }

        FileManager.writeOutputFile("positions",particlesEvolution,l);

        double avgCf = test.getTotalCollisions()/test.getTotalTime();
        double avgCt = test.getTotalTime()/test.getTotalCollisions();

        System.out.println("n: " + n);
        System.out.println("total time: " + test.getTotalTime());
        System.out.println("Average collision frequency: " + avgCf);
        System.out.println("Average collision time: " + avgCt);
        String runInfo = "_n=" + n + "_v=" + 2 + run;
        FileManager.writeCsv("velocidades" + runInfo, test.getOutputData().getVelocitiesForParticles(), "v");
        FileManager.writeCsv("trayectorias" + runInfo, test.getOutputData().getBigParticleTrajectories(), "x;y;t");
        FileManager.writeCsv("collision_times" + runInfo, test.getOutputData().getTimesForParticles(), "dt");

    }
}
