package com.ultraime.game.gdxtraime.ecran;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ultraime.explorers.ecran.EcranManager;

public abstract class Ecran implements InputProcessor {
	public SpriteBatch batch;

	/**
	 * Le manager qui gère l'écran
	 */
	public EcranManager ecranManager;

	/**
	 * Appelée dès que l'écran est changé.
	 * 
	 * @param inputMultiplexer
	 */
	public abstract void changerEcran(final InputMultiplexer inputMultiplexer);

	/**
	 * Appelée à l'initialisation.
	 * 
	 * @param ecranManager
	 */
	public abstract void create(EcranManagerAbstract ecranManager);

	/**
	 * Permet d'afficher l'écran
	 */
	public abstract void render();

	/**
	 * détruit l'écran
	 */
	public abstract void dispose();

}
