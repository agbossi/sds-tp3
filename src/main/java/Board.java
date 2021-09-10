import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Board {

    private final double L;
    private List<Particle> particles;
    private final PriorityQueue<Event> events;
    private final Set<Event> collisions;

    public Board(double L, List<Particle> particles) {
        this.L = L;
        this.particles = particles;
        this.events = new PriorityQueue<>();
        this.collisions = new HashSet<>();
    }

    public static Board getRandomBoardFile(int n, double l, double minR, double minMass, double maxR, double maxMass, double maxV) {

        List<Particle> particles = new ArrayList<>();

        try {
            FileWriter st = new FileWriter("src/main/resources/newStatic.txt",false);
            BufferedWriter stBuffer = new BufferedWriter(st);
            FileWriter dyn = new FileWriter("src/main/resources/newDynamic.txt",false);
            BufferedWriter dynBuffer = new BufferedWriter(dyn);

            stBuffer.write(String.valueOf(n));
            stBuffer.newLine();
            stBuffer.write(String.valueOf(l));
            stBuffer.newLine();

            dynBuffer.write("t0");
            dynBuffer.newLine();
                       
            double x, y, vx, vy, mass, radius;
            
            // Big particle
            x = l/2;
            y = l/2;
            double v = 0;


            int i;
            for (i = 0; i < n; i++) {
                x = Math.random() * l;
                y = Math.random() * l;
                v = Math.random() * maxV;

                dynBuffer.write(String.valueOf(x));
                dynBuffer.write(" ");
                dynBuffer.write(String.valueOf(y));
                dynBuffer.write(" ");
                dynBuffer.write(String.valueOf(vx));
                dynBuffer.write(" ");
                dynBuffer.write(String.valueOf(vy));
                dynBuffer.newLine();

                stBuffer.write(String.valueOf(radius));
                stBuffer.write(" ");
                stBuffer.write(String.valueOf(mass));
                stBuffer.newLine();

                particles.add(new Particle(i, x, y, radius, mass, vx, vy));
            }

            stBuffer.flush();
            dynBuffer.flush();
            stBuffer.close();
            dynBuffer.close();
            st.close();
            dyn.close();
            System.out.println("Nuevo tablero en newStatic.txt y newDynamic.txt");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }

        return new Board(l, m, particles);
    }

    public double getL() {
        return L;
    }

    public int getN() { return particles.size(); }

    public List<Particle> getParticles() {
        return particles;
    }

/*
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(M*M);
        b.append("Board:\n");
        for (int i = 0; i < M * M; i++) {
            b.append(cells.get(i).size()).append(" ");
            if (i % M == M-1){
                b.append("\n");
            }
        }
        return b.toString();
    }
*/

}