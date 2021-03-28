# Projet ColorMemory
**Nino BELIC & Hugo BENABDELHAK**
**nino.belic@etud.u-picardie.fr & hugo.benabdelhak@etud.u-picardie.fr**
## I - Introduction

### Présentation
Notre mission a été de développer un jeu mobile qui a pour objectif d'entraîner notre mémoire. Le jeu nous renvoie une suite de couleur aléatoire que le joueur doit restituer ensuite sur les carrés de couleur présents à l’écran. 

La difficulté change selon le mode choisie, ou le niveau d’avancement de la partie (mode chrono, une couleur supplémentaire à chaque niveau, mode facile jusque expert.)
	
### Version
La version d’android utilisée est la **version 8.0** (Android Oreo) avec l’**API 26**.


### Répartition
Afin d’optimiser la productivité de notre projet, nous avons séparé les tâches en deux. 
Hugo: s’est occupé principalement de la partie __Système d’Authentification__ (II)
Nino : s’est occupé principalement de la partie __Jeu__ (III)

### Gestion du projet
Pour permettre un suivi constant du travail réalisé, et de pouvoir gérer le projet depuis n’importe où, nous avons opté pour GitHub. Le système de branche et de fusion a donc été très utile.



## II - Système d’Authentification

### II.A - Interface graphique

Lors du lancement de l’application, nous sommes redirigés sur la page de connexion <span style="color: orange">**MainActivity.java**</span> il est demandé de saisir son email et son mot de passe. Dans le cas où l'utilisateur saisit les bonnes données pour se connecter, l’activité du menu principal du jeu se lance (<span style="color: orange">**DifficultySelector.java**</span>)et le score du joueur connecté est affiché. 

Si l’utilisateur n’est pas inscrit, un bouton <span style="color: red">**Register**</span> qui redirige sur l’activité d’inscription <span style="color: orange">**SignUpActivity.java**</span>. Pour s’inscrire il est demandé de renseigner son **nom, email, mot de passe, sexe et date de naissance**. Les erreurs sont retransmises via des pop-up (Toast) qui ne gène en rien l’utilisation de l’application.

Dans les deux activités précédentes, chaque bouton possède un attribut onClick qui exécute une fonction.

### II.B - Vérification saisies utilisateurs

Il est important, d’un point de vue développeur, de ne jamais faire confiance aux utilisateurs. C’est pour cela, qu’avant chaque requête envoyé vers la base de données, une vérification est effectuée (expression régulière pour les mails, vérification de longueur pour les mots de passe et noms, une présence de tous les champs lors de l’inscription).

### II.C - Choix du système de base de données

Notre site ayant besoin d’un système de connexion/inscription et d’un suivi de score individuel, il était nécessaire de devoir stocker les informations quelque part. Le SGBDR utilisé pour l’application est SQLite, c’est le système de base de données embarqué et open source le plus connu. 

### II.D - Implémentation de SQLite

Afin d’implémenter notre base de données,il a fallu mettre en place des **classes** (<span style="color: blue">**User.java & Score.java**</span>) qui définissent la structure de nos objets à insérer dans la base de données. (Un objet = une table en base de données, une propriété d’objet = un attribut d’une table). 

Ensuite, les classes UserManager.java & ScoreManager.java permettent à nos objets et à SQLite d'interagir ensemble. Ces classes contiennent les méthodes CRUD basiques (Création, Lecture, Modification, Suppression) suivies par les fonctions permettant d’ouvrir ou de fermer l'accès à la base de données. 

Le principal défaut est que SQLite permet uniquement de gérer une base de données locale. Cependant, la base de données reste bien sauvegardée dans les fichiers de l’appareil, cela permet donc, même après une fermeture complète de l’application, de pouvoir réutiliser les données insérées précédemment.

La dernière partie, qui est la plus importante de tous, est de pouvoir donner à notre application la fonctionnalité permettant de créer une base de données. La classe MySQLite.java qui hérite SQLiteOpenHelper est celle qui ajoute cette fonctionnalité.
 


## III - Jeu

### Intro

Le fonctionnement du jeu est géré séquentiellement. Nous avons deux activités principales : <span style="color: blue">**difficultySelector.java et difficultyStart.java**</span>, qui servent respectivement à sélectionner notre difficulté, puis à lancer la partie en fonction du mode de difficulté choisi. 

Visuellement, nous utilisons aussi un sélecteur <span style="color: orange">**activity_difficulty_selector.xml**</span> et une activité <span style="color: orange">**activity_difficulty.xml**</span> qui correspond à la partie en elle-même : elle ne varie pas en fonction de la difficulté.

### III.A.1 - Sélecteur de difficulté

Pour ce qui est du sélecteur de difficulté, il faut choisir les paramètres en fonction de la difficulté. Pour cela, nous utilisons une fonction pour chaque niveau de difficulté : 	

Par exemple, <span style="color: red">**startNiveauFacile()**</span>, qui stocke dans un **intent** l’extra : “VIES” à 2 (et les autres paramètres), puis lance l’activité <span style="color: blue">**difficultyStart.java**</span>. (Chaque méthode lanceuse de niveau est relié à la propriété <span style="color: red">**"onClick"**</span> du bouton correspondant, dans <span style="color: blue">**difficultySelector.java**</span>)


### III.A.2 - Autre méthodes

Nous surchargerons <span style="color: red">**onActivityResult()**</span> afin récupérer les points réalisés lors de la partie et les ajouter au score général (variable globale). A noter que nous avions au départ fait un système de validation de niveau :
 
Il fallait par exemple valider le niveau Facile pour accéder au niveau Moyen.
Ce système de progression était géré au niveau de cette même méthode <span style="color: red">**onActivityResult()**</span>... Toutefois, nous avons décidé de retirer ce système mais nous avons laissé le code au cas où on désirerait le rajouter.

	[Remarque]
	Nous avons fait une petite méthode displayInfo(), qui affiche des informations sur chaque niveau de difficulté, et ce en cliquant sur le boutons “?”, dans le sélecteur de difficulté (onClick).


### III.B.1 - Lancement du mode

Au lancement du mode de difficulté choisi, soit dans <span style="color: blue">**difficultyStart.java**</span>, on récupère l’**intent** et donc les extras correspondant au mode. Nous ne détaillerons pas chaque méthode mais celles-ci fonctionnent en “cascade”, grâce aux paramètres chargés dans <span style="color: red">**onCreate()**</span> (dans des variables globales donc)

Ainsi, <span style="color: red">**startDifficulty()**</span> appelle <span style="color: red">**startNiveau()**</span>, qui appelle <span style="color: red">**startManche()**</span>, qui appelle <span style="color: red">**switchOnBouton()**</span>, méthode récursive qui s’appelle elle-même un certain nombre fois, en fonction de la progression dans le mode choisi de la partie.


### III.B.2 - Séquence de boutons

Comme dit précédemment, la séquence aléatoire de bouton est gérée par <span style="color: red">**switchOnBouton()**</span>, par récursivité. Une fois la séquence terminée, les boutons sont “clickables” grâce à <span style="color: red">**listenSequence()**</span> qui créer des **onClickListener** pour chaque bouton.

Une fois que le bon nombre de boutons sont cliqués, les séquences sont comparées (séquence **aléatoire** & séquence **de l’utilisateur**). Si elles correspondent, c’est gagné (animation <span style="color: green">verte</span>) et on passe à la manche suivante, sinon c’est perdu (animation <span style="color: red">rouge</span>).


### III.B.3 - Difficulté > Niveau > Manche > Séquence

- Il y a 4 **difficultés** : chacune est divisée en 7 **niveaux**. 
- A chaque **niveau**, on ajoute un bouton (4 à 10 boutons). Chaque **niveau** comporte un nombre défini de **manches** (énoncé : par exemple, le mode facile comporte 10 manches par niveau. Des points sont attribués à la fin d’un niveau.
- Pour chaque **manche**, il y a une **séquence**.


### III.C - Mode Chrono

Le mode Chrono a un fonctionnement un peu différent des autres modes. Pour commencer, son nombre de **manche** n'est pas limité (en vérité, nous avons mis un maximum de 250 manches). 

Pour le système de séquence, nous n’utilisons pas la méthode <span style="color: red">**listenSequence()**</span> mais <span style="color: red">**chronoSequence()**</span> qui en plus des **onClickListener** pour les boutons, valide la séquence au bout d’un certain temps (environ 1 seconde par bouton, par exemple : pour la manche 5, 5 boutons sont allumés, 5 secondes environ), avec <span style="color: red">**chronoFinish()**</span>.

Dans le mode Chrono, comme il n’y a pas de principe de niveau, mais juste des manches, il y a toujours 4 boutons, et les points sont donnés toutes les 5 manches.


### III.D - Petites méthodes utilitaires
Nous utilisons quelques petites méthodes d’aide:
- <span style="color: red">**findFirstFreeIndex()**</span> : trouver le 1er emplacement libre d’un tableau
- <span style="color: red">**compareTwoTab()**</span> : pour comparer 2 tableaux : utilise pour les 2 séquences
- <span style="color: red">**setEnableButtons()**</span> : activer / désactiver tous les boutons (au joueur d’appuyer)
- <span style="color: red">**displayButtons()**</span> : afficher les boutons voulus (à chaque niveau, + 1 bouton)


### III.E - Gestion des points
- Une fois les points donnés (donc en pleine partie), ils sont stockés dans **n_POINTS**
- Une fois la fin de la partie, ils sont retournés par l’**intent**, puis ajoutés au score du joueur (variable globale).
- Une fois que le joueur ne veut plus jouer, il se déconnecte : les points sont stockés dans la base de données.

