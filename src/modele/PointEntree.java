package modele;

import java.util.List;

public class PointEntree extends Point {
    private Direction direction;
    private Route patch;

    public PointEntree(int x, int y, Direction direction, List<Route> patches) {
        super(x, y);
        this.direction = direction;
        for (Route p : patches) {
            if (p.getPosition().getX() <= x && x <= p.getPosition().getX() + p.getLongueur() &&
                    p.getPosition().getY() <= y && y <= p.getPosition().getY() + p.getLargeur()) {
                this.patch = p;
                break;
            }
        }
    }

    public PointEntree(int x, int y, Direction direction, Route patch) {
        super(x, y);
        this.direction = direction;
        this.patch = patch;
    }

    public Direction getDirection() {
        return direction;
    }

    public Route getPatch() {
        return patch;
    }

    @Override
    public PointEntree copie() {
        return new PointEntree(x, y, direction, patch);
    }
}
