/**
 * @author: Haythem Khiri
 * @project: Synchronizer Java App
 * @year: 2015
 * @license: MIT
 */
import java.awt.*;
import javax.swing.*;
import java.util.Vector;

/**
 * @author: Haythem Khiri
 */
public class Panneau extends JPanel {

    private Vector<Voiture> voitures = new Vector<Voiture>();
    private Vector<MonThread> threads = new Vector<MonThread>();
    private static final int NB_VOITURES = 3 * 2;
    private int voituresVectorLength = NB_VOITURES, i = 0;

    public Panneau(){
        for(int i = 0; i < voituresVectorLength/2; i++) {
            this.voitures.add(new Voiture('b'));
            this.voitures.add(new Voiture('g'));
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(300, 0, 100, this.getHeight());

        g.setColor(Color.BLACK);
        g.fillRect(20, 20, 10, 10);
        if(MonThread.black == true){ // feu
            g.setColor(Color.GREEN);
            g.fillRect(30, 20, 10, 10);
        } else {
            g.setColor(Color.RED);
            g.fillRect(30, 20, 10, 10);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Total: " + MonThread.nbBlack, 49, 29);

        g.setColor(Color.GRAY);
        g.fillRect(650, 20, 10, 10);
        if(MonThread.gray == true){
            g.setColor(Color.GREEN);
            g.fillRect(640, 20, 10, 10);
        } else {
            g.setColor(Color.RED);
            g.fillRect(640, 20, 10, 10);
        }
        g.setColor(Color.gray);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Total: " + MonThread.nbGray, 590, 29);

        for(Voiture v : voitures){
            if(v.getCouleur() == 'b')
                g.setColor(Color.black);
            if(v.getCouleur() == 'g')
                g.setColor(Color.gray);
            g.fillOval(v.getPosX(), v.getPosY(), 25, 25);
        }
    }

    public void ajouterVoitureNoire(){
        this.voituresVectorLength++;
        this.voitures.add(new Voiture('b'));
    }

    public void ajouterVoitureGrise(){
        this.voituresVectorLength++;
        this.voitures.add(new Voiture('g'));
    }

    public void cleanVoitures(){
        this.voitures = new Vector<Voiture>();
        this.threads = new Vector<MonThread>();
        this.voituresVectorLength = 0; i = -1;
        MonThread.init();
        this.repaint();
    }

    public void resetVoitures(){
        this.voitures = new Vector<Voiture>();
        this.threads = new Vector<MonThread>();
        this.voituresVectorLength = NB_VOITURES; i = -1;
        MonThread.init();
        for(int j = 0; j < this.voituresVectorLength/2; j++) {
            this.voitures.add(new Voiture('b'));
            this.voitures.add(new Voiture('g'));
        }
        this.repaint();
    }

    public void go() {
        while(true) {
            for (i = 0; i < this.voituresVectorLength; i++) {

                if(threads.size() == i) {
                    this.threads.add((new MonThread(this.voitures.get(i))));
                    this.threads.get(i).start();
                }

                if(threads.size() != 0) // clean -> reset
                    this.threads.get(i).move();

                this.repaint();

                try {
                    MonThread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}