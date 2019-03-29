import intelligence_artificielle.vue.FenetreLogging;

public aspect LoggingTourControle {

    pointcut reception(): execution (public void simulation.modele.agent.TourControle.traiterMessage(..));

    before(): reception() {
        System.out.println("recep");
        FenetreLogging.ajouter(thisJoinPoint.getArgs()[0].toString());
    }

    pointcut envoi(): execution (public void simulation.modele.agent.TourControle.envoyerMessage(..));

    after(): envoi() {
        FenetreLogging.ajouter(thisJoinPoint.getArgs()[0].toString());
    }
}