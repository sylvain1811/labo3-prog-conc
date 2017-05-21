
package airport.v2.tampon;

import java.util.Random;

/**
 * Repr�sente l'avion (version avec Tampon).
 * @author sylvain.renaud
 *
 */
public class AvionV2 implements Runnable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AvionV2(AirportFrameV2 _airportFrame, String _codePlane, AvionsContainer airArr2, AvionsContainer tarmacLand2, AvionsContainer tarmacTakeOff2, AvionsContainer terminal2, AvionsContainer airDep2, int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace)
		{
		// Bool�en pour la fonction start/stop.
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

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// Change l'�tat du bool�en stop, si il est � false, on notifie tous les avions en attente pour les faire red�marrer
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

	// Repr�sente le comportement de l'avion.
	@Override
	public void run()
		{
		try
			{
			// (1) L'avion arrive
			arrive();
			// Simulation de dur�e
			Thread.sleep(nextRandomTime());

			// Check si l'utilisateur a stopp� la sumulation, si oui l'avion reste bloque� ici jusqu'� ce que l'utilisateur presse sur start.
			checkStop();

			// (2) Puis il atterit sur une piste
			atterit();
			Thread.sleep(nextRandomTime());

			checkStop();

			// (3) Puis il se parque
			parque();
			Thread.sleep(nextRandomTime());

			checkStop();

			// (4) Et d�colle
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

	// V�rifier l'�tat du programme (en pause ou non)
	private void checkStop()
		{
		while(stop)
			{
			// Utilisation de la JFrame comme moniteur
			synchronized (airportFrame)
				{
				try
					{

					// Si stop est � true, alors on bloque l'avion (wait)
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

		// On met l'avion dans le container d'arriv�e
		airArr.put(this);
		// Puis on met � jour la JFrame
		airportFrame.updateArrive(this);
		}

	// (2)
	private synchronized void atterit() throws InterruptedException
		{
		// On met l'avion dans le container d'atterrissage et on le retire de celui d'arriv�e
		tarmacLand.put(this);
		airArr.remove(this);
		// Puis on met � jour la JFrame
		airportFrame.updateAtterrit(this);
		}

	// (3)
	private synchronized void parque() throws InterruptedException
		{
		// On met l'avion dans le container du terminal et on le retire de celui d'atterrissage
		terminal.put(this);
		tarmacLand.remove(this);
		// Puis on met � jour la JFrame
		airportFrame.updatePark(this);
		}

	// (4)
	private synchronized void decolle() throws InterruptedException
		{
		// On met l'avion dans le container du d�collage et on le retire de celui du terminal
		tarmacTakeOff.put(this);
		terminal.remove(this);
		// Puis on met � jour la JFrame
		airportFrame.updateDecolle(this);
		}

	// (5)
	private synchronized void part() throws InterruptedException
		{
		// On met l'avion dans le container de air d�part et on le retire de celui de d�collage
		airDep.put(this);
		tarmacTakeOff.remove(this);
		// Puis on met � jour la JFrame
		airportFrame.updatePart(this);
		}

	// Retourne le code de l'avion.
	public String getCode()
		{
		return codePlane;
		}

	// G�n�re un nombre al�atoire pour simuler la dur�e d'une activit�
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

	private AirportFrameV2 airportFrame;
	private String codePlane;
	private AvionsContainer airArr;
	private AvionsContainer tarmacLand;
	private AvionsContainer tarmacTakeOff;
	private AvionsContainer terminal;
	private AvionsContainer airDep;
	private int nbAvion;
	private int nbPisteArr;
	private int nbPisteDep;
	private int nbPlace;
	private int position;

	boolean stop;

	private static Random random = new Random();

	}
