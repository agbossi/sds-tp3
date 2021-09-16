import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OutputData {

    private static final int BIG_PARTICLE = 0;
    private final int n;
    private final List<Double> particlesVelocities;
    private final List<Double> times;
    private final List<TrajectoryData> bigParticleTrajectory;

    public OutputData(List<Particle> particles) {
        this.times = new LinkedList<>();
        this.particlesVelocities = new LinkedList<>();
        this.bigParticleTrajectory = new LinkedList<>();
        this.n = particles.size();
        Particle bigParticle = particles.stream().filter(particle -> particle.getId() == BIG_PARTICLE).findAny().get();
        this.bigParticleTrajectory.add(new TrajectoryData(bigParticle.getState(), 0.0));
    }

    private List<String> doubleListToString(List<Double> list) {
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    public void addBigParticleTrajectory(Particle bigParticle, double t) {
        this.bigParticleTrajectory.add(new TrajectoryData(bigParticle.getState(), t));
    }

    public List<String> getBigParticleTrajectories() {
        return this.bigParticleTrajectory.stream()
                .map(TrajectoryData::toString).collect(Collectors.toList());
    }

    public void addVelocities(List<Particle> particles) {
        this.particlesVelocities.addAll(particles.stream().filter(particle -> particle.getId() != BIG_PARTICLE)
                .map(particle -> particle.getState().getV()).collect(Collectors.toList()));
    }

    public List<String> getVelocitiesForParticles() {
        return doubleListToString(this.particlesVelocities);
    }

    public void addCollisionDt(double dt) {
        this.times.add(dt);
    }

    public List<String> getTimesForParticles() {
        return doubleListToString(this.times);
    }

    public int getN() {
        return n;
    }
    public static int getBigParticleId() { return BIG_PARTICLE; }

    private static class TrajectoryData {
        private final Particle.State state;
        private final double t;

        public TrajectoryData(Particle.State state, double t0) {
            this.state = state;
            this.t = t0;
        }

        @Override
        public String toString() {
            return state.getX() + ";" + state.getY() + ";" + t;
        }
    }
}
