/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import java.util.Collection;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import labyrinthe.ESalle;
import labyrinthe.ISalle;
import labyrinthe.ILabyrinthe;
import personnages.Heros;

/**
 * Sprite du heros. En plus de l'affichage, elle gére les entrées du clavier du
 * joueur pour choisir la prochaine salle du heros
 *
 * @author choco
 */
public class HerosSprite extends ASprite implements EventHandler<KeyEvent> {

    private Heros heros;
    private ILabyrinthe labyrinthe;

    /**
     * Crée le sprite du heros donné, dans le labyrinthe donné.
     *
     * @param heros le heros a representer
     * @param labyrinthe le labyrinthe dans lequel evolue le heros
     */
    public HerosSprite(Heros heros, ILabyrinthe labyrinthe) {
        super(heros, new Image("file:icons/link/LinkRunShieldL1.gif"));
        this.heros = heros;
        this.labyrinthe = labyrinthe;
    }

    /**
     * Traite une touche appuyee par le joueur et met a jour la salle choisie
     * par le heros en conséquence. Fléches pour se déplacer
     * horizontalement/verticalement, M/D pour monter/descendre par un éscalier.
     *
     * @param event l'évenement clavier à traiter
     */
    @Override
    public void handle(KeyEvent event) {
        ISalle pos = heros.getPosition();
        Collection<ISalle> accessibles = labyrinthe.sallesAccessibles(heros);
        switch (event.getCode()) {
            case LEFT:
                heros.setSalleChoisie(bonneSalle(accessibles, pos, -1, 0));
                break;
            case RIGHT:
                heros.setSalleChoisie(bonneSalle(accessibles, pos, 1, 0));
                break;
            case UP:
                heros.setSalleChoisie(bonneSalle(accessibles, pos, 0, -1));
                break;
            case DOWN:
                heros.setSalleChoisie(bonneSalle(accessibles, pos, 0, 1));
                break;
            case M:
                heros.setSalleChoisie(bonEtage(accessibles, pos, ESalle.ESCALIER_DESCENDANT));
                break;
            case D:
                heros.setSalleChoisie(bonEtage(accessibles, pos, ESalle.ESCALIER_MONTANT));
                break;
            default:
                System.err.println("key not handled");
        }
    }

    /**
     * Cherche, parmi les salles accessibles, celle située à un décalage (dx dy)
     * de la position donéee sur le meme etage.
     *
     * @param accessibles les salles accessibles
     * @param position la position de référence
     * @param dx décalage en x a chercher
     * @param dy décalage en y a chercher
     * @return la salle trouvee (ou null)
     */
    public ISalle bonneSalle(Collection<ISalle> accessibles, ISalle position, int dx, int dy) {
        return accessibles.stream()
                .filter(s -> s.getEtage() == position.getEtage()
                && s.getX() == position.getX() + dx
                && s.getY() == position.getY() + dy)
                .findFirst()
                .orElse(null);
    }

    /**
     * Cherche, parmi les salles accessibles, un escalier du type donné aux
     * mêmes coordonnées mais sur l'étage juste au-dessus ou juste en dessous de
     * la position donnée.
     *
     * @param accessibles les salles accessibles
     * @param position la position de reference
     * @param type le type d'escalier recherche
     * @return la salle trouvee, ou null si aucune ne correspond
     */
    public ISalle bonEtage(Collection<ISalle> accessibles, ISalle position, ESalle type) {
        return accessibles.stream()
                .filter(s -> ((s.getEtage().getNum() == position.getEtage().getNum() - 1) || (s.getEtage().getNum() == position.getEtage().getNum() + 1))
                && s.getX() == position.getX()
                && s.getY() == position.getY()
                && s.getType() == type)
                .findFirst()
                .orElse(null);
    }

}
