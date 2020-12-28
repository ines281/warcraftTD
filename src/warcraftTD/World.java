package warcraftTD;

import java.util.List;
import java.util.LinkedList;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
//import 
@SuppressWarnings("unused")
public class World {
	// l'ensemble des monstres, pour gerer (notamment) l'affichage
	List<Monster> monsters = new ArrayList<Monster>();
	
	//???????????????L'ensembles des tours crees ???????????????????????????????????????????????????
	static ArrayList<TourArcher> ta = new ArrayList<TourArcher>();
	static ArrayList<ToureBombe> tb = new ArrayList<ToureBombe>();
	 
	// Position par laquelle les monstres vont venir
	Position spawn;
	
	// Information sur la taille du plateau de jeu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;
	
	// Nombre de points de vie du joueur
	int life = 20;
	
	// Commande sur laquelle le joueur appuie (sur le clavier)
	char key;
	
	// Condition pour terminer la partie
	boolean end = false;
	
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases données
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	public World(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		spawn = new Position(startSquareX * squareWidth + squareWidth / 2, startSquareY * squareHeight + squareHeight / 2);
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
	}
	
	/**
	 * Définit le décors du plateau de jeu.
	 */
	 public void drawBackground() {	
		 for (int i = 0; i < nbSquareX; i++)
			 for (int j = 0; j < nbSquareY; j++) 
				//StdDraw.filledRectangle(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, squareWidth , squareHeight);
				 StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.png", squareWidth, squareHeight);
			  StdDraw.show();
	 }
	 
	 /**
	  * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
	  */
	 public void drawPath() {
		 ArrayList<Position> pos = new ArrayList<Position>();
		 Position p = new Position(spawn);
		 StdDraw.setPenColor(StdDraw.DARK_GRAY);
		 StdDraw.setPenRadius(0.05);
		 StdDraw.filledRectangle(p.x, p.y, squareWidth / 2, squareHeight / 2);
		 //?????????????????????????????????????????????????????????????????????????????????????????????
		 Position p1 = new Position(spawn.x,spawn.y-0.5);
		 Position p2 = new Position(p1.x+0.9,p1.y);
		 Position p3 = new Position(p2.x,p2.y-0.4788);
		 pos.add(spawn);
		 pos.add(p1); pos.add(p2); pos.add(p3);
		 StdDraw.filledRectangle(p1.x,p1.y, squareWidth / 2, squareWidth / 2);
		 StdDraw.filledRectangle(p2.x,p2.y, squareWidth / 2, squareHeight / 2);
		 StdDraw.filledRectangle(p3.x,p3.y, squareWidth / 2, squareHeight / 2);
		 //?????????????????????????????????????????????????????????????????????????????????????????????
		// StdDraw.picture(spawn.x, spawn.y, "images/DirtTile.png", spawn.x, spawn.y);
		// StdDraw.picture((p2.x+p1.x)/2, p1.y, "images/DirtTile.png", p2.x-p1.x, 0.05);
		/* for(int i = 0 ;i<3;i++) {
			// StdDraw.line(pos.get(i).x, pos.get(i).y, pos.get(i+1).x, pos.get(i+1).y);
			//StdDraw.picture(pos.get(i).x, pos.get(i).y, "images/DirtTile.png", pos.get(i+1).x - pos.get(i).x, pos.get(i).y - pos.get(i+1).y);
			 StdDraw.picture(pos.get(i).x, pos.get(i).y, "images/DirtTile.png",squareWidth,squareHeight);
		 }*/
			for(int i = 0 ;i<3;i++) // donc spawn -> p1 -> p2 -> p3
			{
				System.out.println("aqlagh da");
				if(pos.get(i).x!= pos.get(i+1).x) // ils sont sur la meme ligne verticale
				{
					System.out.println("x dif");
					for(double x = Position.minX(pos.get(i),pos.get(i+1)); x <= Position.maxX(pos.get(i),pos.get(i+1)) ; x+= squareWidth)
					{
						//System.out.println(x);
						StdDraw.picture(x, pos.get(i).y, "images/DirtTile.png",squareWidth,squareHeight);
					}
				}
				if(pos.get(i).y!= pos.get(i+1).y) // ils sont sur la meme ligne horizentale 
				{
					System.out.println("y dif");
					for(double x = Position.maxY(pos.get(i),pos.get(i+1)); x >= Position.minY(pos.get(i),pos.get(i+1));x -= squareHeight)
					{
						 StdDraw.picture(pos.get(i).x, x, "images/DirtTile.png",squareHeight,squareWidth);
					}
				}
				
	}
	 }
	 
	 /**
	  * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	  */
	 public void drawInfos() {
		 StdDraw.setPenColor(StdDraw.BLACK);
	 }
	 
	 /**
	  * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
	  *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	  */
	 public void drawMouse() {
	
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;
		String image = null;
		switch (key) {
		case 'a' : 
			 // TODO Ajouter une image pour représenter une tour d'archers
		{
			StdDraw.picture(normalizedX, normalizedY, "images/ARCHER SIMPLE.jpg",0.06,0.06);
			if(StdDraw.isClicked == true ) {
				TourArcher t = new TourArcher(new Position(StdDraw.newX,StdDraw.newY));
				ta.add(t);
				drawTower('a');
			}
			 break;
		}
		case 'b' :
		{
			// TODO Ajouter une image pour représenter une tour à canon
			 StdDraw.picture(normalizedX, normalizedY, "images/BOMBE SIMPLE.jpg",0.06,0.06);
				if(StdDraw.isClicked == true ) {
					ToureBombe t = new ToureBombe(new Position(StdDraw.newX,StdDraw.newY));
					tb.add(t);
					drawTower('b');
				}
			 break;
		}
		}
		 if (image != null)
			 StdDraw.picture(normalizedX, normalizedY, image, squareWidth, squareHeight);
	 }
/*
 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 */
	 public static void drawTower(char c) 
	 {
		 if(c=='a') 
		 {
			 for(TourArcher t: ta) 
			 {
				 StdDraw.picture(t.getP().x, t.getP().y, "images/ARCHER SIMPLE.jpg",0.06,0.06);
			 }
			// StdDraw.show(); 
		 }
		 if(c=='b') 
		 {
			 for(ToureBombe t: tb) 
			 {
				 StdDraw.picture(t.getP().x, t.getP().y, "images/BOMBE SIMPLE.jpg",0.06,0.06);
			 }
		 }
		 else 
		 {
			 for(TourArcher t: ta) 
			 {
				 StdDraw.picture(t.getP().x, t.getP().y, "images/ARCHER SIMPLE.jpg" ,0.06,0.06);
			 }
			 for(ToureBombe t: tb) 
			 {
				 StdDraw.picture(t.getP().x, t.getP().y, "images/BOMBE SIMPLE.jpg",0.06,0.06);
			 }
		 }
	 }
	 /**
	  * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
	  * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
	  */
	 public void updateMonsters() {
	 
		Iterator<Monster> i = monsters.iterator();
		Monster m;
		while (i.hasNext()) {
			 m = i.next();
			 m.update();
			 if(m.p.y < 0) {
				 m.p.y = 1;
			 }
		 }
	 }
	 
	 /**
	  * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	  * @return les points de vie restants du joueur
	  */
	 public int update() {
		drawBackground();
		drawPath();
		drawInfos();
		updateMonsters();
		drawMouse();
		//?????????????????????????????????????,YEEEEES
		drawTower(' ');
		return life;
	 }

	/**
	 * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
	 * @param key la touche utilisée par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		switch (key) {
		case 'a':
		//	System.out.println("Arrow Tower selected (50g).");
			break;
		case 'b':
		//	System.out.println("Bomb Tower selected (60g).");
			break;
		case 'e':
		//	System.out.println("Evolution selected (40g).");
			break;
		case 's':
		//	System.out.println("Starting game!");
		case 'q':
		//System.out.println("Exiting.");
		}
	}
	
	/**
	 * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
	 * 		- Ajouter une tour à la position indiquée par la souris.
	 * 		- Améliorer une tour existante.
	 * Puis l'ajouter à la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		switch (key) {
		case 'a':
			// System.out.println("il faut ajouter une tour d'archers si l'utilisateur à de l'or !!");
			break;
		case 'b':
			//System.out.println("Ici il faut ajouter une tour de bombes");
			break;
		case 'e':
			//System.out.println("Ici il est possible de faire évolué une des tours");
			break;
		}
	}
	
	/**
	 * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les différentes possibilités 
	 * offertes au joueur pour intéragir avec le clavier
	 */
	public void printCommands() {
		//System.out.println("Press A to select Arrow Tower (cost 50g).");
		//System.out.println("Press B to select Cannon Tower (cost 60g).");
		//System.out.println("Press E to update a tower (cost 40g).");
		//System.out.println("Click on the grass to build it.");
		//System.out.println("Press S to start.");
	}
	
	/**
	 * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
	 */
	public void run() {
		printCommands();
		while(!end) {
			
			StdDraw.clear();
			if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}
			
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(50);
			}
			
			update();
			StdDraw.show();
			StdDraw.pause(20);			
		}
	}
}