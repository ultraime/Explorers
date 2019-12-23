package com.ultraime.explorers.ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ultraime.game.gdxtraime.ecran.EcranDebug;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class EcranManager extends EcranManagerAbstract {

	public EcranPrincipal ecranPrincipal;
	public EcranTest ecranTest;
	public EcranHud ecranHud;

	@Override
	public void create() {
		Parametre.initLangue();
		Gdx.graphics.setTitle(Parametre.TITRE_JEU);
		ecranDebug = new EcranDebug(this);

		ecranPrincipal = new EcranPrincipal();
		initialiserEcran(ecranPrincipal);

		ecranTest = new EcranTest();
		ecranActuel.create(this);

		ecranHud = new EcranHud();
		ecranHud.create(this);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ecranActuel.render();

		if (Parametre.MODE_DEBUG && this.ecranDebug != null) {
			this.ecranDebug.render();
		}

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