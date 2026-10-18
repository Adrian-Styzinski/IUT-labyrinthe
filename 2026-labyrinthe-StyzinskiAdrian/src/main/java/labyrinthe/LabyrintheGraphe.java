/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * Labyrinthe qui utilise un graphe (jgrapht) pour calculer les plus courts
 * chemins entre des salles avec un algorithme de parcours en largeur (BFS).
 *
 * @author choco
 */
public class LabyrintheGraphe extends Labyrinthe {

    /**
     * Graphe des salles, avec une arête entre chaque paire de salles
     * adjacentes.
     */
    private Graph<ISalle, DefaultEdge> graphe;

    /**
     * Plus court chemin entre l'entrée et la sortie (calculé a la creation)
     */
    private Collection<ISalle> cheminPlusCourt;

    /**
     * Cache des plus courts chemins déja calcules de chaque salle à chaque
     * autre salle
     */
    private HashMap<ISalle, SingleSourcePaths<ISalle, DefaultEdge>> cachedShortestDistance;
    // BFS est l'algo le plus optimiser pour un graphe non orienté et non pondéré (je crois)
    /**
     * Algorithme de calcul des plus courts chemins (BFS) sur le graphe
     */
    private BFSShortestPath<ISalle, DefaultEdge> BFS;

    /**
     * Le constructeur de la classe. Crée le labyrinthe, construit le graphe des
     * salles, prépare l'algorithme BFS et calcule le plus court chemin entre
     * l'entrée et la sortie.
     */
    public LabyrintheGraphe() {
        super();
        creerLabyrinthe();
        this.BFS = new BFSShortestPath<>(graphe);
        this.cheminPlusCourt = chemin(getEntree(), getSortie());
        distanceGraphe(this.get(1), this.get(0));
    }

    /**
     * Construit le graphe, c'est-à-dire un sommet par salle, et une arête entre
     * chaque paire de salles adjacentes.
     */
    private void creerLabyrinthe() {
        graphe = new SimpleGraph<>(DefaultEdge.class);

        for (ISalle s : this) {
            graphe.addVertex(s);
        }
        for (ISalle s1 : this) {
            for (ISalle s2 : this) {
                if (s1 != s2 && s1.estAdjacente(s2)) {
                    graphe.addEdge(s1, s2);
                }
            }
        }
    }

    /**
     * Calcule le plus court chemin entre deux salles via l'algorithme BFS sur
     * le graphe. Les resultats sont mis dans le cache pour eviter de refaire le
     * calcul à chaque appel.
     *
     * @param u la salle de depart
     * @param v la salle d'arrivee
     * @return les salles formant le plus court chemin entre u et v, ou une
     * liste vide si aucun chemin n'existe
     */
    @Override
    public Collection<ISalle> chemin(ISalle u, ISalle v) {
        remplirCache();
        GraphPath<ISalle, DefaultEdge> path = this.cachedShortestDistance.get(u).getPath(v);
        if (path != null) {
            return path.getVertexList();
        }
        return new ArrayList<>();
    }

    /**
     * Calcule la distance (nombre de salles à traverser) entre deux salles du
     * graphe. Les resultats sont mis dans le cache pour eviter de refaire le
     * calcul à chaque appel.
     *
     * @param u la salle de depart
     * @param v la salle d'arrivee
     * @return la distance entre u et v, 0 si u et v sont la même salle, ou -1
     * si aucun chemin n'éxiste entre elles
     */
    public int distanceGraphe(ISalle u, ISalle v) {
        if (u.equals(v)) {
            return 0;
        }
        remplirCache();
        SingleSourcePaths<ISalle, DefaultEdge> pathsFromU = this.cachedShortestDistance.get(u);
        GraphPath<ISalle, DefaultEdge> path = pathsFromU.getPath(v);
        // Nécessaire si un chemin est isolée (si il n'y a pas de chemin de toutes les salle à toute les salles)
        if (path != null) {
            return path.getLength();
        }
        return -1;
    }

    /**
     * Remplie cachedShortestDistance s'il n'existe pas encore, en calculant
     * (via BFS) l'arbre des plus courts chemins depuis chaque salle du graphe.
     * Ne fait rien si le cache a déjà été fait
     */
    private void remplirCache() {
        if (this.cachedShortestDistance == null) {
            this.cachedShortestDistance = new HashMap<>();
            for (ISalle source : graphe.vertexSet()) {
                this.cachedShortestDistance.put(source, this.BFS.getPaths(source));
            }
        }
    }
}
