
package airport.v1.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Représente l'avion (version avec BlockingQueue).
 * @author sylvain.renaud
 *
 */
public class AvionV1 implements Runnable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AvionV1(AirportFrameV1 _airportFrame, String _codePlane, BlockingQueue<AvionV1> _airArr, BlockingQueue<AvionV1> _tarmacLand, BlockingQueue<AvionV1> _tarmacTakeOff, BlockingQueue<AvionV1> _terminal, BlockingQueue<AvionV1> _airDep, int _nbAvion, int _nbPisteArr, int _nbPisteDep,
			int _nbPlace)
		{
		// Booléen pour la fonction start/stop.
		stop = false;

		airportFrame = _airportFrame;
		codePlane = _codePlane;

		airArr = _airArr;
		tarmacLand = _tarmacLand;
		tarmacTakeOff = _tarmacTakeOff;
		terminal = _terminal;
		airDep = _airDep;

		nbAvion = _nbAvion;
		nbPisteArr = _nbPisteArr;
		nbPisteDep = _nbPisteDep;
		nbPlace = _nbPlace;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// Change l'état du booléen stop, si il est à false, on notifie tous les avions en attente pour les faire redémarrer
	public void setStop(boolean stop)
		{
		this.stop = stop;

		// Utilisation de la JFrame comme moniteur
		synchronized (airportFrame)
			{
			if (!stop)
				{
				airportFrame.notifyAll();
				}
			}
		}

	// Représente le comportement de l'avion.
	@Override
	public void run()
		{
		try
			{
			// (1) L'avion arrive
			arrive();
			// Simulation de durée
			Thread.sleep(nextRandomTime());

			// Check si l'utilisateur a stoppé la sumulation, si oui l'avion reste bloqueé ici jusqu'à ce que l'utilisateur presse sur start.
			checkStop();

			// (2) Puis il atterit sur une piste
			atterit();
			Thread.sleep(nextRandomTime());

			checkStop();

			// (3) Puis il se parque
			parque();
			Thread.sleep(nextRandomTime());

			checkStop();

			// (4) Et décolle
			decolle();
			Thread.sleep(nextRandomTime());

			checkStop();

			// (5) Et fini par partir
			part();
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Vérifier l'état de la simulation (en pause ou non). Si il est en pause on fait attendre l'avion.
	private void checkStop()
		{
		while(stop)
			{
			// Utilisation de la JFrame comme moniteur
			synchronized (airportFrame)
				{
				try
					{
					// Si stop est à true, alors on bloque l'avion (wait)
					airportFrame.wait();
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
				}
			}
		}

	// (1)
	private synchronized void arrive() throws InterruptedException
		{
		// On met l'avion dans la blocking queue d'arrivée
		airArr.put(this);
		// Puis on met à jour la JFrame
		airportFrame.updateArrive(this);
		}

	// (2)
	private synchronized void atterit() throws InterruptedException
		{
		// On met l'avion dans la blocking queue d'atterrissage et on le retire de celle d'arrivée
		tarmacLand.put(this);
		airArr.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updateAtterrit(this);
		}

	// (3)
	private synchronized void parque() throws InterruptedException
		{
		// On met l'avion dans la blocking queue du terminal et on le retire de celle d'atterrissage
		terminal.put(this);
		tarmacLand.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updatePark(this);
		}

	// (4)
	private synchronized void decolle() throws InterruptedException
		{
		// On met l'avion dans la blocking queue du décollage et on le retire de celle du terminal
		tarmacTakeOff.put(this);
		terminal.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updateDecolle(this);
		}

	// (5)
	private synchronized void part() throws InterruptedException
		{
		// On met l'avion dans la blocking queue de air départ et on le retire de celle de décollage
		airDep.put(this);
		tarmacTakeOff.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updatePart(this);
		}

	// Retourne le code de l'avion.
	public String getCode()
		{
		return codePlane;
		}

	// Génère un nombre aléatoire pour simuler la durée d'une activité
	private long nextRandomTime()
		{
		// En mode animation
		long ret = (long)random.nextInt() % 2000;
		if (ret < 0) { return ret * (-1) + 1000; }
		return ret + 1000;

		// Pour les tests de performance, retourne 0.
		//return 0;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AirportFrameV1 airportFrame;
	private String codePlane;
	private BlockingQueue<AvionV1> airArr;
	private BlockingQueue<AvionV1> tarmacLand;
	private BlockingQueue<AvionV1> tarmacTakeOff;
	private BlockingQueue<AvionV1> terminal;
	private BlockingQueue<AvionV1> airDep;
	private int nbAvion;
	private int nbPisteArr;
	private int nbPisteDep;
	private int nbPlace;
	private int position;

	boolean stop;

	private static Random random = new Random();

	}
