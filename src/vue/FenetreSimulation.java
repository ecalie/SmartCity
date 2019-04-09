package vue;

import modele.Constante;
import modele.Simulateur;
import modele.Ville;
import modele.agent.Feu;
import modele.agent.Voiture;

import javax.swing.*;
import java.awt.*;

public class FenetreSimulation extends JFrame {

    private JToggleButton btnGo;
    private JSlider sliderVoitures;
    private JSlider sliderPrioritaires;
    private JSlider sliderVitesse;
    private DessinVille dessinVille;
    private Simulateur simulateur;
    private boolean premierGo;

    public FenetreSimulation(Ville ville) {
        super("Simulation");
        dessinVille = new DessinVille(ville);
        this.simulateur = new Simulateur(ville);
        this.premierGo = true;

     /*   for (Voiture v : ville.getVoitures())
            v.ajouterObserver(dessinVille);*/

        this.setLayout(new BorderLayout());
        this.add(dessinVille, BorderLayout.CENTER);

        JPanel panelVitesse = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoutons.setPreferredSize(new Dimension(250, dessinVille.getHeight()));

        this.ajouterBoutonGo(panelBoutons, ville);
        this.ajouterSliderVoitures(panelBoutons);
        this.ajouterSliderPrioritaires(panelBoutons);
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

                if (premierGo) {
                    premierGo = false;
                    for (Feu f : ville.getFeux())
                        f.ajouterObserver(dessinVille);
                }

                simulateur.initialiser(sliderVoitures.getValue(), sliderPrioritaires.getValue());
                for (Voiture v : ville.getVoitures())
                    v.ajouterObserver(dessinVille);

                dessinVille.repaint();
            } else {
                simulateur.pause();
            }
        });
        panel.add(btnGo);
    }

    private void ajouterSliderVoitures(JPanel panel) {
        panel.add(new JLabel("                                                              "));
        panel.add(new JLabel("Nb voitures :"));

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label = new JLabel("5 ");
        sliderVoitures = new JSlider(0, 0, 20, 5);
        label.setLabelFor(sliderVoitures);
        sliderVoitures.addChangeListener(changeEvent -> label.setText(sliderVoitures.getValue() + " "));
        sliderVoitures.setMajorTickSpacing(5);
        sliderVoitures.setMinorTickSpacing(1);
        sliderVoitures.setPaintTicks(true);
        panel1.add(label);
        panel1.add(sliderVoitures);

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
            label.setText(sliderVitesse.getValue() * 10 + "%");
            Constante.tempsPause = Constante.tempsPauseBase / sliderVitesse.getValue() * 10;
        });

        sliderVitesse.setMajorTickSpacing(5);
        sliderVitesse.setMinorTickSpacing(1);
        sliderVitesse.setPaintTicks(true);

        panel.add(sliderVitesse);
    }

    private void ajouterSliderPrioritaires(JPanel panel) {
        panel.add(new JLabel("                                                              "));
        panel.add(new JLabel("Nb voitures prioritaires :"));

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label = new JLabel("1 ");
        sliderPrioritaires = new JSlider(0, 0, 5, 1);
        label.setLabelFor(sliderPrioritaires);
        sliderPrioritaires.addChangeListener(changeEvent -> label.setText(sliderPrioritaires.getValue() + " "));
        sliderPrioritaires.setMajorTickSpacing(1);
        sliderPrioritaires.setPaintTicks(true);
        panel1.add(label);
        panel1.add(sliderPrioritaires);

        panel.add(panel1);
    }
}
