package warcraftTD;

public class FlyingMonster extends Monster {
	public FlyingMonster(Position p) {
		super(p);
		this.speed = 0.02;
		this.recompense = 8;
		this.pointDeVie = 3;
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledCircle(p.x, p.y, this.r);
		//StdDraw.show();
		//StdDraw.pause(200);
	}

}
