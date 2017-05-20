
package airport.v2;

import java.util.Random;

public class AvionV2 implements Runnable
	{

	AirportFrameV2 airportFrame;
	String codePlane;
	AvionsContainer airArr;
	AvionsContainer tarmacLand;
	AvionsContainer tarmacTakeOff;
	AvionsContainer terminal;
	AvionsContainer airDep;
	int nbAvion;
	int nbPisteArr;
	int nbPisteDep;
	int nbPlace;
	int position;

	boolean stop;

	public AvionV2(AirportFrameV2 _airportFrame, String _codePlane, AvionsContainer airArr2, AvionsContainer tarmacLand2, AvionsContainer tarmacTakeOff2, AvionsContainer terminal2, AvionsContainer airDep2, int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{
		stop = false;
		airportFrame = _airportFrame;
		codePlane = _codePlane;

		airArr = airArr2;
		tarmacLand = tarmacLand2;
		tarmacTakeOff = tarmacTakeOff2;
		terminal = terminal2;
		airDep = airDep2;

		nbAvion = _nbAvion;
		nbPisteArr = _nbPisteArr;
		nbPisteDep = _nbPisteDep;
		nbPlace = _nbPlace;
		}

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

	@Override
	public void run()
		{
		try
			{
			// (1) L'avion arrive
			arrive();
			Thread.sleep(nextRandomTime());

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

	// Vérifier l'état du programme (en pause ou non)
	private void checkStop()
		{
		// Utilisation de la JFrame comme moniteur
		while(stop)
			{
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
	private void arrive() throws InterruptedException
		{

		// On met l'avion dans le container d'arrivée
		airArr.put(this);
		// Puis on met à jour la JFrame
		airportFrame.updateArrive(this);
		}

	// (2)
	private void atterit() throws InterruptedException
		{
		// On met l'avion dans le container d'atterrissage et on le retire de celui d'arrivée
		tarmacLand.put(this);
		airArr.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updateAtterrit(this);
		}

	// (3)
	private void parque() throws InterruptedException
		{
		// On met l'avion dans le container du terminal et on le retire de celui d'atterrissage
		terminal.put(this);
		tarmacLand.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updatePark(this);
		}

	// (4)
	private void decolle() throws InterruptedException
		{
		// On met l'avion dans le container du décollage et on le retire de celui du terminal
		tarmacTakeOff.put(this);
		terminal.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updateDecolle(this);
		}

	// (5)
	private void part() throws InterruptedException
		{
		// On met l'avion dans le container de air départ et on le retire de celui de décollage
		airDep.put(this);
		tarmacTakeOff.remove(this);
		// Puis on met à jour la JFrame
		airportFrame.updatePart(this);
		}

	public String getCode()
		{
		return codePlane;
		}

	// Génère un nombre aléatoire pour la représentation d'une activité
	private long nextRandomTime()
		{
		// Animation

		long ret = (long)random.nextInt() % 2000;
		if (ret < 0) { return ret * (-1) + 1000; }
		return ret + 1000;

		// Performance test
		//return 0;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static Random random = new Random();

	}
