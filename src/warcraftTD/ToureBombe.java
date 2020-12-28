package warcraftTD;

public class ToureBombe extends Tours {
	
	/*
	 * Tour de bombes :
� La tour de bombes co�te 60 pi�ces d�or.
� La tour de bombes attaque
� � une vitesse (temps de rechargement) de 20;
� � une port�e de 0.2.
� La tour de bombes ne peut pas attaquer les cibles a�riennes.
Am�lioration des tours :
� Le joueur peut am�liorer chacun des deux types de tours : les tour d�archers (A) et les tours de bombes
(B).
2� Il appuie sur la touche E pour am�liorer une tour.
� La tour est ensuite am�lior�e en cliquant dessus, � condition que :
� le joueur poss�de suffisamment d�or.
� la tour ne soit pas d�j� am�lior�e.
� L�am�lioration d�une tour modifie la vitesse d�attaque, la port�e ainsi que la puissance d�attaque du
projectile.
Projectiles :
Chaque tour � un type sp�cifique de projectiles: ce sont des bombes ou des fl�ches en fonction de tour.
Les fl�ches peuvent attaquer les cibles a�riennes et terrestres tandis que les bombes ne peuvent attaquer
que les cibles terrestres. Les fl�ches font 2 points de d�g�ts et se d�placent de 0.04 (par appel � update()),
tandis que les bombes font 8 points de d�g�ts pour une vitesse de d�placement de 0.02
	 */
	private enum cible {a�rienne};
	public ToureBombe(Position p) {
		super(60, 20, 0.2, p);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw() {
		StdDraw.picture(this.getP().x, this.getP().y, "images/BOMBE SIMPLE.jpg");
		
	}

}
