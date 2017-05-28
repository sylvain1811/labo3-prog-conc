==================================
Programmation concurrente : Labo 3
==================================

************
Introduction
************

Le but de ce laboratoire est de simuler la gestion d'un aéroport. Les avions le visitent dans cet ordre :

1. Approche
2. Atterrit
3. Se gare
4. Décolle
5. S'envole

Au démarrage de la simulation, l'utilisateur choisi ces paramètres

- Nombre d'avions
- Nombre de piste d'atterrissage
- Nombre de place au terminal
- Nombre de piste de décollage
- Type d'implémentation des tampons
- Mode test
- Durée aléatoire

Si par exemple toute les pistes d'atterrissage sont occupées, l'avion en approche attendra qu'une piste se libère. Idem pour les places au terminal et les pistes de décollage.

***********
Paramétrage
***********

La durée aléatoire représente la durée des ``sleep`` des avions. Si la case est cochée, alors la durée sera choisie aléatoirement entre 1000ms et 3000ms. Si elle ne l'est pas, elle sera fixée à 100ms.

Le mode test permet de chronométrer l'application. La sortie s'affiche dans la console. La JFrame est bloquée durant l'animation à cause des ``join()`` utilisé pour le chronomètre. Pour voir l'animation, il faut désactiver le mode test.

Pour les différentes implémentations des tampons, se référer au paragraphe suivant.

**********************************
Types d'implémentation des tampons
**********************************

Pour chaque piste ou place, un tampon est utilisée. Les choix sont :

- ``BlockingQueue``
- ``LinkedList``
- ``Tableau``

Les ``BlockingQueue`` sont facile à gérer. La partie concurrente est déjà implémentée.

Pour les ``LinkedList``, la partie concurrente est gérée manuellement à l'aide de méthodes ``synchronized`` et d'un ``Object`` servant de moniteur. Idem pour les ``Tableaux``.

********************
Tests de performance
********************

Le tableau suivant représente le temps en millisecondes de l'exécution du simulateur. Les paramètres du simulateur sont : 50 avions, ``sleep`` de 100ms pour représenter la durée de l'action. Ici, les case signifie à la fois le nombre de pistes d'atterrissage, le nombre de places au terminal et le nombre de pistes de décollage. Les valeurs inscrites dans ce tableau sont des moyennes calculées à partir de 4 chronométrages.

Tableau récapitulatif
---------------------

+--------+---------------+------------+---------+
|        | BlockingQueue | LinkedList | Tableau |
+========+===============+============+=========+
| 1 case | 5930          | 5941       | 5982    |
+--------+---------------+------------+---------+
| 4 case | 2029          | 2001       | 1990    |
+--------+---------------+------------+---------+
| 8 case | 1409          | 1438       | 1463    |
+--------+---------------+------------+---------+

Nous observons que pour 1 et 8 cases, les ``BlockingQueue`` sont plus performantes. Avec 4 cases, l'implémentation avec les ``Tableaux`` est meilleure.

*******
Sources
*******

GitHub : https://github.com/sylvain1811/labo3-prog-conc

*******
Crédits
*******

Auteurs : Sylvain Renaud, Dany Chea
