package warcraftTD;

public class TourArcher extends Tours {
	/*
	 * La tour d’archers coûte 50 pièces d’or.
– La tour d’archers attaque :
– à une vitesse (temps de rechargement) de 15;
– à une portée de 0.3.
– La tour d’archers peut attaquer les cibles aériennes et terrestres.
	 */
	private enum cible {aérienne,terrestre};
	public TourArcher(Position p) {
		super(50, 15, 0.3, p);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw() {
		
		StdDraw.picture(this.getP().x, this.getP().y, "images/ARCHER SIMPLE.jpg");
		
	}
	

}
