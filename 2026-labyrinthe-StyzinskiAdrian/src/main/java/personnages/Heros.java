/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.Collection;
import labyrinthe.ISalle;

/**
 * Personnage contrôlé par le joueur. Il se déplace vers la salle choisie
 * (fléches du clavier et M/D) quand celle-ci est accessible.
 *
 * @author choco
 */
public class Heros extends APersonnage {

    /**
     * Salle vers laquelle le heros veut se deplacer.
     */
    private ISalle salleChoisie;

    /**
     * Constructeur de la classe. Crée un heros a la position donnée.
     *
     * @param position position initiale du heros
     */
    public Heros(ISalle position) {
        this.setPosition(position);
        this.salleChoisie = position;
    }

    /**
     * Choisit la prochaine salle du heros. C'est-à-dire la salle choisie par le
     * joueur si elle fait partie des salles accessibles (sinon sa position
     * actuelle).
     *
     * @param sallesAccessibles les salles ou le heros peut se deplacer
     * @return la salle choisie par le heros
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        boolean estAccessibles = sallesAccessibles.stream()
                .anyMatch(n -> n.equals(salleChoisie));

        if (estAccessibles) {
            return salleChoisie;
        }
        return getPosition();
    }

    /**
     * Setter pour salleChoisie.
     *
     * @param salleChoisie la salle choisie par le heros
     */
    public void setSalleChoisie(ISalle salleChoisie) {
        this.salleChoisie = salleChoisie;
    }
}
