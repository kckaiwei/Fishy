package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

import javax.swing.text.Position;

/**
 * Created by Kevin on 8/17/2015.
 */
public class Fish {

    private Ellipse playerBoundingEllipse;
    private Rectangle playerBoundingRectangle;
    private Polygon playerBoundingPolygon;
    private Polygon playerBoundingPolygonLeft;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 accleration;
    private int moveSpeed = 40;

    private boolean facingLeft;

    private boolean isAlive;

    public int size;
    private double height;
    private double width;

    private int startingSize;
    private float startingHeight;
    private float startingWidth;
    private double sizeModifier = 0.07;

    private float[] vertices;
    private float[] verticesLeft;


    float screenWidth;
    float screenHeight;

    public Fish(float x, float y, int width, int height, int size) {
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        isAlive = true;
        vertices = new float[16];
        verticesLeft = new float[16];

        //210, 80
        startingSize = size;
        this.size = size;
        startingHeight = height;
        startingWidth = width;
        this.width =startingWidth+(sizeModifier * size);
        this.height =startingHeight+ ((sizeModifier/2) * size);




        facingLeft = false;
        position = new Vector2((x - (width / 2)), (y + (height / 2)));
        velocity = new Vector2(0, 0);
        accleration = new Vector2(200, 200);
        //playerBoundingEllipse = new Ellipse();
        playerBoundingRectangle = new Rectangle();
        playerBoundingPolygon = new Polygon();
        playerBoundingPolygonLeft = new Polygon();
    }

    public void respawn() {
        isAlive = true;
        position.x = (float) ((136 / 2) - (width / 2));
        position.y = (screenHeight / (screenWidth / 136)) / 2;
        size = startingSize;
        width = startingWidth + (sizeModifier * size);
        height = startingHeight + ((sizeModifier/2) * size);
    }

    public void update(float delta) {

        //velocity.add (accleration.cpy().scl(delta));
        //if (velocity.y > 15){
        //    velocity.y = 15;
        //}
        //Gdx.app.log(String.valueOf(position.x), String.valueOf(position.y));
        //Gdx.app.log(String.valueOf(Gdx.input.getX()),String.valueOf(Gdx.input.getY()));
        position.add(velocity.cpy().scl(delta));

        //playerBoundingEllipse.set(position.x, position.y, (float) width, (float) height);
        playerBoundingRectangle.set(position.x, position.y, (float) width, (float) height);

        //hitbox
        setLeftSideVertices();
        setRightSideVertices();

        playerBoundingPolygon.setVertices(vertices);
        playerBoundingPolygonLeft.setVertices(verticesLeft);


    }

    public void onRelease() {
        velocity.y = 0;
        velocity.x = 0;

    }

    public void onClick(int clickX, int clickY)

    {
        Vector2 delta = new Vector2(clickX-this.getX(),clickY-this.getY());
        delta.nor();

        velocity.x = delta.x * moveSpeed;
        velocity.y = delta.y * moveSpeed;

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

    public void grow() {
        size+=1;
        this.width =startingWidth+(sizeModifier * size);
        this.height =startingHeight+ ((sizeModifier/2) * size);
    }

    public boolean isFacingLeft() {
        return velocity.x < 0;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return (float) width;
    }

    public float getHeight() {
        return (float) height;
    }

    public int getSize() {
        return size;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Rectangle getPlayerBoundingRectangle() {
        return playerBoundingRectangle;
    }

    public Polygon getPlayerBoundingPolygon() {
        return playerBoundingPolygon;
    }

    public Polygon getPlayerBoundingPolygonLeft() {
        return playerBoundingPolygonLeft;
    }


    public void kill() {
        isAlive = false;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getVerticesLeft() {
        return verticesLeft;
    }

}

