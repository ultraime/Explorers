package com.ultraime.explorers.entite;

import java.util.ArrayDeque;

import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.animation.ImageRef;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;
import com.ultraime.game.gdxtraime.pathfinding.AetoileNew;
import com.ultraime.game.gdxtraime.pathfinding.Noeud;

public class EntitePersonnage extends EntiteVivante {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayDeque<Noeud> cheminAParcourir;
	public AetoileNew aetoileNew;
	
	
	protected AnimationManager animationBody;
	protected AnimationManager animationBody_h;
	protected AnimationManager animationBody_g;
	protected AnimationManager animationBody_d;

	protected AnimationManager animationTete;
	protected AnimationManager animationTete_h;
	protected AnimationManager animationTete_g;
	protected AnimationManager animationTete_d;

	public EntitePersonnage(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
		this.animationBody = new AnimationManager(80, 128, 1f, ImageRef.animationBody);
		this.animationBody_h = new AnimationManager(80, 128, 1f, ImageRef.animationBody_h);
		this.animationBody_g = new AnimationManager(80, 128, 1f, ImageRef.animationBody_g);
		this.animationBody_d = new AnimationManager(80, 128, 1f, ImageRef.animationBody_d);

		this.animationTete = new AnimationManager(80, 128, 1f, ImageRef.animationTete);
		this.animationTete_h = new AnimationManager(80, 128, 1f, ImageRef.animationTete_h);
		this.animationTete_g = new AnimationManager(80, 128, 1f, ImageRef.animationTete_g);
		this.animationTete_d = new AnimationManager(80, 128, 1f, ImageRef.animationTete_d);

	}

	public void stoperEntiter(Body bodyEntite) {
		bodyEntite.setLinearVelocity(0, 0);
		if(cheminAParcourir != null) {
			cheminAParcourir.clear();
		}
	}

}
