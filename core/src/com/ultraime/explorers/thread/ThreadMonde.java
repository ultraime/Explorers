package com.ultraime.explorers.thread;

import com.badlogic.gdx.Gdx;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class ThreadMonde implements Runnable {
	public Monde monde;

	public ThreadMonde(final Monde monde) {
		this.monde = monde;
		;
	}

	@Override
	public void run() {
		do {
			if (!Parametre.PAUSE) {
				float deltaTime = Gdx.graphics.getDeltaTime();
				float frameTime = Math.min(deltaTime, 0.25f);
				this.monde.accumulator += frameTime;
				while (this.monde.accumulator >= Monde.STEP_TIME) {
					this.monde.world.step(Monde.STEP_TIME, 6, 2);
					this.monde.accumulator -= Monde.STEP_TIME;
				}
			}

		} while (Gdx.app != null);

	}

}
