package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.monde.CameraGame;
import com.ultraime.game.gdxtraime.monde.Monde;

/**
 * @author ultraime Ecran pour des test
 */
public class EcranTest extends Ecran {

	public Monde monde;
	private CameraGame cameraGame;

	private EntiteVivante entiteVivante;
	private Body bodyEntiteVivante;
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
		entiteVivante = new EntiteVivante(0, 0, 5);
		bodyEntiteVivante = this.monde.addEntiteVivante(entiteVivante,0.4f);
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
		switch (keycode) {
		case Input.Keys.SPACE:
			shot();
			break;
		case Input.Keys.D:
			bodyEntiteVivante.setLinearVelocity(5, 0);
			break;
		case Input.Keys.Q:
			bodyEntiteVivante.setLinearVelocity( -5, 0);
			break;
		default:
			break;
		}
		return false;
	}
	
	public void shot() {
		
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

		Vector3 mousePos = new Vector3(screenX, screenY, 0);
		cameraGame.camera.unproject(mousePos);
		float angle = new Vector2(mousePos.x, mousePos.y).sub(bodyEntiteVivante.getPosition()).angleRad();
		bodyEntiteVivante.setTransform(bodyEntiteVivante.getPosition(), angle);
		  
		  
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.cameraGame.zoom(amount);
		return false;
	}

}
