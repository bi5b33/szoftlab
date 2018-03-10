package sokoban;

import java.util.Random;

public class Warehouse {
	private int remainingCrates;
	private Field[][] fields;
	private Crate[] crates;
	
	private final int size = 7;	//Random m�ret a p�ly�nak...
	private final int pieces = 3; //Random sz�m, majd megbesz�lj�k, mib�l mennyi legyen...
	
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
	 * A p�lya alapj�nak l�trehoz�sa.
	 */
	public void InitFields() {		
		//Egy �j, n�gyzet alak� p�lya l�trehoz�sa.
		fields = new Field[size][size];
		
		/*A mez�k l�trehoz�sa �s �sszek�t�se.
		 *A mez�k elt�rolj�k egym�st szomsz�dk�nt.
		 */
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				fields[i][j] = new Field();
				fields[i][j].SetWarehouse(this);
				ChangeField(fields[i][j], i, j);
			}
		}
	}
	
	/**
	 * A param�ter�l kapott mez� beilleszt�se a p�ly�ba. Elt�rolja mind a 4 ir�nyba a szomsz�dait, valamint �k is elt�rolj�k a kapott mez�t.
	 * @param f	Az �j mez�, mely beker�l a p�ly�ba.
	 * @param i Ebben a sorban fog elhelyezkedni.
	 * @param j	Ebben az oszlopban fog elhelyezkedni.
	 */
	private void ChangeField(Field f, int i, int j) {
		ChangeFieldUp(f, i, j);
		ChangeFieldDown(f, i, j);
		ChangeFieldLeft(f, i, j);
		ChangeFieldRight(f, i, j);
	}
	
	/**
	 * A megadott hely� mez� elt�rolja fels� szomsz�dk�nt a felette l�v� mez�t, m�g az elt�rolja �t als� szomsz�dk�nt.
	 * @param f Ehhez a mez�h�z viszony�tunk. � lesz az als� szomsz�d.
	 * @param i Ebben a sorban van az f.
	 * @param j Ebben az oszlopban van az f.
	 */
	private void ChangeFieldUp(Field f, int i, int j) {
		try {
			f.SetNeighbour(fields[i-1][j], Direction.Up);
			fields[i-1][j].SetNeighbour(f, Direction.Down);
		} catch(NullPointerException e) {
			//Nem csin�l semmit.
		} catch(ArrayIndexOutOfBoundsException out) {
			//Nem csin�l semmit.
		}
	}
	
	/**
	 * A megadott hely� mez� elt�rolja als� szomsz�dk�nt az alatta l�v� mez�t, m�g az elt�rolja �t fels� szomsz�dk�nt.
	 * @param f Ehhez a mez�h�z viszony�tunk. � lesz a fels� szomsz�d.
	 * @param i Ebben a sorban van az f.
	 * @param j Ebben az oszlopban van az f.
	 */
	private void ChangeFieldDown(Field f, int i, int j) {
		try {
			f.SetNeighbour(fields[i+1][j], Direction.Down);
			fields[i+1][j].SetNeighbour(f, Direction.Up);
		} catch(NullPointerException e) {
			//Nem csin�l semmit.
		} catch(ArrayIndexOutOfBoundsException out) {
			//Nem csin�l semmit.
		}
	}
	
	/**
	 * A megadott hely� mez� elt�rolja baloldali szomsz�dk�nt a t�le balra l�v� mez�t, m�g az elt�rolja �t jobboldali szomsz�dk�nt.
	 * @param f Ehhez a mez�h�z viszony�tunk. � lesz a jobboldali szomsz�d.
	 * @param i Ebben a sorban van az f.
	 * @param j Ebben az oszlopban van az f.
	 */
	private void ChangeFieldLeft(Field f, int i, int j) {
		try {
			f.SetNeighbour(fields[i][j-1], Direction.Left);
			fields[i][j-1].SetNeighbour(f, Direction.Right);
		} catch(NullPointerException e) {
			//Nem csin�l semmit.
		} catch(ArrayIndexOutOfBoundsException out) {
			//Nem csin�l semmit.
		}
	}
	
	/**
	 * A megadott hely� mez� elt�rolja jobboldali szomsz�dk�nt a t�le jobbra l�v� mez�t, m�g az elt�rolja �t baloldali szomsz�dk�nt.
	 * @param f Ehhez a mez�h�z viszony�tunk. � lesz a baololdali szomsz�d.
	 * @param i Ebben a sorban van az f.
	 * @param j Ebben az oszlopban van az f.
	 */
	private void ChangeFieldRight(Field f, int i, int j) {
		try {
			f.SetNeighbour(fields[i][j+1], Direction.Right);
			fields[i][j+1].SetNeighbour(f, Direction.Left);
		} catch(NullPointerException e) {
			//Nem csin�l semmit.
		} catch(ArrayIndexOutOfBoundsException out) {
			//Nem csin�l semmit.
		}
	}
	
	/**
	 * A fal l�trehoz�sa, k�rben a p�lya sz�l�n.
	 */
	public void InitWall() {
		Wall w = new Wall();
		
		for(int i = 0; i < size; ++i) {
			//Balsz�l
			Pillar p1 = new Pillar();
			ChangeField(p1, i, 0);
			fields[i][0] = p1;
			p1.SetWarehouse(this);
			w.AddComponent(p1);
			
			//Jobbsz�l
			Pillar p2 = new Pillar();
			ChangeField(p2, i, size-1);
			fields[i][size-1] = p2;
			p2.SetWarehouse(this);
			w.AddComponent(p2);
			
			//Teteje
			Pillar p3 = new Pillar();
			ChangeField(p3, 0, i);
			fields[0][i] = p3;
			p3.SetWarehouse(this);
			w.AddComponent(p3);
			
			//Alja
			Pillar p4 = new Pillar();
			ChangeField(p4, size-1, i);
			fields[size-1][i] = p4;
			p4.SetWarehouse(this);
			w.AddComponent(p4);
		}
	}
	
	/**
	 * Oszlopok l�trehoz�sa �s hozz�ad�sa a p�ly�hoz.
	 */
	public void InitPillar() {
		for(int i = 0; i < pieces; ++i) {
			AddField(new Pillar());
		}
	}
	
	/**
	 * Kapcsol�k �s hozz�juk lyukak l�trehoz�sa.
	 */
	public void InitSwitch() {
		for(int i = 0; i < pieces; ++i) {
			Hole h = new Hole();
			Switch s = new Switch(h);
			AddField(h);
			AddField(s);
		}
	}
	
	/**
	 * A lyukak l�trehoz�sa �s hozz�ad�sa a p�ly�hoz.
	 */
	public void InitHole() {
		for(int i = 0; i < pieces; ++i) {
			AddField(new Hole());
		}
	}
	
	/**
	 * A c�lmez�k l�trehoz�sa �s hozz�ad�sa a p�ly�hoz.
	 */
	public void InitGoal() {
		for(int i = 0; i < pieces; ++i) {
			AddField(new Goal());
		}
	}
	
	/**
	 * A l�d�k l�trehoz�sa �s hozz�ad�sa a p�ly�hoz.
	 */
	public void InitCrate() {
		crates = new Crate[pieces];
		
		for(int i = 0; i < pieces; ++i) {
			Crate c = new Crate();
			AddMovableThing(c);
			crates[i] = c;
		}
		remainingCrates = pieces;
	}
	
	/**
	 * A munk�sok l�trehoz�sa �s hozz�ad�sa a p�ly�hoz.
	 */
	public void InitWorker(int members) {
		for(int i = 0; i < members; ++i) {
			Worker w = new Worker();
			Game.AddWorker(w, i);
			AddMovableThing(w);
		}
	}
	
	/**
	 * A param�ter�l kapott dolgot elhelyezi egy random mez�re.
	 * @param t MovableThing t�pus�, ezt helyezi el a p�ly�n valahol.
	 */
	void AddMovableThing(MovableThing t) {
		Random r = new Random();
		boolean done = false;
		
		while(!done) {
			//K�t sz�m gener�l�sa 1 �s (size-1) k�z�tt
			int i = r.nextInt((size-1) - 1) + 1;
			int j = r.nextInt((size-1) - 1) + 1;
		
			//Ha nem egy extra mez� a sorsolt, akkor a param�ter�l kapott mez� lesz a hely�n.
			if( !(fields[i][j].IsExtra()) ) {
				//Az ir�ny jelen esetben teljesen mindegy, mert nincs semmi a mez�n, �gyhogy el lesz fogadva.
				fields[i][j].Accept(t, Direction.Right);
				fields[i][j].MakeExtra();
				done = true;
			}
		}
	}	
	
	/**
	 * A param�ter�l kapott mez�t egy random helyre beilleszti a p�ly�ba.
	 * @param f Field t�pus�, ez a mez� ker�l a p�ly�ba.
	 */
	void AddField(Field f) {
		Random r = new Random();
		boolean done = false;
		
		while(!done) {
			//K�t sz�m gener�l�sa 1 �s (size-1) k�z�tt.
			int i = r.nextInt((size-1) - 1) + 1;
			int j = r.nextInt((size-1) - 1) + 1;
		
			//Ha nem egy extra mez� a sorsolt, akkor a param�ter�l kapott mez� lesz a hely�n.
			if( !(fields[i][j].IsExtra()) ) {
				//A szomsz�ds�gi viszony l�trehoz�sa.
				ChangeField(f, i, j);
				fields[i][j] = f;
				fields[i][j].SetWarehouse(this);
				//Ezent�l ez egy extra mez� lesz.
				fields[i][j].MakeExtra();
				done = true;
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
