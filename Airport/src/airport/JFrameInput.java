
package airport;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JFrame de type formulaire pour entrer les param�tres de la simulation.
 * Si les donn�es entr�es sont correct, le bouton start d�marre l'animation.
 * @author sylvain.renaud
 *
 */
public class JFrameInput extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameInput(Application application)
		{
		this.application = application;
		geometry();
		control();
		appearance();
		}

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

		btnStart = new JButton("Start");
		// Layout : Specification
			{
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
			flowLayout.setVgap(50);
			setLayout(flowLayout);
			}

		// JComponent : add
		add(jPanelNbAvion);
		add(jPanelNbPisteAtter);
		add(jPanelNbPlaceTerm);
		add(jPanelNbPisteDeco);
		add(btnStart);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		btnStart.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				try
					{
					int nbAvion = Integer.parseInt(jtfNbAvion.getText());
					int nbPisteAtter = Integer.parseInt(jtfNbPisteAtter.getText());
					int nbPisteDeco = Integer.parseInt(jtfNbPisteDeco.getText());
					int nbPlaceTerm = Integer.parseInt(jtfNbPlaceTerm.getText());

					// Maximum 50 avions (codePlane.length()).
					if (nbAvion > 50) { throw new NumberFormatException(); }

					application.startAnimation(nbAvion, nbPisteAtter, nbPisteDeco, nbPlaceTerm);
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

		setTitle("Param�tres de la simulation");
		setSize(330, 500);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Application application;

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

	private JButton btnStart;
	}