package modele;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Point pt) {
        if (x > pt.x || y > pt.y)
            return Math.sqrt((x - pt.x) * (x - pt.x) + (y - pt.y) * (y - pt.y));
        else
            return -Math.sqrt((x - pt.x) * (x - pt.x) + (y - pt.y) * (y - pt.y));
    }

    public Point copie() {
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        assert (obj instanceof Point);
        return (x == ((Point) obj).x && y == ((Point) obj).y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
