package com.ultraime.explorers.entite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

public class EntiteJoueur extends EntiteVivante {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntiteJoueur(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
		this.animationManager = new AnimationManager(80, 128, 1f, "personnage/humain/astro_test3.png");
//		this.animationManager = new AnimationManager(64, 128, 1f, "personnage/humain/astro_test2.png");
//		this.animationManager = new AnimationManager(64, 76, 1f, "personnage/humain/astro_test.png");
	}

	/**
	 * @param batch
	 * @param posX
	 * @param posY
	 */
	public void render(final SpriteBatch batch, final float posX, final float posY) {
		if (animationManager != null) {
			this.animationManager.render(batch, posX, posY, 0);
		}
	}

	public EntiteJoueur(Body bodyJoueur) {
		this(bodyJoueur.getPosition().x, bodyJoueur.getPosition().y,
				bodyJoueur.getFixtureList().get(0).getShape().getRadius(),
				bodyJoueur.getFixtureList().get(0).getFilterData().groupIndex);
	}

}
