package graphicalElements;

import javax.swing.*;

import environment.Environment;
import gameCommons.IFrog;
import util.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private ArrayList<Element> elementsToDisplay;
	private int pixelByCase = 16;
	private int width;
	private int height;
	private IFrog frog;
	private JFrame framefin; 			//modif ici
	private JFrame frame;

	public FroggerGraphic(int width, int height) {
		this.width = width;
		this.height = height;
		elementsToDisplay = new ArrayList<Element>();

		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		JFrame frame = new JFrame("Frogger");
		this.frame = frame;
		this.framefin = frame; 						//modif ici
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Element e : elementsToDisplay) {
			g.setColor(e.color);
			g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				frog.move(Direction.up);
				break;
			case KeyEvent.VK_DOWN:
				frog.move(Direction.down);
				break;
			case KeyEvent.VK_LEFT:
				frog.move(Direction.left);
				frog.changeMode();
				break;
			case KeyEvent.VK_RIGHT:
				frog.move(Direction.right);
				frog.changeMode();
				break;
			case KeyEvent.VK_ENTER:
				frog.finDuJeu();
		}
	}

	public void clear() {
		this.elementsToDisplay.clear();
	}

	public void add(Element e) {
		this.elementsToDisplay.add(e);
	}

	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void endGameScreen(String s, String mode) {
		frame.remove(this);
		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());

		JLabel modeJeu = new JLabel(mode);
		modeJeu.setFont(new Font("Verdana", 1, 14));
		modeJeu.setVerticalAlignment(SwingConstants.BOTTOM);
		modeJeu.setHorizontalAlignment(SwingConstants.CENTER);
		modeJeu.setSize(this.getSize());

		framefin.getContentPane().add(label); //modif ici
		framefin.getContentPane().add(modeJeu);
		framefin.repaint();						//modif ici
	}
	public void restartScreen(){
		this.elementsToDisplay.clear();
		this.removeScreenFin();
		this.addScreen();
	}

	public void removeScreenFin(){
		framefin.getContentPane().removeAll();
		framefin.remove(this);
	}

	public void addScreen(){
		frame.add(this);
	}

	public void afficheBonMode(String s){
		JLabel modeJeu = new JLabel(s);
		modeJeu.setFont(new Font("Verdana", 1, 14));
		modeJeu.setVerticalAlignment(SwingConstants.BOTTOM);
		modeJeu.setHorizontalAlignment(SwingConstants.CENTER);
		modeJeu.setSize(this.getSize());

		framefin.getContentPane().remove(1);
		framefin.getContentPane().add(modeJeu);
		framefin.repaint();
	}
}
