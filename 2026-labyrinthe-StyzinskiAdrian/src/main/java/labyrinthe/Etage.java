package labyrinthe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Implementation d'un etage du labyrinthe. Un etage est une liste de salles
 * avec une largeur, une hauteur et un numero.
 *
 * @author INFO Professors team
 */
public class Etage extends ArrayList<ISalle> implements IEtage {

    /**
     * La largeur de l'étage
     */
    private int largeur;
    /**
     * La hauteur de l'étage
     */
    private int hauteur;
    /**
     * Le numéro de l'étage
     */
    private int num;

    /**
     * Constructeur par défaut. Crée un etage vide avec une taille par defaut
     * (40x40) et de numéro 1.
     */
    public Etage() {
        largeur = 40;
        hauteur = 40;
        num = 1;
    }

    /**
     * Cree un étage vide avec le numéro donné.
     *
     * @param id le numéro de l'etage
     */
    public Etage(int id) {
        this.num = id;
    }

    /**
     * Charge les salles de l'etage depuis un fichier txt. La premiere ligne
     * donne la largeur et la hauteur, les lignes suivantes donnent la position
     * et le type de chaque salle.
     *
     * @param file chemin du fichier a charger
     * @throws IOException si le fichier ne peut pas être lu
     */
    @Override
    public void charger(String file) throws IOException {
        this.clear();
        List<String> lignes = Files.readAllLines(Paths.get(file));
        // dimensions
        String entete = lignes.get(0);
        String[] mots = entete.split(" ");
        largeur = Integer.parseInt(mots[0]);
        hauteur = Integer.parseInt(mots[1]);
        lignes.remove(0);
        // salles
        for (String ligne : lignes) {
            mots = ligne.split(" ");
            // ajout d'une salle  
            this.add(new Salle(Integer.parseInt(mots[0]), Integer.parseInt(mots[1]), Salle.salleLetterToEnumeration(mots[2]), this));
        }
    }

    /**
     * Renvoie la largeur de l'étage.
     *
     * @return la largeur de l'étage
     */
    @Override
    public int getLargeur() {
        return largeur;
    }

    /**
     * Renvoie la hauteur de l'étage.
     *
     * @return la hauteur de l'étage.
     */
    @Override
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Renvoie le numéro de l'étage.
     *
     * @return le numéro de l'étage
     */
    @Override
    public int getNum() {
        return num;
    }

}
