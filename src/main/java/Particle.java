import java.util.Objects;

public class Particle implements Collideable{
    private final int id;
    private final double mass;
    private final double radius;
    private final State state;

    public Particle(int id, double x, double y, double radius, double mass, double vx, double vy) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.state = new State(x, y, vx, vy);
    }

    private double distanceFromAxis(double ax1, double ax2, double L){
        return Math.abs(ax1 - ax2);
    }

    public double calculateDistance(Particle p, double L) {
        double x = distanceFromAxis(state.getX(), p.getState().getX(), L);
        double y = distanceFromAxis(state.getY(), p.getState().getY(), L);

        return Math.sqrt(x*x + y*y) - this.radius - p.getRadius();
    }


    // Getters
    public int getId() { return id; }
    public State getState() { return state; }
    public double getRadius() { return radius; }
    public double getMass() { return mass; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id && radius == particle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, radius);
    }

    @Override
    public double collides(Collideable c) {
        return 0;
    }

    @Override
    public void bounce(Collideable c) {

    }

//    @Override
//    public String toString() {
//        StringBuilder buffer = new StringBuilder("Particle{ ID = " + id + ", V = " + v + ", Radius = " + radius);
//        buffer.append("\n\tStates = { ");
//        int i=0;
//        for(State s : states) {
//            buffer.append("t");
//            buffer.append(i++);
//            buffer.append(s);
//        }
//        buffer.append(" }");
//        return buffer.toString();
//    }


    public static class State{

        private final double x, y;
        private final double vx, vy;

        public State(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getVX() {
            return vx;
        }

        public double getVY() {
            return vy;
        }

//        @Override
//        public String toString() {
//            return " [" +
//                    "x=" + x +
//                    ", y=" + y +
//                    ", vx=" + vx +
//                    ", vy=" + vy +
//                    ", theta=" + theta +
//                    "]";
//        }
    }
}
