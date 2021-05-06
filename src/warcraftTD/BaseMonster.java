package warcraftTD;

public class BaseMonster extends Monster {
	 int cpt = 0;
	public BaseMonster(Position p) {
		super(p);
		this.speed = 0.01;
		this.recompense = 5;
		this.pointDeVie = 5;
	}
	
	/**
	 * Affiche un monstre qui change de couleur au cours du temps
	 * Le monstre est représenté par un cercle de couleur bleue ou grise
	 */
	public void draw() {
		
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledCircle(p.x, p.y, this.r);
		//StdDraw.pause(200);}
	}

	@Override
	public String toString() {
		return " la pos du monstre p=" + p + ", nextP=" + nextP;
	}
	
}
