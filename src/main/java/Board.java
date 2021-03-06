import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private final double L;
    private final List<Particle> particles;
    private final PriorityQueue<Event> events;
    private final Set<Event> collisions;

    public Board(double L, List<Particle> particles) {
        this.L = L;
        this.particles = particles;
        this.events = new PriorityQueue<>();
        this.collisions = new HashSet<>();
    }

    public static Board getRandomBoard(int n, double l, double minR, double minMass, double maxR, double maxMass, double maxV, boolean file) {

        List<Particle> particles = new ArrayList<>();

        // Big particle
        double x = l/2;
        double y = l/2;
        double vx = 0;
        double vy = 0;

        try {
            FileWriter st=null, dyn=null;
            BufferedWriter stBuffer=null, dynBuffer=null;

            if(file) {
                st = new FileWriter("src/main/resources/newStatic.txt",false);
                stBuffer = new BufferedWriter(st);
                dyn = new FileWriter("src/main/resources/newDynamic.txt",false);
                dynBuffer = new BufferedWriter(dyn);

                stBuffer.write(String.valueOf(n+1));
                stBuffer.newLine();
                stBuffer.write(String.valueOf(l));
                stBuffer.newLine();

                dynBuffer.write("t0");
                dynBuffer.newLine();

                FileManager.particleInputFiles(x,y,vx,vy,maxMass,maxR,stBuffer,dynBuffer);
            }

            particles.add(new Particle(0, x, y, maxR, maxMass, vx, vy));

            // Little particles
            double v, theta;
            Random random = new Random();
            for (int i = 1; i <= n; i++) {
                do {
                    x = ThreadLocalRandom.current().nextDouble(minR, l-minR);
                    y = ThreadLocalRandom.current().nextDouble(minR, l-minR);
                } while(overlap(x, y, minR, particles));
                v = random.nextDouble() * maxV;
                theta = Math.random() * 2 * Math.PI;
                vx = v * Math.cos(theta) * (random.nextBoolean() ? -1:1);
                vy = v * Math.sin(theta) * (random.nextBoolean() ? -1:1);

                if(file) {
                    FileManager.particleInputFiles(x,y,vx,vy,minMass,minR,stBuffer,dynBuffer);
                }

                particles.add(new Particle(i, x, y, minR, minMass, vx, vy));
            }

            if(file) {
                stBuffer.flush();
                dynBuffer.flush();
                stBuffer.close();
                dynBuffer.close();
                st.close();
                dyn.close();
                System.out.println("Nuevo tablero en newStatic.txt y newDynamic.txt");
            }

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }

        return new Board(l, particles);
    }

    private static boolean overlap(double x, double y, double r, List<Particle> particles) {
        if(particles.size() == 0) {
            return false;
        }
        for (Particle particle : particles) {
            if((Math.pow(particle.getState().getX() - x, 2) + Math.pow(particle.getState().getY() - y, 2)) <= Math.pow(particle.getRadius() + r, 2)) {
                return true;
            }
        }
        return false;
    }

    public double getL() {
        return L;
    }

    public int getN() { return particles.size(); }

    public List<Particle> getParticles() {
        return particles;
    }

}