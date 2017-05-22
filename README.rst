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
- Type d'implémentation de ``Queue``

Si par exemple toute les piste d'atterrissage sont occupées, l'avion en approche attendra qu'une piste se libère. Idem pour les places au terminal et les pistes de décollage.

************************************
Types d'implémentation de ``Queues``
************************************

Pour chaque piste ou place, une ``Queue`` est utilisée. Les choix sont :

- ``BlockingQueue``
- ``LinkedList``
- ``Tableau``

Les ``BlockingQueue`` sont facile à gérer. La partie concurrente est déjà implémentée.

Pour les ``LinkedList``, la partie concurrente est gérée manuellement avec des méthodes ``synchronized`` et d'un ``Object`` servant de moniteur. Idem pour les ``Tableaux``.

*********************
Tests de performances
*********************


*******
Crédits
*******

Auteurs : Sylvain Renaud, Dany Chea
