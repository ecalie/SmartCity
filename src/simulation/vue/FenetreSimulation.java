package simulation.vue;

import simulation.modele.Constante;
import simulation.modele.Simulateur;
import simulation.modele.Ville;
import simulation.modele.Voiture;
import simulation.modele.agent.Feu;

import javax.swing.*;
import java.awt.*;

public class FenetreSimulation extends JFrame {

    private JToggleButton btnGo;
    private JSlider sliderInitialisation;
    private JSlider sliderVitesse;
    private DessinVille dessinVille;
    private Simulateur simulateur;

    public FenetreSimulation(Ville ville) {
        super("Simulation");
        dessinVille = new DessinVille(ville);
        this.simulateur = new Simulateur(ville);

        for (Voiture v : ville.getVoitures())
            v.ajouterObserver(dessinVille);

        this.setLayout(new BorderLayout());
        this.add(dessinVille, BorderLayout.CENTER);

        JPanel panelVitesse = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoutons.setPreferredSize(new Dimension(250, dessinVille.getHeight()));

        this.ajouterBoutonGo(panelBoutons, ville);
        this.ajouterSliderVoiture(panelBoutons);
        this.ajouterSliderVitesse(panelVitesse);

        this.add(panelBoutons, BorderLayout.WEST);
        this.add(panelVitesse, BorderLayout.NORTH);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajouterBoutonGo(JPanel panel, Ville ville) {
        btnGo = new JToggleButton("   Go   ");

        btnGo.addActionListener(actionEvent -> {
            if (btnGo.isSelected()) {

                simulateur.initialiser(sliderInitialisation.getValue());
                for (Voiture v : ville.getVoitures())
                    v.ajouterObserver(dessinVille);

                for (Feu f : ville.getFeux())
                    f.ajouterObserver(dessinVille);

                dessinVille.repaint();
            } else {
                simulateur.pause();
            }
        });
        panel.add(btnGo);
    }

    private void ajouterSliderVoiture(JPanel panel) {
        panel.add(new JLabel("                                                              "));
        panel.add(new JLabel("Nb voitures initialement :"));

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label = new JLabel("5 ");
        sliderInitialisation = new JSlider(0, 0, 20, 5);
        label.setLabelFor(sliderInitialisation);
        sliderInitialisation.addChangeListener(changeEvent -> label.setText(sliderInitialisation.getValue() + " "));
        sliderInitialisation.setMajorTickSpacing(5);
        sliderInitialisation.setMinorTickSpacing(1);
        sliderInitialisation.setPaintTicks(true);
        panel1.add(label);
        panel1.add(sliderInitialisation);

        panel.add(panel1);
    }

    private void ajouterSliderVitesse(JPanel panel) {
        panel.add(new JLabel("                                                              "));
        panel.add(new JLabel("Vitesse :"));

        JLabel label = new JLabel("100%");
        panel.add(label);
        sliderVitesse = new JSlider(0, 1, 20, 10);
        label.setLabelFor(sliderVitesse);
        sliderVitesse.addChangeListener(changeEvent -> {
            label.setText(sliderVitesse.getValue()*10 + "%");
            Constante.tempsPause = Constante.tempsPauseBase / sliderVitesse.getValue()*10;
        });

        sliderVitesse.setMajorTickSpacing(5);
        sliderVitesse.setMinorTickSpacing(1);
        sliderVitesse.setPaintTicks(true);

        panel.add(sliderVitesse);
    }
}
