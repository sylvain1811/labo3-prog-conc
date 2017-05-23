
package airport.v3.linkedlist;

import airport.Tools;

/**
 * Deuxi�me impl�mentation de l'application, avec l'utilisation des LinkedList.
 * @author sylvain.renaud dany.chea
 *
 */
public class ApplicationV3
	{

	public static Thread[] tabThreadsAvion;

	public static String[] codePlane = { "3B147", "B3291", "6B239", "B1086", "780B4", "32A64", "17A69", "2A431", "647B8", "349A8", "536B8", "9103A", "9B210", "139A4", "96B01", "207B9", "830B6", "8435A", "7301B", "1076B", "5281B", "8A521", "3B806", "B6842", "B6238", "7B816", "A9437", "849A3",
			"60B18", "094B6", "4709B", "36A84", "085A3", "0718B", "80B21", "0A369", "5290A", "370B4", "021A3", "84A02", "052A6", "B6350", "630B5", "8B903", "1398B", "2693A", "902A6", "51A20", "971A5", "A7891" };

	// D�marrage de la simulation, avec les param�tres entr�s par l'utilisateur.
	public void startAnimation(int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{
		int nbAvion = _nbAvion; //nombre d'avion
		int nbPisteArr = _nbPisteArr; //pistes d'atterrisage
		int nbPisteDep = _nbPisteDep; //pistes de depart
		int nbPlace = _nbPlace; //parking

		// Debut du test de performance
		long startTime = System.currentTimeMillis();

		// Cr�ation de la JFrame pour afficher la simulation.
		AirportFrameV3 airportFrame = new AirportFrameV3(nbPisteArr, nbPisteDep, nbPlace, nbAvion);

		// Tampon pour chaque �tat qu'un avion peut avoir.
		AvionsList airArr = new AvionsList(nbAvion);
		AvionsList tarmacLand = new AvionsList(nbPisteArr);
		AvionsList tarmacTakeOff = new AvionsList(nbPisteDep);
		AvionsList terminal = new AvionsList(nbPlace);
		AvionsList airDep = new AvionsList(nbAvion);

		// Cr�ation et d�marrages des avions.
		tabThreadsAvion = new Thread[nbAvion];
		for(int i = 0; i < nbAvion; i++)
			{
			AvionV3 avion = new AvionV3(airportFrame, codePlane[i], airArr, tarmacLand, tarmacTakeOff, terminal, airDep, nbAvion, nbPisteArr, nbPisteDep, nbPlace);
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
