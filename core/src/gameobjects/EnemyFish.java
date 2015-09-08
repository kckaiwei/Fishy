package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import javax.swing.text.Position;

import fishyhelpers.AssetLoader;
import gameworld.GameWorld;

/**
 * Created by Kevin on 8/17/2015.
 */
public class EnemyFish {

    float screenWidth;
    float screenHeight;
    private Vector2 position;
    private Vector2 velocity;
    private int moveSpeed = 5;
    private Ellipse boundingEllipse;
    private Rectangle boundingRectangle;
    private Polygon boundingPolygon;
    private boolean facingLeft;
    private int size;
    private double height;
    private double width;
    private boolean alive;
    private int enemySize;
    private int startingSize;
    private GameWorld gameWorld;
    private double sizeModifier = 0.07;
    private Fish targetFish;
    private int smallSizeMod = 15;

    private float[] vertices;
    private float[] verticesLeft;

    public EnemyFish(float x, float y, int startSize, Fish target, GameWorld world) {
        enemySize = 6;
        this.alive = true;
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        targetFish = target;
        gameWorld = world;
        startingSize = startSize;

        vertices = new float[16];
        verticesLeft = new float[16];

        final Random random = new Random();
        if (random.nextBoolean()) {
            facingLeft = true;
        } else {
            facingLeft = false;
        }
        //210, 80
        this.size = (int) (((targetFish.getSize())-smallSizeMod)+(Math.random()*((targetFish.getSize())+enemySize)));
        this.width = (6 + ((sizeModifier * size)));
        this.height = (3 + (((sizeModifier/2) * size)));

        if (!facingLeft) {
            position = new Vector2((0 - (float) width), (float) Math.random() * (screenHeight / (screenWidth / 136)));
            velocity = new Vector2((float) Math.random() * 40, 0);
        } else {
            position = new Vector2((136 + (float) width), (float) Math.random() * (screenHeight / (screenWidth / 136)));
            velocity = new Vector2(-(float) Math.random() * 40, 0);
        }
        //boundingEllipse = new Ellipse();
        boundingRectangle = new Rectangle();
        boundingPolygon = new Polygon();

    }

    public void update(float delta) {
        if (alive) {
            if (facingLeft){
                setLeftSideVertices();
                boundingPolygon.setVertices(verticesLeft);
            } else {
                setRightSideVertices();
                boundingPolygon.setVertices(vertices);
            }
            position.add(velocity.cpy().scl(delta));
            boundingRectangle.set(position.x, position.y, (float) width, (float) height);
            overTheEdgeCheck();
            checkEating();
        } else {
            respawn();
        }

    }

    private void setRightSideVertices(){
        int vertWidth = 207;
        int vertHeight = 85;
        int widthOffset = -26;
        int heightOffset = -87;

        vertices[0] = (float) (position.x + ((width/vertWidth)*(40+widthOffset)));
        vertices[1] = (float) (position.y + ((height/vertHeight)*(122+heightOffset)));

        vertices[2] = (float) (position.x + ((width/vertWidth)*(111+widthOffset)));
        vertices[3] = (float) (position.y + ((height/vertHeight)*(102+heightOffset)));

        vertices[4] = (float) (position.x + ((width/vertWidth)*(188+widthOffset)));
        vertices[5] = (float) (position.y + ((height/vertHeight)*(101+heightOffset)));

        vertices[6] = (float) (position.x + ((width/vertWidth)*(224+widthOffset)));
        vertices[7] = (float) (position.y + ((height/vertHeight)*(120+heightOffset)));

        vertices[8] = (float) (position.x + ((width/vertWidth)*(224+widthOffset)));
        vertices[9] = (float) (position.y + ((height/vertHeight)*(134+heightOffset)));

        vertices[10] = (float) (position.x + ((width/vertWidth)*(188+widthOffset)));
        vertices[11] = (float) (position.y + ((height/vertHeight)*(162+heightOffset)));

        vertices[12] = (float) (position.x + ((width/vertWidth)*(111+widthOffset)));
        vertices[13] = (float) (position.y + ((height/vertHeight)*(158+heightOffset)));

        vertices[14] = (float) (position.x + ((width/vertWidth)*(40+widthOffset)));
        vertices[15] = (float) (position.y + ((height/vertHeight)*(134+heightOffset)));
    }

    private void setLeftSideVertices() {
        int vertWidth = 207;
        int vertHeight = 85;
        int widthOffset = -26;
        int heightOffset = -87;
        int totalLengthToSub = 256;

        verticesLeft[0] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-40)+widthOffset)));
        verticesLeft[1] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-122)+heightOffset)));

        verticesLeft[2] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-111)+widthOffset)));
        verticesLeft[3] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-102)+heightOffset)));

        verticesLeft[4] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-188)+widthOffset)));
        verticesLeft[5] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-101)+heightOffset)));

        verticesLeft[6] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-224)+widthOffset)));
        verticesLeft[7] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-120)+heightOffset)));

        verticesLeft[8] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-224)+widthOffset)));
        verticesLeft[9] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-134)+heightOffset)));

        verticesLeft[10] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-188)+widthOffset)));
        verticesLeft[11] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-162)+heightOffset)));

        verticesLeft[12] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-111)+widthOffset)));
        verticesLeft[13] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-158)+heightOffset)));

        verticesLeft[14] = (float) (position.x + ((width/vertWidth)*((totalLengthToSub-40)+widthOffset)));
        verticesLeft[15] = (float) (position.y + ((height/vertHeight)*((totalLengthToSub-134)+heightOffset)));

    }



    public boolean isFacingLeft() {
        return facingLeft;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return (float) this.width;
    }

    private void dies() {
        alive = false;
    }

    private void overTheEdgeCheck() {
        if (!facingLeft) {

            if (position.x > (136 + width)) {
                respawn();
            }
        } else {
            if (position.x < (0 - width)) {
                respawn();
            }
        }
    }

    public float getHeight() {
        return (float) this.height;
    }

    public float getSize() {
        return (float) size;
    }

    public void respawn() {
        final Random random = new Random();
        if (random.nextBoolean()) {
            facingLeft = true;
        } else {
            facingLeft = false;
        }
        this.size = (int) (((targetFish.getSize())-smallSizeMod)+(Math.random()*((targetFish.getSize())+enemySize)));
        this.width = (6 + ((sizeModifier * size)));
        this.height = (3 +(((sizeModifier/2) * size)));
        position.y = ((float) Math.random() * (screenHeight / (screenWidth / 136)));


        if (!facingLeft) {
            position.x = (0 - (float) width);
            velocity.x = ((float) Math.random() * 40);
        } else {
            position.x = (136 + (float) width);
            velocity.x = -((float) Math.random() * 40);
        }

        this.alive = true;
    }

    //public Ellipse getBoundingEllipse(){
    //    return boundingEllipse;
    //}

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    public Polygon getBoundingPolygon(){
        return boundingPolygon;
    }

    public boolean collisionCheck(Fish target) {
        /*
        if (Intersector.overlaps(this.getBoundingRectangle(), target.getPlayerBoundingRectangle())) {
            return true;
        } else {
            return false;
        }
        */

        if (target.isFacingLeft()) {
            if (Intersector.overlapConvexPolygons(this.getBoundingPolygon(), target.getPlayerBoundingPolygonLeft())) {
                return true;
            } else {
                return false;
            }

        }else{
            if (Intersector.overlapConvexPolygons(this.getBoundingPolygon(), target.getPlayerBoundingPolygon())) {
                return true;
            } else {
                return false;
            }
        }
    }


    public void checkEating() {
        if (collisionCheck(targetFish) && targetFish.size >= this.size
                && targetFish.isAlive()) {
            this.alive = false;
            targetFish.grow();
            enemySize+=2;
            gameWorld.addScore(1);
        }
        if (collisionCheck(targetFish) && targetFish.size < this.size && targetFish.isAlive()) {
            targetFish.kill();
            AssetLoader.dead.play();
            this.velocity.x = 0;
            gameWorld.isGameOver();
            if (gameWorld.getScore() > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(gameWorld.getScore());
                gameWorld.setState(GameWorld.GameState.HIGHSCORE);
            }
        }
    }

}

