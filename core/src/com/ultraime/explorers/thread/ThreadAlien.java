package com.ultraime.explorers.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.ultraime.explorers.entite.EntiteAlien;
import com.ultraime.game.gdxtraime.monde.Monde;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class ThreadAlien implements Runnable {
	public Monde monde;
	public ArrayList<Body> bodiesEntiteVivante;

	public ThreadAlien(final Monde monde) {
		this.monde = monde;
		this.bodiesEntiteVivante = monde.bodiesEntiteVivant;
	}

	@Override
	public void run() {
		do {
			if (!Parametre.PAUSE) {
				List<Body> aliens = bodiesEntiteVivante.stream().filter(b -> (b.getUserData() instanceof EntiteAlien))
						.collect(Collectors.toList());
				aliens.stream().forEach(a -> ((EntiteAlien) a.getUserData()).manage(monde, a));
			}
		} while (Gdx.app != null);

	}

}
