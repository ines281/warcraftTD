package warcraftTD;

public class ToureBombe extends Tours {
	
	/*
	 * Tour de bombes :
– La tour de bombes coûte 60 pièces d’or.
– La tour de bombes attaque
– à une vitesse (temps de rechargement) de 20;
– à une portée de 0.2.
– La tour de bombes ne peut pas attaquer les cibles aériennes.
Amélioration des tours :
– Le joueur peut améliorer chacun des deux types de tours : les tour d’archers (A) et les tours de bombes
(B).
2– Il appuie sur la touche E pour améliorer une tour.
– La tour est ensuite améliorée en cliquant dessus, à condition que :
– le joueur possède suffisamment d’or.
– la tour ne soit pas déjà améliorée.
– L’amélioration d’une tour modifie la vitesse d’attaque, la portée ainsi que la puissance d’attaque du
projectile.
Projectiles :
Chaque tour à un type spécifique de projectiles: ce sont des bombes ou des flèches en fonction de tour.
Les flèches peuvent attaquer les cibles aériennes et terrestres tandis que les bombes ne peuvent attaquer
que les cibles terrestres. Les flèches font 2 points de dégâts et se déplacent de 0.04 (par appel à update()),
tandis que les bombes font 8 points de dégâts pour une vitesse de déplacement de 0.02
	 */
	private enum cible {aérienne};
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
