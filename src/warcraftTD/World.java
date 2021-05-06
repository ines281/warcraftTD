package warcraftTD;
/**
 * @author Ines
 *
 */

import java.util.List;
import java.util.ListIterator;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;


public class World {
	// l'ensemble des monstres, pour gerer (notamment) l'affichage
	List<Monster> monsters = new ArrayList<Monster>();
	//================================================================================================================
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Nombre de monstres a chaque niveau et nb de vagues et type de spawn!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	int nbMonstresT = 0; 
	int nbMonstresC = 0;
	int nbWavesT = 0;
	int nbWavesC = 0;
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!L'ensemble des tours crées !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	ArrayList<Tours> tours = new ArrayList<Tours>();

	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Difficulté du jeu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	int difficulte = 0;

	//le chemin represente l'endroit de la grille de jeu où peuvent se déplacer les monstres et aussi les positions ou on ne peut pas batir des tours
	ArrayList<Position> chemin = new ArrayList<Position>();
	ArrayList<Position> positions = new ArrayList<Position>();

	// Position par laquelle les monstres vont venir
	Position spawn;
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TIME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Duration deltaTime;
		Instant beginTime; 
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	Font font = new Font("Verdana", Font.BOLD, 19);
	// Information sur la taille du plateau de jeu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;

	// Nombre de points de vie du joueur
	int life = 4; // car dans le niveau 1 y'a 5 monstres donc si on ne tue pas au mois deux monstres game over //changer life selon les niveaux dans gameSetup()
	// Nombre d'or du joueur (au début de la partie = 100) !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	int or = 200;
	// Commande sur laquelle le joueur appuie (sur le clavier)
	char key;

	// Condition pour terminer la partie
	boolean end = false;
	//iiiiiiiiiiiiiiiiiiii REPRESENTE LE CHEMIN ET LES TOURS CREES iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
	/*
	 * les 1 representent le chemin et après à chaque création d'une tour on met dans la case correpondante soit 2 pour une tour d'archer soit 3 pour une de type bombe,
	 * si on met a jour une tour d'archer on met 4 dans la case correpondante et 6 si c'est pour une tour de bombes
	 */

	int [][] game = {
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
	};
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
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
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Définit le décors du plateau de jeu.
	 */
	public void drawBackground() {	
		StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
		for (int i = 0; i < nbSquareX; i++) {
			for (int j = 0; j < nbSquareY; j++) {
				StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass2.jpg", squareWidth+0.001, squareHeight+0.001);
				//dessiner une grille (pas necessaire)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				StdDraw.rectangle(i * squareWidth + squareWidth / 2,  j * squareHeight + squareHeight / 2, squareWidth/2, squareHeight/2);	
			}}
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
	 */
	public void drawPath() {
		Position p = new Position(spawn);
		StdDraw.setPenColor(StdDraw.YELLOW);
		// 2 ème version
		/* for (int i = 0; i < path.length; i++) {
			 for (int j = 0; j < path[i].length; j++) {
				 if(path[i][j]==1) {
				//StdDraw.picture(j * squareWidth , i* squareHeight , "images/DirtTile.png", squareWidth+0.001, squareHeight+0.001);
			StdDraw.picture(j * squareWidth+ squareWidth / 2 , 1-i* squareHeight + squareHeight / 2 - squareHeight, "images/DirtTile.png", squareWidth-0.001, squareHeight-0.001);
			Position pos = new Position(j * squareWidth+ squareWidth / 2,1-i* squareHeight + squareHeight / 2 - squareHeight);
			if(!chemin.contains(pos)) chemin.add(pos);
				//System.out.println(chemin.size());
				 }
			 }
		 }*/
		// 1 ere version de la fonction drawPath() qui marvhe aussi !
		chemin.add(p);//point de départ du chemin
		//quelques points repères
		Position p1 = new Position(p.x, p.y-2*squareHeight);
		//StdDraw.filledRectangle(p1.x, p1.y, squareWidth / 2, squareHeight / 2);
		Position p2 = new Position(p1.x+3*squareWidth, p1.y);		 
		Position p3 = new Position(p2.x, p2.y-3*squareHeight);		 
		Position p4 = new Position(p3.x+2*squareWidth, p3.y);		 
		Position p5 = new Position(p4.x, p4.y-3*squareHeight);		 
		Position p6 = new Position(p5.x+4*squareWidth, p5.y);
		Position finale = new Position(p6.x, p6.y-2*squareHeight);
		StdDraw.filledRectangle(finale.x, finale.y, squareWidth / 2, squareHeight / 2);
		// ajouter les points au chemin
		chemin.add(p1);chemin.add(p2);chemin.add(p3);chemin.add(p4);chemin.add(p5);chemin.add(p6);chemin.add(finale);
		positions.add(p);
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		for(int i = 0 ; i<7 ;i++) // donc p (spawn) -> p1 -> p2 -> p3 ->p4 -> ... -|>finale
		{
			if(chemin.get(i).x!= chemin.get(i+1).x) // ils sont sur la meme ligne verticale
			{
				for(double x = Position.minX(chemin.get(i),chemin.get(i+1)); x <= Position.maxX(chemin.get(i),chemin.get(i+1)) ; x+= squareWidth)
				{
					// j'ai enlevé l'image car y'avait un bug, a chaque fois que les monstres bougent l'image bouge aussi 
					//StdDraw.picture(x, chemin.get(i).y, "images/DirtTile.png",squareWidth-0.001,squareHeight-0.001);
					StdDraw.filledRectangle(x,chemin.get(i).y, squareWidth/2-0.001 , squareHeight/2-0.001);
					//chemin.add(new Position(x,chemin.get(i).y));
					positions.add(new Position(x,chemin.get(i).y));
				}
			}
			if(chemin.get(i).y!= chemin.get(i+1).y) // ils sont sur la meme ligne horizentale 
			{
				for(double x = Position.maxY(chemin.get(i),chemin.get(i+1)); x >= Position.minY(chemin.get(i),chemin.get(i+1));x -= squareHeight)
				{
					//StdDraw.picture(chemin.get(i).x, x, "images/DirtTile.png",squareWidth-0.001,squareHeight-0.001);
					StdDraw.filledRectangle(chemin.get(i).x, x, squareWidth/2-0.001 , squareHeight/2-0.001);
					//chemin.add(new Position(chemin.get(i).x,x));
					positions.add(new Position(chemin.get(i).x,x));
				}
			}

		}positions.add(finale);
		StdDraw.filledRectangle(p.x, p.y, squareWidth / 2, squareHeight / 2);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledRectangle(finale.x, finale.y, squareWidth / 2, squareHeight / 2);
		// marche bien mais ralentit trop le jeu
		/* for(Position pos: chemin) 
		   {
			   StdDraw.picture(pos.x, pos.y, "images/DirtTile.png",squareWidth,squareHeight);
		   }*/	 
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	 */
	public void drawInfos() {
		//Pour dessiner les points de vie
		StdDraw.setPenColor(StdDraw.CYAN);
		StdDraw.picture(0.02, 0.07, "images/heart2.png",squareWidth/4,squareHeight/4);
		StdDraw.text(0.06, 0.07, String.valueOf(life));

		//Pour dessiner le nb d'or
		StdDraw.picture(0.02, 0.02, "images/or.jpg",squareWidth/4,squareHeight/4);
		StdDraw.text(0.06, 0.02, String.valueOf(or));

	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
	 *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	 */
	public void drawMouse() {
		//TODO  ne pas afficher une tour si on choisit un niveau a/B ou c...
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		switch (key) {
		case 'a' : 
			// TODO Ajouter une image pour reprÃ©senter une tour d'archers
			//montrer une image qui suit le curseur
			StdDraw.picture(normalizedX, normalizedY, "images/ARCHER SIMPLE.jpg",squareWidth-0.001,squareHeight-0.001); //0.06
			if(StdDraw.isMousePressed()) 
			{
				if( or< 50) 
				{
					StdDraw.text(normalizedX, normalizedY, "Vous n'avez pas assez d'or !");
					StdDraw.show();
					StdDraw.pause(800);
					break;
				}
				else 
				{		
					//On cherche la case correspondante dans int [] game
					int i = (int)(nbSquareY- Math.floor(normalizedY/squareHeight)-1); //ligne
					int j = (int) (Math.floor(normalizedX/squareWidth)); //colonne
					TourArcher t = new TourArcher(new Position(normalizedX,normalizedY),i,j);
					//double distance = 0;
					//check if pos is not blocked and that ther's no tower built there ???????????????????????????????????????????????????????????????????
					/* 1 ere methode 
					 * distance = ((ArrayList<Position>) chemin).get(0).dist(t.getP());

					for(Position p: chemin)
					{
						//calculer la distance minimum  !!!!!!!!!!!!!!!!!!!!!!!!!!!!
						if(p.dist(t.getP())<distance)  distance = p.dist(t.getP()); 

					}
					if(distance > squareWidth/2) { build tower here !!!}*/
					// Meilleure méthode car on evite les boucles  
					if(game[i][j] == 0) {
						tours.add(t);
						or -= 50;
						game[i][j] = 2;
					}
					else {
						StdDraw.text(normalizedX, normalizedY, "Vous ne pouvez pas batir de tour ici !");
						StdDraw.show();
						StdDraw.pause(800);
						//StdDraw.picture(normalizedX, normalizedY, "images/ARCHER SIMPLE.jpg",squareWidth,squareHeight);
					}
				} 

			}break;

		case 'b' :
			// TODO Ajouter une image pour reprÃ©senter une tour Ã  canon
			StdDraw.picture(normalizedX, normalizedY, "images/BOMBE SIMPLE.jpg",squareWidth-0.001,squareHeight-0.001);
			if(StdDraw.isMousePressed()) 
			{
				if( or< 60) 
				{
					StdDraw.text(normalizedX, normalizedY, "Vous n'avez pas assez d'or !");
					StdDraw.show();
					StdDraw.pause(800);
					break;
				}
				else 
				{
					//On cherche la case correspondante dans int [] game
					int i = (int)(nbSquareY- Math.floor(normalizedY/squareHeight)-1); //ligne
					int j = (int) (Math.floor(normalizedX/squareWidth)); //colonne
					TourBombe t = new TourBombe(new Position(normalizedX,normalizedY),i,j);
					//double distance = 0;
					//check if pos is not blocked ???????????????????????????????????????????????????????????????????
					if(game[i][j] != 2 && game [i][j] != 3) {
						tours.add(t);
						or -= 60;
						game[i][j] = 3;
					}
					else {
						StdDraw.text(normalizedX, normalizedY, "Vous ne pouvez pas batir de tour ici !");
						StdDraw.show();
						StdDraw.pause(800);
						//StdDraw.picture(normalizedX, normalizedY, "images/BOMBE SIMPLE.jpg",squareWidth,squareHeight);
					}
				} 

			}break;
			
		}

	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	 *  Pour chaque tour de la liste des tours crées, affiche une image pour représenter cette tour
	 */
	public void drawTower() 
	{
		//tours.forEach(StdDraw.picture(, y, filename););
		for(Tours t : tours) 
		{
			if(t instanceof TourArcher)
			{//StdDraw.picture(t.getP().x, t.getP().y, "images/ARCHER SIMPLE.jpg",squareWidth-0.001,squareHeight-0.001);
				StdDraw.picture(t.getP().x, t.getP().y, ((TourArcher) t).Image,squareWidth-0.001,squareHeight-0.001);
				StdDraw.circle(t.getP().x, t.getP().y, t.getPortee()); //la portee de la tour 
			}
			else 
			{
				StdDraw.picture(t.getP().x, t.getP().y, ((TourBombe) t).Image,squareWidth-0.001,squareHeight-0.001);
				StdDraw.circle(t.getP().x, t.getP().y, t.getPortee());
			}
		}
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
	 * Modifie la position du monstre au cours du temps à  l'aide du paramètre nextP.
	 */
	boolean startTime = false; // quand l'utilisateur clique sur s on commence à afficher les monstres
	int timeToSpawn = 0;
	boolean waveSpawn = true;
	int nbMonstresK = 0; //nb de monstres tués !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	int wait = 0;
	/*
	 * Version : Othmane RADY
	 * public void updateMonsters()  {

        //Iterator<Monster> i = monsters.iterator();
        //Monster m = monsters.get(0);
        Monster new_monster = new BaseMonster(new Position(1 * this.squareWidth + this.squareWidth / 2, 24 * this.squareHeight + this.squareHeight / 2));
        new_monster.nextP = new Position(1 * this.squareWidth + this.squareWidth / 2, 0);
        new_monster.speed = 0.01;
        monsters.add(new_monster);
        Monster new_monster_bis = new BaseMonster(new Position(1 * this.squareWidth + this.squareWidth / 2, 24 * this.squareHeight + this.squareHeight / 2));
        new_monster_bis.nextP = new Position(1 * this.squareWidth + this.squareWidth / 2, 0);
        new_monster_bis.speed = 0.02;
        monsters.add(new_monster_bis);
        //System.out.println("taille de la vague: "+ monsters.size());
        for (Monster m:monsters){
        System.out.println("monster spawned: "+ nb_monstre_materialise + " SPEED: "+ m.speed);
        m.update();

        //System.out.println("monstre matérialisé numéro: "+ nb_monstre_materialise);
        //nb_monstre_materialise=nb_monstre_materialise+1;
        if(m.p.y < 0) {
        m.p.y = 1;
        }
        System.out.println("monster spawned: "+ nb_monstre_materialise + " SPEED: "+ m.speed+ " DONE");
        nb_monstre_materialise=nb_monstre_materialise+1;


        }

        }
	 */
	public void updateMonsters() 
	{	
		//TODO
		ListIterator<Monster> i = monsters.listIterator();
		Monster m; 
		drawInfos();
		if(monsters.size()==1 && nbWavesC == 0)//debut de la vague
		{
			nbWavesC = 1; //ON A CREER UNE VAGUE
		}
		if(nbWavesC <= nbWavesT) //Y'a TOUJOURS DES VAGUES A INSTANCIER OU ON A PAS ENCORE FINI LA VAGUE
		{
			if(nbMonstresC >= nbMonstresT) // si on arrive à la fin de la vague
			{
				nbWavesC++;
				nbMonstresC = 0; //on reinitialise le nombre de monstres crées
				if(nbWavesC <= nbWavesT) 
				{
					StdDraw.text(0.5, 0.5, "Nouvelle Vague  !");
					StdDraw.show();
					StdDraw.pause(800);
				}
			}
			else //on a pas encore fini avec la vague
			{
						timeToSpawn++;
					while (i.hasNext())
					{
						drawInfos();
						m = i.next();
						m.p = (positions).get(m.checkpoint);
						m.nextP = (positions).get(m.checkpoint+1);
						m.update();
						StdDraw.show();
						StdDraw.pause(50);
						if(m.checkpoint == 23 ) // si le monstre arrive au chateau du joueur
						{ // le monstre est arrivé à destination
							life -= 1;
							m.reached = true;
							i.remove();
						}                        
					}
					if(timeToSpawn >= 18)
					{
						timeToSpawn = 0;
						if (difficulte == 1) {
							spawnEnemyB();//on ajoute un autre monstre terrestre
						}
						if (difficulte == 2) {
							spawnEnemyV(); //on ajoute un autre monstre aerien
						}
						if (difficulte == 3) {
							spawnEnemyS();//on ajoute un autre monstre special
						}
					
					}
				
			}
		}
        else //on a fini avec les vagues
            {
                if(life > 0)
                {
                    end = true; //VERFIER LES POINT DE VIE DANS RUN !!!
                }
            }

	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 *  Fonction qui permet à une tour d'attaquer un monstre 
	 */
	public void target() 
	{
		Iterator<Monster> monstre = monsters.iterator();
		Monster m;
		while (monstre.hasNext()) {
			m = monstre.next();
			//tours.forEach(tower -> tower.checkTarget(m,squareWidth,squareHeight,nbSquareY));
			int i = (int)(nbSquareY- Math.floor(m.p.y/squareHeight)-1); //ligne
			int j = (int) (Math.floor(m.p.x/squareWidth)); //colonne
			for(Tours t : tours) 
			{
				if(m instanceof BaseMonster || m instanceof SpecialMonster) 
				{ //toutes les tours peuvent tirer sur ces types de monstres
					if(peutAtteindre(m,t,i,j,t.caseX,t.caseY,t.getPortee())) 
					{	
						if(t instanceof TourArcher) {
							if(t.canFire()) {
								((TourArcher) t).nbProjectilesTire ++;
								Projectile p = new Fleche(m.p);
								final Position pos = new Position(m.p);
								p.nextP = pos;
								//projectiles.add(new Projectile(m.p));
								p.update();
								if(checkCollision(p,m)) 
								{
									or += m.recompense;
									monstre.remove();
									p = null;
								}
							}
						}
						else 
						{
							
							if(t.canFire()) 
							{
								((TourBombe) t).nbProjectilesTire ++;
								Projectile p = new Bombe(m.p);
								final Position pos = new Position(m.p);
								p.nextP = pos;
								//projectiles.add(new Projectile(m.p));
								p.update();
								if(checkCollision(p,m)) 
								{
									or += m.recompense;
									monstre.remove();
									p = null;
								}
							}
						}
					}
				}
				else 
				{ // monstre aerien il n'y a que la tour d'archers  qui peut tirer sur ce type de monstres
					if(t instanceof TourArcher) 
					{
						if(peutAtteindre(m,t,i,j,t.caseX,t.caseY,t.getPortee())) 
						{	
							
							if(t.canFire()) {
								((TourArcher) t).nbProjectilesTire ++;
								Projectile p = new Fleche(m.p); //on créé une fleche
								final Position pos = new Position(m.p);
								p.nextP = pos;
								//projectiles.add(new Projectile(m.p));
								p.update();
								if(checkCollision(p,m)) 
								{
									or += m.recompense;
									monstre.remove();
									m = null; 
									p = null;
								}
							}
						}
						
					}
				}
			}
		}
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Fonction qui vérifie si une tour peut attaquer un monstre 
	 * @param m
	 * @param t
	 * @param i
	 * @param j
	 * @param caseX
	 * @param caseY
	 * @param Portee
	 * @return
	 */
	// sqrt ((x_2-x_1)²+(y_2-y_1)²)
	public boolean peutAtteindre(Monster m,Tours t ,int i,int j, int caseX, int caseY,double Portee) 
	{
		if(i == caseX || j == caseY) //sur la meme ligne ou colonne  
		{
			if(    Math.sqrt(   ( Math.pow(  ( m.p.x-t.getP().x )  , 2) +  Math.pow(  ( m.p.y-t.getP().y )  , 2)   )   ) <= Portee  ) return true;
		}
		return false;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Fonction qui vérifie si un projectile a atteint un monstre
	 * @param p
	 * @param m
	 * @return
	 */
	//(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
	public boolean checkCollision(Projectile p, Monster m) {
		if((Math.pow((m.p.x-p.p.x),2) + Math.pow((m.p.y-p.p.y),2)) <= Math.pow((m.r+p.w/2),2)) return true;
		return false;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Met à  jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	 * @return les points de vie restants du joueur
	 */
	public int update() {
		drawBackground();
		drawPath();
		drawTower();
	//	tours.forEach(tower -> tower.checkTarget(monsters.forEach(),squareWidth,squareHeight,nbSquareY));
		drawInfos();
		drawMouse();
		if(startTime == true) updateMonsters();
		target();
		//wave();
		return life;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
	 * @param key la touche utilisée par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		switch (key) {
		case 'a':
			StdDraw.text(0.5, 0.5, "Arrow Tower selected (50g).");
			StdDraw.show();
			StdDraw.pause(250);
			break;
		case 'b':
			StdDraw.text(0.5, 0.5, "Bomb Tower selected (50g).");
			StdDraw.show();
			StdDraw.pause(250);
			break;
		case 'e':
			StdDraw.text(0.5, 0.5, "Evolution selected (40g).");
			StdDraw.show();
			StdDraw.pause(250);
			break;
		case 's':
			//TODO COMMENCER LA VAGUE ICI ???? plus 20 secondes.. WHILE ON A PAS CLIQU2 ON NE FAIT PAS UPDATEMONSTER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			StdDraw.text(0.5, 0.5, "Starting game!");
			startTime = true;
			StdDraw.show();
			StdDraw.pause(250);
			break;
		case 'q':
			StdDraw.text(0.5, 0.5, "Exiting.");
			StdDraw.show();
			StdDraw.pause(450);
			exitScreen();
		}
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
	 * 		- Ajouter une tour Ã  la position indiquée par la souris.
	 * 		- Améliorer une tour existante.
	 * Puis l'ajouter Ã  la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.RED);
		switch (key) {
		case 'e':
			//On cherche la case correspondante dans int [] game
			int i = (int)(nbSquareY- Math.floor(normalizedY/squareHeight)-1); //ligne
			int j = (int) (Math.floor(normalizedX/squareWidth)); //colonne
			if(game[i][j] == 2) 
			{ 
				if(or >= 10) 
				{ 
					//TODO ::::
					for(Tours t : tours) 
						if(t.caseX == i && t.caseY == j) 
						{
							t.update();
							game[i][j] = 4;
						}
				}
				else 
				{
					StdDraw.text(normalizedX, normalizedY, "Impossible de faire évolué la tour");
					StdDraw.show();
					StdDraw.pause(250);
				}
			}
			else if(game[i][j] == 3) 
			{
				if(or >= 10) 
				{ 
					for(Tours t : tours) 
						if(t.caseX == i && t.caseY == j) 
						{
								t.update();
								game[i][j] = 6;
						}
				}
				else 
				{
					StdDraw.text(normalizedX, normalizedY, "Impossible de faire évolué la tour");
					StdDraw.show();
					StdDraw.pause(250);
				}
			}
			else 
			{ 
			StdDraw.text(normalizedX, normalizedY, "Impossible de faire évolué la tour");
			StdDraw.show();
			StdDraw.pause(250);
			}break;
		}
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les différentes possibilités 
	 * offertes au joueur pour intéragir avec le clavier
	 */
	public void printCommands() {
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BOOK_BLUE);
		boolean selected = false;
		int option = 0;
		while (!selected) {
			// afficher le menu principal
			StdDraw.text(0.5, 0.75, "1. Start");
			StdDraw.text(0.5, 0.5, "2. Game Logic & Controls");
			StdDraw.text(0.5, 0.25, "3. Exit");
			StdDraw.show();
			// récupère la touche appuyé
			if (StdDraw.isKeyPressed(KeyEvent.VK_1)) { //1
				option = 1;
				selected = true;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
				option = 2;
				selected = true;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
				option = 3;
				selected = true;
			}
		}
		// Aller à l'écran correspondant à l'option sélectionnée
		if (option == 1) {
			startMenu();
		} else if (option == 2) {
			controlScreen();
		} else if (option == 3) {
			exitScreen();
		}
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void startMenu() {
		StdDraw.clear(StdDraw.BLACK);
		boolean selected = false;
		int option = 0;
		// Sélectionnez la difficulté 
		while (!selected) {
			StdDraw.text(0.5, 0.75, "1. Easy");
			StdDraw.text(0.5, 0.5, "2. Medium");
			StdDraw.text(0.5, 0.25, "3. Hard");
			StdDraw.show();
			// Escape key to exit screen.
			if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
				selected = true;
				printCommands();
				break;
			}
			if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
				selected = true;
				option = 1;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
				selected = true;
				option = 2;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
				selected = true;
				option = 3;
			}

		}
		if (option == 1) difficulte = 1;
		else if (option == 2) difficulte = 2;
		else difficulte = 3;
		gameSetup();
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void gameSetup() 
	{
		if(difficulte == 1) 
		{
			//on initialise le nombre de montres a 5
			nbMonstresT = 5;
			nbWavesT = 1;
			life =4;
			//wave();
			//wave(1,5);
			BaseMonster m1 = new BaseMonster(new Position(0,1));//spawn
			monsters.add(m1); nbMonstresC ++;
		}
		else if(difficulte == 2) 
		{
			//on initialise le nombre de montres a 13
			nbMonstresT = 10;
			nbWavesT = 2;
			life = 9;
			//wave();
			FlyingMonster m1 = new FlyingMonster(new Position(0,1));//spawn
			monsters.add(m1); nbMonstresC ++;
		}
		else 
		{
			//a 20
			nbMonstresT = 15;
			nbWavesT = 3;
			life = 14;
			SpecialMonster m1 = new SpecialMonster(new Position(0,1));//spawn
			monsters.add(m1); nbMonstresC ++;
		}
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// Display controls
	public void controlScreen() {
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.show();
		boolean selected = false;
		while (!selected) {
			StdDraw.picture(0.5, 0.5, "images/Control.png", 0.95, 0.95);
			StdDraw.show();
			// Escape key to exit screen.
			if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
				selected = true;
			}
		}
		printCommands();
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Display exit screen.
	public void exitScreen() { 
		boolean selected = false;
		end = true ;
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.show();
		while (!selected) {
			StdDraw.text(0.5, 0.5, "Thank you for playing!");
			StdDraw.show();

			// Escape key to exit screen.
			if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
				selected = true;
			}
		}
		printCommands();
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/*
	 * Les differentes méthodes qui permettent d'ajouter des monstres à l'arrayList monstres 
	 */
	public void spawnEnemyB() {
		Monster m1 = new BaseMonster(spawn);
		monsters.add(m1);
		nbMonstresC++;
		wait = 22;
	}
	public void spawnEnemyV() {
		Monster m1 = new FlyingMonster(spawn);
		monsters.add(m1);
		nbMonstresC++;
		wait = 18;
	}
	public void spawnEnemyS() {
		Monster m1 = new SpecialMonster(spawn);
		monsters.add(m1);
		nbMonstresC++;
		wait = 12;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Récupère la touche entrée au clavier ainsi que la position de la souris et met à  jour le plateau en fonction de ces interractions
	 */
	public void run() {
		printCommands();
		while(!end) {
			if(update() <= 0) end = true;
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
		if(life <= 0) 
		{
			StdDraw.clear(StdDraw.BLACK);
			StdDraw.text(0.5, 0.5, "GAME OVER !");
			StdDraw.show();
		}
		else 
		{
			StdDraw.clear(StdDraw.BLACK);
			StdDraw.text(0.5, 0.5, "GAME WON !");
			StdDraw.show();
		}
	}
}
