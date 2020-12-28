package warcraftTD;

public abstract class Tours {
	private int cout;
	private double vistesse;
	private double portee;
	// Position de la tour a l'instant t
	private	Position p;
	//private enum cible {aeriennes,terrestres}
	public Tours(int cout, double vistesse, double portee,Position p) {
		super();
		this.setCout(cout);
		this.setVistesse(vistesse);
		this.setPortee(portee);
		this.setP(p);
	}
	
	public abstract void update();
	
	/**
	 * Fonction abstraite qui sera instancie dans les classes filles pour afficher la tour sur le plateau de jeu.
	 */
	public abstract void draw();
	
	public int getCout() {
		return cout;
	}
	public void setCout(int cout) {
		this.cout = cout;
	}
	public double getVistesse() {
		return vistesse;
	}
	public void setVistesse(double vistesse) {
		this.vistesse = vistesse;
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
		return "Tours [cout=" + cout + ", vistesse=" + vistesse + ", portee=" + portee + ", p=" + p + "]";
	};
	
}
