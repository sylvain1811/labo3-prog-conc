
package airport.v2.tableau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import airport.Tools;

/**
 * JFrame pour l'affichage de la simulation. Version avec Tampon.
 * @author sylvain.renaud dany.chea
 *
 */
public class AirportFrameV2 extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static final long serialVersionUID = 1L;

	// liste d'avion � chaque endroits
	private List<AvionV2> avionOnAirArray;
	private List<AvionV2> avionLandingArray;
	private List<AvionV2> avionTermArray;
	private List<AvionV2> avionTakeOffArray;
	private List<AvionV2> avionOnAirLeaveArray;

	// images d'avion
	private ArrayList<JLabel> listTerm;
	private ArrayList<JLabel> listArr;
	private ArrayList<JLabel> listDep;

	private JLabel nbOnAirLabel;
	private JLabel nbLandingLabel;
	private JLabel nbTermLabel;
	private JLabel nbTakeOffLabel;
	private JLabel nbOnAirLeaveLabel;

	// Buttons
	private JButton btnStart;
	private JButton btnStop;

	private int nbPisteArr;
	private int nbPisteDep;
	private int nbPlace;
	private int nbAvion;

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AirportFrameV2(int _nbPisteArr, int _nbPisteDep, int _nbPlace, int _nbAvion)
		{

		nbAvion = _nbAvion;
		nbPisteArr = _nbPisteArr;
		nbPisteDep = _nbPisteDep;
		nbPlace = _nbPlace;

		avionOnAirArray = new ArrayList<AvionV2>();
		avionLandingArray = new ArrayList<AvionV2>();
		avionTermArray = new ArrayList<AvionV2>();
		avionTakeOffArray = new ArrayList<AvionV2>();
		avionOnAirLeaveArray = new ArrayList<AvionV2>();

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
		nbTakeOffLabel = new JLabel("nb avion au d�part :", SwingConstants.CENTER);

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
		btnStart = new JButton("Start");
		btnStop = new JButton("Stop");

		start.add(btnStart);
		stop.add(btnStop);

		bouton.add(start);
		bouton.add(stop);
		panel.add(bouton, BorderLayout.EAST);

		this.getContentPane().add(panel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Labo 3 - Gestion d'un a�roport");

		/*------------------------------*\
		|*	    Actions des boutons		*|
		\*------------------------------*/

		btnStart.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				// Red�marrage des avions (la simulation reprend dans l'�tat o� elle s'est arr�t�e)
				stopAvion(false);
				}
			});

		btnStop.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				// Les avions s'arr�tent (la simulation se fige)
				stopAvion(true);
				}

			});
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// (1)
	public synchronized void updateArrive(AvionV2 avion)
		{
		// On ajoute l'avion dans la list d'arriv�e (elle est synchronis�e avec la blocking queue)
		avionOnAirArray.add(avion);

		// On met � jour le compteur
		nbOnAirLabel.setText("nb avion en air (arrive) : " + avionOnAirArray.size());
		}

	// (2)
	public synchronized void updateAtterrit(AvionV2 avion)
		{
		// On retire l'avion de la liste d'arriv�e pour le mettre dans la liste d'atterrissage (syhchrone avec les blocking queue correspondantes)
		avionOnAirArray.remove(avion);
		avionLandingArray.add(avion);

		// On met � jour les compteurs
		nbLandingLabel.setText("nb avion en approche : " + avionLandingArray.size());
		nbOnAirLabel.setText("nb avion en air (arrive) : " + avionOnAirArray.size());

		// On met � jour les images des avions entrain d'atterrir
		updateLandingLabel();
		}

	// (3)
	public synchronized void updatePark(AvionV2 avion)
		{
		// On retire l'avion de la liste d'atterrissage pour le mettre dans la liste du terminal (syhchrone avec les blocking queue correspondantes)
		avionLandingArray.remove(avion);
		avionTermArray.add(avion);

		// On met � jour les compteurs
		nbLandingLabel.setText("nb avion en approche : " + avionLandingArray.size());
		nbTermLabel.setText("nb avion au terminal : " + avionTermArray.size());

		// On mets � jour les images des avions entrain d'atterrir et ceux qui se garent
		updateLandingLabel();
		updateTerminalLabel();
		}

	// (4)
	public synchronized void updateDecolle(AvionV2 avion)
		{
		// On retire l'avion de la liste du terminal pour le mettre dans la liste du d�collage (syhchrone avec les blocking queue correspondantes)
		avionTermArray.remove(avion);
		avionTakeOffArray.add(avion);

		// On met � jour les compteurs
		nbTermLabel.setText("nb avion au terminal : " + avionTermArray.size());
		nbTakeOffLabel.setText("nb avion au d�part : " + avionTakeOffArray.size());

		// On mets � jour les images des avions parqu�s et ceux qui d�collent
		updateTerminalLabel();
		updateTakeOfLabel();
		}

	// (5)
	public synchronized void updatePart(AvionV2 avion)
		{
		// On retire l'avion de la liste du d�collage pour le mettre dans la liste de ceux qui partent (syhchrone avec les blocking queue correspondantes)
		avionTakeOffArray.remove(avion);
		avionOnAirLeaveArray.add(avion);

		// On met � jour les compteurs
		nbTakeOffLabel.setText("nb avion au d�part : " + avionTakeOffArray.size());
		nbOnAirLeaveLabel.setText("nb avion en air (depart) : " + avionOnAirLeaveArray.size());

		// On met � jour les images des avions qui d�collent
		updateTakeOfLabel();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// On met x images de TakeOf visible, avec x le nombre d'avion dans la liste takeOf
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

	// On met x images de terminal visible, avec x le nombre d'avion dans la liste terminal
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

	// On met x images de Landing visible, avec x le nombre d'avion dans la liste landing
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

	// Stop ou start les avions
	private void stopAvion(boolean state)
		{
		// Parcours de toutes les listes pour d�marrer ou arr�ter tous les avions de la simulation.
		for(AvionV2 avion:avionOnAirArray)
			{
			avion.setStop(state);
			}

		for(AvionV2 avion:avionLandingArray)
			{
			avion.setStop(state);
			}

		for(AvionV2 avion:avionTermArray)
			{
			avion.setStop(state);
			}

		for(AvionV2 avion:avionTakeOffArray)
			{
			avion.setStop(state);
			}

		for(AvionV2 avion:avionOnAirLeaveArray)
			{
			avion.setStop(state);
			}
		}

	}
