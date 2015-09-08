package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.List;

import TweenAccessors.Value;
import TweenAccessors.ValueAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import fishyhelpers.AssetLoader;
import fishyhelpers.InputHandler;
import gameobjects.EnemyFish;
import gameobjects.Fish;
import sun.java2d.pipe.SpanShapeRenderer;
import ui.MenuButtons;
import ui.SimpleButton;

/**
 * Created by Kevin on 8/17/2015.
 */
public class GameRenderer {
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private GameWorld myWorld;

    private SpriteBatch batcher;

    private Fish fish;
    private EnemyFish enemyFish1;
    private EnemyFish enemyFish2;
    private EnemyFish enemyFish3;
    private EnemyFish[] enemies;

    private Animation fishAnimation;
    private Animation fishAnimationLeft;
    private TextureRegion fishRegion;

    private int midPointX;
    private int midPointY;
    private int gameHeight;
    private int gameWidth;
    private List<SimpleButton> menuButtons, replayButtons;


    private TweenManager manager;
    private Value alpha = new Value();

    public GameRenderer(GameWorld world, int gameHeight, int gameWidth, int midPointX, int midPointY, MenuButtons menu, EnemyFish[] enemies) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.midPointX = midPointX;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);
        myWorld = world;

        this.menuButtons = menu.getMenuButtons();
        this.replayButtons = menu.getReplayButtons();


        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
        setUpTweens();


    }

    private void drawMenuUI() {
        //Fishy title
        batcher.draw(AssetLoader.title, (gameWidth / 2) - ((AssetLoader.title.getRegionWidth() / 6) / 2), ((gameHeight / 2) - ((AssetLoader.title.getRegionHeight() / 6) / 2)) - 12,
                AssetLoader.title.getRegionWidth() / 6, AssetLoader.title.getRegionHeight() / 6);
        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }
    }

    private void drawCredits(){
        String creditTitle = "Development Team";
        AssetLoader.lucidaSans.draw(batcher, creditTitle, (gameWidth/2)- (creditTitle.length()/2), (gameHeight/2)-15);
        String programmerString = "Programmer: Kevin Chang";
        AssetLoader.lucidaSans.draw(batcher, programmerString, (gameWidth/2)-(programmerString.length()/2), (gameHeight/2)- 10);
        String artistString = "Artist: Danielle Braden";
        AssetLoader.lucidaSans.draw(batcher, artistString, (gameWidth/2)-(artistString.length()/2), (gameHeight/2)-5);
        String musicianString = "Musician: Stapless";
        AssetLoader.lucidaSans.draw(batcher, musicianString, (gameWidth/2)-(musicianString.length()/2), (gameHeight/2));
        //AssetLoader.shadow.draw(batcher, "Game Over", (136 / 2)- (2* gameOver.length()), 15);
    }

    private void setUpTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void initGameObjects() {
        fish = myWorld.getFish();
        enemies= myWorld.getEnemies();
    }

    private void initAssets() {
        fishAnimation = AssetLoader.fishAnimation;
        fishAnimationLeft = AssetLoader.fishAnimationFaceLeft;

    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public void render(float delta, float runTime) {

        //Gdx.app.log("GameRenderer", "render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        shapeRenderer.begin(ShapeType.Filled);


        //draw background
        shapeRenderer.setColor(0 / 255.0f, 144 / 255.0f, 255 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, gameHeight);


        //draw sand
        shapeRenderer.setColor(255 / 255.0f, 204 / 255.0f, 0 / 255.0f, 1);
        shapeRenderer.rect(0, gameHeight - 6, 136, 6);

        shapeRenderer.end();

        batcher.begin();
        batcher.enableBlending();


        if (myWorld.isRunning()) {
            drawScore();
            /*
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.polygon(fish.getVertices());
            shapeRenderer.end();
            */
            drawEnemyFish(runTime);
            drawPlayerFish(runTime);
        } else if (myWorld.isReady()) {
            drawScore();
            drawPlayerFish(runTime);
        } else if (myWorld.isMenu()) {
            drawPlayerFishCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawScore();
            drawPlayerFish(runTime);
            drawEnemyFish(runTime);
            drawReplay();
            drawNotHighScore();
        } else if (myWorld.isHighScore()) {
            drawScore();
            drawNewHighScore();
            drawPlayerFish(runTime);
            drawEnemyFish(runTime);
            drawReplay();

        } else if (myWorld.isCredits()){
            drawEnemyFish(runTime);
            drawCredits();
        }
        batcher.end();
        drawTransition(delta);


    }

    private void drawNewHighScore(){
                   String highScoreNew = "High Score!";
                   AssetLoader.splashFont.draw(batcher, highScoreNew, (136 / 2)- (highScoreNew.length()/2)+2, (gameHeight -7)-10);
                   //AssetLoader.font.draw(batcher, highScoreNew, (136 / 2)- (2* highScoreNew.length()), (gameHeight -7)-10);
    }
    private void drawReplay(){
        for (SimpleButton button : replayButtons) {
            button.draw(batcher);
        }
                  String gameOver = "Game Over";
                  AssetLoader.splashFont.draw(batcher, "Game Over", (136 / 2)- (gameOver.length()/2)+2, 15);
                  //AssetLoader.font.draw(batcher, "Game Over", (136 / 2) - (2* gameOver.length()), 15);

        String tryAgain = "Try again?";
        AssetLoader.splashFont.draw(batcher, tryAgain, (136 / 2) - (tryAgain.length()/2)+2, (gameHeight / 2)-12);
        //AssetLoader.font.draw(batcher, tryAgain, (136 / 2)- (2* tryAgain.length()), (gameHeight / 2)-12);
    }

    private void drawNotHighScore(){
        String highScoreDisplay = "High Score:";
        AssetLoader.splashFont.draw(batcher, highScoreDisplay, (136 / 2)- (highScoreDisplay.length()/2)+2, (gameHeight - 7)-10);
        //AssetLoader.font.draw(batcher, highScoreDisplay, (136 / 2)- (2* highScoreDisplay.length()), (gameHeight - 7)-10);

        String highScore = AssetLoader.getHighScore() + "";

        AssetLoader.splashFont.draw(batcher, highScore, (136 / 2)
                - (highScore.length()/2), (gameHeight - 10));

        //AssetLoader.font.draw(batcher, highScore, (136 / 2)
        //        - (3 * highScore.length()), (gameHeight - 10));

    }

    private void drawEnemyFish(float runTime){
        for (int i=0; i<10; i++){
            if (!enemies[i].isFacingLeft()){
                batcher.draw(fishAnimation.getKeyFrame(runTime), enemies[i].getX(), enemies[i].getY(), enemies[i].getWidth(), enemies[i].getHeight());

            }
            if (enemies[i].isFacingLeft()){
                batcher.draw(fishAnimationLeft.getKeyFrame(runTime), enemies[i].getX(), enemies[i].getY(), enemies[i].getWidth(), enemies[i].getHeight());
            }
        }

    }

    private void drawPlayerFish(float runTime) {
        if (fish.isFacingLeft() == false)

        {
            batcher.draw(fishAnimation.getKeyFrame(runTime), fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight());
        } else

        {
            batcher.draw(fishAnimationLeft.getKeyFrame(runTime), fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight());
        }
    }


    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

    private void drawScore() {
        String score = myWorld.getScore() + "";
        AssetLoader.splashFont.draw(batcher, "" + myWorld.getScore(), (136 / 2)-2, 5);
        //AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length()), 5);
    }

    private void drawPlayerFishCentered(float runTime) {
        batcher.draw(fishAnimation.getKeyFrame(runTime), (136 / 2) - (fish.getWidth() / 2), fish.getY(), fish.getWidth(), fish.getHeight());
    }

}

