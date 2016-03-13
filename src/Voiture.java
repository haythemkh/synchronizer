/**
 * @author: Haythem Khiri
 * @project: Synchronizer Java App
 * @year: 2015
 * @license: MIT
 */

/**
 * @author: Haythem Khiri
 */
public class Voiture {

    private int posX;
    private int posY;
    private char couleur;

    public Voiture(char couleur){
        this.couleur = couleur;
        if(couleur == 'b'){
            this.posX = (int) (Math.random() * (Fenetre.FRAME_WIDTH / 2 - 120));
            this.posY = Fenetre.FRAME_HEIGHT / 2 - 55;
        }
        if(couleur == 'g') {
            this.posX = (int) (Math.random() * (Fenetre.FRAME_WIDTH / 2 - 90)) + (Fenetre.FRAME_WIDTH / 2 + 60);
            this.posY = Fenetre.FRAME_HEIGHT / 2 - 25;
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public char getCouleur(){
        return this.couleur;
    }
}