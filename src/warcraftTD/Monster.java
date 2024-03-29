package warcraftTD;

public abstract class Monster {
	// Position du monstre à l'instant t
	Position p;
	// Vitesse du monstre
	double speed;
	// Position du monstre à l'instant t+1
	Position nextP;
	// Boolean pour savoir si le monstre à atteint le chateau du joueur
	boolean reached;
	// Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
	int checkpoint = 0;
	int recompense ;
	int pointDeVie;
	double r = 0.01;
	//hitbox (la hitbox est le monstre ._. on fais une collison entre le projectile (rectangle) et le monstre (cercle) ) 
	//double x;
	//double y;
	public Monster(Position p) {
		this.p = p;
		this.nextP = new Position(p);
		reached = false;
	//	x = p.x;
	//	y= p.y;
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
		checkpoint++;
		//System.out.println("update base monster");
	}
	
	/**
	 * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public abstract void draw();
}
