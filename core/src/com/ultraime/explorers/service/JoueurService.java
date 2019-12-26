package com.ultraime.explorers.service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

public class JoueurService {

	public Body bodyJoueur;

	public JoueurService(final Body bodyJoueur) {
		this.bodyJoueur = bodyJoueur;
	}

	public void rotation(final int screenX,final int screenY,final OrthographicCamera camera) {
		Vector3 mousePos = new Vector3();
		mousePos = new Vector3(screenX, screenY, 0);
		camera.unproject(mousePos);
		float angle = new Vector2(mousePos.x, mousePos.y).sub(bodyJoueur.getPosition()).angleRad();
		bodyJoueur.setTransform(bodyJoueur.getPosition(), angle);
		
	}
	
	public void deplacer(final short directionX, final short directionY) {
		final EntiteVivante entiteJoueur = (EntiteVivante) bodyJoueur.getUserData();
		float velocity = entiteJoueur.habiliter.vitesse;

		float veloX = 0;
		float veloY = 0;

		if (directionX < 0) {
			veloX = -velocity;
		} else if (directionX > 0) {
			veloX = velocity;
		}

		if (directionY < 0) {
			veloY = -velocity;
		} else if (directionY > 0) {
			veloY = velocity;
		}

		bodyJoueur.setLinearVelocity(veloX, veloY);
	}

}
