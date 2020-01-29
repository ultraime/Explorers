package com.ultraime.explorers.objet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ultraime.game.gdxtraime.animation.AnimationManager;
import com.ultraime.game.gdxtraime.objet.Arme;

public class ArmesManager {

	public static final Arme pistolet = Arme.creerArme("Pistolet",
			new Sprite(new Texture(Gdx.files.internal("armes/pistolet.png"))),
			new AnimationManager(50, 45, 0.05f, "armes/pistolet_tir_anime.png"));

	public static Arme creerPistolet() {
		pistolet.habiliterGeneral.degat = 1;
		pistolet.habiliterGeneral.vitesse = 10f;
		return new Arme(pistolet);
	}
}
