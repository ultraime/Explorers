package com.ultraime.game.gdxtraime.monde;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
	static final float STEP_TIME = 1f / 300f;
	float accumulator = 0;
	// Le vrai monde, avec les stats normal (petit nombre)
	public World world;
	public float gravite = -50f;

	// la carte du monde
	public Carte carte;

	// entite Body dans le monde.
	public ArrayList<Body> bodiesEntiteVivant = new ArrayList<Body>();
	public ArrayList<Body> bodiesBullets = new ArrayList<Body>();
	// les entites static sont une liste de body + rectangle
	private List<Rectangle> rectangleBodies;

	public Box2DDebugRenderer debugRenderer;
	OrthographicCamera cameraDebug = new OrthographicCamera(Parametre.LARGEUR_ECRAN / MULTIPLICATEUR,
			Parametre.HAUTEUR_ECRAN / MULTIPLICATEUR);

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
		this.debugRenderer = new Box2DDebugRenderer();
		this.batch = new SpriteBatch();
		this.rectangleBodies = new ArrayList<>();

	}

	public void render() {
		if (carte != null) {
			carte.render();
		}
		if (!Parametre.PAUSE) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			float frameTime = Math.min(deltaTime, 0.25f);
			accumulator += frameTime;
			while (accumulator >= STEP_TIME) {
				world.step(STEP_TIME, 6, 2);
				accumulator -= STEP_TIME;
			}
		}
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

		Rectangle rectangle = new Rectangle(x, y, entiteStatic.getLargeur() / 2f, entiteStatic.getHauteur() / 2f);
		rectangleBodies.add(rectangle);
	}

	/**
	 * Ajoute une entité au monde. Retourne le body de l'entité crée
	 * 
	 * @param entiteVivante
	 * @param radius        0.4f
	 * @return
	 */
	public Body addEntiteVivante(final EntiteVivante entiteVivante, final float radius) {
		final float posx = entiteVivante.x;
		final float posy = entiteVivante.y;
		Body body = MondeBodyService.creerCercleVivant(world, radius, posx, posy, entiteVivante);
		bodiesEntiteVivant.add(body);
		return body;
	}

	/**
	 * 
	 * Ajoute une bullet au monde. Retourne le body de la bullet cr�e
	 * 
	 * @param entiteVivante
	 * @param radius        0.4f
	 * @return
	 */
	public Body addBullet(final EntiteVivante entiteVivante, final float radius) {
		final float posx = entiteVivante.x;
		final float posy = entiteVivante.y;
		Body body = MondeBodyService.creerCercleVivant(world, radius, posx, posy, entiteVivante);
		bodiesBullets.add(body);
		return body;
	}

	private void gestionBodies() {
		ArrayList<Body> bodies = bodiesEntiteVivant;
		for (int i = 0; i < bodies.size(); i++) {
			final Body body = bodies.get(i);
			final EntiteVivante entiteVivante = (EntiteVivante) body.getUserData();
			final float x = body.getPosition().x * MULTIPLICATEUR;
			final float y = body.getPosition().y * MULTIPLICATEUR;
			entiteVivante.render(batch, x, y);
		}

	}

	/**
	 * @param OrthographicCamera camera
	 */
	public void renderDebug(final OrthographicCamera camera) {
		if (Parametre.MODE_DEBUG) {
			try {
				this.debugRenderer.render(world, cameraDebug.combined);
//				this.debugRenderer.render(worldAffichage, camera.combined);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeEntiteStatic(final Body body, final ArrayList<Body> arrayBody) {

		if (body.getUserData() instanceof EntiteStatic) {
			final float posX = body.getPosition().x;
			final float posY = body.getPosition().y;
			for (Rectangle rect : rectangleBodies) {
				if (rect.x == posX && rect.y == posY) {
					rectangleBodies.remove(rect);
					break;
				}
			}
		}
		arrayBody.remove(body);
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
		world.QueryAABB(callback, point.x - 0.1f, point.y - 0.1f, point.x + 0.1f, point.y + 0.1f);
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
		this.debugRenderer.dispose();
		this.carte.dispose();
	}

}
