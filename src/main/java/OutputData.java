import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OutputData {

    private static final int BIG_PARTICLE = 1;
    private int n;
    private final List<Double> particlesVelocities;
    private final List<TrajectoryData> bigParticleTrajectory;

    public OutputData(List<Particle> particles) {
        this.particlesVelocities = new LinkedList<>();
        this.bigParticleTrajectory = new LinkedList<>();
        this.n = particles.size();
        this.particlesVelocities.addAll(addVelocities(particles));
        Particle bigParticle = particles.stream().filter(particle -> particle.getId() == BIG_PARTICLE).findAny().get();
        this.bigParticleTrajectory.add(new TrajectoryData(bigParticle.getState(), 0.0));
    }

    public void addBigParticleTrajectory(Particle bigParticle, double t0) {
        this.bigParticleTrajectory.add(new TrajectoryData(bigParticle.getState(), t0));
    }

    public List<String> getBigParticleTrajectories() {
        return this.bigParticleTrajectory.stream()
                .map(TrajectoryData::toString).collect(Collectors.toList());
    }

    public List<Double> addVelocities(List<Particle> particles) {
        return particles.stream().filter(particle -> particle.getId() != BIG_PARTICLE)
                .map(particle -> particle.getState().getV()).collect(Collectors.toList());
    }

    public List<String> getVelocitiesForParticles() {
        return this.particlesVelocities
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public int getN() {
        return n;
    }

    private static class TrajectoryData {
        private final Particle.State state;
        private final double t0;

        public TrajectoryData(Particle.State state, double t0) {
            this.state = state;
            this.t0 = t0;
        }

        @Override
        public String toString() {
            return state.getX() + ";" + state.getY() + ";" + state.getVX() + ";" + state.getVY() + ";" + t0;
        }
    }
}
