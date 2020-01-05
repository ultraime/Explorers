package com.ultraime.explorers.service;

import com.badlogic.gdx.graphics.OrthographicCamera;
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

	public void rotation(final int screenX, final int screenY, final OrthographicCamera camera) {
		// Vector3 mousePos = new Vector3();
		// mousePos = new Vector3(screenX, screenY, 0);
		// camera.unproject(mousePos);
		// float angle = new Vector2(mousePos.x,
		// mousePos.y).sub(bodyJoueur.getPosition()).angleRad();
		// bodyJoueur.setTransform(bodyJoueur.getPosition(), angle);

		Vector3 mousePos = new Vector3();
		mousePos = new Vector3(screenX, screenY, 0);
		camera.unproject(mousePos);
		final EntiteJoueur entiteJoueur = (EntiteJoueur) bodyJoueur.getUserData();

		float angle = new Vector2(mousePos.x, mousePos.y).sub(entiteJoueur.getAmresPosition()).angleRad();
		entiteJoueur.rotation(Math.toDegrees(angle));

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

	public void shot(final int screenX, final int screenY, final OrthographicCamera camera, final Monde monde) {
		Vector3 mousePos = new Vector3();
		mousePos = new Vector3(screenX, screenY, 0);
		camera.unproject(mousePos);
		final EntiteJoueur entiteJoueur = (EntiteJoueur) bodyJoueur.getUserData();

		float angle = new Vector2(mousePos.x, mousePos.y).sub(entiteJoueur.getAmresPosition()).angleRad();
		entiteJoueur.rotation(Math.toDegrees(angle));
		
		float velocity = 10f;

		final Vector2 decalage = entiteJoueur.getArmeDecalage();
		float posX = bodyJoueur.getPosition().x + decalage.x;
		float posY = bodyJoueur.getPosition().y + decalage.y; //+0.5
		float velX = MathUtils.cos(angle) * velocity;
		float velY = MathUtils.sin(angle) * velocity;
//		posX + 10, posY + 35
		
		EntiteVivante bullet = new EntiteVivante(posX, posY, 0.04f, (short) -1);
		Body body = monde.addEntiteVivante(bullet, monde.bodiesBullets);
		body.setBullet(true);
		body.setLinearVelocity(velX, velY);
//		System.err.println(Math.toDegrees(angle));

	}

}
