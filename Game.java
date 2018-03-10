package sokoban;

import java.util.Scanner;

public final class Game {
	private static int round = 0;
	private static Warehouse map;
	private static Worker[] workers;
	
	/**
	 * �j j�t�k futtat�sa.
	 */
	public static void NewGame() {
		CreateMap();
		
		//Am�g lehet l�pni �s nem fogytak el a l�d�k.
		while(map.HasMoves(workers) && map.GetRemainingCrates() != 0) {
			//Sorban l�pnek a j�t�kosok, ha lehet m�g l�pni.
			workers[round].Step();
			NextRound();
		}
		
		EndGame();
	}
	
	/**
	 * A j�t�k k�vetkez� k�rbe l�pteti.
	 * Eggyel n�veli a round v�ltoz�t (vagy null�zza, ha k�rbe�rtek a j�t�kosok).
	 */
	public static void NextRound() {
		++round;
		if(round == workers.length)
			round = 0;
	}
	
	/**
	 * L�trehoz egy �j rakt�r�p�letet �s gener�l hozz� egy p�ly�t.
	 */
	public static void CreateMap() {
		map = new Warehouse();
		map.InitFields();
		map.InitWall();
		map.InitPillar();
		map.InitSwitch();
		map.InitHole();
		map.InitGoal();
		map.InitCrate();
		
		//Egyel�re ez csak ilyen...
		System.out.println("H�ny munk�s legyen?");
		Scanner Cin = new Scanner(System.in);
		int members;
		members = Cin.nextInt();
		Cin.close();
		workers = new Worker[members];
		
		map.InitWorker(members);
	}
	
	public static void EndGame() {
		//Felj. alatt...
	}
	
	/**
	 * Visszaadja az aktu�lis k�r sorsz�m�t.
	 * @return	int t�pussal t�r vissza.
	 */
	public static int GetRound() { return round; }
	
	/**
	 * Visszaadja az aktu�lis k�rben mozg� munk�st.
	 * @return Worker t�pussal t�r vissza.
	 */
	public static Worker GetCurrentWorker() { return workers[round]; }
	
	/**
	 * A param�ter�l kapott munk�st elt�rolja a workers t�mbben.
	 * @param w Worker t�pus�, ez az objektum ker�l a t�mbbe.
	 * @param i int t�pus�, a munk�s sorsz�m�t adja meg.
	 */
	public static void AddWorker(Worker w, int i) {
		workers[i] = w;
	}
 }
