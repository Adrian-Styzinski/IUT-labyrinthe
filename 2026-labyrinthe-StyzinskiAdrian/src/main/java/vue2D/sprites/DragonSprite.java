/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import javafx.scene.image.Image;
import personnages.Dragon;

/**
 * Sprite affichant le dragon dans la vue
 *
 * @author choco
 */
public class DragonSprite extends ASprite {

    final static Image imageDragon = new Image("file:icons/monstre1.gif");

    /**
     * Cree le sprite d'un dragon donné.
     *
     * @param dragon le dragon à représenter
     */
    public DragonSprite(Dragon dragon) {
        super(dragon, imageDragon);
    }
}
