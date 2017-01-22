package gdx.griffon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gdx.griffon.GamGriffon;
import gdx.griffon.TbMenu;
import gdx.griffon.TbsMenu;

public class ScrGameover implements Screen, InputProcessor {
    private GamGriffon game;
    private TbsMenu tbsMenu;
    private TbMenu tbPlay, tbMenu;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont screenName;

    //------------------------------------ CONSTRUCTOR ----------------------------------------
    public ScrGameover(GamGriffon _game) {  //Referencing the main class.
        game = _game;
    }

    //------------------------------------ SHOW ----------------------------------------
    public void show() {
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbPlay = new TbMenu("PLAY", tbsMenu);
        tbMenu = new TbMenu("MENU", tbsMenu);
        tbPlay.setY(0);
        tbPlay.setX(440);
        tbMenu.setY(0);
        tbMenu.setX(0);
        stage.addActor(tbMenu);
        stage.addActor(tbPlay);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnPlayListener();
    }

    //------------------------------------ RENDER ----------------------------------------
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //black background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        screenName.draw(batch, "Gameover", 230, 275);
        batch.end();
        stage.act();
        stage.draw();
    }

    //------------------------------------ BTN MENU LISTENER ----------------------------------------
    private void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

                game.updateState(0);
            }
        });
    }

    //------------------------------------ BTN PLAY LISTENER ----------------------------------------
    private void btnPlayListener() {
        tbPlay.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

                game.updateState(2);
            }
        });
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}