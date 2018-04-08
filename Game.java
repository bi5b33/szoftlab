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
		
		//CreateMap(filename);
		
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
			String line;
			try {
				int i = 0; //beolvasott sorok
				while(i < map.GetRowSize()) {
					line = br.readLine();
					//Sz�k�z alapj�n darabol
					String[] t = line.split(" ");
					for(int j = 0; j < map.GetColumnSize(); ++j) {
						switch(t[j]) {
						//Sima mez�
						case "F":
							map.AddField(new Field(), i, j);
							break;
						//C�lmez�
						case "G":
							map.AddField(new Goal(), i, j);
							break;
						//Nyitott lyuk
						case "H":
							Hole h = new Hole();
							h.Open();
							map.AddField(h, i, j);
							break;
						//Bez�rt lyuk
						case "C":
							Hole c = new Hole();
							c.Close();
							map.AddField(c, i, j);
							break;
						//Kapcsol�
						case "S":
							map.AddField(new Switch(), i, j);
							break;
						//Oszlop
						case "P":
							map.AddField(new Pillar(), i, j);
							break;
						}
					}
					++i;
				}
				line = br.readLine();
				//Lyukak-kapcsol�k �sszerendel�se
				if(line.equals("holes")) {
					line = br.readLine();
					while(!line.equals("crates") || !line.equals("workers")) {
						//2 koordin�ta sz�tv�laszt�sa
						String[] t = line.split(" ");
						String[] scoord = t[0].split(","); //kapcsol� koordin�t�i
						String[] hcoord = t[1].split(","); //lyuk koordin�t�i
						//Az �j kapcsol� �s hozz�rendelt lyuk hozz�ad�sa a p�ly�hoz
						Switch s = new Switch();
						Hole h = new Hole();
						s.SetHole(h);
						map.AddField(s, Integer.parseInt(scoord[0]), Integer.parseInt(scoord[1]));
						map.AddField(h, Integer.parseInt(hcoord[0]), Integer.parseInt(hcoord[1]));
						if(t[2].equals("O")) {
							h.Open();
						}
						if(t[2].equals("C")) {
							h.Close();
						}
						
						line = br.readLine();
					}
				}
				//L�d�k p�ly�ra helyez�se
				if(line.equals("crates")) {
					line = br.readLine();
					while(!line.equals("workers")) {
						String[] coord = line.split(",");
						Crate c = new Crate();
						map.GetField(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])).SetThing(c);
						map.AddCrate(c);
						
						line = br.readLine();
					}
				}
				//Munk�sok p�ly�ra helyez�se
				if(line.equals("workers")) {
					line = br.readLine();
					for(int n = 0; n < workers.length; ++n) {
						String[] coord = line.split(",");
						map.GetField(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])).SetThing(workers[n]);
						
						line = br.readLine();
					}
				}
			} catch (IOException e1) {
				//Nem siker�lt sort beolvasni.
				e1.printStackTrace();
			}
			
			try {
				br.close();
			} catch (IOException e) {
				//Nem siker�lt bez�rni.
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			//Rossz a f�jl neve.
			e.printStackTrace();
		}
		
		//Szomsz�dok be�ll�t�sa a p�ly�ban.
		map.SetFieldsNeighbours();
	}
	
	/**
	 * J�t�k befejez�se, nyertes nev�nek ki�r�sa.
	 */
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
	 * Az aktu�lis k�r�n l�v� j�t�kosnak ad egy pontot.
	 */
	public static void AddPointToWorker() {
		workers[playersNumber].AddPoint();
	}
	
	private void DrawMap() {
		//Felj. alatt...
	}
 }
