
package airport.v3.linkedlist;

import java.util.LinkedList;

/**
 * Remplace les BlockingQueue pour la partie tests de performance du labo. Utilisation d'une LinkedList.
 * @author sylvain.renaud dany.chea
 */
public class AvionsList
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs			                          				*|
	\*------------------------------------------------------------------*/

	public AvionsList(int size)
		{
		this.size = size;
		listAvion = new LinkedList<>();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public					                        		*|
	\*------------------------------------------------------------------*/

	// Ajout d'un avion dans le tableau
	public void put(AvionV3 avion)
		{
		synchronized (monitor)
			{
			// On attend si la liste est pleine.
			while(!(listAvion.size() < size))
				{
				try
					{
					monitor.wait();
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
				}

			// Quand une place est libre, on place l'avion � cette place.
			listAvion.add(avion);
			}

		}

	// On retire un avion de la liste.
	public void remove(AvionV3 avion)
		{
		synchronized (monitor)
			{
			listAvion.remove(avion);

			// On signale qu'une place s'est lib�r�e.
			monitor.notifyAll();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private					                         	*|
	\*------------------------------------------------------------------*/

	private LinkedList<AvionV3> listAvion;
	private int size;

	// Cet objet sert de moniteur
	private final Object monitor = new Object();
	}
