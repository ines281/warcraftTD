package warcraftTD;

public class Main {

	public static void main(String[] args) {
		int width = 1300;
		int height = 650;
		int nbSquareX = 11;
		int nbSquareY = 11;
		int startX = 1;
		int startY = 10;		
		World w = new World(width, height, nbSquareX, nbSquareY, startX, startY);		
		// Lancement de la boucle principale du jeu
		w.run();
	}
}

