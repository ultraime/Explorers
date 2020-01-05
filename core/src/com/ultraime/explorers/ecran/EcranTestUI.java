package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;

/**
 * @author ultraime Ecran de base pour d�marrer une partie
 */
public class EcranTestUI extends Ecran {

	Stage stage;
	TextButton button;
	TextButtonStyle textButtonStyle;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		this.ecranManager = (EcranManager) ecranManager;
		this.batch = new SpriteBatch();
		
		stage = new Stage();
		
		font = new BitmapFont();
		skin = new Skin(Gdx.files.internal("ui-editor/test/test.json"));
		stage = new Stage(new ScreenViewport());
		
		TextButton button = new TextButton("click me ",skin);
		button.setWidth(200);
		button.setHeight(50);
		
		final Dialog dialog = new Dialog("Click on me :O", skin);
		
		button.addListener( new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        dialog.show(stage);
		    }
		} );
		
		
		stage.addActor(button);
		
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		this.batch.begin();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
//		this.batch.end();
	}

	@Override
	public void dispose() {
		this.batch.dispose();
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
