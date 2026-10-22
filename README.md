# Labyrinthe et Dragon : Projet de reprise (Java / JavaFX)

*Note : Ce projet a été initialement développé sur une instance GitLab interne puis importé ici pour archivage (d'où l'absence d'historique de commits).*

Application Java/JavaFX en programmation orientée objet. Un personnage évolue dans un labyrinthe pendant qu'un dragon le poursuit en calculant le plus court chemin. Projet réalisé seul dans le cadre du BUT Informatique (IUT de Bordeaux, 2026-2027), en suivant cinq objectifs successifs.

| Touche | Action |
|--------|--------|
| **← ↑ → ↓** | Déplacer le personnage |
| **M** | Monter d'un étage |
| **D** | Descendre d'un étage |

## Fonctionnalités

- **Dessin de l'étage :** Construction et affichage du labyrinthe (cases, murs) avec JavaFX.
- **Déplacement et animation :** Déplacement du personnage dans l'étage, avec animation fluide.
- **Monstres et polymorphisme :** Hiérarchie de classes pour les personnages et les monstres, chacun avec son propre comportement.
- **Améliorations visuelles et de jeu :** Gestion des murs et effet d'éclairage autour du personnage.
- **Plus courts chemins (JGraphT) :** Le labyrinthe est modélisé sous forme de graphe, et le **dragon** calcule le plus court chemin vers le joueur pour le traquer.

## Compétences mises en œuvre

Programmation orientée objet (héritage, polymorphisme, abstraction), interfaces graphiques JavaFX, modélisation en graphe, algorithmes de plus court chemin, gestion de projet avec Maven.

## Prérequis

- **Java JDK 21**
- **Maven** ou **NetBeans**
- **JavaFX** et **JGraphT** (gérées par Maven)

## Installation et lancement

```bash
git clone https://github.com/Adrian-Styzinski/IUT-labyrinthe.git
cd IUT-labyrinthe/2026-labyrinthe-StyzinskiAdrian
mvn clean javafx:run
```
