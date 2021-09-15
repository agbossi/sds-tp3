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

    private static void printDummies(BufferedWriter buffer, double l) {
        try {
            buffer.newLine();
            buffer.write("-4 0 0 0 0 0 255 255 255");
            buffer.newLine();
            buffer.write("-3 0 " + l + " 0 0 0 255 255 255");
            buffer.newLine();
            buffer.write("-2 0 0 " + l + " 0 0 255 255 255");
            buffer.newLine();
            buffer.write("-1 0 " + l + " " + l + " 0 0 255 255 255");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static BufferedWriter createOutputFile(String fileName) {
        if(fileName.equals("")){
            fileName = "positions";
        }

        BufferedWriter buffer = null;

        try {
            FileWriter pos = new FileWriter(fileName + ".xyz", false);
            buffer = new BufferedWriter(pos);
//            System.out.println("Resultados en "+ fileName + ".xyz");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return buffer;
    }

    public static void writeOutputFile(List<Particle> particles, BufferedWriter buffer, double l) {
        try {
            buffer.write(String.valueOf(particles.size()+4));
            buffer.newLine();

            // dummies for box
            printDummies(buffer,l);

            for(Particle p : particles) {
                buffer.newLine();
                buffer.write(p.getId() + " " + p.getRadius() + " " + p.getState().getX() + " " + p.getState().getY() + " " + p.getState().getVX() + " " + p.getState().getVY());
                if(p.getId() == 0) {
                    buffer.write(" 255 0 0");
                } else {
                    buffer.write(" 0 255 255");
                }
            }

            buffer.flush();

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
            buffer.flush();
            buffer.close();
            pos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
