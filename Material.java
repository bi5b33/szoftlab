package sokoban;

public abstract class Material {
	private double cohesion;
	
	/**
	 * Visszaadja az anyagra jellemz� s�rl�d�si er�t.
	 * @return double t�pussal t�r vissza, melynek �rt�ke a s�rl�d�si er�.
	 */
	public double GetCohesion() { return cohesion; }
	
	/**
	 * Elt�rolja az �j s�rl�d�si er� �rt�k�t.
	 * @param c double t�pus�, mely az �j s�rl�d�si er� �rt�k�t adja meg.
	 */
	public void SetCohesion(double c) { cohesion = c; } 
	
	/**
	 * A param�ter�l kapott mez�re ker�l az anyag.
	 * @param f Field t�pus�, erre a mez�re r�helyezi mag�t az anyag.
	 */
	public void PutOn(Field f) {
		f.SetMaterial(this);
	}
	
	public abstract double ModifyCohesion(double c);
}
