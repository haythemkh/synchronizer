/**
 * @author: Haythem Khiri
 * @project: Synchronizer Java App
 * @year: 2015
 * @license: MIT
 */
import java.util.concurrent.Semaphore;

/**
 * @author: Haythem Khiri
 */
public class MonThread extends Thread {

    private Voiture v;
    private static Semaphore sB_mutex = new Semaphore(1);
    private static Semaphore sB = new Semaphore(0);
    private static Semaphore sG_mutex = new Semaphore(1);
    private static Semaphore sG = new Semaphore(0);
    public static boolean gray = false, black = false;
    private boolean blocked = false, left = false;
    public static int nbBlack = 0, nbGray = 0, nbBlackLeft = 0, nbGrayLeft = 0;

    private int X_DEBUT_SECTION_CRITIQUE = 275, X_FIN_SECTION_CRITIQUE = 399;

    public MonThread(Voiture v){
        if(v.getCouleur() == 'b') {
            sB.release();
            nbBlack++;
        }
        if(v.getCouleur() == 'g') {
            sG.release();
            nbGray++;
        }
        this.v = v;
    }

    public static void init(){
        sB = new Semaphore(0);
        sG = new Semaphore(0);
        sB_mutex = new Semaphore(1);
        sG_mutex = new Semaphore(1);
        gray = black = false;
        nbBlack = nbGray = nbBlackLeft = nbGrayLeft = 0;
    }

    @Override
    public void run() {
        super.run();

        this.move();
    }

    public void move(){
        if(this.v.getCouleur() == 'b') {
            if(this.v.getPosX() <= X_FIN_SECTION_CRITIQUE && this.v.getPosX() >= X_DEBUT_SECTION_CRITIQUE) {
                try {
                    if(blocked == false) {
                        blocked = true;
                        sB.acquire();
                    }
                    sG_mutex.acquire();
                    if (gray == false) {
                        sG_mutex.release();
                        sB_mutex.acquire();
                        black = true;
                        sB_mutex.release();
                        int bX = this.v.getPosX();
                        this.v.setPosX(++bX);
                    } else
                        sG_mutex.release();
                    //System.out.print("B: " + sB.availablePermits() + " " + sG.availablePermits() + " " + sB_mutex.availablePermits() + " " + sG_mutex.availablePermits()  + " " + black + " " + gray + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.print("B: " + (this.v.getPosX() > X_DEBUT_SECTION_CRITIQUE) + " " + this.v.getPosX() + " " + X_DEBUT_SECTION_CRITIQUE + "\n");
                if(this.v.getPosX() > X_DEBUT_SECTION_CRITIQUE && !left) {
                    nbBlackLeft++;
                    left = true;
                }
                if(sB.availablePermits() == 0 && nbBlack == nbBlackLeft){
                    try {
                        sB_mutex.acquire();
                        black = false;
                        sB_mutex.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int bX = this.v.getPosX();
                this.v.setPosX(++bX);
            }
        }

        if (this.v.getCouleur() == 'g') {
            if (this.v.getPosX() <= X_FIN_SECTION_CRITIQUE && this.v.getPosX() >= X_DEBUT_SECTION_CRITIQUE) {
                try {
                    if(blocked == false) {
                        blocked = true;
                        sG.acquire();
                    }
                    sB_mutex.acquire();
                    if (black == false) {
                        sB_mutex.release();
                        sG_mutex.acquire();
                        gray = true;
                        sG_mutex.release();
                        int gX = this.v.getPosX();
                        this.v.setPosX(--gX);
                    } else
                        sB_mutex.release();
                    //System.out.print("G: " + sB.availablePermits() + " " + sG.availablePermits() + " " + sB_mutex.availablePermits() + " " + sG_mutex.availablePermits()  + " " + black + " " + gray + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if(this.v.getPosX() < X_FIN_SECTION_CRITIQUE && !left) {
                    nbGrayLeft++;
                    left = true;
                }
                if (sG.availablePermits() == 0 && nbGray == nbGrayLeft) {
                    try {
                        sG_mutex.acquire();
                        gray = false;
                        sG_mutex.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int gX = this.v.getPosX();
                this.v.setPosX(--gX);
            }
        }
    }
}
