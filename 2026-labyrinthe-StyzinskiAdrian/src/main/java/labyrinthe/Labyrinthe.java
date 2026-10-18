package labyrinthe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import personnages.IPersonnage;

/**
 *
 * Implementation du labyrinthe. C'est-à-dire une liste de salles réparties sur
 * plusieurs étages, avec un étage courant (celui affiché).
 *
 * @author professor team et choco
 */
public class Labyrinthe extends ArrayList<ISalle> implements ILabyrinthe {

    private IEtage etageCourant = new Etage();

    /**
     * Renvoie les salles accessibles pour un personnage donné, c'est-à-dire les
     * salles adjacentes à sa position actuelle.
     *
     * @param heros le personnage dont on veut les deplacements possibles
     * @return les salles accessibles depuis la position du personnage
     */
    @Override
    public Collection<ISalle> sallesAccessibles(IPersonnage heros) {
        //Variable sinon on appelera getPoistion plusieur fois
        ISalle pos = heros.getPosition();
        return this.stream()
                .filter(salle -> salle.estAdjacente(pos))
                .collect(Collectors.toList());
    }

    /**
     * Renvoie la salle d'entrée du labyrinthe.
     *
     * @return la salle d'entrée (null si aucune n'est trouvée)
     */
    @Override
    public ISalle getEntree() {
        return this.stream()
                .filter(salle -> salle.getType() == ESalle.ENTREE)
                .findFirst()
                .orElse(null);

    }

    /**
     * Renvoie la salle de sortie du labyrinthe.
     *
     * @return la salle de sortie (null si aucune n'est trouvée)
     */
    @Override
    public ISalle getSortie() {
        return this.stream()
                .filter(salle -> salle.getType() == ESalle.SORTIE)
                .findFirst()
                .orElse(null);
    }

    /**
     * Renvoie l'étage actuellement afficée
     *
     * @return l'étage courant
     */
    @Override
    public IEtage getEtageCourant() {
        return this.etageCourant;
    }

    /**
     * Change l'étage courant, soit, celui actuellement affiché.
     *
     * @param etage le nouvel étage courant
     */
    @Override
    public void setEtageCourant(IEtage etage) {
        this.etageCourant = etage;
    }

    /**
     * Calcule un plus court chemin entre deux salles.
     *
     * @param u la salle de départ
     * @param v la salle d'arrivée
     * @return les salles formant le plus court chemin entre u et v
     */
    @Override
    public Collection<ISalle> chemin(ISalle u, ISalle v) {
        return null;
    }

    /**
     * Crée le labyrinthe en chargeant deux étages depuis les fichiers
     * etages/etage1N.txt et etages/etage2N.txt, et fixe le premier étage comme
     * courant.
     */
    public Labyrinthe() {
        IEtage etage1 = new Etage();
        IEtage etage2 = new Etage(2);
        try {
            etage1.charger("etages/etage1N.txt");
            etage2.charger("etages/etage2N.txt");
        } catch (IOException ex) {
            System.err.println("Error during loading of the maze");
        }
        this.addAll(etage1);
        this.addAll(etage2);
        this.etageCourant = etage1;
    }
}
