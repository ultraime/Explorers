package com.ultraime.explorers.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ultraime.game.gdxtraime.carte.Carte;
import com.ultraime.game.gdxtraime.entite.Entite;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class MondeBaseService {

	public Monde monde;

	public MondeBaseService() {
		Carte carte = new Carte("carte/base.tmx");
		this.monde = new Monde(carte, 0);
		createCollisionListener();
	}

	private void createCollisionListener() {
		this.monde.world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();

				if (monde.bodiesBullets.contains(fixtureA.getBody())) {
					Entite e = (Entite) fixtureB.getBody().getUserData();
					e.isDeleted = true;
				} else if (monde.bodiesBullets.contains(fixtureB.getBody())) {
					Entite e = (Entite) fixtureB.getBody().getUserData();
					e.isDeleted = true;
				}

			}

			@Override
			public void endContact(Contact contact) {
//				Fixture fixtureA = contact.getFixtureA();
//				Fixture fixtureB = contact.getFixtureB();
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	public void render(final JoueurService joueurService) {
		if (this.monde.carte != null) {
			this.monde.carte.render();
		}
		if (!Parametre.PAUSE) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			float frameTime = Math.min(deltaTime, 0.25f);
			this.monde.accumulator += frameTime;
			while (this.monde.accumulator >= this.monde.STEP_TIME) {
				this.monde.world.step(this.monde.STEP_TIME, 6, 2);
				this.monde.accumulator -= this.monde.STEP_TIME;
			}
		}
		this.monde.batch.begin();
		joueurService.render(this.monde.batch);
		this.monde.gestionBodies();
		this.monde.batch.end();

		this.monde.removeDeathEntite(this.monde.bodiesEntiteVivant);
		this.monde.removeDeathEntite(this.monde.bodiesBullets);
	}

}
