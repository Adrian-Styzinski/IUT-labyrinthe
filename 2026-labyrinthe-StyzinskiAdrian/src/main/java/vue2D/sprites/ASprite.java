/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import java.util.Collection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import labyrinthe.ISalle;
import personnages.IPersonnage;
import vue2D.AVue;

/**
 * Classe abstraite de tous les sprites. Elle relie un personnage a son
 * affichage. C'est-à-dire la gestion de l'image, des coordonnees en pixels et
 * de l'animation du déplacement case par case
 *
 * @author choco
 */
public abstract class ASprite implements ISprite {

    private final int VITESSE_DEPLACEMENT = 3;

    private Image image;
    private int x;
    private int y;
    private int XVisée;
    private int yVisée;

    private final IPersonnage personnage;

    /**
     * Crée un sprite associé a un personnage et une image.
     *
     * @param personnage le personnage représente par ce sprite
     * @param image l'image utilisee pour dessiner le sprite
     */
    public ASprite(IPersonnage personnage, Image image) {
        this.personnage = personnage;
        this.image = image;
    }

    /**
     * Crée un sprite associé a un personnage, sans image.
     *
     * @param personnage le personnage represente par ce sprite
     */
    public ASprite(IPersonnage personnage) {
        this.personnage = personnage;
    }

    /**
     * Fait avancer l'animation du sprite vers sa case visee, puis dessine son
     * image a la position courante.
     *
     * @param g le contexte graphique dans lequel dessiner
     */
    @Override
    public void dessiner(GraphicsContext g) {
        g.drawImage(image, x, y);
    }

    /**
     * Fixe les coordonnées en pixels du sprite.
     *
     * @param xpix abscisse en pixels
     * @param ypix ordonnée en pixels
     */
    @Override
    public void setCoordonnees(int xpix, int ypix) {
        this.x = xpix;
        this.y = ypix;
    }

    /**
     * Demande au personnage de choisir sa prochaine salle, sauf si le sprite
     * est encore en train de se déplacer
     *
     * @param sallesAccessibles les salles ou le personnage peut se déplacer
     * @return la salle choisie
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        Deplacer();
        if (enMouvement()) {
            return getPosition();
        }
        return personnage.faitSonChoix(sallesAccessibles);
    }

    /**
     * Renvoie la position (salle) du personnage représenté
     *
     * @return la salle ou se trouve le personnage
     */
    @Override
    public ISalle getPosition() {
        return personnage.getPosition();
    }

    /**
     * Change la position du personnage et met a jour la case visée par
     * l'animation.
     *
     * @param s la nouvelle salle du personnage
     */
    @Override
    public void setPosition(ISalle s) {
        personnage.setPosition(s);
        if (s != null) {
            this.XVisée = s.getX() * AVue.UNITE;
            this.yVisée = s.getY() * AVue.UNITE;
        }
    }

    /**
     * Indique si le sprite est encore en train de se déplacer visuellement vers
     * sa case visée.
     *
     * @return true si la position en pixels n'a pas encore atteint la case
     * visée
     */
    private boolean enMouvement() {
        return x != XVisée || y != yVisée;
    }

    /**
     * Fait avancer la position en pixels du sprite d'un pas vers sa case visée,
     * sans la depasser.
     */
    private void Deplacer() {
        if (x < XVisée) {
            x = Math.min(x + VITESSE_DEPLACEMENT, XVisée);
        } else if (x > XVisée) {
            x = Math.max(x - VITESSE_DEPLACEMENT, XVisée);
        }

        if (y < yVisée) {
            y = Math.min(y + VITESSE_DEPLACEMENT, yVisée);
        } else if (y > yVisée) {
            y = Math.max(y - VITESSE_DEPLACEMENT, yVisée);
        }
    }
}
