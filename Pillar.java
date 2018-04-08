package sokoban;

public class Pillar extends Field {
	/**
	 * Nem fogad el semmit ez a mez�.
	 */
	public boolean Accept(MovableThing t, Direction d, double s) {
		//Nem csin�lunk semmit, csak visszat�r�nk hamissal.
		return false;
	}
	
	/**
	 * Nem fogad el semmit ez a mez�.
	 */
	public boolean DirectAccept(MovableThing t, Direction d, double s) {
		//Nem csin�lunk semmit, csak visszat�r�nk hamissal.
		return false;
	}
}
