import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Ej4b {

    public static void main(String[] args) {
        int n = 135;
        int maxV = 2;
        double l = 6;
        int iterations = 10000;

        Board test = Board.getRandomBoard(n, l, 0.2, 0.9, 0.7, 2, maxV, false);
        System.out.println("New board created");
        test.calculateEvents();
        for (int j = 0; j < iterations; j++) {
            if (j % 1000 == 0) {
                System.out.println("Events executed: " + j);
            }
            test.executeEvent(j >= iterations - 0.3 * iterations);
        }
        List<OutputData.TrajectoryData> data = test.getOutputData().getParticlesTrajectories().stream().filter(td -> td.id != 0).collect(Collectors.toList());

        FileManager.writeCsv("msd-little-particles-calc", data, OutputData.TrajectoryData::toString,"id;x;y;t0");
    }
}
