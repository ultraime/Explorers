package com.ultraime.game.gdxtraime.Evenement;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Evenement implements Serializable {
	
	public transient static final  String LONG = "long";
	public transient static final  String HAUT = "haut";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector2 position;
	public float largeur, hauteur;
	public int id = -1;
	public ETAT etat = ETAT.STOP;
	public String direction = "";
	
	
	public enum ETAT {
		PLAY, STOP
	};

	/**
	 * le bouton d'animation
	 */
	public boolean showTouchEvent = false;

	public Evenement(final Vector2 position, final float largeur, final float hauteur) {
		this.position = position;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public abstract void render(final SpriteBatch batch);

}
