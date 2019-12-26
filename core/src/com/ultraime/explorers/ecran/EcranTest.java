package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ultraime.explorers.service.JoueurService;
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
	private Vector2 positionSouris;

	private boolean isDispose = false;

	public JoueurService joueurService;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		this.ecranManager = (EcranManager) ecranManager;
		this.positionSouris = new Vector2(0, 0);
		this.batch = new SpriteBatch();
		this.cameraGame = new CameraGame();
		this.cameraGame.camera.position.x = 0;
		this.cameraGame.camera.position.y = 0;

		this.monde = new Monde(0);

		EntiteVivante entiteVivante = new EntiteVivante(0, 0, 0.4f, (short) -1);

		joueurService = new JoueurService(this.monde.addEntiteVivante(entiteVivante, this.monde.bodiesEntiteVivant));

	}

	private void updateCamera() {

	}

	@Override
	public void render() {
		updateCamera();
		if (!isDispose) {
			this.batch.begin();
			this.monde.render();
			joueurService.rotation((int) positionSouris.x, (int) positionSouris.y, cameraGame.camera);
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

	public void deplacementJoueur() {
		short directionX = 0;
		short directionY = 0;

		if (isTouchPressed(Input.Keys.D)) {
			directionX = 1;
			System.err.println(1);
		} else if (isTouchPressed(Input.Keys.Q)) {
			directionX = -1;
		}

		if (isTouchPressed(Input.Keys.Z)) {
			directionY = 1;
		} else if (isTouchPressed(Input.Keys.S)) {
			directionY = -1;
		}
		joueurService.deplacer(directionX, directionY);
	}

	@Override
	public boolean keyDown(int keycode) {
		presserTouche(keycode);
		deplacementJoueur();

//		switch (keycode) {
//		case Input.Keys.SPACE:
//			break;
//		case Input.Keys.D:
//			bodyEntiteVivante.setLinearVelocity(5, 0);
//			break;
//		case Input.Keys.Q:
//			bodyEntiteVivante.setLinearVelocity(-5, 0);
//			break;
//		default:
//			break;
//		}
		return false;
	}

	public void shot(float screenX, float screenY) {
//		mousePos = new Vector3(screenX, screenY, 0);
//		cameraGame.camera.unproject(mousePos);
//		float angle = new Vector2(mousePos.x, mousePos.y).sub(bodyEntiteVivante.getPosition()).angleRad();
//		bodyEntiteVivante.setTransform(bodyEntiteVivante.getPosition(), angle);
//
//		float velocity = 10f;
//
//		float velX = MathUtils.cos(angle) * velocity; // X-component.
//		float velY = MathUtils.sin(angle) * velocity;
//
//		float posX = bodyEntiteVivante.getPosition().x; // X-component.
//		float posY = bodyEntiteVivante.getPosition().y;
//
//		EntiteVivante bullet = new EntiteVivante(posX, posY, 0.04f, (short) -1);
//		Body body = this.monde.addEntiteVivante(bullet, this.monde.bodiesBullets);
//		body.setLinearVelocity(velX, velY);

	}

	@Override
	public boolean keyUp(int keycode) {
		relacherTouche(keycode);
		deplacementJoueur();
//		switch (keycode) {
//		case Input.Keys.D:
//			bodyEntiteVivante.setLinearVelocity(0, 0);
//			break;
//		case Input.Keys.Q:
//			bodyEntiteVivante.setLinearVelocity(0, 0);
//			break;
//		default:
//			break;
//		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			shot(screenX, screenY);
		}

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
		positionSouris.x = screenX;
		positionSouris.y = screenY;

//		mousePos = new Vector3(screenX, screenY, 0);
//		cameraGame.camera.unproject(mousePos);
//		float angle = new Vector2(mousePos.x, mousePos.y).sub(bodyEntiteVivante.getPosition()).angleRad();
//		bodyEntiteVivante.setTransform(bodyEntiteVivante.getPosition(), angle);

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.cameraGame.zoom(amount);
		return false;
	}

}
