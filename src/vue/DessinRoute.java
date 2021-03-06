package vue;

import modele.Route;

import java.awt.*;

public class DessinRoute {

    private Route route;

    public DessinRoute(Route route) {
        this.route = route;
    }

    public void dessiner(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(route.getPosition().getX(), route.getPosition().getY(), route.getLongueur(), route.getLargeur());
    }
}
