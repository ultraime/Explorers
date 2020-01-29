package com.ultraime.game.gdxtraime.objet;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.entite.EntiteVivante.Direction;
import com.ultraime.game.gdxtraime.entite.metier.HabiliterGeneral;

public class Arme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// pour l'animation
	public Sprite spirteArme;
	public AnimationManager animationTir;
	public boolean isShot = false;

	// stat de l'arme
	public String nom;
	public HabiliterGeneral habiliterGeneral = new HabiliterGeneral();

	/**
	 * @param nom
	 * @param balle
	 * @param spirteArme
	 * @param animationTir
	 * @return
	 */
	public static Arme creerArme(final String nom, final Sprite spirteArme, final AnimationManager animationTir) {
		Arme arme = new Arme();
		arme.nom = nom;
		arme.spirteArme = spirteArme;
		arme.animationTir = animationTir;
		return arme;
	}

	private Arme() {
	}

	public Arme(Arme arme) {
		this.nom = arme.nom;
		this.spirteArme = arme.spirteArme;
		this.animationTir = arme.animationTir;
		this.habiliterGeneral = new HabiliterGeneral(arme.habiliterGeneral);
	}

	/**
	 * @param batch
	 * @param posX
	 * @param posY
	 */
	public void render(final SpriteBatch batch, final float posX, final float posY, final Direction direction) {
		int decalageX = 0;
		int decalageY = 0;
		switch (direction) {
		case BAS:
			decalageX = 10;
			decalageY = 25;
			break;
		case HAUT:
			decalageX = 10;
			decalageY = 55;
			break;
		case GAUCHE:
			decalageX = -20;
			decalageY = 35;
			break;
		case DROITE:
			decalageX = 40;
			decalageY = 35;
			break;
		default:
			break;
		}
		this.spirteArme.setPosition(posX + decalageX, posY + decalageY);
		this.spirteArme.draw(batch);

		if (isShot) {
			final boolean isEnd = this.animationTir.renderWithRotation(batch, posX + decalageX, posY + decalageY, 0,
					this.spirteArme.getRotation(), this.spirteArme.isFlipX(), this.spirteArme.isFlipY());
			if (isEnd) {
				isShot = false;
				this.animationTir.resetAnimation();
			}
		}
	}

	/**
	 * @param direction
	 * @return
	 */
	public Vector2 getArmeDecalage(final Direction direction) {
		Vector2 decalage = new Vector2();
		switch (direction) {
		case BAS:
			decalage.y = 0.5f;
			break;
		case HAUT:
			decalage.y = 1f;
			break;
		case GAUCHE:
			decalage.y = 0.5f;
			decalage.x = -0.5f;
			break;
		case DROITE:
			decalage.y = 0.5f;
			decalage.x = 0.5f;
			break;
		default:
			break;
		}
		return decalage;
	}

}
