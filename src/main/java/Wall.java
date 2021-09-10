public class Wall  implements Collideable {
    private WallType type;

    public Wall(WallType type) {
        this.type = type;
    }

    public WallType getWallType() {
        return type;
    }

    @Override
    public double collides(Collideable c) {
        return 0;
    }

    @Override
    public void bounce(Collideable c) {

    }
}
