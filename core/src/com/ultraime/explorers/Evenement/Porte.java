package com.ultraime.explorers.Evenement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.monde.Monde;

public class Porte extends Evenement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AnimationManager animationOuverturePorte;
	
	public Porte(Vector2 position, float largeur, float hauteur) {
		super(position, largeur, hauteur);
		animationOuverturePorte = new AnimationManager(192, 64, 0.1f, "images/objet/porte_long.png");
	}

	@Override
	public void render(SpriteBatch batch) {
		animationOuverturePorte.render(batch, position.x - Monde.MULTIPLICATEUR, position.y, 0);
	}

}
