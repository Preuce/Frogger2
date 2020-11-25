package environment;

import java.util.ArrayList;
import java.util.Random;

import util.Case;
import gameCommons.Game;

public class Lane{
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars;
	private boolean leftToRight;
	private double density;
	private Environment env;

	public Lane(Game game, int ord, int speed, boolean leftToRight, double density, Environment env){
		this.game = game;
		this.ord = ord;
		this.speed = speed;
		this.cars = new ArrayList<>();
		this.leftToRight = leftToRight;
		this.density = density;
		this.env = env;
	}


	public void update(int tick) {


		// Toutes les voitures se déplacent d'une case au bout d'un nombre "tic
		// d'horloge" égal à leur vitesse

		if (tick % this.speed == 0) {
			for (Car c : this.cars){
				if(c.getAbsc() == this.getLastCase().absc){
					this.retireCar(c);
					break;
				}else {
					c.avanceCar();
				}
			}
		}

		// Notez que cette méthode est appelée à chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elles ne bougent pas
		for (int i = 0; i < this.cars.size(); i++) {
			cars.get(i).addToGraphics();
		}

		// A chaque tic d'horloge, une voiture peut être ajoutée
		this.mayAddCar();
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Permet d'obtenir la liste des voitures de la voie
	 * @return une List de Car de la Lane
	 */
	public ArrayList<Car> getCars(){
		return this.cars;
	}

	/**
	 * Permet d'obtenir l'ordonnée d'une voie
	 * @return l'ord de la Lane
	 */
	public int getOrd(){return this.ord;}

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (this.env.isSafe(getFirstCase()) && this.env.isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				this.cars.add(new Car(game, new Case(getBeforeFirstCase().absc, ord), leftToRight));
			}
		}
	}

	/**
	 * Cree une voie contenant des valeurs d'attributs aléatoires
	 * @param game le jeu en cours
	 * @param ord l'ordonnée de la voie
	 * @param env l'environnement utilisé
	 * @return une Lane vide mais utilisable
	 */
	public static Lane creeVoie(Game game, int ord, Environment env){
		Random random = new Random();
		int speedAl = random.nextInt(4);
		int sensAl = random.nextInt(2);
		int densAl = random.nextInt(3);


		int speed = game.minSpeedInTimerLoops + speedAl;

		boolean ltr;
		if(sensAl == 0){
			ltr = true;
		}else{
			ltr = false;
		}
		return new Lane(game, ord, speed, ltr, game.defaultDensity + (float) densAl/500, env);

	}

	/**
	 * Rempli la Lane de Car (en fonction de sa densité)
	 * @return la Lane en question
	 */
	public Lane chargeLaneCars() {
		for (int i = 0; i < game.width; i++) {
			if (this.env.isSafe(new Case(i, ord))) {
				if (game.randomGen.nextDouble() < density) {
					this.cars.add(new Car(game, new Case(i, ord), leftToRight));
				}
			}
		}
		return this;
	}

	/**
	 * Enleve la voiture de la voie
	 * @param car la voiture à enlevé
	 */
	public void retireCar(Car car){
		this.cars.remove(car);
	}

	public void actualiseOrdCar(){
		for(int i = 0; i < this.cars.size(); i++){
			this.cars.get(i).changerOrdCar(this.ord);
		}
	}

	/**
	 * Change l'ordonnée d'une voie
	 * @param ord la nouvelle ordonnée de la Lane
	 */
	public void changerOrdLane(int ord){
		this.ord = ord;
	}

	/**
	 * Permet d'obtenir la première case de la Lane (relatif au sens)
	 * @return une Case
	 */
	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	/**
	 * Permet d'obtenir la dernière case de la Lane (relatif au sens)
	 * @return une Case
	 */
	private Case getLastCase(){
		if(leftToRight){
			return new Case(game.width, ord);
		}else{
			return new Case(0, ord);
		}
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		}
		return new Case(game.width, ord);
	}
}
