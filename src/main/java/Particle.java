import java.util.Objects;

public class Particle {

    private final int id;
    private int collisionCount;
    private final double mass;
    private final double radius;
    private final State state;
    public static final double UNREACHABLE = Double.MAX_VALUE;

    public Particle(int id, double x, double y, double radius, double mass, double vx, double vy) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.state = new State(x, y, vx, vy);
        this.collisionCount = 0;
    }

    public double collides(Particle p) {
        State p2 = p.getState();
        double dx = p2.getX() - state.getX();
        double dy = p2.getY() - state.getY();

        double dvx = (p2.getVX() - state.getVX());
        double dvy = (p2.getVY() - state.getVY());

        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double dvdr = dvx*dx + dvy*dy;
        double sigma = p.getRadius() + this.radius;

        double d = dvdr*dvdr - (dvx*dvx + dvy*dvy)*(drdr - sigma*sigma);

        if (d < 0 || dvdr >= 0){
            return Double.MAX_VALUE;
        }
        return -((dvdr+Math.sqrt(d)) / (dvdv));
    }

    public void bounce(Particle p) {

        double sigma = p.getRadius() + this.radius;

        State p2 = p.getState();
        double dx = p2.getX() - state.getX();
        double dy = p2.getY() - state.getY();

        double dvx = (p2.getVX() - state.getVX());
        double dvy = (p2.getVY() - state.getVY());

        double dvdr = dvx*dx + dvy*dy;
        double j = (2 * mass * p.getMass() * dvdr)/ (sigma*(mass+p.getMass()));
        double jx = (j*dx)/sigma;
        double jy = (j*dy)/sigma;

        state.vx = state.vx + jx / mass;
        state.vy = state.vy + jy / mass;

        p.getState().vx = p.getState().vx - jx / p.getMass();
        p.getState().vy = p.getState().vy - jy / p.getMass();

        this.collisionCount++;
    }

    public double collidesX(double boardLength){
        if (state.getVX() > 0){
            return (boardLength - radius - state.getX()) / state.getVX();
        }else if(state.getVX() < 0){
            return (radius - state.getX()) / state.getVX();
        }

        return UNREACHABLE;
    }

    public double collidesY(double boardLength) {
        if (state.getVY() > 0){
            return (boardLength - radius - state.getY()) / state.getVY();
        }else if(state.getVY() < 0){
            return (radius - state.getY()) / state.getVY();
        }

        return UNREACHABLE;
    }

    public void bounceX(){
        state.vx = -state.vx;
        collisionCount++;
    }

    public void bounceY(){
        state.vy = -state.vy;
        collisionCount++;
    }

    public void updateState(double dt) {
        updateX(dt);
        updateY(dt);
    }

    public void updateX(double dt){
        state.x = state.x + state.vx * dt;
    }

    public void updateY(double dt){
        state.y = state.y + state.vy * dt;
    }

    // Getters

    public CollisionType getType() {
        return CollisionType.PARTICLE;
    }

    public int getId() { return id; }
    public State getState() { return state; }
    public double getRadius() { return radius; }
    public double getMass() { return mass; }
    public int getCollisionCount(){ return collisionCount; }

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

        private double x, y;
        private double vx, vy;

        public State(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public State(State state) {
            this.x = state.getX();
            this.y = state.getY();
            this.vx = state.getVX();
            this.vy = state.getVY();
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

        public double getV() { return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)); }

    }
}
