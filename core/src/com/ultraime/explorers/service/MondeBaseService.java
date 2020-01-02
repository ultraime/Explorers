package com.ultraime.explorers.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ultraime.game.gdxtraime.carte.Carte;
import com.ultraime.game.gdxtraime.entite.Entite;
import com.ultraime.game.gdxtraime.monde.CameraGame;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class MondeBaseService {

	public Monde monde;
	private OrthographicCamera gamecam;
	private Viewport gamePort;

	public MondeBaseService() {
		Carte carte = new Carte("carte/base.tmx");
		this.monde = new Monde(carte, 0);
		createCollisionListener();

		// Camera and Viewport
		gamecam = new OrthographicCamera();
		gamePort = new ExtendViewport(Parametre.LARGEUR_ECRAN, Parametre.HAUTEUR_ECRAN, gamecam);
		gamecam.setToOrtho(false);

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
				// Fixture fixtureA = contact.getFixtureA();
				// Fixture fixtureB = contact.getFixtureB();
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	/**
	 * @param joueurService
	 * @param cameraGame
	 */
	public void render(final JoueurService joueurService, final CameraGame cameraGame) {
		if (this.monde.carte != null) {
			this.monde.carte.render();
		}
		if (!Parametre.PAUSE) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			float frameTime = Math.min(deltaTime, 0.25f);
			this.monde.accumulator += frameTime;
			while (this.monde.accumulator >= Monde.STEP_TIME) {
				this.monde.world.step(Monde.STEP_TIME, 6, 2);
				this.monde.accumulator -= Monde.STEP_TIME;
				
				
				updateCamera(joueurService, cameraGame);
			}
		}
		this.monde.batch.begin();
		joueurService.render(this.monde.batch);
		this.monde.gestionBodies();
		this.monde.batch.end();

		this.monde.removeDeathEntite(this.monde.bodiesEntiteVivant);
		this.monde.removeDeathEntite(this.monde.bodiesBullets);
	}

	/**
	 * @param joueurService
	 * @param cameraGame
	 */
	private void updateCamera(final JoueurService joueurService, final CameraGame cameraGame) {
		gamecam.position.x = joueurService.bodyJoueur.getPosition().x * Monde.MULTIPLICATEUR;
		gamecam.position.y = joueurService.bodyJoueur.getPosition().y * Monde.MULTIPLICATEUR;
//		this.monde.batch.setProjectionMatrix(gamecam.combined);
		gamecam.update();
		monde.updateCamera(gamecam);
//		cameraGame.updateCamera();
//		cameraGame.camera.position.x = joueurService.bodyJoueur.getPosition().x * Monde.MULTIPLICATEUR;
//		cameraGame.camera.position.y = joueurService.bodyJoueur.getPosition().y * Monde.MULTIPLICATEUR;
//		OrthographicCamera camera = cameraGame.camera;
//		this.monde.batch.setProjectionMatrix(camera.combined);
//		monde.updateCamera(camera);
		monde.updateDebugCamera(joueurService.bodyJoueur.getPosition());
	}

}
