package com.ultraime.explorers.Evenement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.animation.AnimationManager;

public class Interrupteur extends Evenement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final AnimationManager animation_interaction = new AnimationManager(42, 42, 0.200f,
			"images/interaction/animation_e.png");

	public Interrupteur(Vector2 position, float largeur, float hauteur) {
		super(position, largeur, hauteur);
	}

	public void render(final SpriteBatch batch) {
		if (showTouchEvent) {
			animation_interaction.render(batch, position.x, position.y, 0);
		}
	}

}
