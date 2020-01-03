package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.service.JoueurService;
import com.ultraime.explorers.service.MondeBaseService;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.monde.CameraGame;

/**
 * @author ultraime Ecran pour des test
 */
public class EcranTest extends Ecran {

	public MondeBaseService mondeService;

	private CameraGame cameraGame;
	private Vector2 positionSouris;

	private boolean isDispose = false;

	public JoueurService joueurService;

	public Body bodyEnemie;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
		this.cameraGame.camera.position.x = joueurService.bodyJoueur.getPosition().x;
		this.cameraGame.camera.position.y = joueurService.bodyJoueur.getPosition().y;
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		// creation des elements de gestion d'affichage
		this.ecranManager = (EcranManager) ecranManager;
		this.positionSouris = new Vector2(0, 0);
		this.batch = new SpriteBatch();
		this.cameraGame = new CameraGame();
		this.cameraGame.camera.position.x = 0;
		this.cameraGame.camera.position.y = 0;

		// creatuib du monde
		this.mondeService = new MondeBaseService();

		// Creation des entites
		EntiteVivante entiteVivante = new EntiteVivante(0, 0, 0.4f, (short) -1);
		joueurService = new JoueurService(
				this.mondeService.monde.addEntiteVivante(entiteVivante, this.mondeService.monde.bodiesEntiteVivant));

		EntiteVivante entiteEnemie = new EntiteVivante(2, 0, 0.4f, (short) -2);
		bodyEnemie = this.mondeService.monde.addEntiteVivante(entiteEnemie, this.mondeService.monde.bodiesEntiteVivant);

	}

	@Override
	public void render() {
		if (!isDispose) {
			this.mondeService.render(this.joueurService, this.cameraGame);
			this.mondeService.monde.renderDebug(cameraGame.camera);
		}
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.mondeService.monde.dispose();
		isDispose = true;
	}

	public void deplacementJoueur() {
		short directionX = 0;
		short directionY = 0;

		if (isTouchPressed(Input.Keys.D)) {
			directionX = 1;
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
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		relacherTouche(keycode);
		deplacementJoueur();

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			joueurService.shot(screenX, screenY, cameraGame.camera, this.mondeService.monde);
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
//		joueurService.rotation(screenX, screenY, this.mondeService.monde.cameraDebug);
		positionSouris.x = screenX;
		positionSouris.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.cameraGame.zoom(amount);
		return false;
	}

}
