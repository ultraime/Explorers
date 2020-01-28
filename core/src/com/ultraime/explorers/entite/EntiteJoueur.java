package com.ultraime.explorers.entite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.objet.ArmesManager;
import com.ultraime.game.gdxtraime.objet.Arme;

public class EntiteJoueur extends EntitePersonnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Arme arme;

	public EntiteJoueur(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
		arme = ArmesManager.creerPistolet();
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
				this.arme.render(batch, posX, posY, direction);
				break;
			case HAUT:
				this.arme.render(batch, posX, posY, direction);
				this.animationBody_h.render(batch, posX - 10, posY, 0);
				this.animationTete_h.render(batch, posX - 10, posY, 0);
				break;
			case GAUCHE:
				this.animationBody_g.render(batch, posX, posY, 0);
				this.animationTete_g.render(batch, posX, posY, 0);
				this.arme.render(batch, posX, posY, direction);
				break;
			case DROITE:
				this.animationBody_d.render(batch, posX - 10, posY, 0);
				this.animationTete_d.render(batch, posX - 10, posY, 0);
				this.arme.render(batch, posX, posY, direction);
				break;
			default:
				this.animationBody.render(batch, posX - 10, posY, 0);
				break;
			}

		}
	}

	public Vector2 getArmeDecalage() {
		return this.arme.getArmeDecalage(direction);
	}

	public void rotation(final double d) {
		this.arme.spirteArme.setRotation((float) d);
		if (d < -114 || d > 75) {
			this.arme.spirteArme.setFlip(false, true);
		} else {
			this.arme.spirteArme.setFlip(false, false);
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
		return new Vector2(this.arme.spirteArme.getX(), this.arme.spirteArme.getY());
	}

}
