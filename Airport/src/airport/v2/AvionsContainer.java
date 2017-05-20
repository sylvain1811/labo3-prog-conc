
package airport.v2;

public class AvionsContainer
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AvionsContainer(int size)
		{
		this.size = size;
		tabAvion = new AvionV2[size];
		this.indexPut = 0;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// Ajout d'un avion dans le tableau
	public synchronized void put(AvionV2 avion)
		{

		//System.out.println(Arrays.toString(tabAvion));
		int i = 0;
		while(i < size)
			{
			if (tabAvion[i] == null)
				{
				tabAvion[i] = avion;
				break;
				}

			if (i == size - 1)
				{
				i = 0;
				try
					{
					this.wait();
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
				}
			i++;
			}
		}

	public synchronized void remove(AvionV2 avion)
		{
		int i = 0;
		while(i < size)
			{
			if (tabAvion[i] == avion)
				{
				tabAvion[i] = null;
				break;
				}
			i++;
			}
		indexPut = i;
		this.notifyAll();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AvionV2[] tabAvion;
	private int size;
	private int indexPut;
	}
