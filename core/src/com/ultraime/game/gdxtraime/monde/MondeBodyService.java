package com.ultraime.game.gdxtraime.monde;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ultraime.game.gdxtraime.Evenement.Evenement;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

/**
 * @author Ultraime Extension de la classe Monde, uniquement pour les méthodes
 *         static lié au BODY de libgdx
 *
 */
public class MondeBodyService {
	public short GROUPE_JOUEUR = -1;
	public static short ZONE_DETECTION_ALIEN = 1;

	public MondeBodyService() {
	}

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param largeur
	 * @param hauteur
	 */
	public static void creerRectangleStatic(final World world, final float x, final float y, final float largeur,
			final float hauteur, final Object objet) {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(x, y));
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(largeur / 2, hauteur / 2);
		groundBody.createFixture(groundBox, 0.0f);
		groundBody.setUserData(objet);
		
		groundBox.dispose();

	}

	/**
	 * @param world
	 * @param entiteVivante
	 * @return Body
	 */
	public static Body creerCercleVivant(World world, final EntiteVivante entiteVivante) {
		final float posX = entiteVivante.x;
		final float posY = entiteVivante.y;
		// body 1
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(entiteVivante.cercleShape.radius);

		// fixture du body 1
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne
																// pas etre en
																// colli

		// Ajout de la fixture 1
		body.createFixture(fixtureDef);
		circle.dispose();

		body.setUserData(entiteVivante);
		return body;
	}

	/**
	 * @param world
	 * @param entiteVivante
	 * @return
	 */
	public static Body creerPersonnageMuscle(World world, final EntiteVivante entiteVivante) {
		final float posX = entiteVivante.x;
		final float posY = entiteVivante.y;

		// body 1
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(entiteVivante.cercleShape.radius);

		// fixture du body 1
		// FixtureDef fixtureDef =
		// creerFixtureDef(0.5f,0.5f,0,circle,entiteVivante.idGroup);
		final FixtureDef fixtureDef = creerFixtureDef(0.5f, 0.5f, 0.5f, circle, entiteVivante.idGroup, false);
		// Ajout de la fixture 1
		body.createFixture(fixtureDef);

		// circle2 2
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(entiteVivante.cercleShape.radius + 0.1f);
		circle2.setPosition(new Vector2(0, 0.4f));
		final FixtureDef fixtureDef2 = creerFixtureDef(0, 0, 0, circle2, entiteVivante.idGroup, true);
		// FixtureDef fixtureDef2 = creerFixtureDef(entiteVivante, circle2);

		// Ajout de la fixture 12
		body.createFixture(fixtureDef2);

		// circle2 3
		CircleShape circle3 = new CircleShape();
		circle3.setRadius(entiteVivante.cercleShape.radius - 0.1f);
		circle3.setPosition(new Vector2(0, 0.4f + 0.6f));
		// fixture 3
		final FixtureDef fixtureDef3 = creerFixtureDef(0, 0, 0, circle3, entiteVivante.idGroup, true);
		// Ajout de la fixture 12
		body.createFixture(fixtureDef3);

		body.setFixedRotation(true);
		body.setUserData(entiteVivante);
		circle3.dispose();
		circle2.dispose();
		circle.dispose();
		return body;
	}

	/**
	 * @param world
	 * @param entiteVivante
	 * @return
	 */
	public static Body creerAlienMuscle(World world, final EntiteVivante entiteVivante) {
		Body body = creerPersonnageMuscle(world, entiteVivante);
		// zone de recherche
		float zoneDeRecherche = 6f;
		 CircleShape circle4 = new CircleShape();
		 circle4.setRadius(zoneDeRecherche);
		 circle4.setPosition(new Vector2(0,0));

		// fixture 4
		 FixtureDef fixtureDef4 =  creerFixtureDef(-1f,0,0,circle4,ZONE_DETECTION_ALIEN,true);
		// Ajout de la fixture 4
		 body.createFixture(fixtureDef4);
		body.setFixedRotation(true);

		body.setUserData(entiteVivante);
		 circle4.dispose();
		return body;
	}

	private static FixtureDef creerFixtureDef(final float density, final float friction, final float restitution,
			final CircleShape circle, short group, boolean isSensor) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = isSensor;
		fixtureDef.shape = circle;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		fixtureDef.filter.groupIndex = group;
		return fixtureDef;
	}

	public static Body creerCercleBullet(World world, final EntiteVivante entiteVivante) {
		final float posX = entiteVivante.x;
		final float posY = entiteVivante.y;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(entiteVivante.cercleShape.radius);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		// pour le rebond
		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne
																// pas etre en
																// colli
		body.createFixture(fixtureDef);
		body.setUserData(entiteVivante);
		return body;
	}

	public static Body creerEvent(final World world, final Evenement event) {
		BodyDef groundBodyDef = new BodyDef();
		final Vector2 position = new Vector2(event.position.x/ Monde.MULTIPLICATEUR +0.5f, event.position.y/ Monde.MULTIPLICATEUR + 0.5f);
		groundBodyDef.position.set(position);
		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
//		groundBox.setAsBox(0.5f,1f);
		groundBox.setAsBox((event.largeur/ Monde.MULTIPLICATEUR) / 2,( event.hauteur/ Monde.MULTIPLICATEUR) / 2);
		Fixture fixture = groundBody.createFixture(groundBox, 0.0f);
		fixture.setSensor(true);
		groundBody.setUserData(event);
		
		groundBox.dispose();
		return groundBody;
	}
}
