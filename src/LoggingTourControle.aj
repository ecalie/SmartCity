import intelligence_artificielle.vue.FenetreLogging;
import simulation.modele.agent.Feu;

public aspect LoggingTourControle {

    pointcut reception(): execution (public void simulation.modele.agent.TourControle.traiterMessage(..));

    after(): reception() {
        FenetreLogging.ajouter(thisJoinPoint.getArgs()[0].toString());
    }

    pointcut envoi(): execution (public void simulation.modele.agent.TourControle.envoyerMessage(..));

    after(): envoi() {
        FenetreLogging.ajouter(thisJoinPoint.getArgs()[0].toString());
    }
}