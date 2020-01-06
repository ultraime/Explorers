package com.ultraime.explorers.entite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntiteAlien extends EntitePersonnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntiteAlien(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
	}
	
	public void render(final SpriteBatch batch, final float posX, final float posY) {
		if (animationBody != null) {
			switch (direction) {
			case BAS:
				this.animationBody.render(batch, posX - 10, posY, 0);
				this.animationTete.render(batch, posX - 10, posY, 0);
				break;
			case HAUT:
				this.animationBody_h.render(batch, posX - 10, posY, 0);
				this.animationTete_h.render(batch, posX - 10, posY, 0);
				break;
			case GAUCHE:
				this.animationBody_g.render(batch, posX, posY, 0);
				this.animationTete_g.render(batch, posX, posY, 0);
				break;
			case DROITE:
				this.animationBody_d.render(batch, posX - 10, posY, 0);
				this.animationTete_d.render(batch, posX - 10, posY, 0);
				break;

			default:
				this.animationBody.render(batch, posX - 10, posY, 0);
				this.animationTete.render(batch, posX - 10, posY, 0);
				break;
			}

		}
	}

}
