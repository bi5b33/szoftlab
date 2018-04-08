package sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public final class Game {
	private static int round = 0;
	private static Warehouse map;
	private static Worker[] workers;
	private static int playersNumber = 0; //Az aktu�lisan soron l�v� j�t�kos sorsz�ma.
	
	/**
	 * �j j�t�k futtat�sa.
	 */
	public static void NewGame(int pieces) {
		//CreateMap(filename);
		
		//Munk�sok l�trehoz�sa
		workers = new Worker[pieces];
		Scanner scanner = new Scanner(System.in);
		for(int i = 0; i < pieces; ++i) {
			System.out.println("Mi legyen a(z) " + i + ". j�t�kos neve?");
			String s;
			s = scanner.nextLine();
			CreateWorker(s, i);
		}
		scanner.close();
		
		//Am�g lehet l�pni �s nem fogytak el a l�d�k.
		while(map.HasMoves(workers) && map.GetRemainingCrates() != 0) {
			//Sorban l�pnek a j�t�kosok, ha lehet m�g l�pni.
			workers[playersNumber].Step();
			NextRound();
		}
		
		EndGame();
	}
	
	private static void CreateWorker(String s, int i) {
		workers[i] = new Worker(s);
	}
	
	/**
	 * A j�t�kot eggyel tov�bb l�pteti.
	 * Eggyel n�veli a round v�ltoz�t, ha minden j�t�kos l�pet a k�rben.
	 */
	public static void NextRound() {
		++playersNumber;
		if(playersNumber == workers.length) {
			playersNumber = 0;
			++round;
		}
	}
	
	/**
	 * L�trehoz egy �j rakt�r�p�letet �s gener�l hozz� egy p�ly�t a f�jl alapj�n.
	 * @param file String t�pus�, a p�ly�t tartalmaz� f�jl neve.
	 */
	public static void CreateMap(String file) {
		map = new Warehouse();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			//Felj. alatt..
			
			
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		map.SetFieldsNeighbours();
	}
	
	public static void EndGame() {
		int max = 0; //Legt�bb pontot gy�jt�tt j�t�kos sorsz�ma lesz a v�ltoz�ban.
		for(int i = 1; i < workers.length; ++i) {
			if(workers[max].GetPoints() < workers[i].GetPoints()) {
				max = i;
			}
		}
		
		System.out.println(workers[max].GetName() + " nyert!");
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
	public static Worker GetCurrentWorker() { return workers[playersNumber]; }
	
	/**
	 * Az aktu�lis k�r�n l�v� j�t�kosnak ad egy pontot.
	 */
	public static void AddPointToWorker() {
		workers[playersNumber].AddPoint();
	}
	
	private void DrawMap() {
		//Felj. alatt...
	}
 }
