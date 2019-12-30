package com.ultraime.game.gdxtraime.ecran;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public abstract class EcranManagerAbstract extends ApplicationAdapter implements InputProcessor {
	/**
	 * L'ecran qui est actuellement utilisé
	 */
	public Ecran ecranActuel;

	/**
	 * Ecran pour afficher des éléments en mode débug.
	 */
	public EcranDebug ecranDebug;

	/**
	 * Entré clavié souris
	 */
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public void initialiserEcran(final Ecran ecran) {
		inputMultiplexer.clear();
		inputMultiplexer.addProcessor(this);
		if (Parametre.MODE_DEBUG && ecranDebug != null) {
			ecranDebug.changerEcran(inputMultiplexer);
		}
		ecran.changerEcran(inputMultiplexer);

		Gdx.input.setInputProcessor(inputMultiplexer);
		ecranActuel = ecran;
	}

	@Override
	public abstract void create();

	@Override
	public void render() {
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		ecranActuel.render();
		if (Parametre.MODE_DEBUG && this.ecranDebug != null) {
			this.ecranDebug.render();
		}

	}
}
