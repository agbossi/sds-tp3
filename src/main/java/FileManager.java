import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class FileManager {

    public FileManager() {    }

    public static void particleInputFiles(double x, double y, double vx, double vy, double mass, double radius, BufferedWriter stBuffer, BufferedWriter dynBuffer) {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void outputFile(List<Particle> particles, String fileName) {
        if(fileName.equals("")){
            fileName = "positions";
        }
        try {
            FileWriter pos = new FileWriter(fileName + ".xyz", false);
            BufferedWriter buffer = new BufferedWriter(pos);
            buffer.write(String.valueOf(particles.size()));
            buffer.newLine();
            for(Particle p : particles) {
                buffer.newLine();
                buffer.write(p.getId() + " " + p.getState().getX() + " " + p.getState().getY() + " " + p.getState().getVX() + " " + p.getState().getVY());
            }
            buffer.flush();
            buffer.close();
            pos.close();
            System.out.println("Resultados en "+ fileName + ".xyz");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeCsv(String fileName, List<String> lines, String header) {
        try {
            FileWriter pos = new FileWriter(fileName + ".csv", false);
            BufferedWriter buffer = new BufferedWriter(pos);
            buffer.write(header);
            buffer.newLine();
            for(String line : lines) {
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
