package fishyhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Kevin on 8/17/2015.
 */
public class AssetLoader {

    private boolean facingLeft;

    public static Texture texture;
    public static Texture mainScreenTexture;
    public static TextureRegion bg, grass;
    public static TextureRegion playButtonUp, playButtonDown, logoTR, title, creditsButtonUp, creditsButtonDown;

    public static Texture logo, titleTexture;

    public static Animation fishAnimation;
    public static Animation fishAnimationFaceLeft;
    public static TextureRegion playerFish, playerFish2, playerFish3, playerFish4;

    public static TextureRegion playerFishLeft, playerFish2Left, playerFish3Left, playerFish4Left;

    public static Sound dead;

    public static Music ambience;

    private static Preferences prefs;

    public static BitmapFont font, shadow, lucidaSans, splashFont;

    public static void load() {

        //title
        titleTexture = new Texture(Gdx.files.internal("data/title.png"));
        titleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title = new TextureRegion(titleTexture, 112, 21, 268, 106);
        title.flip(false, true);


        //mainscreen

        mainScreenTexture = new Texture(Gdx.files.internal("data/texture.png"));
        mainScreenTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new Texture(Gdx.files.internal("data/logo.png"));
        logo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        logoTR = new TextureRegion(logo, 52, 25, 416, 71);

        playButtonUp = new TextureRegion(mainScreenTexture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(mainScreenTexture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);
        creditsButtonUp = new TextureRegion(mainScreenTexture, 58,83,41,16);
        creditsButtonDown = new TextureRegion(mainScreenTexture, 99,83,41,16);
        creditsButtonUp.flip(false,true);
        creditsButtonDown.flip(false,true);


        //fish textures

        texture = new Texture(Gdx.files.internal("data/fish.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        playerFish = new TextureRegion(texture, 26, 87, 207, 85);
        playerFish.flip(false, true);

        playerFish2 = new TextureRegion(texture, 281, 87, 207, 85);
        playerFish2.flip(false, true);

        playerFish3 = new TextureRegion(texture, 536, 87, 207, 85);
        playerFish3.flip(false, true);

        playerFish4 = new TextureRegion(texture, 797, 87, 207, 85);
        playerFish4.flip(false, true);

        TextureRegion[] fishes = {playerFish, playerFish2, playerFish3, playerFish4};
        fishAnimation = new Animation(0.06f, fishes);
        fishAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Left Facing

        playerFishLeft = new TextureRegion(texture, 26, 87, 207, 85);
        playerFishLeft.flip(true, true);

        playerFish2Left = new TextureRegion(texture, 281, 87, 207, 85);
        playerFish2Left.flip(true, true);

        playerFish3Left = new TextureRegion(texture, 536, 87, 207, 85);
        playerFish3Left.flip(true, true);

        playerFish4Left = new TextureRegion(texture, 797, 87, 207, 85);
        playerFish4Left.flip(true, true);

        TextureRegion[] fishesLeft = {playerFishLeft, playerFish2Left, playerFish3Left, playerFish4Left};
        fishAnimationFaceLeft = new Animation(0.06f, fishesLeft);
        fishAnimationFaceLeft.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        ambience = Gdx.audio.newMusic(Gdx.files.internal("data/590422_Dreamology.mp3"));
        ambience.setLooping(true);



        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.10f, -.10f);
        lucidaSans = new BitmapFont(Gdx.files.internal("data/lucidasans.fnt"));
        lucidaSans.getData().setScale(.10f, -.10f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.10f, -.10f);
        splashFont = new BitmapFont(Gdx.files.internal("data/splash.fnt"));
        splashFont.getData().setScale(.10f,-.10f);


        //create prefs
        prefs = Gdx.app.getPreferences("Fishy");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }


    public static void dispose() {
        texture.dispose();
        dead.dispose();
        ambience.dispose();
        font.dispose();
        shadow.dispose();
        lucidaSans.dispose();
        splashFont.dispose();
    }

}
