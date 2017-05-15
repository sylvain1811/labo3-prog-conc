
package airport.com;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
//represente l'avion

public class Avion implements Runnable
	{

	AirportFrame airportFrame;
	String codePlane;
	BlockingQueue<Avion> airArr;
	BlockingQueue<Avion> tarmacLand;
	BlockingQueue<Avion> tarmacTakeOff;
	BlockingQueue<Avion> terminal;
	BlockingQueue<Avion> airDep;
	int nbAvion;
	int nbPisteArr;
	int nbPisteDep;
	int nbPlace;

	int position;

	public Avion(AirportFrame _airportFrame, String _codePlane, BlockingQueue<Avion> _airArr, BlockingQueue<Avion> _tarmacLand, BlockingQueue<Avion> _tarmacTakeOff, BlockingQueue<Avion> _terminal, BlockingQueue<Avion> _airDep, int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{
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

	@Override
	public void run()
		{
		try
			{
			// (1) L'avion arrive
			arrive();
			Thread.sleep(nextRandomTime());

			// (2) Puis il atterit sur une piste
			atterit();
			Thread.sleep(nextRandomTime());

			// (3) Puis il se parque
			parque();
			Thread.sleep(nextRandomTime());

			// (4) Et décolle
			decolle();
			Thread.sleep(nextRandomTime());

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

	public String getCode()
		{
		return codePlane;
		}

	// Génère un nombre aléatoire pour la représentation d'une activité
	private long nextRandomTime()
		{
		long ret = (long)random.nextInt() % 2000;
		if (ret < 0) { return ret * (-1) + 1000; }
		return ret + 1000;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static Random random = new Random();

	}
