
package airport.v2.tableau;

import airport.Tools;

/**
 * Deuxieme implementation de l'application, avec l'utilisation des Tableaux.
 * @author sylvain.renaud dany.chea
 *
 */
public class ApplicationV2
	{

	public static Thread[] tabThreadsAvion;

	public static String[] codePlane = { "3B147", "B3291", "6B239", "B1086", "780B4", "32A64", "17A69", "2A431", "647B8", "349A8", "536B8", "9103A", "9B210", "139A4", "96B01", "207B9", "830B6", "8435A", "7301B", "1076B", "5281B", "8A521", "3B806", "B6842", "B6238", "7B816", "A9437", "849A3",
			"60B18", "094B6", "4709B", "36A84", "085A3", "0718B", "80B21", "0A369", "5290A", "370B4", "021A3", "84A02", "052A6", "B6350", "630B5", "8B903", "1398B", "2693A", "902A6", "51A20", "971A5", "A7891" };

	// Demarrage de la simulation, avec les parametres entres par l'utilisateur.
	public void startAnimation(int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{
		int nbAvion = _nbAvion; //nombre d'avion
		int nbPisteArr = _nbPisteArr; //pistes d'atterrisage
		int nbPisteDep = _nbPisteDep; //pistes de depart
		int nbPlace = _nbPlace; //parking

		// Debut du test de performance
		long startTime = System.currentTimeMillis();

		// Creation de la JFrame pour afficher la simulation.
		AirportFrameV2 airportFrame = new AirportFrameV2(nbPisteArr, nbPisteDep, nbPlace, nbAvion);

		// Tampon pour chaque etat qu'un avion peut avoir.
		AvionsTableau airArr = new AvionsTableau(nbAvion);
		AvionsTableau tarmacLand = new AvionsTableau(nbPisteArr);
		AvionsTableau tarmacTakeOff = new AvionsTableau(nbPisteDep);
		AvionsTableau terminal = new AvionsTableau(nbPlace);
		AvionsTableau airDep = new AvionsTableau(nbAvion);

		// Creation et demarrages des avions.
		tabThreadsAvion = new Thread[nbAvion];
		for(int i = 0; i < nbAvion; i++)
			{
			AvionV2 avion = new AvionV2(airportFrame, codePlane[i], airArr, tarmacLand, tarmacTakeOff, terminal, airDep, nbAvion, nbPisteArr, nbPisteDep, nbPlace);
			tabThreadsAvion[i] = new Thread(avion);
			tabThreadsAvion[i].start();
			}

		airportFrame.setVisible(true);
		airportFrame.setSize(1300, 700);
		airportFrame.setLocationRelativeTo(null);

		// Attendre la fin des thread pour terminer le programme
		// Attendre la fin des thread pour terminer le programme si en mode test
		if (Tools.isTestMode)
			{
			for(int i = 0; i < nbAvion; i++)
				{
				try
					{
					tabThreadsAvion[i].join();
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
				}
			}

		// Test de performance
		long endTime = System.currentTimeMillis();
		System.out.println("Duration : " + (endTime - startTime) + " ms");
		}
	}
