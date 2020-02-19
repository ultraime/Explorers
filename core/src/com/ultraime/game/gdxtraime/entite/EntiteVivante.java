package com.ultraime.game.gdxtraime.entite;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.entite.metier.ActionEntite;
import com.ultraime.game.gdxtraime.entite.metier.HabiliterGeneral;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

public class EntiteVivante extends Entite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient public static final int BAS = 0;
	transient public static final int GAUCHE = 1;
	transient public static final int DROITE = 2;
	transient public static final int HAUT = 3;

	public static enum TypeShape {
		CERCLE, RECTANGLE
	}

	// Le type de l'entite
	public TypeShape typeShape;
	public Circle cercleShape;
	public short idGroup = 0;

	// pour l'animation
	protected AnimationManager animationManager;

	// pour le deplacement
	private ArrayDeque<Noeud> listeDeNoeudDeplacement;
	private List<ActionEntite> listeAction;

	// élément propre au entité
	public HabiliterGeneral habiliter;

	// etat de l'entite
	public enum Etat {
		NORMAL, COURS, SAUTE, TOMBE;
	}

	public enum Direction {
		HAUT, BAS, GAUCHE, DROITE, AUCUNE
	}

	public Etat etat = Etat.NORMAL;
	public Direction direction = Direction.AUCUNE;

	// Autre
	public String prenom = "Prenom par defaut";

	/**
	 * @param x
	 * @param y
	 * @param radius
	 * @param idDgroupe
	 */
	public EntiteVivante(final float x, final float y, final float radius, final short idDgroupe) {
		super(x, y);
		this.listeDeNoeudDeplacement = new ArrayDeque<Noeud>();
		this.listeAction = new ArrayList<ActionEntite>();
		this.typeShape = TypeShape.CERCLE;
		this.cercleShape = new Circle(x, y, radius);
		this.habiliter = new HabiliterGeneral();
		this.idGroup = idDgroupe;
	}

	/**
	 * @param batch
	 */
	public void render(final SpriteBatch batch) {
		if (animationManager != null) {
			this.animationManager.render(batch, this.x,this.y, 0);
		}
	}

	public List<ActionEntite> getListeAction() {
		return listeAction;
	}

	public void setListeAction(List<ActionEntite> listeAction) {
		this.listeAction = listeAction;
	}

	public void ajouterAction(ActionEntite actionEntite) {
		listeAction.add(actionEntite);

	}

	public ArrayDeque<Noeud> getListeDeNoeudDeplacement() {
		return listeDeNoeudDeplacement;
	}

	public void setListeDeNoeudDeplacement(ArrayDeque<Noeud> listeDeNoeudDeplacement) {
		this.listeDeNoeudDeplacement = listeDeNoeudDeplacement;
	}

	public Circle getCercleShape() {
		return cercleShape;
	}

	public void setCercleShape(Circle cercleShape) {
		this.cercleShape = cercleShape;
	}

}
