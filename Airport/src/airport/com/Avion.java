
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
			//long wait = 500;
			arrive();
			Thread.sleep(nextRandomTime());
			atterit();
			Thread.sleep(nextRandomTime());
			parque();
			Thread.sleep(nextRandomTime());
			decolle();
			Thread.sleep(nextRandomTime());
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

	private synchronized void arrive() throws InterruptedException
		{
		airArr.put(this);
		airportFrame.updateArrive(this);
		}

	private synchronized void atterit() throws InterruptedException
		{
		tarmacLand.put(this);
		airArr.remove(this);
		airportFrame.updateAtterrit(this);
		}

	private synchronized void parque() throws InterruptedException
		{
		terminal.put(this);
		tarmacLand.remove(this);
		airportFrame.updatePark(this);
		}

	private synchronized void decolle() throws InterruptedException
		{
		tarmacTakeOff.put(this);
		terminal.remove(this);
		airportFrame.updateDecolle(this);
		}

	private synchronized void part() throws InterruptedException
		{
		airDep.put(this);
		tarmacTakeOff.remove(this);
		airportFrame.updatePart(this);
		}

	public String getCode()
		{
		return codePlane;
		}

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
