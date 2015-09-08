package gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import gameobjects.EnemyFish;
import gameobjects.Fish;

/**
 * Created by Kevin on 8/17/2015.
 */
public class GameWorld {
    private Fish fish;
    private EnemyFish[] enemies;
    private int score = 0;
    private GameState currentState;
    private int midPointX, midPointY;


    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE, MENU, CREDITS
    }


    public GameWorld(int midPointX, int midPointY) {
        currentState = GameState.MENU;
        enemies = new EnemyFish[10];
        this.midPointX = midPointX;
        this.midPointY = midPointY;
        fish = new Fish(midPointX, midPointY, 6, 3, 4);
        for (int i = 0; i < 10; i++) {
            final Random random = new Random();
            int b = random.nextInt(fish.getSize()+4);

                enemies[i] = new EnemyFish(midPointX, midPointY, b, fish, this);

        }
    }

    //private Rectangle rect = new Rectangle(0,0,17,12);

    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
        }


    }

    public int getMidPointX() {
        return midPointX;
    }

    public int getMidPointY() {
        return midPointY;
    }

    private void updateReady(float delta) {

    }

    public void updateRunning(float delta) {
        fish.update(delta);
        for (int i = 0; i < 10; i++) {
            enemies[i].update(delta);
        }
        if (!fish.isAlive()) {
            currentState = GameState.GAMEOVER;
        }
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void menu() {
        currentState = GameState.MENU;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        fish.respawn();
        for (int i = 0; i < 10; i++) {
            enemies[i].respawn();
        }
        currentState = GameState.READY;

    }

    public void credits(){
        currentState = GameState.CREDITS;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public void setState(GameState state) {
        currentState = state;
    }


    public Fish getFish() {
        return fish;
    }

    public EnemyFish getEnemyFish1() {
        return enemies[1];
    }

    public EnemyFish getEnemyFish2() {
        return enemies[2];
    }

    public EnemyFish getEnemyFish3() {
        return enemies[3];
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isCredits() {
        return currentState == GameState.CREDITS;
    }

    public EnemyFish[] getEnemies() {
        return enemies;
    }


}
