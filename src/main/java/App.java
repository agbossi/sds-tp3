import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n = 100;//(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        double maxV = 2.0;
        double l = 6;
        Board test = Board.getRandomBoard(n,l,0.2,0.9,0.7,2,maxV,true);

        List<List<Particle>> particlesEvolution = new ArrayList<>();
        List<Particle> current = test.getParticlesForLogging();
        particlesEvolution.add(current);

        List<Particle.State> bigParticleStates = new ArrayList<>();
        bigParticleStates.add(current.get(0).getState());

        test.calculateEvents();
        int iterations = 50000;
        Event currentEvent = null;

        int i;
        for (i = 0; i < iterations; i++) {
            if (i % 5000 == 0){
                System.out.println("Events executed: " + i);
            }

            test.executeEvent(i >= 2 * iterations / 3);
            current = test.getParticlesForLogging();
            particlesEvolution.add(current);
            bigParticleStates.add(current.get(0).getState());

            currentEvent = test.getLastEvent();
            if(currentEvent.getType()!=CollisionType.PARTICLE) {
                if(currentEvent.getP1()!=null && currentEvent.getP1().getId()==0) {
                    System.out.println(currentEvent.getType()+" "+currentEvent.getP1()+" "+currentEvent.getP2());
                    break;
                } else if(currentEvent.getP2()!=null && currentEvent.getP2().getId()==0) {
                    System.out.println(currentEvent.getType()+" "+currentEvent.getP1()+" "+currentEvent.getP2());
                    break;
                }
            }

            // System.out.println(test.getTotalTime());
            // System.out.println(test.getTotalCollisions());

        }

        String runConfig = "_it=" + i + "_n=" + n + "_v=" + maxV + "_run=" + 3;

        double avgCf = test.getTotalCollisions()/test.getTotalTime();
        double avgCt = test.getTotalTime()/test.getTotalCollisions();

        /* System.out.println("n: " + n);
        System.out.println("total collisions: " + test.getTotalCollisions());
        System.out.println("total time: " + test.getTotalTime());
        System.out.println("Average collision frequency: " + avgCf);
        System.out.println("Average collision time: " + avgCt); */

        FileManager.writeOutputFile("v2",particlesEvolution,l);
        FileManager.writeCsv("big_particle_trajectory"+runConfig, bigParticleStates, Object::toString,"x;y;vx;vy;");
//        FileManager.writeCsv("velocidades" + runConfig, test.getOutputData().getParticlesVelocities(), Object::toString,"v");
//        FileManager.writeCsv("trayectorias" + runConfig, test.getOutputData().getParticlesTrajectories(), OutputData.TrajectoryData::toString,"id;x;y;t0");
//        FileManager.writeCsv("tiempos_colision" + runConfig, test.getOutputData().getTimes(), Object::toString, "dt");
    }
}
