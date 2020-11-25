package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;

	public Car (Game game, Case leftPosition, boolean leftToRight){
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = 1;
	}

	/**
	 * Fait avancer une voiture dans la bonne direction en changeant son abscisse

	 */
	public void avanceCar(){
		if(this.leftToRight){
			this.leftPosition.absc++;
		}else {
			this.leftPosition.absc--;
		}
	}

	/**
	 * Permet d'obtenir l'abscisse d'une voiture
	 * @return un abscisse
	 */
	public int getAbsc(){ //ca pourrait servir
		return this.leftPosition.absc;
	}

	public Case getLeftPosition(){
		return this.leftPosition;
	}

	public int getLength(){ return this.length;}

	public void changerOrdCar(int ord){
		this.getLeftPosition().ord = ord;
	}

	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	public void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, color));
		}
	}

}
