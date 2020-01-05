package com.ultraime.game.gdxtraime.monde;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

/**
 * @author Ultraime Extension de la classe Monde, uniquement pour les méthodes
 *         static lié au BODY de libgdx
 *
 */
public class MondeBodyService {

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

	}
	/**
	 * @param world
	 * @param entiteVivante
	 * @return Body
	 */
	public static Body creerCercleVivant(World world, final EntiteVivante entiteVivante) {
		final float posX = entiteVivante.x;
		final float posY = entiteVivante.y;
		//body 1
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(entiteVivante.cercleShape.radius);
		
		//fixture du body 1
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne pas etre en colli
		
		//Ajout de la fixture 1 
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
		
		//body 1
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(entiteVivante.cercleShape.radius);	
		//fixture du body 1
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne pas etre en colli
		
		//Ajout de la fixture 1 
		body.createFixture(fixtureDef);
		
		
		//circle2 2
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(entiteVivante.cercleShape.radius + 0.1f);
		circle2.setPosition(new Vector2(0,0.4f));
		//fixture  2
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.isSensor = true;
		fixtureDef2.shape = circle2;
		fixtureDef2.density = 0.0f;
		fixtureDef2.friction = 0.0f;
		fixtureDef2.restitution = 0.0f;
		fixtureDef2.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne pas etre en colli
		
		//Ajout de la fixture 12
		body.createFixture(fixtureDef2);
		
		//circle2 3
		CircleShape circle3 = new CircleShape();
		circle3.setRadius(entiteVivante.cercleShape.radius - 0.1f);
		circle3.setPosition(new Vector2(0,0.4f+0.6f));
		//fixture  3
		FixtureDef fixtureDef3 = new FixtureDef();
		fixtureDef3.isSensor = true;
		fixtureDef3.shape = circle3;
		fixtureDef3.density = 0.0f;
		fixtureDef3.friction = 0.0f;
		fixtureDef3.restitution = 0.0f;
		fixtureDef3.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne pas etre en colli
		
		//Ajout de la fixture 12
		body.createFixture(fixtureDef3);
		
		body.setFixedRotation(true);
		body.setUserData(entiteVivante);
		circle3.dispose();
		circle2.dispose();
		circle.dispose();
		return body;
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
		fixtureDef.filter.groupIndex = entiteVivante.idGroup;// pour entite a ne pas etre en colli
		body.createFixture(fixtureDef);
		body.setUserData(entiteVivante);
		return body;
	}
}
