package warcraftTD;

public abstract class Tours {
	private int cout;
	private double vitesse;
	private double portee;
	// Position de la tour a l'instant t
	private	Position p;
	//Case ou se trouve la tour !!
	int caseX;
	int caseY;
	//private enum cible {aeriennes,terrestres}
	public Tours(int cout, double vitesse, double portee,Position p,int caseX, int caseY) {
		super();
		this.setCout(cout);
		this.setvitesse(vitesse);
		this.setPortee(portee);
		this.setP(p);
		this.caseX = caseX;
		this.caseY = caseY;
	}

	public abstract void update();


	public int getCout() {
		return cout;
	}
	public void setCout(int cout) {
		this.cout = cout;
	}
	public double getvitesse() {
		return vitesse;
	}
	public void setvitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	public double getPortee() {
		return portee;
	}
	public void setPortee(double portee) {
		this.portee = portee;
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	@Override
	public String toString() {
		return "Tours [cout=" + cout + ", vitesse=" + vitesse + ", portee=" + portee + ", p=" + p + "]";
	}
	public abstract boolean canFire();
	//refait dans la classe world !!!!!!!
	/*public abstract void checkTarget(Monster m,double squareWidth,double squareHeight,int nbSquareY);
	public abstract void fire(ArrayList<Projectile> projectile,Position p ,Position nextP) ;*/
}