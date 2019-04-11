package vue;

import modele.CalculateurTempsAttente;
import modele.Constante;
import observer.Observer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueTempsAttente extends JFrame implements Observer {

    private JLabel tempsMoyen;
    private ChartPanel chartPanel;

    public GraphiqueTempsAttente() {
        super("Temps d'attente moyen");
        this.setLayout(new BorderLayout());
        this.tempsMoyen = new JLabel("0 tick");
        this.tempsMoyen.setFont((new Font("Arial", Font.BOLD, 25)));
        this.add(tempsMoyen, BorderLayout.NORTH);

        this.chartPanel = new ChartPanel(ChartFactory.createBarChart(
                "Temps attente",
                "temps",
                "nombre de voitures",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, false, false));
        this.add(chartPanel, BorderLayout.SOUTH);

        this.setLocation(1100, 0);
        this.setSize(new Dimension(520, 520));
        this.setVisible(true);

        CalculateurTempsAttente.getInstance().ajouterObserver(this);
    }

    @Override
    public void update() {
        int moyenne = 0;
        for (int i : CalculateurTempsAttente.getInstance().getTempsAttente())
            moyenne += i;
        moyenne = moyenne / CalculateurTempsAttente.getInstance().getTempsAttente().size();

        // convertir en millisecondes
    //    moyenne = moyenne * Constante.tempsPause;
        this.tempsMoyen.setText(moyenne + " ticks");

        this.remove(chartPanel);
        // afficher le diagramme
        chartPanel = new ChartPanel(ChartFactory.createBarChart(
                "Temps attente moyen",
                "temps attente (x10 ticks)",
                "nombre de voitures",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, false, false));

        this.add(chartPanel, BorderLayout.SOUTH);
        this.validate();

    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        java.util.List<Double> tempsAttente = new ArrayList<>();

        for (int i = 0 ; i < 11 ; i++)
            tempsAttente.add(0.0);

        for (int i : CalculateurTempsAttente.getInstance().getTempsAttente()) {
            if (i/10 < 10)
                tempsAttente.set(i/10, tempsAttente.get(i/10) + 1);
            else
                tempsAttente.set(10,  tempsAttente.get(10) + 1);
        }

        for (int i  = 0 ; i < 11 ; i++)
            dataset.addValue(tempsAttente.get(i), "", (i==10?"10+":i+""));

        return dataset;
    }
}