package warcraftTD;

public class SpecialMonster extends Monster {
	int recompense;
	public SpecialMonster(Position p) {
		super(p);
		this.speed = 0.04;
		this.recompense = 20;
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(p.x, p.y, this.r);
	}

}
