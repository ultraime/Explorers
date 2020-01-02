package com.ultraime.explorers.service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteJoueur;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.monde.Monde;

public class JoueurService {

	public Body bodyJoueur;

	public JoueurService(final Body bodyJoueur) {
		this.bodyJoueur = bodyJoueur;
		EntiteJoueur entiteJoueur = new EntiteJoueur(bodyJoueur);
		this.bodyJoueur.setUserData(entiteJoueur);
	}
	
	public void render(SpriteBatch batch) {
		final EntiteJoueur entiteJoueur =  (EntiteJoueur) this.bodyJoueur.getUserData();
		final float posX =  (bodyJoueur.getPosition().x * Monde.MULTIPLICATEUR) - ( Monde.MULTIPLICATEUR);
		final float posY = (bodyJoueur.getPosition().y * Monde.MULTIPLICATEUR) - ( Monde.MULTIPLICATEUR);
		entiteJoueur.render(batch,posX, posY);
//		entiteJoueur.render(batch,100, 100);
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
	

	public void shot(final int screenX,final int screenY,final OrthographicCamera camera,final Monde monde) {
//		Vector2 positionSouris = new Vector2(screenX,screenY);
		rotation((int) screenX, (int) screenY, monde.cameraDebug);
		float angle = bodyJoueur.getAngle();
		
		float velocity = 10f;

		float velX = MathUtils.cos(angle) * velocity; // X-component.
		float velY = MathUtils.sin(angle) * velocity;

		float posX = bodyJoueur.getPosition().x; // X-component.
		float posY = bodyJoueur.getPosition().y;

		EntiteVivante bullet = new EntiteVivante(posX, posY, 0.04f, (short) -1);
		Body body = monde.addEntiteVivante(bullet, monde.bodiesBullets);
		body.setBullet(true);
		body.setLinearVelocity(velX, velY);
		
	}



}
