package com.ultraime.explorers.service;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteAlien;
import com.ultraime.explorers.entite.EntiteAlien.ETAT_ALIEN;
import com.ultraime.game.gdxtraime.monde.Monde;

public class EntiteService {

	private Monde monde;

	public EntiteService(final Monde monde) {
		this.monde = monde;
	}

	public void initAlien(final Vector2 position) {
		EntiteAlien alien = new EntiteAlien((position.x / Monde.MULTIPLICATEUR) - 10, position.y / Monde.MULTIPLICATEUR,
				0.4f, (short) -10);
		this.monde.addPAlienMuscle(alien);
	}

	/**
	 * appelé par le render d'un écran. NE gére pas d'affichage, juste du métier !
	 * 
	 * @param bodyJoueur
	 */
	public void manage(Body bodyJoueur) {

		ArrayList<Body> bodies = this.monde.bodiesEntiteVivant;
		for (int i = 0; i < bodies.size(); i++) {
			final Body bodyEntite = bodies.get(i);
			if (bodyEntite.getUserData() instanceof EntiteAlien) {
				final EntiteAlien ev = (EntiteAlien) bodyEntite.getUserData();
				ev.manage();
				if (ev.etatAlien.equals(ETAT_ALIEN.CHASSE_JOUEUR)) {
					chasseJoueur(bodyJoueur, bodyEntite, ev);
				} else {
					stopEntite(bodyEntite);
				}
			}
		}
	}

	private void stopEntite(Body bodyEntite) {
		bodyEntite.setLinearVelocity(0, 0);

	}

	private void chasseJoueur(final Body bodyJoueur, final Body bodyEntite, final EntiteAlien ev) {
		Vector3 targetPos = new Vector3(bodyJoueur.getPosition().x, bodyJoueur.getPosition().y, 0);
		float angle = new Vector2(targetPos.x, targetPos.y).sub(bodyEntite.getPosition()).angleRad();
		float velocity = ev.habiliter.vitesse;
		float velX = MathUtils.cos(angle) * velocity;
		float velY = MathUtils.sin(angle) * velocity;
		bodyEntite.setLinearVelocity(velX, velY);
	}

}
