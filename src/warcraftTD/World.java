package warcraftTD;

import java.util.List;
import java.util.LinkedList;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.font.*;
//import 
@SuppressWarnings("unused")
public class World {
	// l'ensemble des monstres, pour gerer (notamment) l'affichage
	List<Monster> monsters = new ArrayList<Monster>();

	//???????????????L'ensembles des tours crees ???????????????????????????????????????????????????
	static ArrayList<TourArcher> ta = new ArrayList<TourArcher>();
	static ArrayList<TourBombe> tb = new ArrayList<TourBombe>();
	//?????????????????????????????????????????????????????????????????????????????????????????????
	static ArrayList<Position> blockedPos = new ArrayList<Position>();
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
	//Le joueur démarre la partie avec 100 pièces d’or dans sa réserve
	int or = 100;

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
				StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass2.jpg", squareWidth, squareHeight);
		// StdDraw.show();
	}

	/**
	 * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
	 */
	public void drawPath() {
		ArrayList<Position> pos = new ArrayList<Position>();
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.05);
		//?????????????????????????????????????????????????????????????????????????????????????????????
		Position p1 = new Position(spawn.x,spawn.y-0.5);
		Position p2 = new Position(p1.x+0.9,p1.y);
		Position p3 = new Position(p2.x,p2.y-0.4788);
		pos.add(spawn);
		pos.add(p1); pos.add(p2); pos.add(p3);
		for(int i = 0 ; i<3 ;i++) // donc spawn -> p1 -> p2 -> p3
		{
			if(pos.get(i).x!= pos.get(i+1).x) // ils sont sur la meme ligne verticale
			{
				for(double x = Position.minX(pos.get(i),pos.get(i+1)); x <= Position.maxX(pos.get(i),pos.get(i+1)) ; x+= squareWidth)
				{
					StdDraw.picture(x, pos.get(i).y, "images/DirtTile.png",squareWidth,squareHeight);
					blockedPos.add(new Position(x,pos.get(i).y));
				}
			}
			if(pos.get(i).y!= pos.get(i+1).y) // ils sont sur la meme ligne horizentale 
			{
				for(double x = Position.maxY(pos.get(i),pos.get(i+1)); x >= Position.minY(pos.get(i),pos.get(i+1));x -= squareHeight)
				{
					StdDraw.picture(pos.get(i).x, x, "images/DirtTile.png",squareWidth,squareHeight);
					blockedPos.add(new Position(pos.get(i).x,x));
				}
			}

		}
		//System.out.println(blockedPos.toString());
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledRectangle(spawn.x, spawn.y, squareWidth /2, squareHeight/2);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(p3.x,p3.y, squareWidth/2, squareHeight/2);
	}

	/**
	 * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	 */
	public void drawInfos() {
		//Pour dessiner les points de vie
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		double lifePer = (double)life/100; 
		StdDraw.picture(0.02, 0.07, "images/heart2.png",squareWidth,squareHeight);
		StdDraw.filledRectangle(0.05+lifePer, 0.07, lifePer , 0.01);
		//TODO UNE BOUCLE QUI DIMINUE LE NB DE POINTS DE VIE
		//Pour dessiner le nb d'or
		StdDraw.setPenColor(StdDraw.YELLOW);
		double orPer = (double)or/100; 
		StdDraw.picture(0.02, 0.02, "images/or.jpg",squareWidth,squareHeight);
		//StdDraw.text(0.02+orPer, 0.1, String.valueOf(or));
		StdDraw.text(0.06, 0.02, String.valueOf(or));
		StdDraw.show();
		//TODO UNE BOUCLE QUI DIMINUE LE NB DE POINTS DE VIE
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
			//montrer une image qui suit le curseur
			StdDraw.picture(normalizedX, normalizedY, "images/ARCHER SIMPLE.jpg",squareWidth,squareHeight); //0.06
			//si l'utlisateur a cliqué sur la souris
			if(StdDraw.isClicked == true ) {
				//on crée une tour d'archers
				TourArcher t = new TourArcher(new Position(StdDraw.newX,StdDraw.newY));
				double distance = 0;
				//check if pos is not blocked ???????????????????????????????????????????????????????????????????
				distance = blockedPos.get(0).dist(t.getP());
				
				for(Position p: blockedPos)
				{
					//calculer la distance minimum  !!!!!!!!!!!!!!!!!!!!!!!!!!!!
					if(p.dist(t.getP())<distance)  distance = p.dist(t.getP()); 
					
				}
				if(distance >= squareWidth/2) {
				ta.add(t);
				drawTower('a',squareWidth,squareHeight);
				} 
				else 
				{
					StdDraw.picture(normalizedX, normalizedY, "images/ARCHER SIMPLE.jpg",squareWidth,squareHeight);
				}
				
			}
			break;
		}
		case 'b' :
		{
			// TODO Ajouter une image pour représenter une tour à canon
			StdDraw.picture(normalizedX, normalizedY, "images/BOMBE SIMPLE.jpg",squareWidth,squareHeight);
			if(StdDraw.isClicked == true ) {
				TourBombe t = new TourBombe(new Position(StdDraw.newX,StdDraw.newY));
				double distance = 0;
				//check if pos is not blocked ???????????????????????????????????????????????????????????????????
				distance = blockedPos.get(0).dist(t.getP());
				
				for(Position p: blockedPos)
				{
					//calculer la distance minimum  !!!!!!!!!!!!!!!!!!!!!!!!!!!!
					if(p.dist(t.getP())<distance)  distance = p.dist(t.getP()); 
					
				}
				if(distance >= squareWidth/2) {
					tb.add(t);
					drawTower('b',squareWidth,squareHeight);
				} 
				else 
				{
					StdDraw.picture(normalizedX, normalizedY, "images/BOMBE SIMPLE.jpg",squareWidth,squareHeight);
				}
				
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
	public static void drawTower(char c, double squareWidth , double squareHeight) 
	{
		if(c=='a') 
		{
			for(TourArcher t: ta) 
			{
				StdDraw.picture(t.getP().x, t.getP().y, "images/ARCHER SIMPLE.jpg",squareWidth,squareHeight);
			}
			// StdDraw.show(); 
		}
		if(c=='b') 
		{
			for(TourBombe t: tb) 
			{
				StdDraw.picture(t.getP().x, t.getP().y, "images/BOMBE SIMPLE.jpg",squareWidth,squareHeight);
			}
		}
		else 
		{
			for(TourArcher t: ta) 
			{
				StdDraw.picture(t.getP().x, t.getP().y, "images/ARCHER SIMPLE.jpg" ,squareWidth,squareHeight);
			}
			for(TourBombe t: tb) 
			{
				StdDraw.picture(t.getP().x, t.getP().y, "images/BOMBE SIMPLE.jpg",squareWidth,squareHeight);
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
		drawTower(' ',squareWidth,squareHeight);
		return life;
	}

	/**
	 * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
	 * @param key la touche utilisée par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		StdDraw.setPenColor(StdDraw.BLACK);
		switch (key) {
		case 'a':
			//	System.out.println("Arrow Tower selected (50g).");
			StdDraw.clear(StdDraw.YELLOW_GREEN);
			StdDraw.text(StdDraw.mouseX(), StdDraw.mouseY(), "Arrow Tower selected (50g).");
			StdDraw.show();
			break;
		case 'b':
			//	System.out.println("Bomb Tower selected (60g).");
			StdDraw.clear(StdDraw.YELLOW_GREEN);
			StdDraw.text(StdDraw.mouseX(), StdDraw.mouseY(), "Bomb Tower selected (60g).");
			StdDraw.show();
			break;
		case 'e':
			//	System.out.println("Evolution selected (40g).");
			StdDraw.clear(StdDraw.YELLOW_GREEN);
			StdDraw.text(StdDraw.mouseX(), StdDraw.mouseY(), "Evolution selected (40g).");
			StdDraw.show();
			break;
		case 's':
			//	System.out.println("Starting game!");
			StdDraw.clear(StdDraw.YELLOW_GREEN);
			StdDraw.text(0.5, 0.5, "Starting game!");
			StdDraw.show();
			break;
		case 'q':
			//System.out.println("Exiting.");
			StdDraw.clear(StdDraw.YELLOW_GREEN);
			StdDraw.text(0.5, 0.5, "Exiting.");
			StdDraw.show();
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
	public void menu() 
	{
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.03);
		StdDraw.text(0.5,0.5, "azul");
		StdDraw.show();
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
