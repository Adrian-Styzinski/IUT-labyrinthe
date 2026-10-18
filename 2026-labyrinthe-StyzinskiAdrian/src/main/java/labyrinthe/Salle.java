/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import java.util.Objects;

/**
 *
 * Implementation d'une salle du labyrinthe. c'est-à-dire coordonnées x/y, type
 * et étage d'appartenance.
 *
 * @author choco
 */
public class Salle implements ISalle {

    private final int x;
    private final int y;
    private final ESalle type;
    private final IEtage etage;

    /**
     * Constructeur de la classe. Crée une salle avec ses coordonnées, son type
     * et son étage.
     *
     * @param x abscisse de la salle
     * @param y ordonnee de la salle
     * @param type type de la salle
     * @param etage etage auquel appartient la salle
     */
    public Salle(int x, int y, ESalle type, IEtage etage) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.etage = etage;
    }

    /**
     * Indique si une autre salle est adjacente a celle-ci. Soit elle est juste
     * a cote sur le meme etage (horizontalement ou verticalement), soit c'est
     * un éscalier qui mene directement a l'etage du dessus ou du dessous.
     *
     * @param autre la salle a comparer
     * @return true si les deux salles sont adjacentes, false sinon
     */
    @Override
    public boolean estAdjacente(ISalle autre) {
        boolean etage = this.etage == autre.getEtage();
        boolean memesCoor = this.x == autre.getX() && this.y == autre.getY();
        boolean vertical = (autre.getX() == this.x - 1 || this.x + 1 == autre.getX()) && (this.y == autre.getY());
        boolean horizontal = (autre.getY() == this.y - 1 || this.y + 1 == autre.getY()) && (this.x == autre.getX());
        boolean escalier = memesCoor
                && ((this.type == ESalle.ESCALIER_MONTANT && autre.getType() == ESalle.ESCALIER_DESCENDANT && autre.getEtage().getNum() == this.etage.getNum() + 1)
                || (this.type == ESalle.ESCALIER_DESCENDANT && autre.getType() == ESalle.ESCALIER_MONTANT && autre.getEtage().getNum() == this.etage.getNum() - 1));

        return (etage && (vertical || horizontal)) || escalier;
    }

    /**
     * Renvoie l'abscisse de la salle.
     *
     * @return l'abscisse (coordonnée x)
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Renvoie l'ordonnee de la salle.
     *
     * @return l'ordonnee (coordonnée y)
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Renvoie le type de la salle.
     *
     * @return le type de la salle
     */
    @Override
    public ESalle getType() {
        return type;
    }

    /**
     * Renvoie l'étage auquel appartient la salle.
     *
     * @return l'étage de la salle
     */
    @Override
    public IEtage getEtage() {
        return etage;
    }

    /**
     * Calcule le hash code de la salle a partir de ses coordonnees, son type et
     * son étage.
     *
     * @return le hash coded de la salle
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        return hash;
    }

    /**
     * Compare cette salle a un autre objet. Deux salles sont égales si elles
     * ont les memes coordonnées, le meme type et le même étage.
     *
     * @param obj l'objet a comparer
     * @return true si les deux objets representent la meme salle
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salle other = (Salle) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return this.etage.getNum() == other.etage.getNum();
    }

    /**
     * Convertit la lettre lue dans le fichier en type de salle ("N" -> normale,
     * "S" -> sortie, "E" -> entree, "M" -> escalier montant, "D" -> escalier
     * descendant). renvoie une salle normale par defaut.
     *
     * @param s la lettre a convertir
     * @return le type de salle
     */
    public static ESalle salleLetterToEnumeration(String s) {
        switch (s) {
            case "N":
                return ESalle.NORMALE;

            case "S":
                return ESalle.SORTIE;

            case "E":
                return ESalle.ENTREE;

            case "M":
                return ESalle.ESCALIER_MONTANT;

            case "D":
                return ESalle.ESCALIER_DESCENDANT;

            default:
                System.err.print("Unkown string:" + s + "fallback to normal");
                return ESalle.NORMALE;
        }
    }
}
