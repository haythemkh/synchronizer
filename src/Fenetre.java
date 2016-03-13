/**
 * @author: Haythem Khiri
 * @project: Synchronizer Java App
 * @year: 2015
 * @license: MIT
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Haythem Khiri
 */
public class Fenetre extends JFrame {

    public static final int FRAME_WIDTH = 700;
    public static final int FRAME_HEIGHT = 300;
    private Panneau panneau = new Panneau();
    private JButton boutonNoir = new JButton("+ Voiture noire");
    private JButton boutonGris = new JButton("+ Voiture grise");
    private JButton boutonReset = new JButton("Reset");
    private JButton boutonClean = new JButton("Clean");

    public static void main(String[] args) {
        new Fenetre();
    }

    public Fenetre() {
        this.setTitle("Projet: FBSD");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panneau.setLayout(new BorderLayout());

        boutonNoir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panneau.ajouterVoitureNoire();
            }
        });

        boutonGris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panneau.ajouterVoitureGrise();
            }
        });

        boutonClean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panneau.cleanVoitures();
            }
        });

        boutonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panneau.resetVoitures();
            }
        });

        JPanel south = new JPanel();
        south.add(boutonNoir);
        south.add(boutonGris);
        south.add(boutonReset);
        south.add(boutonClean);
        panneau.add(south, BorderLayout.SOUTH);
        this.setContentPane(panneau);
        this.setVisible(true);

        panneau.go();
    }
}