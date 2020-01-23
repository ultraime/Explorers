package com.ultraime.explorers.entite;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntiteAlien extends EntitePersonnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<EntiteJoueur> entiteJoueursDansZone = new ArrayList<EntiteJoueur>();

	public enum ETAT_ALIEN {
		ATTEND, CHASSE_JOUEUR
	};

	public ETAT_ALIEN etatAlien = ETAT_ALIEN.ATTEND;

	public EntiteAlien(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
	}
	
	public void manage() {
		if (entiteJoueursDansZone.size() > 0) {
			etatAlien = ETAT_ALIEN.CHASSE_JOUEUR;
		}else {
			etatAlien = ETAT_ALIEN.ATTEND;
		}
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
