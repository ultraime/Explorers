package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ultraime.game.gdxtraime.composant.Bouton;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

/**
 * @author ultraime
 * Ecran de base pour d�marrer une partie
 */
public class EcranPrincipal extends Ecran {

	private Bouton boutonStartPartie;
	private Bouton boutonLoadPartie;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		this.ecranManager = (EcranManager) ecranManager;
		this.batch = new SpriteBatch();

		String label2 = Parametre.bundle.get("txt.menu.start");
		this.boutonStartPartie = new Bouton(Parametre.x(752), Parametre.y(600), Parametre.x(300), Parametre.y(50),
				label2, Bouton.CLASSIQUE);

		String label3 = Parametre.bundle.get("txt.menu.load");
		this.boutonLoadPartie = new Bouton(Parametre.x(752), Parametre.y(420), Parametre.x(300), Parametre.y(50),
				label3, Bouton.CLASSIQUE);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.batch.begin();
		this.boutonStartPartie.render(batch);
		this.boutonLoadPartie.render(batch);
		this.batch.end();
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.boutonStartPartie.dispose();
		this.boutonLoadPartie.dispose();
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

		if (this.boutonStartPartie.isClique(screenX, screenY)) {
			this.ecranManager.ecranTest.create(this.ecranManager);
			//TODO charger ecran de jeu
			this.ecranManager.initialiserEcran(this.ecranManager.ecranTest);
//			this.ecranManager.initialiserEcran(this.ecranManager.ecranTestUI);
		}
		this.boutonLoadPartie.isClique(screenX, screenY);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.boutonStartPartie.touchUP(screenX, screenY);
		this.boutonLoadPartie.touchUP(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.boutonStartPartie.isOver(screenX, screenY);
		this.boutonLoadPartie.isOver(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
