package com.ultraime.game.gdxtraime.entite.metier;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

/**
 * @author Ultraime implements Comparable<ActionEntite>
 */
public abstract class ActionEntite implements Comparable<ActionEntite>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Monde monde;
	private int priorite;

	public abstract void initAction(World world, Body body);

	/**
	 * @param monde
	 * @param priorite
	 */
	public ActionEntite(final Monde monde, final int priorite) {
		this.monde = monde;
		this.priorite = priorite;
	}

	/**
	 * @param xDestination
	 * @param yDestination
	 * @param monde
	 * @param priorite
	 */
	public ActionEntite(final int xDestination, final int yDestination, final Monde monde, final int priorite) {
		this(monde, priorite);

	}

	/**
	 * @param entiteVivante
	 * @param world
	 * @param worldAffichage
	 */
	public abstract boolean doAction(final Body body, final World world, final World worldAffichage);

	/**
	 * @param body
	 * @param worldAffichage
	 * @return true si déplacement fini
	 */
	protected boolean doDeplacement(final Body body, final World worldAffichage) {
		boolean isDeplacementEnd = true;
		int direction = -1;
		if (body.getUserData() instanceof EntiteVivante) {
			final EntiteVivante ev = (EntiteVivante) body.getUserData();
			final ArrayDeque<Noeud> noeudDeplacements = ev.getListeDeNoeudDeplacement();
			if (noeudDeplacements.size() > 0) {
				isDeplacementEnd = false;
				final float valeurArrondi = 0.05f * Parametre.VITESSE_DE_JEU;
				final Noeud noeuddeplacementencours = noeudDeplacements.getFirst();
				final float posX = body.getPosition().x;
				final float posY = body.getPosition().y;
				float xVelocity = 0;
				float yVelocity = 0;
				float vitesse = ev.habiliter.vitesse * Parametre.VITESSE_DE_JEU;
				if (posX + valeurArrondi < noeuddeplacementencours.x) {
					xVelocity = vitesse;
					direction = EntiteVivante.DROITE;
				} else if (posX - valeurArrondi > noeuddeplacementencours.x) {
					xVelocity = -vitesse;
					direction = EntiteVivante.GAUCHE;
				}
				if (posY + valeurArrondi < noeuddeplacementencours.y) {
					yVelocity = vitesse;
					direction = EntiteVivante.HAUT;
				} else if (posY - valeurArrondi > noeuddeplacementencours.y) {
					yVelocity = -vitesse;
					direction = EntiteVivante.BAS;
				}
				float xDiff = posX - noeuddeplacementencours.x;
				float yDiff = posY - noeuddeplacementencours.y;

				body.setLinearVelocity(xVelocity, yVelocity);

				if (xVelocity == 0 && yVelocity == 0 || xDiff < valeurArrondi && xDiff > -valeurArrondi
						&& yDiff < valeurArrondi && yDiff > -valeurArrondi) {
					noeudDeplacements.removeFirst();
					if (noeudDeplacements.size() == 0) {
						body.setLinearVelocity(0, 0);
						isDeplacementEnd = true;
					}
				}
			}
		}
		try {
			ArrayList<Body> bodiesAffichage = monde.bodiesEntiteVivant;
			final EntiteVivante ev = (EntiteVivante) body.getUserData();
			for (int i = 0; i < bodiesAffichage.size(); i++) {
				final Body bodyAfficher = bodiesAffichage.get(i);
				if (bodyAfficher.getUserData() instanceof EntiteVivante) {
					final EntiteVivante evAfficher = (EntiteVivante) bodyAfficher.getUserData();
					if (evAfficher.id == ev.id) {
						bodyAfficher.setTransform(body.getPosition().x , body.getPosition().y , 0);
						evAfficher.x = body.getPosition().x;
						evAfficher.y = body.getPosition().y;
						if (direction != -1) {
							//TODO direction à set
//							evAfficher.setDirection(direction);
						}
						break;
					}
				}
			}
		} catch (GdxRuntimeException e) {
			if (Parametre.MODE_DEBUG) {
				e.printStackTrace();
			}
			doDeplacement(body, worldAffichage);
		}
		return isDeplacementEnd;
	}

	@Override
	public int compareTo(ActionEntite o) {
		int retour = 0;
		if (this.priorite < o.priorite) {
			retour = -1;
		} else if (this.priorite < o.priorite) {
			retour = 1;
		}
		return retour;
	}

}
