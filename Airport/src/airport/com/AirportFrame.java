
package airport.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AirportFrame extends JFrame
	{

	private static final long serialVersionUID = 1L;

	// liste d'avion à chaque endroits
	private List<Avion> avionOnAirArray;
	private List<Avion> avionLandingArray;
	private List<Avion> avionTermArray;
	private List<Avion> avionTakeOffArray;
	private List<Avion> avionOnAirLeaveArray;

	// images d'avion
	private ArrayList<JLabel> listTerm;
	private ArrayList<JLabel> listArr;
	private ArrayList<JLabel> listDep;

	private JLabel nbOnAirLabel;
	private JLabel nbLandingLabel;
	private JLabel nbTermLabel;
	private JLabel nbTakeOffLabel;
	private JLabel nbOnAirLeaveLabel;

	private int nbPisteArr;
	private int nbPisteDep;
	private int nbPlace;

	public AirportFrame(int _nbPisteArr, int _nbPisteDep, int _nbPlace, int _nbAvion)
		{

		nbPisteArr = _nbPisteArr;
		nbPisteDep = _nbPisteDep;
		nbPlace = _nbPlace;

		avionOnAirArray = new ArrayList<Avion>();
		avionLandingArray = new ArrayList<Avion>();
		avionTermArray = new ArrayList<Avion>();
		avionTakeOffArray = new ArrayList<Avion>();
		avionOnAirLeaveArray = new ArrayList<Avion>();

		listArr = new ArrayList<JLabel>();
		listTerm = new ArrayList<JLabel>();
		listDep = new ArrayList<JLabel>();

		JPanel panel = new JPanel(new BorderLayout());

		JPanel airportPanel = new JPanel();
		airportPanel.setLayout(new GridLayout(1, 3));

		ImageIcon imgRoad = new ImageIcon("img/piste.png");

		JPanel landPanel = new JPanel();
		landPanel.setLayout(new GridLayout(2 + (nbPisteArr - 1), 1));
		ImageIcon imgLand = new ImageIcon("img/landing.png");
		nbLandingLabel = new JLabel("nb avion en approche :", SwingConstants.CENTER);

		for(int i = 1; i <= _nbPisteArr; i++)
			{
			JLabel imgLandingLabel = new JLabel("", Tools.scaleImage(imgLand, 50, 50), SwingConstants.CENTER);
			imgLandingLabel.setVisible(false);
			listArr.add(imgLandingLabel);
			landPanel.add(imgLandingLabel);
			landPanel.add(new JLabel("", Tools.scaleImage(imgRoad, 50, 50), SwingConstants.CENTER));
			}

		landPanel.add(new JLabel());
		landPanel.add(nbLandingLabel);
		airportPanel.add(landPanel);

		JPanel airportImgPanel = new JPanel();
		airportImgPanel.setLayout(new GridLayout(3, 1));
		ImageIcon imgAir = new ImageIcon("img/airport.png");
		airportImgPanel.add(new JLabel("", Tools.scaleImage(imgAir, 150, 150), SwingConstants.CENTER));
		nbTermLabel = new JLabel("nb avion au terminal :", SwingConstants.CENTER);
		airportImgPanel.add(nbTermLabel);
		airportPanel.add(airportImgPanel);

		JPanel takeOffPanel = new JPanel();
		takeOffPanel.setLayout(new GridLayout(2 + (nbPisteDep - 1), 1));
		ImageIcon imgTakeOff = new ImageIcon("img/takeoff.png");
		nbTakeOffLabel = new JLabel("nb avion au départ :", SwingConstants.CENTER);

		for(int i = 1; i <= _nbPisteDep; i++)
			{
			JLabel imgTakeOffLabel = new JLabel("", Tools.scaleImage(imgTakeOff, 50, 50), SwingConstants.CENTER);
			imgTakeOffLabel.setVisible(false);
			listDep.add(imgTakeOffLabel);
			takeOffPanel.add(new JLabel("", Tools.scaleImage(imgRoad, 50, 50), SwingConstants.CENTER));
			takeOffPanel.add(imgTakeOffLabel);
			}

		takeOffPanel.add(nbTakeOffLabel);
		airportPanel.add(takeOffPanel);

		panel.add(airportPanel, BorderLayout.CENTER);

		JPanel parkPanel = new JPanel();

		for(int i = 1; i <= _nbPlace; i++)
			{
			ImageIcon imgPark = new ImageIcon("img/waiting.png");
			JLabel imgParkLabel = new JLabel("", Tools.scaleImage(imgPark, 50, 50), SwingConstants.CENTER);
			imgParkLabel.setVisible(false);
			listTerm.add(imgParkLabel);
			imgParkLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			parkPanel.add(imgParkLabel);
			}

		panel.add(parkPanel, BorderLayout.SOUTH);

		JPanel onAirPanel = new JPanel();
		onAirPanel.setLayout(new GridLayout(2, 2));
		ImageIcon imgOnAir = new ImageIcon("img/onair.png");
		nbOnAirLabel = new JLabel("nb avion en air (arrive) :", SwingConstants.CENTER);
		onAirPanel.add(new JLabel("", Tools.scaleImage(imgOnAir, 50, 50), SwingConstants.CENTER));
		onAirPanel.add(new JLabel("", Tools.scaleImage(imgOnAir, 50, 50), SwingConstants.CENTER));
		onAirPanel.add(nbOnAirLabel);
		nbOnAirLeaveLabel = new JLabel("nb avion en air (depart) :", SwingConstants.CENTER);
		onAirPanel.add(nbOnAirLeaveLabel);
		panel.add(onAirPanel, BorderLayout.NORTH);

		JPanel bouton = new JPanel();
		bouton.setLayout(new GridLayout(1, 2));
		JPanel start = new JPanel();
		JPanel stop = new JPanel();
		start.add(new JButton("Start"));
		stop.add(new JButton("Stop"));

		bouton.add(start);
		bouton.add(stop);
		panel.add(bouton, BorderLayout.EAST);

		this.getContentPane().add(panel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Labo 3 - Gestion d'un aéroport");
		}

	public synchronized void updateArrive(Avion avion)
		{
		avionOnAirArray.add(avion);
		nbOnAirLabel.setText("nb avion en air (arrive) : " + avionOnAirArray.size());
		}

	public synchronized void updateAtterrit(Avion avion)
		{
		avionOnAirArray.remove(avion);
		avionLandingArray.add(avion);
		nbLandingLabel.setText("nb avion en approche : " + avionLandingArray.size());
		nbOnAirLabel.setText("nb avion en air (arrive) : " + avionOnAirArray.size());

		updateLandingLabel();
		}

	public synchronized void updatePark(Avion avion)
		{
		avionLandingArray.remove(avion);
		avionTermArray.add(avion);
		nbLandingLabel.setText("nb avion en approche : " + avionLandingArray.size());
		nbTermLabel.setText("nb avion au terminal : " + avionTermArray.size());

		updateLandingLabel();
		updateTerminalLabel();
		}

	public synchronized void updateDecolle(Avion avion)
		{
		avionTermArray.remove(avion);
		avionTakeOffArray.add(avion);
		nbTermLabel.setText("nb avion au terminal : " + avionTermArray.size());
		nbTakeOffLabel.setText("nb avion au départ : " + avionTakeOffArray.size());

		updateTerminalLabel();
		updateTakeOfLabel();
		}

	public synchronized void updatePart(Avion avion)
		{
		avionTakeOffArray.remove(avion);
		avionOnAirLeaveArray.add(avion);
		nbTakeOffLabel.setText("nb avion au départ : " + avionTakeOffArray.size());
		nbOnAirLeaveLabel.setText("nb avion en air (depart) : " + avionOnAirLeaveArray.size());
		updateTakeOfLabel();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void updateTakeOfLabel()
		{
		for(int i = 0; i < nbPisteDep; i++)
			{
			if (i < avionTakeOffArray.size())
				{
				listDep.get(i).setVisible(true);
				listDep.get(i).setText(avionTakeOffArray.get(i).getCode());
				}
			else
				{
				listDep.get(i).setVisible(false);
				}
			}
		}

	private void updateTerminalLabel()
		{
		for(int i = 0; i < nbPlace; i++)
			{
			if (i < avionTermArray.size())
				{
				listTerm.get(i).setVisible(true);
				listTerm.get(i).setText(avionTermArray.get(i).getCode());
				}
			else
				{
				listTerm.get(i).setVisible(false);
				}
			}
		}

	private void updateLandingLabel()
		{
		for(int i = 0; i < nbPisteArr; i++)
			{
			if (i < avionLandingArray.size())
				{
				listArr.get(i).setVisible(true);
				listArr.get(i).setText(avionLandingArray.get(i).getCode());
				}
			else
				{
				listArr.get(i).setVisible(false);
				}
			}
		}
	}
