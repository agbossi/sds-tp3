import java.util.ArrayList;
import java.util.List;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n =(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        System.out.println(n);
//        Board b = Board.getRandomBoard(n,6,0.2,0.9,0.7,2,2,true);
//        FileManager.outputFile(b.getParticles(),"positions");

        List<Particle> particleList = new ArrayList<>();
        particleList.add(new Particle(0,0.3,0.5,0.1,1,1,1));
        particleList.add(new Particle(1,0.7,0.5,0.1,1,-1,1));
        particleList.add(new Particle(2,0.8,0.2,0.1,1,1,0));
        Board b = new Board(1, particleList);

        b.calculateEvents();
        for (int i = 0; i < 10000; i++) {
            if (i % 500 == 0){
                System.out.println("Events executed: " + i);
            }
            b.executeEvent();
        }

        System.out.println("Average collision frequency: " + b.getTotalCollisions()/b.getTotalTime());
    }
}
