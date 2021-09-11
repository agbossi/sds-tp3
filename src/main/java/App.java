public class App {

    private final static int MIN_N = 100;
    private final static int MAX_N = 150;

    public static void main( String[] args ) {
        int n =(int) (Math.random()*(MAX_N-MIN_N)) + MIN_N;
        System.out.println(n);
        Board test = Board.getRandomBoard(n,6,0.2,0.9,0.7,2,2,true);
        FileManager.outputFile(test.getParticles(),"positions");
    }
}
