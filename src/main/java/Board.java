import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private final double L;
    private double totalTime;
    private final List<Particle> particles;
    private final PriorityQueue<Event> events;
    private final Set<Event> collisions;

    public Board(double L, List<Particle> particles) {
        this.L = L;
        this.totalTime = 0;
        this.particles = particles;
        this.events = new PriorityQueue<>();
        this.collisions = new HashSet<>();
    }

    public static Board getRandomBoard(int n, double l, double minR, double minMass, double maxR, double maxMass, double maxV, boolean file) {

        List<Particle> particles = new ArrayList<>();

        // Big particle
        double x = l / 2;
        double y = l / 2;
        double vx = 0;
        double vy = 0;

        try {
            FileWriter st = null, dyn = null;
            BufferedWriter stBuffer = null, dynBuffer = null;

            if (file) {
                st = new FileWriter("src/main/resources/newStatic.txt", false);
                stBuffer = new BufferedWriter(st);
                dyn = new FileWriter("src/main/resources/newDynamic.txt", false);
                dynBuffer = new BufferedWriter(dyn);

                stBuffer.write(String.valueOf(n + 1));
                stBuffer.newLine();
                stBuffer.write(String.valueOf(l));
                stBuffer.newLine();

                dynBuffer.write("t0");
                dynBuffer.newLine();

                FileManager.particleInputFiles(x, y, vx, vy, maxMass, maxR, stBuffer, dynBuffer);
            }

            particles.add(new Particle(0, x, y, minR, minMass, vx, vy));

            // Little particles
            double v, theta;
            Random random = new Random();
            for (int i = 1; i <= n; i++) {
                do {
                    x = ThreadLocalRandom.current().nextDouble(minR, l - minR);
                    y = ThreadLocalRandom.current().nextDouble(minR, l - minR);
                } while (overlap(x, y, minR, particles));
                v = random.nextDouble() * maxV;
                theta = Math.random() * 2 * Math.PI;
                vx = v * Math.cos(theta) * (random.nextBoolean() ? -1 : 1);
                vy = v * Math.sin(theta) * (random.nextBoolean() ? -1 : 1);

                if (file) {
                    FileManager.particleInputFiles(x, y, vx, vy, minMass, minR, stBuffer, dynBuffer);
                }

                particles.add(new Particle(i, x, y, minR, minMass, vx, vy));
            }

            if (file) {
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
        if (particles.size() == 0) {
            return false;
        }
        for (Particle particle : particles) {
            if ((Math.pow(particle.getState().getX() - x, 2) + Math.pow(particle.getState().getY() - y, 2)) <= Math.pow(particle.getRadius() + r, 2)) {
                return true;
            }
        }
        return false;
    }

    public void calculateEvents() {

        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                events.add(new Event(p1.collides(p2), p1, p2, CollisionType.PARTICLE));
            }
            events.add(new Event(p1.collidesX(L), p1, null, CollisionType.VERTICAL_WALL));
            events.add(new Event(p1.collidesY(L), p1, null, CollisionType.HORIZONTAL_WALL));
        }
    }

    public void executeEvent() {

        Event e = events.poll();
        while (e != null && !e.isValid()){
            e = events.poll();
        }

        if (e != null){
            double dt = e.getTime();
            totalTime += dt;
            for (Particle p : particles) {
                p.updateState(dt);
            }
            List<Particle> collidingParticles = e.collide();

            // Invalido los eventos en los que participaban esta/s particula/s
            for (Event oldEvent : events){
                oldEvent.stillValid(collidingParticles);
            }

            // Calculo los nuevos eventos para las particulas que cambiaron su trayectoria
            for (int i = 0; i < collidingParticles.size(); i++) {
                Particle p1 = particles.get(i);
                for (int j = i + 1; j < particles.size(); j++) {
                    Particle p2 = particles.get(j);
                    events.add(new Event(p1.collides(p2), p1, p2, CollisionType.PARTICLE));
                }
                events.add(new Event(p1.collidesX(L), p1, null, CollisionType.VERTICAL_WALL));
                events.add(new Event(p1.collidesY(L), p1, null, CollisionType.HORIZONTAL_WALL));
            }
        }else{
            throw new IllegalStateException(); // Siempre hay un evento para correr
        }
    }

    public double getL() {
        return L;
    }

    public int getN() {
        return particles.size();
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public double getTotalCollisions(){
        return particles.stream().mapToDouble(Particle::getCollisionCount).sum();
    }
}