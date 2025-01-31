# POLYNAMES
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

**POLYNAMES** est une application web inspirée du célèbre jeu d'association de mots **Codenames**. Le but du jeu est de deviner des mots liés à un indice donné par votre coéquipier, tout en évitant certains mots dangereux. Ce jeu est conçu pour être joué en ligne, avec chaque joueur utilisant son propre navigateur, ce qui permet de jouer à distance.

## Table des matières
- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Règles du jeu](#règles-du-jeu)

## Fonctionnalités

- **Grille de mots interactive** : Affiche une grille de mots qui peuvent être cliqués pour révéler leurs couleurs associées.
- **Mots codés par couleurs** : Les mots sont assignés aléatoirement avec différentes couleurs (par exemple, Bleu, Gris, Noir), ce qui influence la dynamique du jeu.
- **Communication avec le serveur** : Utilise **SSE** (Server-Sent Events) pour la communication en temps réel entre les joueurs.

## Technologies utilisées

- **Frontend** :
    - HTML5
    - CSS3
    - JavaScript

- **Backend** :
    - Java
    - MySQL

## Règles du jeu

### Créer une partie
Un premier joueur se connecte à Polynames et demande la création d'une partie. Il intègre la partie et obtient un code unique qu'il pourra transmettre à son partenaire pour que ce dernier rejoigne la partie à son tour.

### Début de la partie
Dans chaque partie, il y a un Maître des mots (qui doit faire deviner les mots) et un Maître de l'intuition (qui doit deviner les mots). Soit les joueurs choisissent leur rôle, soit le jeu le détermine pour eux aléatoirement. Le choix est définitif pour la partie.

Au début de la partie, 25 mots sont tirés aléatoirement et représentés dans une grille de 5 x 5 mots. Le Maître des mots voit les mots qu'il doit faire deviner (bleus), les mots neutres (gris) et les mots éliminatoires (noirs). Chaque tirage propose 8 mots bleus, 15 mots gris et 2 mots noirs. Un même mot ne peut pas apparaître deux fois.

Au début de la partie, le Maître des mots voit les mots et leur couleur. Le Maître des intuitions ne voit que les mots, sans distinction de couleur.

### Déroulement de la partie
À chaque tour, le Maître des mots propose un indice formé d'un et un seul mot. Par ailleurs, il indique à son partenaire le nombre de mots (N) auxquels son indice est susceptible de correspondre.

Le Maître des intuitions peut alors découvrir jusqu'à N+1 cartes. Les cartes sont découvertes une à une. Si le Maître des intuitions tombe sur une carte grise, le tour est terminé et le Maître des mots doit donner un nouvel indice. Si le Maître des intuitions découvre une carte noire, la partie est perdue.

### Score
À chaque tour, on comptabilise les points obtenus pour chaque carte bleue découverte. La première carte vaut 1 point, la seconde 2 points, la troisième 3 points, ... Si le Maître des intuitions découvre une carte bleue en N+1, cette carte vaut N² points.

Lorsqu'une carte grise est découverte, le tour s'arrête et les points obtenus sont ajoutés au score. Les cartes grises n'apportent aucun point.

Si une carte noire est découverte, la partie est perdue et le score de l'équipe tombe à 0.

