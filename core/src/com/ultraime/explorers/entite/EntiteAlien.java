package com.ultraime.explorers.entite;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;
import com.ultraime.game.gdxtraime.pathfinding.AetoileDestinationBlockException;
import com.ultraime.game.gdxtraime.pathfinding.AetoileException;
import com.ultraime.game.gdxtraime.pathfinding.AetoileNew;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

public class EntiteAlien extends EntitePersonnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Body> bodyEntiteJoueursDansZone = new ArrayList<Body>();

	public enum ETAT_ALIEN {
		ATTEND, CHASSE_JOUEUR
	};

	public ETAT_ALIEN etatAlien = ETAT_ALIEN.ATTEND;

	public EntiteAlien(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
	}

	public void initAEtoile(Monde monde, Body bodyEntite) {
		aetoileNew = new AetoileNew(monde, bodyEntite);
	}

	public void manage(Monde monde, Body bodyEntite) {
		if (bodyEntiteJoueursDansZone.size() > 0) {
			etatAlien = ETAT_ALIEN.CHASSE_JOUEUR;
			chasserJoueur(monde, bodyEntite);
		} else {
			etatAlien = ETAT_ALIEN.ATTEND;
			stoperEntiter(bodyEntite);
		}
	}

	public void render(final SpriteBatch batch, final float posX, final float posY) {
		if (animationBody != null) {
			switch (direction) {
			case BAS:
				this.animationBody.render(batch, posX - 10, posY, 0);
				this.animationTete.render(batch, posX - 10, posY, 0);
				break;
			case HAUT:
				this.animationBody_h.render(batch, posX - 10, posY, 0);
				this.animationTete_h.render(batch, posX - 10, posY, 0);
				break;
			case GAUCHE:
				this.animationBody_g.render(batch, posX, posY, 0);
				this.animationTete_g.render(batch, posX, posY, 0);
				break;
			case DROITE:
				this.animationBody_d.render(batch, posX - 10, posY, 0);
				this.animationTete_d.render(batch, posX - 10, posY, 0);
				break;

			default:
				this.animationBody.render(batch, posX - 10, posY, 0);
				this.animationTete.render(batch, posX - 10, posY, 0);
				break;
			}

		}
	}

	public void chasserJoueur(Monde monde, Body bodyEntite) {

		Body bodyJoueur = bodyEntiteJoueursDansZone.get(0);
		Noeud depNoeud = new Noeud((int) bodyEntite.getPosition().x, (int) bodyEntite.getPosition().y, 0);
		Noeud arrNoeud = new Noeud((int) bodyJoueur.getPosition().x, (int) bodyJoueur.getPosition().y, 0);

		if(aetoileNew.isProximite(depNoeud,arrNoeud)) {
			Vector3 targetPos = new Vector3(bodyJoueur.getPosition().x, bodyJoueur.getPosition().y, 0);
			float angle = new Vector2(targetPos.x, targetPos.y).sub(bodyEntite.getPosition()).angleRad();
			float velocity = habiliter.vitesse;
			float velX = MathUtils.cos(angle) * velocity;
			float velY = MathUtils.sin(angle) * velocity;
			bodyEntite.setLinearVelocity(velX, velY);
			cheminAParcourir.clear();
		} else {

			try {
				if (cheminAParcourir == null || (cheminAParcourir != null && cheminAParcourir.isEmpty())) {
					cheminAParcourir = aetoileNew.cheminPlusCourt(arrNoeud, depNoeud, 1000);
				}

				if (cheminAParcourir != null && !cheminAParcourir.isEmpty()) {
					Noeud way = cheminAParcourir.getFirst();
					Vector3 targetPos = new Vector3(way.x, way.y, 0);
					float angle = new Vector2(targetPos.x, targetPos.y).sub(bodyEntite.getPosition()).angleRad();
					float velocity = habiliter.vitesse;
					float velX = MathUtils.cos(angle) * velocity;
					float velY = MathUtils.sin(angle) * velocity;
					bodyEntite.setLinearVelocity(velX, velY);
					float arrondi = 0.7f;
					if (bodyEntite.getPosition().x > way.x - arrondi && bodyEntite.getPosition().x < way.x + arrondi
							&& bodyEntite.getPosition().y > way.y - arrondi
							&& bodyEntite.getPosition().y < way.y + arrondi) {
						cheminAParcourir.removeFirst();
//					cheminAParcourir = aetoileNew.cheminPlusCourt(arrNoeud, depNoeud, 1000);
					}
				}
			} catch (AetoileException e) {
				if (Parametre.MODE_DEBUG)
					e.printStackTrace();
			} catch (AetoileDestinationBlockException e) {
				if (Parametre.MODE_DEBUG)
					e.printStackTrace();
			}
		}
	}

}
