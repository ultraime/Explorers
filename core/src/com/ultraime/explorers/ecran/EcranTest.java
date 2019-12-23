package com.ultraime.explorers.ecran;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.monde.CameraGame;
import com.ultraime.game.gdxtraime.monde.Monde;

public class EcranTest extends Ecran {

	public Monde monde;
	private CameraGame cameraGame;

	private boolean isDispose = false;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		this.ecranManager = (EcranManager) ecranManager;
		this.batch = new SpriteBatch();
		this.cameraGame = new CameraGame();
		this.cameraGame.camera.position.x = 0;
		this.cameraGame.camera.position.y = 0;

		this.monde = new Monde(0);
	}
	
	

	private void updateCamera() {

	}

	@Override
	public void render() {
		updateCamera();
		if (!isDispose) {
			this.batch.begin();
			this.monde.render();
			this.monde.renderDebug(cameraGame.camera);
			this.batch.end();
		}
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.monde.dispose();
		isDispose = true;
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
		this.cameraGame.zoom(amount);
		return false;
	}

}
