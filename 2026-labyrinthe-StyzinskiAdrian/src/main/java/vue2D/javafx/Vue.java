package vue2D.javafx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import labyrinthe.ILabyrinthe;
import labyrinthe.LabyrintheGraphe;
import vue2D.IVue;
import vue2D.AVue;
import static vue2D.javafx.Dessin.DISTANCE_LUMIERE;
import vue2D.sprites.ISprite;

/**
 * Gére la vue du labyrinthe. Crée la scene JavaFX, et gére l'ajout des sprites
 *
 * @author INFO Professors team
 */
public class Vue extends AVue implements IVue {

    Dessin dessin;
    ILabyrinthe labyrinthe;
    public Scene scene;
    ISprite heros;

    /**
     * Crée la vue JavaFX pour le labyrinthe donné
     *
     * @param labyrinthe le labyrinthe à afficher
     */
    public Vue(ILabyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        dessin = new Dessin(labyrinthe, this);
        Group root = new Group();
        this.scene = new Scene(root);
        root.getChildren().add(dessin);
    }

    /**
     * Redéssine entiérement la vue pour l'étage courant. C'est-à-dire fond,
     * salles, sprites (luminosité), puis le plus court chemin vers la sortie.
     */
    @Override
    public void dessiner() {
        // recopie du fond (image); murs + salles
        dessin.dessinFond();
        // dessin des salles
        dessin.dessinSalles(labyrinthe.getEtageCourant(), this.heros.getPosition());
        // ...
        GraphicsContext g = dessin.getGraphicsContext2D();
        double luminosite;
        for (ISprite s : this) {
            if (s.getPosition().getEtage() == labyrinthe.getEtageCourant()) {
                if (dessin.sallesVisitees.contains(s.getPosition()) && !(s instanceof EventHandler)) {
                    luminosite = Dessin.LUMINOSITE_DEJA_EXPLOREE;
                } else {
                    LabyrintheGraphe labyrintheGraphe = (LabyrintheGraphe) labyrinthe;
                    int distanceChemin = labyrintheGraphe.distanceGraphe(s.getPosition(), heros.getPosition());
                    double distance = Dessin.Manhattan(s.getPosition(), this.heros.getPosition());
                    luminosite = distanceChemin > DISTANCE_LUMIERE ? -1 : -Math.min(1.0, distance / Dessin.DISTANCE_LUMIERE);
                }
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(luminosite);
                g.setEffect(colorAdjust);
                s.dessiner(g);
                g.setEffect(null);
            }
        }
        dessin.dessinPlusCourtChemin(heros);
    }

    /**
     * Ajoute un sprite à la vue. Si le sprite "ecoute le clavier" (heros), le
     * retient comme heros de la vue.
     *
     * @param sprite le sprite a ajouter
     * @return true
     */
    @Override
    public boolean add(ISprite sprite) {
        super.add(sprite);
        // si le sprite est controle par le clavier
        if (sprite instanceof EventHandler) {
            System.out.println("registering keylistener");
            // association de l'ecouteur sur le clavier avec le composant graphique principal
            this.scene.setOnKeyPressed((EventHandler) sprite);
            this.heros = sprite;
        }
        return true;
    }

}
