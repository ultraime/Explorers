package com.ultraime.explorers.service;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ultraime.game.gdxtraime.entite.Entite;
import com.ultraime.game.gdxtraime.monde.Monde;

public class MondeBaseService {

	public Monde monde;

	public MondeBaseService() {
		this.monde = new Monde(0);
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

}
