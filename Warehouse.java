package sokoban;

public class Warehouse {
	private int remainingCrates;
	private int rowSize = 4; //P�lda �rt�k...
	private int columnSize = 4; //P�lda �rt�k...
	private Field[][] fields = new Field[rowSize][columnSize];
	private Crate[] crates;
	
	/**
	 * Visszaadja, hogy h�ny sorb�l �ll a p�lya.
	 * @return int t�pussal t�r vissza.
	 */
	public int GetRowSize() { return rowSize; }
	
	/**
	 * Visszaadja, hogy h�ny oszlopb�l �ll a p�lya.
	 * @return int t�pussal t�r vissza.
	 */
	public int GetColumnSize() { return columnSize; }
	
	/**
	 * Visszaadja a param�terek �ltal meghat�rozott helyen l�v� mez�t.
	 * @param i int t�pus�, ebben a sorban van a mez�.
	 * @param j int t�pus�, ebben az oszlopban van a mez�.
	 * @return Field t�pussal t�r vissza.
	 */
	public Field GetField(int i, int j) { return fields[i][j]; }
	
	/**
	 * A param�ter�l kapott l�d�t a l�d�kat tartalmaz� t�mb v�g�re sz�rja.
	 * @param c Crate t�pus�.
	 */
	public void AddCrate(Crate c) {
		if(crates == null) {
			crates = new Crate[1];
			crates[0] = c;
		}
		else {
			Crate[] temp = new Crate[crates.length + 1];
			for(int i = 0; i < crates.length; ++i) {
				temp[i] = crates[i];
			}
			temp[crates.length] = c;
			crates = temp;
		}
	}
	
	/**
	 * Megadja a rakt�rban l�v� l�d�k sz�m�t.
	 * @return int t�pust ad vissza.
	 */
	public int GetRemainingCrates() { return remainingCrates; }
	
	/**
	 * Egy l�da megsz�nt valamilyen okb�l.
	 * @param c Crate t�pus�, ez a l�da sz�nt meg.
	 */
	public void CrateRemoved(Crate c) {
		for(int i = 0; i < remainingCrates; ++i) {
			if(crates[i] == c) {
				Crate[] temp = new Crate[remainingCrates-1];
				for(int j = 0; j < remainingCrates-1; ++j) {
					if(i != j) {
						temp[j] = crates[j];
					}
				}
				crates = temp;
			}
		}
		
		--remainingCrates;
	}
	
	/**
	 * A param�ter�l kapott mez�t besz�rja a p�ly�ba, a param�ter�l megadott helyre.
	 * @param f Field t�pus�, ez a mez� ker�l besz�r�sra.
	 * @param i int t�pus�, a sor sz�ma.
	 * @param j int t�pus�, az oszlop sz�ma.
	 */
	public void AddField(Field f, int i, int j) {
		fields[i][j] = f;
	}
	
	/**
	 * L�trehozza a szomsz�ds�gi viszonyt a mez�k k�z�tt.
	 */
	public void SetFieldsNeighbours() {
		for(int i = 0; i < rowSize; ++i) {
			for(int j = 0; j < columnSize; ++j) {
				//A mez� szomsz�dainak be�ll�t�sa
				try {
					fields[i][j].SetNeighbour(fields[i-1][j], Direction.Up);
					fields[i-1][j].SetNeighbour(fields[i][j], Direction.Down);
				} catch(NullPointerException e) {
					//Nem csin�l semmit.
				} catch(ArrayIndexOutOfBoundsException out) {
					//Nem csin�l semmit.
				}
				try {
					fields[i][j].SetNeighbour(fields[i+1][j], Direction.Down);
					fields[i+1][j].SetNeighbour(fields[i][j], Direction.Up);
				} catch(NullPointerException e) {
					//Nem csin�l semmit.
				} catch(ArrayIndexOutOfBoundsException out) {
					//Nem csin�l semmit.
				}
				try {
					fields[i][j].SetNeighbour(fields[i][j-1], Direction.Left);
					fields[i][j-1].SetNeighbour(fields[i][j], Direction.Right);
				} catch(NullPointerException e) {
					//Nem csin�l semmit.
				} catch(ArrayIndexOutOfBoundsException out) {
					//Nem csin�l semmit.
				}
				try {
					fields[i][j].SetNeighbour(fields[i][j+1], Direction.Right);
					fields[i][j+1].SetNeighbour(fields[i][j], Direction.Left);
				} catch(NullPointerException e) {
					//Nem csin�l semmit.
				} catch(ArrayIndexOutOfBoundsException out) {
					//Nem csin�l semmit.
				}
			}
		}
	}
	
	/**
	 * Ellen�rzi, hogy van-e m�g �rv�nyes l�p�s a j�t�kban.
	 * @param workers Worker[] t�pus�, mely a j�t�kban l�v� munk�sok referenci�j�t adja meg.
	 * @return boolean t�pussal t�r vissza, mely akkor true, ha van olyan munk�s �s olyan l�da, mely tud l�pni.
	 */
	public boolean HasMoves(Worker[] workers) {
		//Munk�sok
		boolean workersCanMove = false;
		for(int i = 0; i < workers.length; ++i) {
			if(workers[i].HasMoves())
				workersCanMove = true;
		}
		
		//L�d�k
		boolean cratesCanMove = false;
		for(int i = 0; i < crates.length; ++i) {
			if(crates[i].HasMoves())
				cratesCanMove = true;
		}
	
		//Csak akkor igaz, ha a munk�sok �s a l�d�k k�z�l is tud valamelyik l�pni. 
		if(workersCanMove && cratesCanMove)
			return true;
		else
			return false;
	}	
}
