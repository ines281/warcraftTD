package warcraftTD;


public class TourBombe extends Tours {
	String Image;
	 int nbProjectilesTire = 0;
	//static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public TourBombe(Position p,int caseX, int caseY) {
		super(60, 20, 0.2, p, caseX, caseY);
		Image = "images/BOMBE SIMPLE.jpg";
	}
	@Override
	public void update() {
		this.setPortee(0.5);
		this.setvitesse(10);
		Image = "images/BOMBE UPDATED.jpg";
		this.nbProjectilesTire = 0; // pour réinitialiser le compteur
	}
	@Override
	public boolean canFire() {
		if(this.nbProjectilesTire %this.getvitesse() == 0) return true;
		return false;
	}

	//refait dans la classe world !!!!!!!
/*	@Override
	public void fire(ArrayList<Projectile> projectile,Position p ,Position nextP) {
		// TODO Auto-generated method stub
		
	}
	//(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
	@Override
	public void checkTarget(Monster m,double squareWidth,double squareHeight,int nbSquareY) {
		
	}
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		TourBombe.projectiles = projectiles;
	}
	*/
}

