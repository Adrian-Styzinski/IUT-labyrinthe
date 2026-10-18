/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.ArrayList;
import java.util.Collection;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import vue2D.sprites.ISprite;

/**
 * Personnage qui poursuit le heros à travers le labyrinthe en suivant le plus
 * court chemin vers sa position, avec un délai entre chaque deplacement.
 *
 * @author choco
 */
public class Dragon extends APersonnage {

    private static final long DELAI_DEPLACEMENT = 300;

    private long dernierMouvement = System.currentTimeMillis();
    private ILabyrinthe labyrinthe;
    private ISprite heros;
    private ISalle cachedHeroPosition;
    private ArrayList<ISalle> cachedChemin;
    private int cachedIndex;

    /**
     * Constructeur de la classe. Crée un dragon à une position donnée, qui
     * poursuivra le heros fourni dans le labyrinthe donné.
     *
     * @param pos position initiale du dragon
     * @param labyrinthe labyrinthe dans lequel evolue le dragon
     * @param heros sprite du heros a poursuivre
     */
    public Dragon(ISalle pos, ILabyrinthe labyrinthe, ISprite heros) {
        this.setPosition(pos);
        this.labyrinthe = labyrinthe;
        this.heros = heros;
    }

    /**
     * Choisit la prochaine salle du dragon. Si le heros n'a pas bougé depuis le
     * dernier calcul, réutilise le chemin deja calcule (avec uncache) pour
     * avancer d'une case. Sinon recalcule le plus court chemin vers le heros et
     * avance vers celui-ci
     *
     * @param sallesAccessibles les salles ou le dragon peut se déplacer
     * @return la salle choisie par le dragon
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        if (System.currentTimeMillis() - dernierMouvement < DELAI_DEPLACEMENT || sallesAccessibles.isEmpty()) {
            return getPosition();
        }
        dernierMouvement = System.currentTimeMillis();
        if (this.cachedHeroPosition == heros.getPosition()) {
            this.cachedIndex++;
            return cachedChemin.get(this.cachedIndex);
        }
        ArrayList<ISalle> chemin = (ArrayList<ISalle>) labyrinthe.chemin(this.getPosition(), heros.getPosition());
        if (chemin.size() < 2) {
            return getPosition();
        }
        if (sallesAccessibles.contains(chemin.get(1))) {
            this.cachedHeroPosition = heros.getPosition();
            this.cachedChemin = chemin;
            this.cachedIndex = 1;
            return chemin.get(1);
        }
        return getPosition();
    }
}
