package com.ultraime.explorers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ultraime.explorers.Evenement.Interrupteur;
import com.ultraime.explorers.Evenement.Porte;
import com.ultraime.explorers.entite.EntiteJoueur;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.carte.Carte;
import com.ultraime.game.gdxtraime.entite.Entite;
import com.ultraime.game.gdxtraime.entite.EntiteStatic;
import com.ultraime.game.gdxtraime.monde.CameraGame;
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
				boolean isContact = contactJoueurEvent(fixtureA, fixtureB);
				if (!isContact) {
					contactBullets(fixtureA, fixtureB);
				}

			}

			private boolean contactJoueurEvent(final Fixture fixtureA, final Fixture fixtureB) {
				boolean isContact = false;
				final Body bodyA = fixtureA.getBody();
				final Body bodyB = fixtureB.getBody();

				EntiteJoueur entiteJoueur = null;
				Object objEvent = null;
				// recuperation des datas
				if (bodyA.getUserData() instanceof EntiteJoueur) {
					entiteJoueur = (EntiteJoueur) bodyA.getUserData();
					if (bodyB.getUserData() instanceof Evenement) {
						objEvent = bodyB.getUserData();
					}
				} else if (bodyB.getUserData() instanceof EntiteJoueur)
					entiteJoueur = (EntiteJoueur) bodyB.getUserData();
				if (bodyA.getUserData() instanceof Evenement) {
					objEvent = bodyA.getUserData();
				}
				// regle metier
				if (entiteJoueur != null && objEvent != null) {
					if (objEvent instanceof Interrupteur) {
						final Interrupteur interrupteur = (Interrupteur) objEvent;
						interrupteur.showTouchEvent = true;
						isContact = true;
					}
				}
				return isContact;
			}

			private void contactBullets(final Fixture fixtureA, final Fixture fixtureB) {
				if (monde.bodiesBullets.contains(fixtureA.getBody())) {
					if (!(fixtureB.getBody().getUserData() instanceof Evenement)
							|| ((fixtureB.getBody().getUserData() instanceof Evenement)
									&& !fixtureB.getBody().getFixtureList().get(0).isSensor())) {
						Entite e = (Entite) fixtureB.getBody().getUserData();
						e.isDeleted = true;
					}
				} else if (monde.bodiesBullets.contains(fixtureB.getBody())) {
					if (!(fixtureA.getBody().getUserData() instanceof Evenement)
							|| ((fixtureA.getBody().getUserData() instanceof Evenement)
									&& !fixtureA.getBody().getFixtureList().get(0).isSensor())) {
						Entite e = (Entite) fixtureB.getBody().getUserData();
						e.isDeleted = true;
					}
				}
			}

			@Override
			public void endContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				endContactJoueurEvent(fixtureA, fixtureB);
			}

			private void endContactJoueurEvent(final Fixture fixtureA, final Fixture fixtureB) {
				final Body bodyA = fixtureA.getBody();
				final Body bodyB = fixtureB.getBody();

				EntiteJoueur entiteJoueur = null;
				Object objEvent = null;
				// recuperation des datas
				if (bodyA.getUserData() instanceof EntiteJoueur) {
					entiteJoueur = (EntiteJoueur) bodyA.getUserData();
					if (bodyB.getUserData() instanceof Evenement) {
						objEvent = bodyB.getUserData();
					}
				} else if (bodyB.getUserData() instanceof EntiteJoueur)
					entiteJoueur = (EntiteJoueur) bodyB.getUserData();
				if (bodyA.getUserData() instanceof Evenement) {
					objEvent = bodyA.getUserData();
				}
				//
				if (entiteJoueur != null && objEvent != null) {
					if (objEvent instanceof Interrupteur) {
						final Interrupteur interrupteur = (Interrupteur) objEvent;
						interrupteur.showTouchEvent = false;
					}
				}

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	public void initialiserCollision() {
		final TiledMapTileLayer layer = monde.carte.getLayers("mur");
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				final Cell cell = layer.getCell(x, y);
				if (cell != null && cell.getTile() != null) {
					float largeur = 1f;
					float longueur = 1f;
					float posX = x + 0.5f;
					float posY = y + 0.5f;
					EntiteStatic entiteStatic = new EntiteStatic(posX, posY, largeur, longueur);
					monde.addEntiteStatic(entiteStatic);

				}
			}
		}
	}

	/**
	 * @param joueurService
	 * @param cameraGame
	 */
	public void render(final JoueurService joueurService, final CameraGame cameraGame) {
		if (this.monde.carte != null) {
			this.monde.carte.render();
		}

		this.monde.batch.begin();
		this.monde.gestionBodies();
		this.monde.renderEvent();

		this.monde.removeDeathEntite(this.monde.bodiesEntiteVivant);
		this.monde.removeDeathEntite(this.monde.bodiesBullets);

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
		this.monde.batch.end();

	}

	/**
	 * @param joueurService
	 * @param cameraGame
	 */
	private void updateCamera(final JoueurService joueurService, final CameraGame cameraGame) {
		cameraGame.camera.position.x = joueurService.bodyJoueur.getPosition().x * Monde.MULTIPLICATEUR;
		cameraGame.camera.position.y = joueurService.bodyJoueur.getPosition().y * Monde.MULTIPLICATEUR;
		cameraGame.camera.update();
		monde.updateCamera(cameraGame.camera);
		monde.updateDebugCamera(joueurService.bodyJoueur.getPosition());
	}

	public void creerPorte(List<MapObject> eventInterupteur, List<MapObject> eventPorte) {

		List<Interrupteur> interupteurs = new ArrayList<Interrupteur>();
		for (MapObject object : eventInterupteur) {
			float posX = (Float) object.getProperties().get("x");
			float posY = (Float) object.getProperties().get("y");
			final float longueur = (Float) object.getProperties().get("width");
			final float hauteur = (Float) object.getProperties().get("height");
			final int id_interrupteur = (Integer) object.getProperties().get("porte");
			final String direction = (String) object.getProperties().get("direction");

			final Vector2 position = decalageEvenement(direction, object, posX, posY, 2);

			Interrupteur interrupteur = new Interrupteur(position, longueur, hauteur);
			interrupteur.id = id_interrupteur;
			this.monde.addEvent(interrupteur);

			interupteurs.add(interrupteur);
		}

		for (MapObject object : eventPorte) {
			float posX = (Float) object.getProperties().get("x");
			float posY = (Float) object.getProperties().get("y");
			final float longueur = (Float) object.getProperties().get("width");
			final float hauteur = (Float) object.getProperties().get("height");
			final int id_porte = (Integer) object.getProperties().get("porte");
			final String direction = (String) object.getProperties().get("direction");
			final Vector2 position = decalageEvenement(direction, object, posX, posY, 1);

			Porte porte = new Porte(position, longueur, hauteur, direction);
			porte.id = id_porte;
//			Optional<Interrupteur> matchingObject = interupteurs.stream().filter(i -> i.id == id_porte).findFirst();
			final Body body = this.monde.addEvent(porte);
			body.getFixtureList().get(0).setSensor(false);

			// porte
//			float largeur = 1f;
//			float longueur = 1f;
//			float posX = x + 0.5f;
//			float posY = y + 0.5f;
//			EntiteStatic entiteStatic = new EntiteStatic(posX, posY, largeur, longueur);
//			monde.addEntiteStatic(entiteStatic);
		}

	}

	/**
	 * @param object
	 * @param posX
	 * @param posY
	 * @param diviseur
	 * @return
	 */
	private Vector2 decalageEvenement(final String direction, final MapObject object, float posX, float posY,
			final int diviseur) {
		if (direction.equals("long")) {
			posX = posX + Monde.MULTIPLICATEUR / diviseur;
		} else {
			posY = posY + Monde.MULTIPLICATEUR / diviseur;
		}
		return new Vector2(posX, posY);
	}

	public Body getEventAcionne() {
		Body body = null;
		Optional<Body> objectFInd = this.monde.bodiesEvent.stream()
				.filter(e -> ((Evenement) e.getUserData()).showTouchEvent == true).findFirst();
		if (objectFInd.isPresent()) {
			body = objectFInd.get();
		}
		return body;

	}

}
