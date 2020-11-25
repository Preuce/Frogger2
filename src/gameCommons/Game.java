package gameCommons;

import java.awt.Color;
import java.util.Random;

import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.Statut;

public class Game {

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int best;
	public boolean gameInf;
	public Statut etat;

	// Lien aux objets utilis�s
	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant d�placement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.best = 0;
		this.gameInf = false;
		this.etat = Statut.playing;
	}

	/**
	 * Lie l'objet frog a la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un ecran de fin approprie si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public void testLose() {
		if(!environment.isSafe(this.frog.getPosition()) && this.etat == Statut.playing) {
			etat = Statut.lost;
			if (gameInf) {
				if (environment.getScore() > best) {
					best = environment.getScore();
				}
				this.graphic.endGameScreen("Votre score : " + environment.getScore() + " Record : " + best, "Infini");
			}else{
				this.graphic.endGameScreen("Vous avez perdu", "Normal");
			}
		}
	}

	public void changeMode(){
		gameInf = !gameInf;
		if(gameInf) {
			this.graphic.afficheBonMode("Infini");
		}else{
			this.graphic.afficheBonMode("Normal");
		}
	}

	/**
	 * Teste si la partie est gagnee et lance un ecran de fin approprie si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagn�e
	 */
	public void testWin() {
		if(environment.isWinningPosition(this.frog.getPosition()) && this.etat == Statut.playing ){
			etat = Statut.won;
			this.graphic.endGameScreen("Vous avez gagne en " + (float) this.environment.getTick()/10 + " secondes", "Normal");
		}
	}

	public IEnvironment getEnvironment(){
		return this.environment;
	}

	public void restartJeu(){
		this.etat = Statut.playing;
		this.environment.restartEnvironment();
		this.frog.restartFrog();
		this.graphic.restartScreen();
		this.graphic.clear();
		this.graphic.addScreen();
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();
		this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
		testLose();
		testWin();
	}
}
