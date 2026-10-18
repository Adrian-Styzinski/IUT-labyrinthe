/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author choco
 */
public class LabyrintheGraphTest {

    @Test
    public void testChemin() throws IOException {
        LabyrintheGraphe laby = new LabyrintheGraphe();

        ISalle entree = laby.getEntree();
        ISalle sortie = laby.getSortie();
        Collection<ISalle> chemin = laby.chemin(entree, sortie);

        assertNotNull(chemin);
        assertFalse(chemin.isEmpty());

        // Vérification de la cohérence (doit commencer par l'entrée et finir par la sortie)
        List<ISalle> cheminList = new ArrayList<>(chemin);
        assertEquals(entree, cheminList.get(0));
        assertEquals(sortie, cheminList.get(cheminList.size() - 1));
        
        
        Etage e = new Etage();
        e.charger("etages/etageInvalide3N.txt");
    }
}
