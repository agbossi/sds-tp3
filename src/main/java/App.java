import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n =(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        String run = " run " + 1;
        double l = 6;
        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,2,true);

        test.calculateEvents();
        BufferedWriter buffer = FileManager.createOutputFile("positions");
        FileManager.writeOutputFile(test.getParticles(), buffer, l);
        for (int i = 0; i < 10000; i++) {
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
        FileManager.writeCsv("velocidades" + run, test.getOutputData().getVelocitiesForParticles(), "v");
        FileManager.writeCsv("trayectorias" + run, test.getOutputData().getBigParticleTrajectories(), "x;y;vx;vy;t0");
        FileManager.writeCsv("collision_times " + run, test.getOutputData().getTimesForParticles(), "dt");

        System.out.println("n: " + n + run);
        System.out.println("total time: " + test.getTotalTime() + run);
        System.out.println("Average collision frequency: " + test.getTotalCollisions()/test.getTotalTime() + run);
        System.out.println("Average collision time: " + test.getTotalTime()/test.getTotalCollisions() + run);

        try {
            buffer.flush();
            buffer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
