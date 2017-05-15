
package airport.com;

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
			Thread.sleep(2000);
			arrive();
			Thread.sleep(2000);
			atterit();
			Thread.sleep(2000);
			parque();
			Thread.sleep(2000);
			decolle();
			Thread.sleep(2000);
			part();
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	private void arrive() throws InterruptedException
		{
		airArr.put(this);
		airportFrame.updateArrive(this);
		}

	private void atterit() throws InterruptedException
		{
		tarmacLand.put(this);
		airArr.remove(this);
		airportFrame.updateAtterrit(this);
		}

	private void parque() throws InterruptedException
		{
		terminal.put(this);
		tarmacLand.remove(this);
		airportFrame.updatePark(this);
		}

	private void decolle() throws InterruptedException
		{
		tarmacTakeOff.put(this);
		terminal.remove(this);
		}

	private void part() throws InterruptedException
		{
		airDep.put(this);
		tarmacTakeOff.remove(this);
		}

	public String getCode()
		{
		return codePlane;
		}

	}
