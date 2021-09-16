import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n = 100; //(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        String run = "_run=" + 1;
        double l = 6;
        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,2,true);

        test.calculateEvents();
        BufferedWriter buffer = FileManager.createOutputFile("positions");
        FileManager.writeOutputFile(test.getParticles(), buffer, l);
        for (int i = 0; i < 2; i++) {
            if (i % 500 == 0){
                System.out.println("Events executed: " + i);
            }
            test.executeEvent();
            System.out.println(test.getTotalTime());
            System.out.println(test.getTotalCollisions());
            try {
                buffer.newLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            FileManager.writeOutputFile(test.getParticles(), buffer, l);
        }

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

        try {
            buffer.flush();
            buffer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
