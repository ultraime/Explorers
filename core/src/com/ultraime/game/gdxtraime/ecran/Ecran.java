package com.ultraime.game.gdxtraime.ecran;

import java.util.ArrayList;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ultraime.explorers.ecran.EcranManager;

public abstract class Ecran implements InputProcessor {
	public SpriteBatch batch;

	/**
	 * Le manager qui gÃ¨re l'Ã©cran
	 */
	public EcranManager ecranManager;

	/**
	 * AppelÃ©e dÃ¨s que l'Ã©cran est changÃ©.
	 * 
	 * @param inputMultiplexer
	 */
	public abstract void changerEcran(final InputMultiplexer inputMultiplexer);

	/**
	 * AppelÃ©e Ã  l'initialisation.
	 * 
	 * @param ecranManager
	 */
	public abstract void create(EcranManagerAbstract ecranManager);

	/**
	 * Permet d'afficher l'Ã©cran
	 */
	public abstract void render();

	/**
	 * dÃ©truit l'Ã©cran
	 */
	public abstract void dispose();
	
	public abstract void resize(int width, int height);

	/**
	 * touche du clavier up
	 */
	private ArrayList<Integer> touchePress = new ArrayList<Integer>();

	public void presserTouche(final Integer key) {
		if (!touchePress.contains(key)) {
			touchePress.add(key);
		}
	}

	public void relacherTouche(final Integer key) {
		if (touchePress.contains(key)) {
			touchePress.remove( key);
		}
	}

	public boolean isTouchPressed(final Integer key) {
		boolean isPressed = false;
		if (touchePress.contains(key)) {
			isPressed = true;
		}
		return isPressed;
	}

}
