package com.ultraime.explorers.ecran;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ultraime.explorers.Evenement.Interrupteur;
import com.ultraime.explorers.Evenement.Porte;
import com.ultraime.explorers.service.EntiteService;
import com.ultraime.explorers.service.JoueurService;
import com.ultraime.explorers.service.MondeBaseService;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.ecran.Ecran;
import com.ultraime.game.gdxtraime.ecran.EcranManagerAbstract;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.monde.CameraGame;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

/**
 * @author ultraime Ecran pour des test
 */
public class EcranTest extends Ecran {

	public MondeBaseService mondeService;
	public JoueurService joueurService;
	public EntiteService entiteService;

	private CameraGame cameraGame;
	private Vector2 positionSouris;

	private boolean isDispose = false;

	@Override
	public void changerEcran(InputMultiplexer inputMultiplexer) {
		inputMultiplexer.addProcessor(this);
		this.cameraGame.camera.position.x = joueurService.bodyJoueur.getPosition().x;
		this.cameraGame.camera.position.y = joueurService.bodyJoueur.getPosition().y;
	}

	@Override
	public void create(final EcranManagerAbstract ecranManager) {
		// creation des elements de gestion d'affichage
		  long startTime = System.currentTimeMillis();
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Debut");
		}
		this.ecranManager = (EcranManager) ecranManager;
		this.positionSouris = new Vector2(0, 0);
		this.batch = new SpriteBatch();
		this.cameraGame = new CameraGame(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		this.cameraGame = new CameraGame(Parametre.LARGEUR_ECRAN, Parametre.HAUTEUR_ECRAN);
		this.cameraGame.camera.position.x = 0;
		this.cameraGame.camera.position.y = 0;

		// creation du monde
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Creation ecran/camea en "+ (System.currentTimeMillis() - startTime) + " ms") ;
			startTime = System.currentTimeMillis();
		}
		this.mondeService = new MondeBaseService();
	
		// creations des murs.
		this.mondeService.initialiserCollision();
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Creation du monde en "+ (System.currentTimeMillis() - startTime) + " ms") ;
			startTime = System.currentTimeMillis();
		}
		// Creation du joueur
		final Vector2 position = this.mondeService.monde.carte.recupererPositionDepart("event", "centre");
		EntiteVivante entiteVivante = JoueurService.creerEntiteVivante(position);
		this.joueurService = new JoueurService(this.mondeService.monde.addPersonnageMuscle(entiteVivante));
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Creation du joueur en "+ (System.currentTimeMillis() - startTime) + " ms") ;
			startTime = System.currentTimeMillis();
		}
		// création des entites
		this.entiteService = new EntiteService(this.mondeService.monde);
		this.entiteService.initAlien(position);
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Creation des entite en "+ (System.currentTimeMillis() - startTime) + " ms") ;
			startTime = System.currentTimeMillis();
		}
		// creation des portes et interrupteur.
		List<MapObject> eventInterupteur = this.mondeService.monde.carte.recupererEvents("event", "interrupteur");
		List<MapObject> eventPorte = this.mondeService.monde.carte.recupererEvents("event", "porte");

		this.mondeService.creerPorte(eventInterupteur, eventPorte);
		if(Parametre.MODE_DEBUG) {
			System.out.println("chargement : Creation des events en "+ (System.currentTimeMillis() - startTime) + " ms") ;
			startTime = System.currentTimeMillis();
		}

	}

	@Override
	public void render() {
		if (!isDispose) {
			this.mondeService.render(this.joueurService, this.cameraGame);
			this.mondeService.monde.renderDebug(cameraGame.camera);
			this.entiteService.manage(this.joueurService.bodyJoueur);
		}
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.mondeService.monde.dispose();
		isDispose = true;
	}

	public void deplacementJoueur() {
		short directionX = 0;
		short directionY = 0;

		if (isTouchPressed(Input.Keys.D)) {
			directionX = 1;
		} else if (isTouchPressed(Input.Keys.Q)) {
			directionX = -1;
		}

		if (isTouchPressed(Input.Keys.Z)) {
			directionY = 1;
		} else if (isTouchPressed(Input.Keys.S)) {
			directionY = -1;
		}
		joueurService.deplacer(directionX, directionY);
	}

	@Override
	public boolean keyDown(int keycode) {
		presserTouche(keycode);
		deplacementJoueur();
		toucheEvent();
		return false;
	}

	private void toucheEvent() {
		if (isTouchPressed(Input.Keys.E)) {
			Body body = this.mondeService.getEventAcionne();
			if (body != null) {
				Evenement event = (Evenement) body.getUserData();
				if (event instanceof Interrupteur) {
					ouvertureFermeturePorte(event);
				}
			}
		}

	}

	private void ouvertureFermeturePorte(Evenement event) {
		Optional<Body> matchingObject = this.mondeService.monde.bodiesEvent.stream().filter(
				i -> ((Evenement) i.getUserData()) instanceof Porte && ((Evenement) i.getUserData()).id == event.id)
				.findFirst();
		final Body bodyPorte = matchingObject.get();
		final Porte porte = (Porte) bodyPorte.getUserData();

		final Fixture fixture = bodyPorte.getFixtureList().get(0);
		if (fixture.isSensor()) {
			fixture.setSensor(false);
			porte.fermerPorte(true);
		} else {
			fixture.setSensor(true);
			porte.fermerPorte(false);
		}

	}

	@Override
	public boolean keyUp(int keycode) {
		relacherTouche(keycode);
		deplacementJoueur();

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			joueurService.shot(screenX, screenY, cameraGame.camera, this.mondeService.monde);
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		joueurService.rotation(screenX, screenY, cameraGame.camera);
//		joueurService.rotation(screenX, screenY, this.mondeService.monde.cameraDebug);
		positionSouris.x = screenX;
		positionSouris.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.cameraGame.zoom(amount);
		this.mondeService.monde.zoomCameraDebug(amount);
		return false;
	}

	@Override
	public void resize(int width, int height) {

	}

}
