package com.ultraime.explorers.Evenement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteAlien;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.monde.Monde;

public class GenerateurAlien extends Evenement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenerateurAlien(Vector2 position, float largeur, float hauteur) {
		super(position, largeur, hauteur);
	}

	public void genererAlien(final Monde monde) {
		EntiteAlien alien = EntiteAlien.creerAlienMusclee(position);
		Body bodyEntite = monde.addPAlienMuscle(alien);
		alien.initAEtoile(monde, bodyEntite);
	}

	@Override
	public void render(SpriteBatch batch) {
	}

}
