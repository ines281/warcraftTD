package warcraftTD;

public class Fleche extends Projectile {
	public Fleche(Position p) {
		super(p);
		this.degat = 2;
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.ORANGE);
		StdDraw.filledRectangle(p.x, p.y, w, h);
		StdDraw.show();
		StdDraw.pause(5);
		//;(p.x, p.y, 0.01);
	}

}
