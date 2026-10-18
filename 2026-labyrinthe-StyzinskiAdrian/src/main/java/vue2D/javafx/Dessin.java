package vue2D.javafx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import labyrinthe.ESalle;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import vue2D.AVue;
import vue2D.sprites.ISprite;
import labyrinthe.IEtage;
import labyrinthe.LabyrintheGraphe;

/**
 * Gére le dessin du labyrinthe sur un Canvas JavaFX. dessine fond, salles,
 * murs, et effet de lumiére autour du heros en fonction de la distance.
 *
 * @author INFO Professors team
 */
public class Dessin extends Canvas {

    public static final int DISTANCE_LUMIERE = 5;
    private ColorAdjust colorAdjust = new ColorAdjust();
    public Set<ISalle> sallesVisitees = new HashSet<>();
    final static int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    public static final double LUMINOSITE_DEJA_EXPLOREE = -0.5;
    public Set<List<Integer>> revelees = new HashSet<>();

    private Collection<ISprite> sprites;
    private int unite;

    // donnees labyrinthe labyrinthe
    private ILabyrinthe labyrinthe;
    private ISalle entree;
    private ISalle sortie;
    private int largeur;
    private int hauteur;

    private GraphicsContext tampon;
    private Image murImage;
    private Image solImage;
    private Image escalierM;
    private Image escalierD;
    private Image sortieImage;
    private Image vraiSol; // solImage est le backgorund je comprend par pourquoi l'apeller 'sol'...
    private int tailleLinkH = 6;
    private int tailleLinkL = 2;

    /**
     * Crée la fenêtre de dessin pour un labyrinthe et une collection de sprites
     * donnés. C'est-à-dire recupere les dimensions de l'étage courant, charge
     * les images et dessine le fond initial.
     *
     * @param labyrinthe le labyrinthe a dessiner
     * @param sprites les sprites a afficher
     */
    public Dessin(ILabyrinthe labyrinthe, Collection<ISprite> sprites) {
        this.labyrinthe = labyrinthe;
        this.sprites = sprites;
        this.unite = AVue.UNITE;
        entree = labyrinthe.getEntree();
        sortie = labyrinthe.getSortie();
        largeur = labyrinthe.getEtageCourant().getLargeur();
        hauteur = labyrinthe.getEtageCourant().getHauteur();
        setWidth(largeur * unite);
        setHeight(hauteur * unite);
        tampon = this.getGraphicsContext2D();
        chargementImages();
        dessinFond();
    }

    /**
     * Charge en mémoire les images utilisées pour dessiner le labyrinthe
     */
    public void chargementImages() {
        murImage = new Image("file:icons/mur0.gif");
        solImage = new Image("file:icons/pyramide.png");
        escalierM = new Image("file:icons/up.gif");
        escalierD = new Image("file:icons/down.gif");
        sortieImage = new Image("file:icons/sortie.gif");
        vraiSol = new Image("file:icons/ground.gif");
    }

    /**
     * Dessine l'image de fond sur toute la surface de la fenêtre
     */
    public void dessinFond() {
        tampon.drawImage(solImage, 0, 0, unite * largeur,
                unite * hauteur);
    }

    /**
     * Dessine toutes les salles de l'étage donné, puis les murs autour des
     * zones deja visitées par le heros.
     *
     * @param etage l'étage a dessiner
     * @param posHeros la position actuelle du heros
     */
    public void dessinSalles(IEtage etage, ISalle posHeros) {
        sallesVisitees.add(posHeros);
        for (ISalle s : etage) {
            Color c = Color.rgb(200, 200, 200);
            dessinSalle(s, c, posHeros);
        }
        dessinMurs(etage, posHeros);
    }

    /**
     * Dessine les murs autour des salles deja visitées par le heros. Pour
     * chaque case adjacente a une salle visitée qui n'est pas une salle de
     * l'étage.
     *
     * @param etage l'etage dont on dessine les murs
     * @param posHeros la position actuelle du heros
     */
    private void dessinMurs(IEtage etage, ISalle posHeros) {
        Set<List<Integer>> liste = new HashSet<>();
        for (ISalle s : etage) {
            liste.add(List.of(s.getX(), s.getY()));
        }
        for (ISalle visitee : sallesVisitees) {
            if (visitee.getEtage() == etage) {
                for (int[] d : DIRECTIONS) {
                    List<Integer> coord = List.of(visitee.getX() + d[0], visitee.getY() + d[1]);
                    this.revelees.add(coord);
                }
            }
        }
        for (List<Integer> coord : this.revelees) {
            if (!liste.contains(coord)) {
                int x = coord.get(0);
                int y = coord.get(1);
                double distance = Manhattan(x, y, posHeros.getX(), posHeros.getY());
                double luminosite = Math.max(-Math.min(1.0, distance / DISTANCE_LUMIERE), LUMINOSITE_DEJA_EXPLOREE);
                colorAdjust.setBrightness(luminosite);
                tampon.setEffect(colorAdjust);
                tampon.drawImage(murImage, x * unite, y * unite, unite,
                        unite);
                tampon.setEffect(null);
            }
        }
    }

    /**
     * Dessine une salle avec l'image correspondant a son type, avec une
     * luminosité selon qu'elle é deja ete visitée ou selon sa distance au
     * heros.
     *
     * @param s la salle à dessiner
     * @param c couleur (inutile)
     * @param posHeros la position actuelle du heros
     */
    public void dessinSalle(ISalle s, Color c, ISalle posHeros) {
        Image image;
        double luminosité;
        if (this.sallesVisitees.contains(s)) {
            luminosité = LUMINOSITE_DEJA_EXPLOREE;
        } else {
            LabyrintheGraphe labyrintheGraphe = (LabyrintheGraphe) labyrinthe;
            int distanceChemin = labyrintheGraphe.distanceGraphe(s, posHeros);
            if (distanceChemin > DISTANCE_LUMIERE) {
                luminosité = -1.0;
            } else {
                double distance = Manhattan(s, posHeros);
                luminosité = -Math.min(1.0, distance / DISTANCE_LUMIERE);
            }
        }
        colorAdjust.setBrightness(luminosité);
        tampon.setEffect(colorAdjust);

        switch (s.getType()) {
            case NORMALE:
                image = vraiSol;
                break;
            case SORTIE:
                image = sortieImage;
                break;
            case ESCALIER_DESCENDANT:
                image = escalierD;
                break;
            case ESCALIER_MONTANT:
                image = escalierM;
                break;
            default:
                image = murImage;
        }
        tampon.drawImage(image, s.getX() * unite, s.getY() * unite, unite,
                unite);
        tampon.setEffect(null);
    }

    /**
     * Surligne en jaune le plus court chemin entre la position actuelle d'un
     * sprite donne et la sortie du labyrinthe, sur l'étage courant.
     *
     * @param p le sprite donné
     */
    public void dessinPlusCourtChemin(ISprite p) {
        ISalle posHeros = p.getPosition();
        double luminosité;
        Collection<ISalle> chemin = labyrinthe.chemin(posHeros, labyrinthe.getSortie());
        tampon.setFill(Color.rgb(255, 255, 0, 0.4));

        for (ISalle s : chemin) {
            if (posHeros != s && s.getType() != ESalle.SORTIE && s.getEtage() == labyrinthe.getEtageCourant()) {
                LabyrintheGraphe labyrintheGraphe = (LabyrintheGraphe) labyrinthe;
                int distanceChemin = labyrintheGraphe.distanceGraphe(s, posHeros);
                double distance = Manhattan(s, posHeros);
                luminosité = distanceChemin > DISTANCE_LUMIERE ? -1.0 : -Math.min(1.0, distance / DISTANCE_LUMIERE);
                colorAdjust.setBrightness(luminosité);
                tampon.setEffect(colorAdjust);
                tampon.fillRect(s.getX() * unite, s.getY() * unite, unite, unite);
                tampon.setEffect(null);
            }
        }
    }

    /**
     * Calcule la distance de Manhattan entre deux points donnés par leurs
     * coordonnées.
     *
     * @param x1 abscisse du premier point
     * @param y1 ordonnée du premier point
     * @param x2 abscisse du second point
     * @param y2 ordonnée du second point
     * @return la distance de Manhattan entre les deux points
     */
    public static int Manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    /**
     * Calcule la distance de Manhattan entre deux salles.
     *
     * @param salle1 la premiere salle
     * @param salle2 la seconde salle
     * @return la distance de Manhattan entre les deux salles
     */
    public static int Manhattan(ISalle salle1, ISalle salle2) {
        return Math.abs(salle2.getX() - salle1.getX()) + Math.abs(salle2.getY() - salle1.getY());
    }

}
