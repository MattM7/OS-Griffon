package gdx.griffon.utils;
/*
SOURCES
- Conner Anderson’s lights lessons: https://www.youtube.com/watch?v=p024Ix0I8W0&list=PLD_bW3UTVsEmdPDSc_XAjID5h9VB2Ocn8
*/
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static gdx.griffon.utils.Constants.fPPM;

public class Lights {

    private RayHandler rayHandler; //for lighting
    private PointLight pointLight;
    private ConeLight coneLight;
    private int nDegrees = 0;

    //------------------------------------ CONSTRUCTOR ----------------------------------------
    public Lights(World _world, Body _bodyPlayer) {
        rayHandler = new RayHandler(_world);

        rayHandler.setAmbientLight(0.1f); // this makes it so the default light isnt just black
        // rayHandler.setBlur(true); //adds a blur effect over all the lights, kind of interesting
        //rayHandler.setBlurNum(50); //sets amount of blur
        pointLight = new PointLight(rayHandler, 200, Color.RED, 128 / fPPM, 17, 11); // PointLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y) between 20 and 200 rays is good

        pointLight.setSoftnessLength(1.5f); //so it bleeds a bit into the character
        //pointLight.attachToBody(bodyPlayer); // can add an offset in x and y
        //pointLight.setXray(true); // light goes through everything
        coneLight = new ConeLight(rayHandler, 50, Color.GOLDENROD, 5, 7, 7, 0, 45); //last two numbers are angle and cone size

        coneLight.attachToBody(_bodyPlayer, 0, 0, nDegrees); //body, offset x, offset y, degrees

    }

    //------------------------------------ RENDER LIGHTS ----------------------------------------
    public void renderLights() {
        rayHandler.render();
    }

    //------------------------------------ DISPOSE ----------------------------------------
    public void dispose() {
        rayHandler.dispose();
        coneLight.dispose();
        pointLight.dispose();
    }

    //------------------------------------ UPDATE ----------------------------------------
    public void update(Camera cam) {
        rayHandler.update();
        rayHandler.setCombinedMatrix(cam.combined.cpy().scl(fPPM), 0, 0, cam.viewportWidth, cam.viewportHeight);
    }

    //------------------------------------ INPUT UPDATE ----------------------------------------
    public void inputUpdate(Body _bodyPlayer, int _nDeg) {
        nDegrees = _nDeg;
        coneLight.attachToBody(_bodyPlayer, 0, 0, nDegrees); //body, offset x, offset y, degrees
    }

}
