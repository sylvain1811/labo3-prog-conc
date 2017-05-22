
package airport;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import airport.v1.blockingqueue.ApplicationV1;
import airport.v2.tableau.ApplicationV2;
import airport.v3.linkedlist.ApplicationV3;

/**
 * JFrame de type formulaire pour entrer les paramètres de la simulation.
 * Si les données entrées sont correct, le bouton start démarre l'animation.
 * @author sylvain.renaud
 *
 */
public class JFrameInput extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameInput()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static void main(String[] args)
		{
		new JFrameInput();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		jPanelNbAvion = new JPanel();
		lblNbAvion = new JLabel("Nb avion");
		jtfNbAvion = new JTextField("20");
		jtfNbAvion.selectAll();
		jPanelNbAvion.add(lblNbAvion);
		jPanelNbAvion.add(jtfNbAvion);

		jPanelNbPisteAtter = new JPanel();
		lblNbPisteAtter = new JLabel("Nb PisteAtter");
		jtfNbPisteAtter = new JTextField("3");
		jtfNbPisteAtter.selectAll();
		jPanelNbPisteAtter.add(lblNbPisteAtter);
		jPanelNbPisteAtter.add(jtfNbPisteAtter);

		jPanelNbPisteDeco = new JPanel();
		lblNbPisteDeco = new JLabel("Nb PisteDeco");
		jtfNbPisteDeco = new JTextField("3");
		jtfNbPisteDeco.selectAll();
		jPanelNbPisteDeco.add(lblNbPisteDeco);
		jPanelNbPisteDeco.add(jtfNbPisteDeco);

		jPanelNbPlaceTerm = new JPanel();
		lblNbPlaceTerm = new JLabel("Nb PlaceTerm");
		jtfNbPlaceTerm = new JTextField("4");
		jtfNbPlaceTerm.selectAll();
		jPanelNbPlaceTerm.add(lblNbPlaceTerm);
		jPanelNbPlaceTerm.add(jtfNbPlaceTerm);

		buttonGroup = new ButtonGroup();
		btnBlockingQueue = new JRadioButton("BlockingQueue");
		btnLinkedList = new JRadioButton("LinkedList");
		btnTableau = new JRadioButton("Tableau");

		buttonGroup.add(btnBlockingQueue);
		buttonGroup.add(btnLinkedList);
		buttonGroup.add(btnTableau);

		checkBoxRandom = new JCheckBox("Durée aléatoire");

		btnStart = new JButton("Start");
		// Layout : Specification
			{
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
			flowLayout.setVgap(20);
			setLayout(flowLayout);
			}

		// JComponent : add
		add(jPanelNbAvion);
		add(jPanelNbPisteAtter);
		add(jPanelNbPlaceTerm);
		add(jPanelNbPisteDeco);
		add(btnBlockingQueue);
		add(btnLinkedList);
		add(btnTableau);
		add(checkBoxRandom);
		add(btnStart);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		btnBlockingQueue.setSelected(true);
		checkBoxRandom.setSelected(true);
		btnStart.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				try
					{
					Tools.isRandom = checkBoxRandom.isSelected();
					int nbAvion = Integer.parseInt(jtfNbAvion.getText());
					int nbPisteAtter = Integer.parseInt(jtfNbPisteAtter.getText());
					int nbPisteDeco = Integer.parseInt(jtfNbPisteDeco.getText());
					int nbPlaceTerm = Integer.parseInt(jtfNbPlaceTerm.getText());

					// Maximum 50 avions (codePlane.length()).
					if (nbAvion > 50) { throw new NumberFormatException(); }

					if (btnBlockingQueue.isSelected())
						{
						(new ApplicationV1()).startAnimation(nbAvion, nbPisteAtter, nbPisteDeco, nbPlaceTerm);
						}
					else if (btnLinkedList.isSelected())
						{
						(new ApplicationV3()).startAnimation(nbAvion, nbPisteAtter, nbPisteDeco, nbPlaceTerm);
						}
					else if (btnTableau.isSelected())
						{
						(new ApplicationV2()).startAnimation(nbAvion, nbPisteAtter, nbPisteDeco, nbPlaceTerm);
						}

					JFrameInput.this.setVisible(false);
					}
				catch (NumberFormatException exception)
					{
					exception.printStackTrace();
					JOptionPane.showMessageDialog(JFrameInput.this, "Veuillez entrer des nombres valides. Max 50 avions", "Saisie invalide", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}

	private void appearance()
		{
		Dimension dimension = new Dimension(200, 30);
		jtfNbAvion.setPreferredSize(dimension);
		jtfNbPisteAtter.setPreferredSize(dimension);
		jtfNbPisteDeco.setPreferredSize(dimension);
		jtfNbPlaceTerm.setPreferredSize(dimension);

		setTitle("Paramètres de la simulation");
		setSize(330, 400);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanel jPanelNbAvion;
	private JPanel jPanelNbPisteAtter;
	private JPanel jPanelNbPisteDeco;
	private JPanel jPanelNbPlaceTerm;

	private JLabel lblNbAvion;
	private JLabel lblNbPisteAtter;
	private JLabel lblNbPisteDeco;
	private JLabel lblNbPlaceTerm;

	private JTextField jtfNbAvion;
	private JTextField jtfNbPisteAtter;
	private JTextField jtfNbPisteDeco;
	private JTextField jtfNbPlaceTerm;

	private ButtonGroup buttonGroup;
	private JRadioButton btnBlockingQueue;
	private JRadioButton btnLinkedList;
	private JRadioButton btnTableau;

	private JCheckBox checkBoxRandom;

	private JButton btnStart;
	}
