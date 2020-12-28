package warcraftTD;

public class TourArcher extends Tours {
	/*
	 * La tour d�archers co�te 50 pi�ces d�or.
� La tour d�archers attaque :
� � une vitesse (temps de rechargement) de 15;
� � une port�e de 0.3.
� La tour d�archers peut attaquer les cibles a�riennes et terrestres.
	 */
	private enum cible {a�rienne,terrestre};
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
