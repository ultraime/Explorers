package com.ultraime.explorers.thread;

import com.badlogic.gdx.Gdx;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class ThreadMonde implements Runnable {
	public Monde monde;

	public ThreadMonde(final Monde monde) {
		this.monde = monde;
	}

	@Override
	public void run() {
		do {
			if (!Parametre.PAUSE) {
			
			}

		} while (Gdx.app != null);

	}

}
