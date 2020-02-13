package com.ultraime.explorers.service;

import java.util.ArrayDeque;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteAlien;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

public class EntiteService {

	private Monde monde;
	ArrayDeque<Noeud> chemin;

	public EntiteService(final Monde monde) {
		this.monde = monde;
	}

	public void initAlien(final Vector2 position) {
		EntiteAlien alien = EntiteAlien.creerAlienMusclee(position);
		Body bodyEntite = this.monde.addPAlienMuscle(alien);
		alien.initAEtoile(monde, bodyEntite);
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
//				final EntiteAlien ev = (EntiteAlien) bodyEntite.getUserData();
//				ev.manage();
			}
		}
	}

}
