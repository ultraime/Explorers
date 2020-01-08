package com.ultraime.game.gdxtraime.Evenement;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class Evenement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector2 position;
	public float largeur, hauteur;

	public Evenement(final Vector2 position, final float largeur, final float hauteur) {
		this.position = position;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

}
