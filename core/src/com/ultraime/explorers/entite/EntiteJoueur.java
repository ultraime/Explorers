package com.ultraime.explorers.entite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.game.gdxtraime.animation.AnimationManager;

public class EntiteJoueur extends EntitePersonnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Sprite pistolet;
	protected AnimationManager animationTir;

	public EntiteJoueur(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);

		pistolet = new Sprite(new Texture(Gdx.files.internal("armes/pistolet.png")));
		animationTir = new AnimationManager(50, 45, 0.1f, "armes/pistolet_tir_anime.png");
		// this.animationManager = new AnimationManager(64, 128, 1f,
		// "personnage/humain/astro_test2.png");
		// this.animationManager = new AnimationManager(64, 76, 1f,
		// "personnage/humain/astro_test.png");
	}

	/**
	 * @param batch
	 * @param posX
	 * @param posY
	 */
	public void render(final SpriteBatch batch, final float posX, final float posY) {
		if (animationBody != null) {
			switch (direction) {
			case BAS:
				this.animationBody.render(batch, posX - 10, posY, 0);
				this.animationTete.render(batch, posX - 10, posY, 0);
				this.pistolet.setPosition(posX + 10, posY + 25);
				this.pistolet.draw(batch);
				this.animationTir.renderWithRotation(batch, posX + 10, posY + 25 , 0,this.pistolet.getRotation(),this.pistolet.isFlipX(),this.pistolet.isFlipY());
				break;
			case HAUT:
				this.pistolet.setPosition(posX + 10, posY + 55);
				this.pistolet.draw(batch);
				this.animationBody_h.render(batch, posX - 10, posY, 0);
				this.animationTete_h.render(batch, posX - 10, posY, 0);
				break;
			case GAUCHE:
				this.animationBody_g.render(batch, posX, posY, 0);
				this.animationTete_g.render(batch, posX, posY, 0);
				this.pistolet.setPosition(posX - 20, posY + 35);
				this.pistolet.draw(batch);
				break;
			case DROITE:
				this.animationBody_d.render(batch, posX - 10, posY, 0);
				this.animationTete_d.render(batch, posX - 10, posY, 0);
				this.pistolet.setPosition(posX + 40, posY + 35);
				this.pistolet.draw(batch);
				break;
			default:
				this.animationBody.render(batch, posX - 10, posY, 0);
				break;
			}
			
	

		}
	}

	public Vector2 getArmeDecalage() {
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

	public void rotation(final double d) {
		this.pistolet.setRotation((float) d);
		if (d < -114 || d > 75) {
			this.pistolet.setFlip(false, true);
		} else {
			this.pistolet.setFlip(false, false);
		}

		if (d > 40 && d < 133) {
			direction = Direction.HAUT;
		} else if (d > -38 && d < 40) {
			direction = Direction.DROITE;
		} else if (d > -133 && d < -38) {
			direction = Direction.BAS;
		} else {
			direction = Direction.GAUCHE;
		}

	}

	public EntiteJoueur(Body bodyJoueur) {
		this(bodyJoueur.getPosition().x, bodyJoueur.getPosition().y,
				bodyJoueur.getFixtureList().get(0).getShape().getRadius(),
				bodyJoueur.getFixtureList().get(0).getFilterData().groupIndex);
	}

	public Vector2 getAmresPosition() {
		return new Vector2(this.pistolet.getX(), this.pistolet.getY());
	}

}
