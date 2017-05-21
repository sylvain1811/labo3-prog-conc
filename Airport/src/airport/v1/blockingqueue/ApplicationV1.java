
package airport.v1.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import airport.Application;
import airport.JFrameInput;

/**
 * Première implémentation de l'application, avec l'utilisation des BlockingQueue.
 * @author sylvain.renaud
 *
 */
public class ApplicationV1 implements Application
	{

	public static Thread[] tabThreadsAvion;

	static String[] codePlane = { "3B147", "B3291", "6B239", "B1086", "780B4", "32A64", "17A69", "2A431", "647B8", "349A8", "536B8", "9103A", "9B210", "139A4", "96B01", "207B9", "830B6", "8435A", "7301B", "1076B", "5281B", "8A521", "3B806", "B6842", "B6238", "7B816", "A9437", "849A3", "60B18",
			"094B6", "4709B", "36A84", "085A3", "0718B", "80B21", "0A369", "5290A", "370B4", "021A3", "84A02", "052A6", "B6350", "630B5", "8B903", "1398B", "2693A", "902A6", "51A20", "971A5", "A7891" };

	public static void main(String[] args)
		{
		// Création de l'application et affichage de la JFrame de paramétrages.
		ApplicationV1 application = new ApplicationV1();
		new JFrameInput(application);
		}

	// Démarrage de la simulation, avec les paramètres entrés par l'utilisateur.
	@Override
	public void startAnimation(int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{

		int nbAvion = _nbAvion; //nombre d'avion
		int nbPisteArr = _nbPisteArr; //pistes d'atterrisage
		int nbPisteDep = _nbPisteDep; //pistes de depart
		int nbPlace = _nbPlace; //parking

		// Debut du test de performance
		long startTime = System.currentTimeMillis();

		// Création de la JFrame pour afficher la simulation.
		AirportFrameV1 airportFrame = new AirportFrameV1(nbPisteArr, nbPisteDep, nbPlace, nbAvion);

		// BlockingQueue pour chaque état qu'un avion peut avoir.
		BlockingQueue<AvionV1> airArr = new ArrayBlockingQueue<AvionV1>(nbAvion);
		BlockingQueue<AvionV1> tarmacLand = new ArrayBlockingQueue<AvionV1>(nbPisteArr);
		BlockingQueue<AvionV1> tarmacTakeOff = new ArrayBlockingQueue<AvionV1>(nbPisteDep);
		BlockingQueue<AvionV1> terminal = new ArrayBlockingQueue<AvionV1>(nbPlace);
		BlockingQueue<AvionV1> airDep = new ArrayBlockingQueue<AvionV1>(nbAvion);

		// Création et démarrages des avions.
		tabThreadsAvion = new Thread[nbAvion];
		for(int i = 0; i < nbAvion; i++)
			{
			AvionV1 avion = new AvionV1(airportFrame, codePlane[i], airArr, tarmacLand, tarmacTakeOff, terminal, airDep, nbAvion, nbPisteArr, nbPisteDep, nbPlace);
			tabThreadsAvion[i] = new Thread(avion);
			tabThreadsAvion[i].start();
			}

		airportFrame.setVisible(true);
		airportFrame.setSize(1300, 700);
		airportFrame.setLocationRelativeTo(null);
		airportFrame.pack();

		// Attendre la fin des thread pour terminer le programme
		//		for(int i = 0; i < nbAvion; i++)
		//			{
		//			try
		//				{
		//				tabThreadsAvion[i].join();
		//				}
		//			catch (InterruptedException e)
		//				{
		//				e.printStackTrace();
		//				}
		//			}

		// Test de performance
		long endTime = System.currentTimeMillis();
		System.out.println("Duration : " + (endTime - startTime) + " ms");
		}
	}
