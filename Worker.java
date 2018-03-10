package sokoban;

public class Worker extends MovableThing{
	private int points;
	private String name;
	
	/**
	 * A munk�s pontsz�m�nak eggyel n�vel�se.
	 */
	public void AddPoint() { ++points; }
	
	/**
	 * A munk�s megpr�b�l az adott ir�nyba l�pni (direktben, nem tolj�k).
	 * @param d Direction t�pus�, ebbe az ir�nyba haladna.
	 * @return boolean t�pussal t�r vissza, mely akkor true, ha siker�lt a mozg�s.
	 */
	public boolean DirectMove(Direction d) {
		if(GetField().GetNeighbour(d).DirectAccept(this, d))
		{
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Egy ir�nyt v�r a felhaszn�l�t�l, majd abba az ir�nyba megpr�b�l l�pni.
	 */
	public void Step() {
		//Szkeletonban csak jobbra l�p a munk�s
		DirectMove(Direction.Right);
	}
	
	/**
	 * T�rli mag�t az aktu�lis mez�r�l.
	 * null �rt�kre �ll�tja a t�rolt mez� �rt�k�t.
	 */
	public void Disappear() {
		GetField().Remove(this);
		SetField(null);
	}
	
	/**
	 * Kikapcsolja a param�ter�l kapott kapcsol�t.
	 */
	public void ControlSwitch(Switch s) {
		s.TurnOff();
	}
	
	/**
	 * A munk�st nem k�zvetlen pr�b�lj�k meg arr�bb tolni, �gy megpr�b�l az adott ir�nyba tol�dni.
	 * Ha nem siker�l arr�bb tol�dnia, akkor meghal.
	 * @param d Direction t�pus�, ebbe az ir�nyba mozogna.
	 * @return boolean t�pussal t�r vissza, mely k�l�nb�z� okokb�l, de mindig true lesz.
	 */
	public boolean PushedBy(Direction d) {
		if(!Move(d)) {
			Disappear();
		}
		
		return true;
	}
	
	/**
	 * A munk�st nem lehet direktben eltolni, ez�rt mindig false �rt�kkel t�r vissza.
	 * @return boolean t�pussal t�r vissza.
	 */
	public boolean DirectPushedBy(Direction d) {
		return false;
	}
	
	/**
	 * A munk�snak akkor van �rv�nyes l�p�se, ha b�rmelyik ir�nyba tud l�pni egyet.
	 * @return boolean t�pussal t�r vissza, mely akkor true, ha tud l�pni valamerre a munk�s. 
	 */
	public boolean HasMoves() {
		if( GetField().CheckMove(Direction.Up) ||
			GetField().CheckMove(Direction.Down) ||
			GetField().CheckMove(Direction.Left) ||
			GetField().CheckMove(Direction.Right) ) {
				return true;
		}
		else
			return false;
	}
}
