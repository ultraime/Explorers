package com.ultraime.game.gdxtraime.monde;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.ultraime.game.gdxtraime.carte.Carte;
import com.ultraime.game.gdxtraime.entite.EntiteStatic;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class Monde {

	// Le vrai monde, avec les stats normal (petit nombre)
	public World world;
	public float gravite = -50f;

	// la carte du monde
	public Carte carte;

	// entite Body dans le monde.
	public ArrayList<Body> bodiesEntiteVivant = new ArrayList<Body>();

	// les entites static sont une liste de body + rectangle
	private List<Rectangle> rectangleBodies;

	// World utilisé surtout pour le debug
	public World worldAffichage;
	public ArrayList<Body> bodiesAffichageEntiteVivant = new ArrayList<Body>();
	public Box2DDebugRenderer debugRenderer;

	// affichage
	private SpriteBatch batch;

	/**
	 * correspond à la taille d'un tile. Par défaut, le multiplicateur = 64
	 */
	public static int MULTIPLICATEUR = 64;

	private Body bodyTouche;

	// private int tempsAnimation = 0;

	public Monde(final Carte carte, final float gravite) {
		this(gravite);
		this.carte = carte;
	}

	public Monde(final float gravite) {
		this.gravite = gravite;
		world = new World(new Vector2(0, gravite), true);
		worldAffichage = new World(new Vector2(0, 0), true);
		this.debugRenderer = new Box2DDebugRenderer();
		this.batch = new SpriteBatch();
		this.rectangleBodies = new ArrayList<>();

	}

	public void render() {
		if (carte != null) {
			carte.render();
		}
		if (!Parametre.PAUSE) {
			world.step(1 / 60f, 6, 2);
			worldAffichage.step(1 / 60f, 6, 2);
		}
		// tempsAnimation += Gdx.graphics.getDeltaTime();
		this.batch.begin();
		gestionBodies();

		this.batch.end();
	}

	public Body recupererBodyFromEntite(final EntiteVivante entiteVivante) {
		Body retourBody = null;
		ArrayList<Body> bodies = bodiesEntiteVivant;
		for (int i = 0; i < bodies.size(); i++) {
			final Body body = bodies.get(i);
			if (body.getUserData() instanceof EntiteVivante) {
				final EntiteVivante ev = (EntiteVivante) body.getUserData();
				if (ev == entiteVivante) {
					retourBody = body;
					break;
				}
			}
		}
		return retourBody;
	}

	public void addEntiteStatic(EntiteStatic entiteStatic) {
		final float x = entiteStatic.x;
		final float y = entiteStatic.y;
		final float largeur = entiteStatic.getLargeur();
		final float hauteur = entiteStatic.getHauteur();
		MondeBodyService.creerRectangleStatic(world, x, y, largeur, hauteur, entiteStatic);

		MondeBodyService.creerRectangleStatic(worldAffichage, x * MULTIPLICATEUR + 32, y * MULTIPLICATEUR + 32,
				largeur * MULTIPLICATEUR, hauteur * MULTIPLICATEUR, entiteStatic);

		Rectangle rectangle = new Rectangle(x, y, entiteStatic.getLargeur() / 2f, entiteStatic.getHauteur() / 2f);
		rectangleBodies.add(rectangle);
	}

	/**
	 * Ajoute une entité au monde. Retourne le body de l'entité crée
	 * 
	 * @param entiteVivante
	 * @return Body body
	 */
	public Body addEntiteVivante(final EntiteVivante entiteVivante) {
		final float radius = 0.4f;
		final float posx = entiteVivante.x;
		final float posy = entiteVivante.y;

		Body body = MondeBodyService.creerCercleVivant(world, radius, posx, posy, entiteVivante);
		Body bodyAffichage = MondeBodyService.creerCercleVivant(worldAffichage, MULTIPLICATEUR * radius,
				posx * MULTIPLICATEUR + 32, posy * MULTIPLICATEUR + 32, entiteVivante);

		bodiesEntiteVivant.add(body);
		bodiesAffichageEntiteVivant.add(bodyAffichage);

		return body;
	}

	private void gestionBodies() {
		ArrayList<Body> bodies = bodiesEntiteVivant;
		for (int i = 0; i < bodies.size(); i++) {
			final Body body = bodies.get(i);
			final EntiteVivante entiteVivante = (EntiteVivante) body.getUserData();
			final float x = body.getPosition().x * MULTIPLICATEUR;
			final float y = body.getPosition().y * MULTIPLICATEUR;

			final Body bodyAffichage = bodiesAffichageEntiteVivant.get(i);
			bodyAffichage.setTransform(body.getPosition().x * MULTIPLICATEUR + (MULTIPLICATEUR / 2),
					body.getPosition().y * MULTIPLICATEUR + (MULTIPLICATEUR / 2), 0);
			// bodyAffichage.getPosition().x = body.getPosition().x;
			// bodyAffichage.getPosition().y = body.getPosition().y;

			entiteVivante.render(batch, x, y);
		}

	}

	/**
	 * @param OrthographicCamera camera
	 */
	public void renderDebug(final OrthographicCamera camera) {
		if (Parametre.MODE_DEBUG) {
			try {
				this.debugRenderer.render(world, camera.combined);
				this.debugRenderer.render(worldAffichage, camera.combined);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeEntiteStatic(int posX, int posY) {
		try {
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (Body body : bodies) {
				if (posX == body.getPosition().x && posY == body.getPosition().y) {
					world.destroyBody(body);
					bodiesEntiteVivant.remove(body);
					break;
				}
			}
			worldAffichage.getBodies(bodies);
			for (Body body : bodies) {
				if (posX * MULTIPLICATEUR + 32 == body.getPosition().x
						&& posY * MULTIPLICATEUR + 32 == body.getPosition().y) {
					worldAffichage.destroyBody(body);
					break;
				}
			}
			for (Rectangle rect : rectangleBodies) {
				if (rect.x == posX && rect.y == posY) {
					rectangleBodies.remove(rect);
					break;
				}
			}
		} catch (GdxRuntimeException e) {
			if (Parametre.MODE_DEBUG) {
				e.printStackTrace();
			}
			removeEntiteStatic(posX, posY);
		}
	}

	/**
	 * @param screenX
	 * @param screenY
	 * @param pointer
	 * @param button
	 * @param OrthographicCamera
	 * @return
	 */
	public Body touchDown(final int screenX, final int screenY, final int pointer, final int button,
			final OrthographicCamera camera) {
		Body bodyToucheAretourner = null;
		final Vector3 point = new Vector3();
		point.set(screenX, screenY, 0);
		camera.unproject(point);

		QueryCallback callback = new QueryCallback() {
			@Override
			public boolean reportFixture(Fixture fixture) {
				if (fixture.testPoint(point.x, point.y)) {
					bodyTouche = fixture.getBody();
					return false;
				} else {
					return true;
				}

			}
		};
		worldAffichage.QueryAABB(callback, point.x - 0.1f, point.y - 0.1f, point.x + 0.1f, point.y + 0.1f);
		if (bodyTouche != null) {
			bodyToucheAretourner = bodyTouche;
			bodyTouche = null;
		}
		return bodyToucheAretourner;
	}

	public void updateCamera(OrthographicCamera camera) {
		batch.setProjectionMatrix(camera.combined);
		carte.updateCamera(camera);
	}

	public void dispose() {
		this.batch.dispose();
		this.world.dispose();
		this.worldAffichage.dispose();
		this.debugRenderer.dispose();
		this.carte.dispose();
	}

}
