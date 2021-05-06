package warcraftTD;

public abstract class Projectile {
	Position p;
	Position nextP;
	Double speed = 0.0;
	double w = 0.02;
	double h = 0.005;
	int degat;
	public Projectile(Position p) {
		super();
		this.p = p;
		this.nextP = new Position(p);
	}
	/**
	 * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		if (dy + dx != 0){
			// Mesure la distance à laquelle le monstre à pu se déplacer.
			double ratioX = dx/(Math.abs(dx) + Math.abs(dy));
			double ratioY = dy/(Math.abs(dx) + Math.abs(dy));
			 p.x += ratioX * speed;
			 p.y += ratioY * speed;
		}
	}

	public void update() {
		move();
		draw();
	}
	
	/**
	 * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public  abstract void draw();
}
