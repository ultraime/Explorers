package com.ultraime.game.gdxtraime.entite;

public class EntiteStatic extends Entite {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float largeur;
	private float hauteur;

	public EntiteStatic(float x, float y, float largeur, float hauteur) {
		super(x, y);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public float getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public float getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

}
