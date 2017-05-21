
package airport;

/**
 * Inteface de l'application. Il y a deux implémentations, une utilisant les BlockingQueue, et l'autre un tampon.
 * @author sylvain.renaud
 *
 */
public interface Application
	{

	public void startAnimation(int _nbAvion, int _nbPisteArr, int _nbPisteDep, int _nbPlace);
	}
