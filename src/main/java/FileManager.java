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

    public static void writeOutputFile(String fileName, List<List<Particle>> particlesEvolution, double l) {
            try {
    
                if(fileName.equals("")){
                    fileName = "positions";
                }
    
                FileWriter pos = new FileWriter(fileName + ".xyz", false);
                BufferedWriter buffer = new BufferedWriter(pos);
                boolean first = true;
                for(List<Particle> particles : particlesEvolution) {
                    if(first) {
                        first = false;
                    } else {
                        buffer.newLine();
                    }
                    buffer.write(String.valueOf(particles.size()+4));
                    buffer.newLine();
                    printDummies(buffer,l);
                    for(Particle p : particles) {
                        buffer.newLine();
                        buffer.write(p.getId() + " " + p.getRadius() + " " + p.getState().getX() + " " + p.getState().getY() + " " + p.getState().getVX() + " " + p.getState().getVY());
                        if (p.getId() == 0) {
                            buffer.write(" 255 0 0");
                        } else {
                            buffer.write(" 0 255 255");
                        }
                    }
                }
    
                System.out.println("Resultados en "+ fileName + ".xyz");
    
                buffer.flush();
                buffer.close();
                pos.close();
    
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
