package com.ultraime.explorers.service;

import java.util.ArrayDeque;
import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteAlien;
import com.ultraime.explorers.entite.EntiteAlien.ETAT_ALIEN;
import com.ultraime.game.gdxtraime.entite.metier.HabiliterGeneral;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.pathfinding.AetoileDestinationBlockException;
import com.ultraime.game.gdxtraime.pathfinding.AetoileException;
import com.ultraime.game.gdxtraime.pathfinding.AetoileNew;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

public class EntiteService {

	private Monde monde;
	ArrayDeque<Noeud> chemin;

	public EntiteService(final Monde monde) {
		this.monde = monde;
	}

	public void initAlien(final Vector2 position) {
		EntiteAlien alien = new EntiteAlien((position.x / Monde.MULTIPLICATEUR) - 10, position.y / Monde.MULTIPLICATEUR,
				0.4f, (short) -10);
		alien.habiliter.sante[HabiliterGeneral.MAX] = 3;
		alien.habiliter.sante[HabiliterGeneral.ACTUEL] = 3;
		alien.habiliter.vitesse = 3;
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
		// TODO ici on place le A*.
		//// ici on suit bétement :
//		Vector3 targetPos = new Vector3(bodyJoueur.getPosition().x, bodyJoueur.getPosition().y, 0);
//		float angle = new Vector2(targetPos.x, targetPos.y).sub(bodyEntite.getPosition()).angleRad();
//		float velocity = ev.habiliter.vitesse;
//		float velX = MathUtils.cos(angle) * velocity;
//		float velY = MathUtils.sin(angle) * velocity;
//		bodyEntite.setLinearVelocity(velX, velY);

//		//test du A* ici
		AetoileNew aetoileNew = new AetoileNew(monde, bodyEntite);
		Noeud depNoeud = new Noeud((int) bodyEntite.getPosition().x, (int) bodyEntite.getPosition().y, 0);
		Noeud arrNoeud = new Noeud((int) bodyJoueur.getPosition().x, (int) bodyJoueur.getPosition().y, 0);
		try {
			if (chemin == null || (chemin != null && chemin.isEmpty())) {
				chemin = aetoileNew.cheminPlusCourt(arrNoeud, depNoeud, 1000);
			}
			if (chemin != null && !chemin.isEmpty()) {
				Noeud way = chemin.getFirst();
				Vector3 targetPos = new Vector3(way.x, way.y, 0);
				float angle = new Vector2(targetPos.x, targetPos.y).sub(bodyEntite.getPosition()).angleRad();
				float velocity = ev.habiliter.vitesse;
				float velX = MathUtils.cos(angle) * velocity;
				float velY = MathUtils.sin(angle) * velocity;
				bodyEntite.setLinearVelocity(velX, velY);

				if (  bodyEntite.getPosition().x   > way.x -0.5f && bodyEntite.getPosition().x < way.x  + 0.5f
						&& bodyEntite.getPosition().y   > way.y -0.5f && bodyEntite.getPosition().y < way.y  + 0.5f) {
					chemin.removeFirst();
				}

			}
		} catch (AetoileException e) {
			e.printStackTrace();
		} catch (AetoileDestinationBlockException e) {
			e.printStackTrace();
		}

	}

}
