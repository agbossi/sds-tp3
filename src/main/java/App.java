import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n =(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        System.out.println("n: " + n);
        double l = 6;
        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,2,true);
//        FileManager.outputFile(test.getOutputData(), test.getL(), "positions");

/*        List<Particle> particleList = new ArrayList<>();
        particleList.add(new Particle(0,0.3,0.5,0.1,1,1,1));
        particleList.add(new Particle(1,0.7,0.5,0.1,1,-1,1));
        particleList.add(new Particle(2,0.8,0.2,0.1,1,1,0));
        Board b = new Board(1, particleList); */
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
        FileManager.writeCsv("velocidades", test.getOutputData().getVelocitiesForParticles(), "v");
        FileManager.writeCsv("trayectorias", test.getOutputData().getBigParticleTrajectories(), "x;y;vx;vy;t0");

        System.out.println("Average collision frequency: " + test.getTotalCollisions()/test.getTotalTime());

        try {
            buffer.flush();
            buffer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
