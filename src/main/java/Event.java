import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Event implements Comparable<Event> {

    private final double time;
    private final Particle p1;
    private final Particle p2;
    private final CollisionType type;
    private boolean isValid;

    public Event(double t, Particle p1, Particle p2, CollisionType type) {
        this.time = t;
        this.p1 = p1;
        this.p2 = p2;
        this.type = type;
        this.isValid = true;
    }

    public List<Particle> collide(){
        if (type == CollisionType.PARTICLE){
            p1.bounce(p2);
            return Arrays.asList(p1,p2);
        }else if (type == CollisionType.HORIZONTAL_WALL){
            p1.bounceY();
            return Collections.singletonList(p1);
        }else if(type == CollisionType.VERTICAL_WALL){
            p1.bounceX();
            return Collections.singletonList(p1);
        }
        return Collections.emptyList();
    }

    public double getTime() { return time; }

    public Particle getP1() { return p1; }

    public Particle getP2() { return p2; }

    public CollisionType getType() { return type; }

    public boolean isValid() { return isValid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.getTime(), getTime()) == 0 && isValid() == event.isValid() && getP1().equals(event.getP1()) && getP2().equals(event.getP2()) && getType() == event.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getP1(), getP2(), getType());
    }

    @Override
    public int compareTo(Event event) {
        return Double.compare(this.time, event.time);
    }

    public void invalidate() { this.isValid = false; }

    public void stillValid(List<Particle> collidingParticles) {
        for(Particle p : collidingParticles){
            if (p.equals(p1) || p.equals(p2)){
                invalidate();
            }
        }
    }
}
