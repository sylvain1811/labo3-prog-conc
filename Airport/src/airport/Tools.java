
package airport;

import java.awt.Image;
//gestion de ressources
import java.util.Random;

import javax.swing.ImageIcon;

public class Tools
	{

	public static ImageIcon scaleImage(ImageIcon icon, int w, int h)
		{
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();
		if (icon.getIconWidth() > w)
			{
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
			}
		if (nh > h)
			{
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
			}
		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
		}

	/**
	 * Génère un nombre aléatoire pour simuler la durée d'une activité
	 * Si le paramètre est vrai, alors retourne un long aléatoire, sinon retourne 100.
	 * @param isRandom
	 * @return
	 */
	public static long getDuree()
		{

		if (isRandom)
			{
			// En mode animation
			long ret = (long)random.nextInt() % 2000;
			if (ret < 0) { return ret * (-1) + 1000; }
			return ret + 1000;
			}
		else
		// En mode test
			{
			return 100;
			}

		}

	public static boolean isRandom = true;
	private static Random random = new Random();
	}
