package com.ultraime.explorers.Evenement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.monde.Monde;

public class Porte extends Evenement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnimationManager animationOuverturePorte;
	public ETATPORTE etatporte = ETATPORTE.FERMEE;

	public enum ETATPORTE {
		FERMEE, OUVERT
	};

	public Porte(Vector2 position, float largeur, float hauteur) {
		super(position, largeur, hauteur);
		animationOuverturePorte = new AnimationManager(192, 64, 0.1f, "images/objet/porte_long.png");
	}

	@Override
	public void render(SpriteBatch batch) {
		if (ETAT.STOP.equals(etat)) {
			if (ETATPORTE.FERMEE.equals(etatporte)) {
				animationOuverturePorte.renderFirstFrame(batch, position.x - Monde.MULTIPLICATEUR, position.y, 0);
			}
		} else {
			Boolean isEnd = animationOuverturePorte.renderOneTime(batch, position.x - Monde.MULTIPLICATEUR, position.y,
					0);
			if (isEnd) {
				etat = ETAT.STOP;
			}
		}
	}

	public void fermerPorte(boolean doitEtreFermer) {
		if (doitEtreFermer) {
			animationOuverturePorte.setPlayMode(0, PlayMode.LOOP_REVERSED);
			etatporte = ETATPORTE.FERMEE;
		} else {
			animationOuverturePorte.setPlayMode(0, PlayMode.NORMAL);
			etatporte = ETATPORTE.OUVERT;
		}
		animationOuverturePorte.resetAnimation();
		etat = ETAT.PLAY;

	}

}
