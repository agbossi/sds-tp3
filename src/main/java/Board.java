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

    private final OutputData outputData;
    private final static int bigId = OutputData.getBigParticleId();

    public Board(double L, List<Particle> particles) {
        this.L = L;
        this.totalTime = 0;
        this.particles = particles;
        this.events = new PriorityQueue<>(Event::compareTo);
        this.outputData = new OutputData(this.particles);
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

            particles.add(new Particle(bigId, x, y, maxR, maxMass, vx, vy));

            // Little particles
            double v, theta;
            Random random = new Random();
            for (int i = 1; i <= n; i++) {
                do {
                    x = ThreadLocalRandom.current().nextDouble(minR, l - minR);
                    y = ThreadLocalRandom.current().nextDouble(minR, l - minR);
                } while (overlap(x, y, minR, particles));
//                v = random.nextDouble() * maxV;
                v = Math.random() * maxV;
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
                Event e = new Event(p1.collides(p2), p1, p2, CollisionType.PARTICLE);
                if(Double.compare(e.getTime(), Double.MAX_VALUE) != 0)
                    events.add(e);
            }
            events.add(new Event(p1.collidesX(L), p1, null, CollisionType.VERTICAL_WALL));
            events.add(new Event(p1.collidesY(L), p1, null, CollisionType.HORIZONTAL_WALL));
        }
    }

    public void executeEvent(boolean saveVelocity) {

        Event e = events.poll();
        while (e != null && !e.isValid()){
            e = events.poll();
        }

        double dt = e.getTime();
        totalTime += dt;
        for (Particle p : particles) {
            p.updateState(dt);
        }
        List<Particle> collidingParticles = e.collide();

        // guardo las posiciones de las particulas para reconstruir trayectoria de la grande
        // y para calcular los DCM
        outputData.addParticleTrajectories(this.particles, totalTime);

        // para la distribucion de tiempos de colision
        outputData.addCollisionDt(dt);

        // guardo las velocidades de todas las particulas para el
        // tiempo donde se produjo colision
        if(saveVelocity)
            outputData.addVelocities(particles);


        // Invalido los eventos en los que participaban esta/s particula/s
        for (Event oldEvent : events){
            oldEvent.stillValid(collidingParticles, dt);
        }

        // Calculo los nuevos eventos para las particulas que cambiaron su trayectoria
        for (Particle p1 : collidingParticles) {
            for (Particle p2 : particles) {
                if (!p1.equals(p2)) {
                    Event event = new Event(p1.collides(p2), p1, p2, CollisionType.PARTICLE);
                    if(Double.compare(event.getTime(), Double.MAX_VALUE) != 0)
                        events.add(event);
                }
            }
            events.add(new Event(p1.collidesX(L), p1, null, CollisionType.VERTICAL_WALL));
            events.add(new Event(p1.collidesY(L), p1, null, CollisionType.HORIZONTAL_WALL));
        }
    }

    public OutputData getOutputData() { return outputData; }

    public List<Particle> getParticles() { return particles; }

    public List<Particle> getParticlesForLogging() {
        final List<Particle> particles = new LinkedList<>();
        this.particles
                .forEach(particle -> particles.add(
                        new Particle(particle.getId(),
                                particle.getState().getX(),
                                particle.getState().getY(),
                                particle.getRadius(),
                                particle.getMass(),
                                particle.getState().getVX(),
                                particle.getState().getVY())));
        return particles;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public double getTotalCollisions(){
        return particles.stream().mapToDouble(Particle::getCollisionCount).sum();
    }
}