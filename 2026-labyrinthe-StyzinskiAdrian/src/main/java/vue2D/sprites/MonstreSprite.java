/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import javafx.scene.image.Image;
import personnages.Monstre;

/**
 * Sprite affichant un monstre dans la vue.
 *
 * @author choco
 */
public class MonstreSprite extends ASprite {

    final static Image imageMonstre = new Image("file:icons/monstre0.gif");

    /**
     * Cree le sprite d'un monstre donné
     *
     * @param monstre le monstre a représenter
     */
    public MonstreSprite(Monstre monstre) {
        super(monstre, imageMonstre);
    }
}
