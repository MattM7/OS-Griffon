package gdx.frosch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import gdx.frosch.screens.ScrPlay;

import static gdx.frosch.utils.Constants.fPPM;
import java.util.ArrayList;

public class SpriteExtended extends Sprite {

    public int nNumJumps = 2;
    World world;
    private float fX, fY; // just for original spawn points, or last alive point when we reset for deaths
    private String sFile;
    private Texture txImg;
    private Sprite sprImg;
    private Animation araniPlayer[];
    private Sprite sprPlayer, spr;
    private float fSx, fSy, fW, fH;
    private Texture txSheet;
    private TextureRegion trTemp;
    private int nFrame;
    public boolean isMoving;
    public Body body;
    public int nDir, nPos;
    float fLeft, fRight;
    boolean isLeft;
    ArrayList<Bullet> alBullets;

    //------------------------------------ CONSTRUCTOR ----------------------------------------
    public SpriteExtended(String _sFile, float _fX, float _fY, World world) {
        createBoxBody(world, _fX, _fY);
        nPos = 0;
        nFrame = 0;
        sFile = _sFile;
        fX = body.getPosition().x * fPPM;
        fY = body.getPosition().y * fPPM;
        txImg = new Texture(sFile);
        //txSheet=txImg;
        txSheet = new Texture(sFile);
        sprImg = new Sprite(txImg);
        araniPlayer = new Animation[8];
        fW = txSheet.getWidth() / 8;
        fH = txSheet.getHeight() / 8;
        for (int i = 0; i < 8; i++) {
            Sprite[] arSprPlayer = new Sprite[8];
            for (int j = 0; j < 8; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprPlayer = new Sprite(txSheet, Math.round(fSx), Math.round(fSy), Math.round(fW), Math.round(fH));
                arSprPlayer[j] = new Sprite(sprPlayer);
            }
            araniPlayer[i] = new Animation(10f, arSprPlayer);
        }
    }
    
    public SpriteExtended(String _sFile,float _fX, float _fY, float _fLeft, float _fRight, World _world, ArrayList<Bullet> _alBullets ) {
        createBoxBody(_world, _fX, _fY);
        alBullets = _alBullets;
        world = _world;
        isMoving = true;
        isLeft = false;
        nPos = 0;
        nFrame = 0;
        sFile = _sFile;
        txImg = new Texture(sFile);
        //txSheet=txImg;
        txSheet = new Texture(sFile);
        sprImg = new Sprite(txImg);
        araniPlayer = new Animation[8];
        fW = txSheet.getWidth() / 8;
        fH = txSheet.getHeight() / 8;
        fLeft = _fLeft / fPPM;
        fRight = _fRight / fPPM;
        for (int i = 0; i < 8; i++) {
            Sprite[] arSprPlayer = new Sprite[8];
            for (int j = 0; j < 8; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprPlayer = new Sprite(txSheet, Math.round(fSx), Math.round(fSy), Math.round(fW), Math.round(fH));
                arSprPlayer[j] = new Sprite(sprPlayer);
            }
            araniPlayer[i] = new Animation(10f, arSprPlayer);
        }
    }

    //------------------------------------ DEATH ----------------------------------------
    public void death(World world) {
        reset(world);
    }
    
    public void aiShooting(float fPlayersY, float fPlayersX) {
            System.out.println("playerY" + fPlayersY);
            System.out.println("playerX" + fPlayersX);
        if (fPlayersY < fY + 3 && fPlayersY > fY - 3 && fPlayersX > fX - 10 && fPlayersX < fX + 10) {
            alBullets.add(new Bullet("bullet.png", fX, fY + this.getSprite().getHeight() / 2, world, 1));
            System.out.println("PEW PEW");
            
        }
    }
    
    public void aiMovement(float fPlayersY,float fPlayersX) {
        animate();
        fX = body.getPosition().x;
        fY = body.getPosition().y;
        aiShooting(fPlayersY, fPlayersX);
        if (fX <= fLeft) {
            nPos = 0;
            isLeft = false;
        } else if (fX >= fRight) {
            nPos = 7;
            isLeft = true;
        }
        
        if (isLeft) {       
            body.setLinearVelocity(-3, 0);
        } else {
            body.setLinearVelocity(3, 0);
        }
        System.out.println("X" + fX);
        System.out.println("fLeft" + fLeft);
        System.out.println("fRight" + fRight);
    }

    //------------------------------------ RESET ----------------------------------------
    private void reset(World world) {
        world.destroyBody(body);
        createBoxBody(world, fX, fY);
    }

    //------------------------------------ JUMP ----------------------------------------
    public void jump() {
        nNumJumps--;
    }

    //------------------------------------ RESET JUMP ----------------------------------------
    public void resetJump() {
        nNumJumps = 2;
    }

    //------------------------------------ ANIMATE ----------------------------------------
    private void animate() {
        if (isMoving) {
            nFrame++;
        } else {
            nFrame = 0;
        }
        trTemp = araniPlayer[nPos].getKeyFrame(nFrame, true);
    }

    //------------------------------------ GET SPRITE ----------------------------------------
    public Sprite getSprite() {
        animate();
        spr = new Sprite(trTemp);
        return spr;
    }

    //------------------------------------ CREATE BOX BODY ----------------------------------------
    private void createBoxBody(World world, float x, float y) {
        BodyDef bDef = new BodyDef();
        bDef.fixedRotation = true;
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x / fPPM, y / fPPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / 2 / fPPM, 32 / 2 / fPPM);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 1.0f;

        body = world.createBody(bDef);
        body.createFixture(fixDef).setUserData(this);
        shape.dispose();
    }
}
