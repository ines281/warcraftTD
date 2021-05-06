package warcraftTD;

public class Bombe extends Projectile {

	public Bombe(Position p) {
		super(p);
		this.degat = 8;
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledRectangle(p.x, p.y, w, h);
		StdDraw.show();
		StdDraw.pause(5);
		//;(p.x, p.y, 0.01);
		
	}

}
