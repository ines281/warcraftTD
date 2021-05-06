package warcraftTD;

public class TourArcher extends Tours {
	
	String Image;
	int nbProjectilesTire = 0;
	//static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public TourArcher(Position p,int caseX, int caseY) {
		super(50, 15, 0.3, p,caseX, caseY);
		Image = "images/ARCHER SIMPLE.jpg";
	}

	@Override
	public void update() {
		this.setPortee(0.6);
		this.setvitesse(5);
		Image = "images/ARCHER UPDATED.jpg";
		this.nbProjectilesTire = 0; // pour réinitialiser le compteur 
	}

	@Override
	public boolean canFire() {
		if(this.nbProjectilesTire %this.getvitesse() == 0) return true;
		return false;
	}

	//refait dans la classe world !!!!!!!

/*	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		TourArcher.projectiles = projectiles;
	}

	@Override
	public void fire(ArrayList<Projectile> projectile,Position p ,Position nextP) {
		// TODO Auto-generated method stub
		projectile.forEach(proj -> proj.update());
	}

	@Override
	//vérifier la collision entre deux cercles monster + la portée de la tour (x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
	public void checkTarget(Monster m,double squareWidth,double squareHeight,int nbSquareY) {
		// TODO Auto-generated method stub
			int i = (int)(nbSquareY- Math.floor(m.p.y/squareHeight)-1); //ligne
			int j = (int) (Math.floor(m.p.x/squareWidth)); //colonne
			System.out.println("le monstre i = "+i+" j = "+j);
			//puisque le tours d'archer tirent sur tous les types de monstre on a pas à verifier le type
			//if((Math.pow((m.p.x-this.getP().x),2) + Math.pow((m.p.y-this.getP().y),2)) <= Math.pow((m.r+squareWidth/2),2)) 
			//{}
			if(this.peutAtteindre(i,j)) 
			{
				System.out.println("faqou");
				Projectile p = new Projectile(this.getP());
				projectiles.add(p);
				fire(projectiles,m.p,m.nextP);
			}
			else projectiles.clear();
		
	}
	public boolean peutAtteindre(int i,int j) 
	{
		System.out.println("la tour "+this.caseX+" "+this.caseY);
		if(i <= this.caseX-(100*this.getPortee())|| i <=this.caseX+(100*this.getPortee()) && j<=this.caseY-(100*this.getPortee()) || j<=this.caseY+(100*this.getPortee()))
		 return true;
		else return false;
		}*/
}
