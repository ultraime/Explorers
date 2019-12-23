package com.ultraime.game.gdxtraime.composant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class ImageBouton {
	
	private static Texture btnNormal[] = new Texture[10];
	private static Texture btnOver[] = new Texture[10];
	private static Texture btnClique[] = new Texture[10];
	
	
	public static void initialisation() {
		getBtnNormal(1);
		getBtnClique(1);
		getBtnOver(1);
	}

	// pour les boutons
	public static Texture getBtnOver(final int typeElement) {
		if (btnOver[1] == null) {
			btnOver[Bouton.CLASSIQUE] = new Texture(Gdx.files.internal("images/bouton/btn_over.png"));
		}
		return btnOver[typeElement];
	}

	public static Texture getBtnClique(final int typeElement) {
		if (btnClique[1] == null) {
			btnClique[Bouton.CLASSIQUE] = new Texture(Gdx.files.internal("images/bouton/btn_clique.png"));
		}
		return btnClique[typeElement];
	}

	public static Texture getBtnNormal(final int typeElement) {
		if (btnNormal[1] == null) {
			btnNormal[Bouton.CLASSIQUE] = new Texture(Gdx.files.internal("images/bouton/btn_normal.png"));
		}
		return btnNormal[typeElement];
	}

}
