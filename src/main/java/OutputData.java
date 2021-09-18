import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OutputData {

    private static final int BIG_PARTICLE = 0;
    private final List<Double> particlesVelocities;
    private final List<Double> times;
    private final List<List<TrajectoryData>> particlesTrajectories;

    public OutputData(List<Particle> particles) {
        this.times = new LinkedList<>();
        this.particlesVelocities = new LinkedList<>();
        this.particlesTrajectories = new LinkedList<>();
        this.particlesTrajectories.add(getTrajectories(particles, 0.0));
    }

    public List<TrajectoryData> getTrajectories(List<Particle> particles, double t) {
        return particles.stream()
                .map(particle -> new TrajectoryData(particle.getId(), particle.getState().getX(), particle.getState().getY(), t))
                .collect(Collectors.toList());
    }

    private List<String> doubleListToString(List<Double> list) {
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    public void addParticleTrajectories(List<Particle> particles, double t) {
        this.particlesTrajectories.add(getTrajectories(particles, t));
    }

    public List<String> getParticlesTrajectories() {
        List<String> trajectoryLines = new LinkedList<>();
        this.particlesTrajectories.forEach(tTrajectory -> tTrajectory
                        .forEach(trajectoryData -> trajectoryLines.add(trajectoryData.toString())));
        return trajectoryLines;
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

    public static int getBigParticleId() { return BIG_PARTICLE; }

    private static class TrajectoryData {
        private final int id;
        private final double x;
        private final double y;
        private final double t;

        public TrajectoryData(int id, double x, double y, double t) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.t = t;
        }

        @Override
        public String toString() {
            return id + ";" + x + ";" + y + ";" + t;
        }
    }
}