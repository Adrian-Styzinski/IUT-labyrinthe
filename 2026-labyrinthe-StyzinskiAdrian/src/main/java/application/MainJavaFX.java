package application;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import labyrinthe.Etage;
import vue2D.javafx.Vue;

/**
 * Classe qui permet le lancement de l'application avec JavaFX. Elle crée le
 * labyrinthe et les sprites, affiche la fenetre, et lance la boucle de jeu dans
 * un nouveau thread.
 */
public class MainJavaFX extends Application {

    /**
     * Instance de la classe principale qui a le labyrinthe et la boucle de jeu.
     */
    private Core core;

    /**
     * Initialise le jeu et affiche une fenetre. Fonction Appelee
     * automatiquement par JavaFX au demarrage.
     *
     * @param stage le stage principale
     */
    @Override
    public void start(Stage stage) {
        System.out.println("Test restauration");
        core = new Core();
        core.initLabyrinthe(); // initialisation du labyrinthe
        Vue vue = new vue2D.javafx.Vue(core.labyrinthe); // creation de la vue
        core.initSprites(vue); // creation des sprites dans la vue

        stage.setScene(vue.scene);
        stage.setTitle("Labyrinthe : " + System.getProperty("user.name"));
        stage.show();

        //rafraichissement de la vue
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                vue.dessiner();
            }
        };
        timer.start();

        // boucle principale
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                core.jeu(vue); // go !
                System.out.println("fin du jeu ...");
                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    /**
     * Charge un etage de test puis lance l'application JavaFX.
     *
     * @param args un arguments de la ligne de commande (inutile)
     */
    public static void main(String[] args) {
        Etage etage = new Etage();
        try {
            etage.charger("etages/etage1N.txt");
        } catch (IOException e) {
            System.out.println("erreur");
        }
        launch();
    }

}
