package simulation.modele;

public enum Direction {
    Nord,
    Sud,
    Est,
    Ouest;

    public Direction sensOppose() {
        if (this == Nord)
            return Sud;
        if (this == Sud)
            return Nord;
        if (this == Est)
            return Ouest;
        else
            return Est;
    }
}
