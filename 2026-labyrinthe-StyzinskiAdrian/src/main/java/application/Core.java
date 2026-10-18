package application;

import java.util.ArrayList;
import java.util.Collection;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import personnages.Dragon;
import personnages.Heros;
import personnages.Monstre;
import vue2D.IVue;
import vue2D.sprites.DragonSprite;
import vue2D.sprites.HerosSprite;
import vue2D.sprites.ISprite;
import vue2D.sprites.MonstreSprite;

/**
 * Classe principale du jeu. Elle cree le labyrinthe, place le heros, les
 * monstres et le dragon, puis fait tourner la boucle de jeu.
 *
 * @author arpecher
 */
public class Core {

    /**
     * Sprite du heros.
     */
    ISprite heros;

    /**
     * Labyrinthe sur lequel se deroule la partie.
     */
    ILabyrinthe labyrinthe;

    /**
     * Sprites de tout les monstres.
     */
    private ArrayList<MonstreSprite> monstres = new ArrayList<>();

    /**
     * Sprite du dragon.
     */
    private DragonSprite dragon;

    /**
     * Cree le labyrinthe.
     */
    protected void initLabyrinthe() {
        // creation du labyrinthe
        labyrinthe = new labyrinthe.LabyrintheGraphe();
    }

    /**
     * Cree le heros, les monstres et le dragon, et les ajoute a la vue.
     *
     * @param vue la vue dans laquelle ajouter les sprites créés
     */
    protected void initSprites(IVue vue) {
        // creation du heros 
        Heros h = new personnages.Heros(labyrinthe.getEntree());
        this.heros = new HerosSprite(h, labyrinthe);
        vue.add(this.heros);

        ArrayList<ISalle> monsterPositions = Monstre.populate(labyrinthe, 11);
        for (int i = 0; i < monsterPositions.size() - 1; i++) {
            ISalle pos = monsterPositions.get(i);
            Monstre monstre = new Monstre(pos);
            MonstreSprite spriteMonstre = new MonstreSprite(monstre);
            monstres.add(spriteMonstre);
            vue.add(spriteMonstre);
        }
        ISalle posDragon = monsterPositions.get(monsterPositions.size() - 1);
        Dragon dragon = new Dragon(posDragon, labyrinthe, heros);
        DragonSprite spriteDragon = new DragonSprite(dragon);
        this.dragon = new DragonSprite(dragon);
        vue.add(spriteDragon);
    }

    /**
     * Fait tourner la boucle de jeu jusqu'a ce que le heros atteigne la sortie.
     * gére les deplacement de chaque personnage, la detection des collisions et
     * fait une pause (10ms) avant le tour suivant.
     *
     * @param vue la vue dans laquelle le jeu se déroule
     */
    protected void jeu(IVue vue) {
        // boucle principale
        ISalle destination = null;
        while (!labyrinthe.getSortie().equals(heros.getPosition())) {
            // ajustement etage courant: celui du héros
            labyrinthe.setEtageCourant(heros.getPosition().getEtage());
            // choix et deplacements de chaque sprite
            for (ISprite s : vue) {
                Collection<ISalle> sallesAccessibles = labyrinthe.sallesAccessibles(s);
                destination = s.faitSonChoix(sallesAccessibles); // on demande au personnage de faire son choix de salle
                s.setPosition(destination); // deplacement

            }
            // detection des collisions
            boolean collision = false;
            ISprite monstre = null;
            for (ISprite s : vue) {
                if (s != heros) {
                    if (s.getPosition() == heros.getPosition()) {
                        System.out.println("Collision !!");
                        collision = true;
                        monstre = s;
                    }
                }
            }
            if (collision) {
                vue.remove(monstre);
                vue.remove(heros);
                System.out.println("Perdu !");
            }

            temporisation(10);
        }
        System.out.println("Gagné!");
    }

    /**
     * Met en pause le thread courant pendant nb millisecondes.
     *
     * @param nb duree de la pause en millisecondes
     */
    protected void temporisation(int nb) {
        try {
            Thread.sleep(nb); // pause de nb millisecondes
        } catch (InterruptedException ie) {
        }
    }
}
