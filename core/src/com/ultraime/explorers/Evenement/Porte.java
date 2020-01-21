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

	public Porte(Vector2 position, float largeur, float hauteur, final String direction) {
		super(position, largeur, hauteur);
		this.direction = direction;
		if (this.direction.equals(LONG)) {
			animationOuverturePorte = new AnimationManager(192, 64, 0.1f, "images/objet/porte_long.png");
		} else if (this.direction.equals(HAUT)) {
			animationOuverturePorte = new AnimationManager(64, 192, 0.1f, "images/objet/porte_haut.png");
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		final Vector2 positionDraw = positionRender();
		final float posX = positionDraw.x;
		final float posY = positionDraw.y;
		
		if (ETAT.STOP.equals(etat)) {
			if (ETATPORTE.FERMEE.equals(etatporte)) {
				animationOuverturePorte.renderFirstFrame(batch, posX, posY, 0);
			}
		} else {
			Boolean isEnd = animationOuverturePorte.renderOneTime(batch, posX, posY, 0);
			if (isEnd) {
				etat = ETAT.STOP;
				if (ETATPORTE.FERMEE.equals(etatporte)) {
					// réaffichage pour évite que pendant 0.1sec rien n'est à l'écransqs
					animationOuverturePorte.renderFirstFrame(batch, posX, posY, 0);
				}
			}
		}

	}
	
	private Vector2 positionRender() {
		Vector2 positionRender = new Vector2(this.position.x,this.position.y);
		if (this.direction.equals(LONG)) {
			positionRender.x = position.x - Monde.MULTIPLICATEUR;
		} else if (this.direction.equals(HAUT)) {
			positionRender.y = position.y - Monde.MULTIPLICATEUR;
		}
		return positionRender;
	}
	
	public void fermerPorte(boolean doitEtreFermer) {
		if (doitEtreFermer) {
			animationOuverturePorte.setPlayMode(0, PlayMode.LOOP_REVERSED);
			etatporte = ETATPORTE.FERMEE;
			animationOuverturePorte.resetAnimation();
		} else {
			animationOuverturePorte.setPlayMode(0, PlayMode.NORMAL);
			etatporte = ETATPORTE.OUVERT;
		}
		animationOuverturePorte.resetAnimation();
		etat = ETAT.PLAY;

	}

}
