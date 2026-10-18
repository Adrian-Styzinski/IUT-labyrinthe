/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import labyrinthe.ESalle;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;

/**
 * Personnage qui se déplace aleatoirement dans le labyrinthe, avec un delai
 * aléatoire entre chaque deplacement (modifiable).
 *
 * @author choco
 */
public class Monstre extends APersonnage {

    private final Random RANDOM = new Random();
    private static final long DELAI_DEPLACEMENT = 750;

    //pour augmenter "l'aléatoire" des mouvement entre chaque monstre
    private static final int DELAI_DEPLACEMENT_AJOUT_MAX = 400;
    private static final int DELAI_DEPLACEMENT_AJOUT_MIN = 20;

    private long dernierMouvement = System.currentTimeMillis();

    /**
     * Crée un monstre a la position donnée.
     *
     * @param pos position initiale du monstre
     */
    public Monstre(ISalle pos) {
        this.setPosition(pos);
    }

    /**
     * Choisit la prochaine salle du monstre au hasard parmi les salles
     * accessibles (avec un délai)
     *
     * @param sallesAccessibles les salles ou le monstre peut se déplacer
     * @return la salle choisie par le monstre
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        if (System.currentTimeMillis() - dernierMouvement < DELAI_DEPLACEMENT) {
            return getPosition();
        }

        dernierMouvement = System.currentTimeMillis() + DELAI_DEPLACEMENT_AJOUT_MIN + RANDOM.nextInt(DELAI_DEPLACEMENT_AJOUT_MAX - DELAI_DEPLACEMENT_AJOUT_MIN);
        if (sallesAccessibles.isEmpty()) {
            return getPosition();
        }
        ArrayList<ISalle> liste = new ArrayList<>(sallesAccessibles);
        return liste.get(RANDOM.nextInt(liste.size()));
    }

    /**
     * Tire au hasard n salles "normales" du labyrinthe, pour y placer des
     * monstres.
     *
     * @param labyrinthe le labyrinthe dans lequel chercher des salles
     * @param n le nombre de salles a tirer
     * @return une liste de n salles normales tirées au hasard
     */
    public static ArrayList<ISalle> populate(ILabyrinthe labyrinthe, int n) {
        List<ISalle> sallesPossible = new ArrayList<>((Collection<ISalle>) labyrinthe).stream()
                .filter(s -> s.getType() == ESalle.NORMALE)
                .collect(Collectors.toList());

        if (n > sallesPossible.size()) {
            System.err.println("Error during the populating of the monsters, not enough correct rooms to accomodate the number of monsters");
            return null;
        }
        Collections.shuffle(sallesPossible);
        return new ArrayList<>(sallesPossible.subList(0, n));
    }

    /**
     * Tire au hasard 11 salles "normales" du labyrinthe, pour y placer des
     * monstres.
     *
     * @param labyrinthe le labyrinthe dans lequel chercher des salles
     * @return une liste de 11 salles normales tirées au hasard
     */
    public static ArrayList<ISalle> populate(ILabyrinthe labyrinthe) {
        return populate(labyrinthe, 11);
    }

}
