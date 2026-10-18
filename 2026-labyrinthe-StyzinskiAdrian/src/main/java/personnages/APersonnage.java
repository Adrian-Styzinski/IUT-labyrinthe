/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import labyrinthe.ISalle;

/**
 *
 * Classe abstraite commune à tous les personnages du jeu. Elle gére uniquement
 * la position du personnage dans le labyrinthe.
 *
 * @author choco
 */
public abstract class APersonnage implements IPersonnage {

    private ISalle position;

    /**
     * Renvoie la position actuelle du personnage.
     *
     * @return la salle ou se trouve le personnage
     */
    @Override
    public ISalle getPosition() {
        return position;
    }

    /**
     * Change la position du personnage.
     *
     * @param s la nouvelle salle du personnage
     */
    @Override
    public void setPosition(ISalle s) {
        this.position = s;
    }
}
