import java.util.Objects;

public class Event implements Comparable<Event> {

    private final double time;
    private final Particle a;
    private final Particle b;
    private final CollisionType type;
    private boolean isValid;

    public Event(double t, Particle a, Particle b, CollisionType type) {
        this.time = t;
        this.a = a;
        this.b = b;
        this.type = type;
        this.isValid = true;
    }

    public double getTime() { return time; }

    public Particle getA() { return a; }

    public Particle getB() { return b; }

    public CollisionType getType() { return type; }

    public boolean isValid() { return isValid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.getTime(), getTime()) == 0 && isValid() == event.isValid() && getA().equals(event.getA()) && getB().equals(event.getB()) && getType() == event.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getA(), getB(), getType());
    }

    @Override
    public int compareTo(Event event) {
        return Double.compare(this.time, event.time);
    }

    public void invalidate() { this.isValid = false; }
}
