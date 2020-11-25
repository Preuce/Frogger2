package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

public class Environment implements IEnvironment{

    //attributs
    private Game game;
    private ArrayList<Lane> lanes;
    private int tick;
    private int score;

    //constructeur
    public Environment(Game game){
        this.game = game;
        this.lanes = new ArrayList<>(game.height);
        this.tick = 0;
        this.score = 0;
    }

    //méthodes
    /**
     * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser
     * sans mourir
     *
     * @param c
     *            la case � tester
     * @return vrai s'il n'y a pas danger
     */
    public boolean isSafe(Case c){
        Lane voie = this.lanes.get(c.ord);
        int taille = voie.getCars().size();
        for(int i = 0; i < taille; i++) {
            if(voie.getCars().get(i).getAbsc() == c.absc) { //sur la voie correspondante, toute les voitures comparées à la case
                return false;
            }
        }
        return true;
    }

    public int getSize(){
        return this.lanes.size();
    }

    public int getScore(){ return score;}

    public int getTick(){return tick;}

    /**
     * Teste si la case est une case d'arrivee
     *
     * @param c
     * @return vrai si la case est une case de victoire
     */
    public boolean isWinningPosition(Case c){
        if(c.ord == this.game.height-1){
            return true;
        }
        return false;
    }

    /**
     * Cree un nombre de voie égal à la hauteur du jeu
     */
    public void initialiseVoies(){
        for(int i = 0; i < game.height; i++){
            this.lanes.add(Lane.creeVoie(game, i, this));
        }
    }

    /**
     * Rempli les voies actives (qui sont dangereuses) de voitures, n'est appelée qu'1 fois
     */
    public void chargeVoies(){
        for(int i = 2; i < lanes.size(); i++) {
            this.lanes.get(i).chargeLaneCars();
        }
    }

    public void enleveLane(int i){
        this.lanes.remove(i);
    }

    public void actualiseLanes(){
        for(int i = 0; i < this.getSize(); i++){
            this.lanes.get(i).actualiseOrdCar();
        }
    }

    /**
     * Deplace les voies vers le bas en décrémentant l'ordonnée
     */
    public void bougeLaneDown(){
        this.enleveLane(0);
        for(int i = 0; i < this.getSize(); i++){
            this.lanes.get(i).changerOrdLane(i);
        }
        actualiseLanes();
        Lane l = Lane.creeVoie(game, this.getSize(), this);
        lanes.add(l);
        lanes.get(lanes.size()-1).chargeLaneCars(); //j'aime pas mais j'ai besoin que ma lane fasse partie d'une arraylist pour pouvoir faire ça, ça découle du fait que mayAddCar appelle isSafe
        score++;
    }

    /**
     * Deplace les voies vers le haut en incrémentant l'ordonnée
     */
    public void bougeLaneUp(){
        this.enleveLane(this.getSize()-1);

        Lane l = Lane.creeVoie(game, 0, this);
        lanes.add(0, l);

        lanes.get(0).chargeLaneCars();

        for(int i = 1; i < this.getSize(); i++){
            this.lanes.get(i).changerOrdLane(i);
        }
        actualiseLanes();
        score--;
    }

    public void restartEnvironment(){
        this.lanes.clear();
        this.score = 0;
        this.tick = 0;
    }

    /**
     * Effectue une étape d'actualisation de l'environnement
     */
    public void update(){
        if(this.lanes.isEmpty()){
            this.initialiseVoies();
            this.chargeVoies();
        }
        for(int i = 0; i < this.getSize(); i++) {
            this.lanes.get(i).update(this.tick);
        }
        this.tick++;
    }
}
