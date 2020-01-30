package com.ultraime.explorers.entite;

import com.badlogic.gdx.math.Vector2;
import com.ultraime.game.gdxtraime.entite.EntiteVivante;

public class Balle extends EntiteVivante {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector2 positionDepart;

	public Balle(float x, float y, float radius, short idDgroupe) {
		super(x, y, radius, idDgroupe);
		this.positionDepart = new Vector2(x, y);
		if (positionDepart.x < 0) {
			positionDepart.x = positionDepart.x + positionDepart.x + positionDepart.x;
		}
	}

	public boolean isPorteeAteinte(Vector2 position) {
		boolean isPorteeAteinte = false;
		isPorteeAteinte = controlisPorteeAteinte(positionDepart.x, position.x);
		if (!isPorteeAteinte) {
			isPorteeAteinte = controlisPorteeAteinte(positionDepart.y, position.y);
		}
		return isPorteeAteinte;
	}

	private boolean controlisPorteeAteinte(float Xdepart, float XYactuel) {
		boolean isPorteeAteinte = false;
		if (XYactuel < 0) {
			XYactuel = XYactuel + XYactuel + XYactuel;
		}
		float diffX = 0;
		if(Xdepart  > XYactuel) {
			diffX = Xdepart - XYactuel;
		}else {
			diffX = XYactuel -  Xdepart;
		}
		
		if (diffX> habiliter.portee) {
			isPorteeAteinte = true;
		}
		return isPorteeAteinte;
	}

}
