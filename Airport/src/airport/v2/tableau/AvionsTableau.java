
package airport.v2.tableau;

/**
 * Remplace les BlockingQueue pour la partie tests de performance du labo. Utilisation d'un tableau d'avions.
 * @author sylvain.renaud
 */
public class AvionsTableau
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AvionsTableau(int size)
		{
		this.size = size;
		tabAvion = new AvionV2[size];
		nbAvion = 0;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// Ajout d'un avion dans le tableau
	public void put(AvionV2 avion)
		{
		synchronized (monitor)
			{
			// On attend si le tableau est plein
			while(!(nbAvion < size))
				{
				try
					{
					//System.out.println("Wait");
					monitor.wait();
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
				}

			// Quand une place est libre, on place l'avion à cette place.
			int i = 0;
			while(i < size)
				{
				if (tabAvion[i] == null)
					{
					tabAvion[i] = avion;
					nbAvion++;
					break;
					}
				i++;
				}
			}
		}

	// On retire un avion du tableau.
	public void remove(AvionV2 avion)
		{
		synchronized (monitor)
			{
			int i = 0;

			// Parcours du tableau pour trouver l'instance de l'avion à retirer.
			while(i < size)
				{
				if (tabAvion[i] == avion)
					{
					tabAvion[i] = null;
					nbAvion--;
					break;
					}
				i++;
				}

			// On signale qu'une place s'est libérée.
			monitor.notifyAll();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AvionV2[] tabAvion;
	private int size;
	private int nbAvion;

	// Cet objet sert de moniteur
	private final Object monitor = new Object();
	}
