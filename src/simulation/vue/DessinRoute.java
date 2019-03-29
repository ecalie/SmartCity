package simulation.vue;

import simulation.modele.Route;

import java.awt.*;

public class DessinRoute {

    private Route route;

    public DessinRoute(Route route) {
        this.route = route;
    }

    public void dessiner(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(route.getX(), route.getY(), route.getLongueur(), route.getLargeur());
    }
}
