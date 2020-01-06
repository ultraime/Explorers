package com.ultraime.explorers.entite;

import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

public class EntitePersonnage extends EntiteVivante {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
		this.animationBody = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/body.png");
		this.animationBody_h = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/body_h.png");
		this.animationBody_g = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/body_g.png");
		this.animationBody_d = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/body_d.png");

		this.animationTete = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/tete.png");
		this.animationTete_h = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/tete_h.png");
		this.animationTete_g = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/tete_g.png");
		this.animationTete_d = new AnimationManager(80, 128, 1f, "personnage/astro_muscle/tete_d.png");

	}
	
	

}
