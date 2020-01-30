package com.ultraime.game.gdxtraime.entite.metier;

import java.io.Serializable;

public class HabiliterGeneral implements Serializable {

	public transient static final int GAIN = 0;
	public transient static final int ACTUEL = 1;
	public transient static final int MAX = 2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// stat de base
	public float vitesse = 5f;// 3f
	public float acceleration = 10f;
	public float saut = 270f;
	
	//stat non obligatoire
	public int degat = 1;
	
	//la portée
	public float portee = 10f;
	
	//stat modulable
	public int sante[];
	public int energie[];

	public HabiliterGeneral() {
		sante = new int[3];
		energie = new int[3];
	}

	public HabiliterGeneral(final HabiliterGeneral habiliter) {
		this();
		initStatAvecHabiliter(habiliter);

	}

	private void initStatAvecHabiliter(final HabiliterGeneral habiliter) {
		sante[GAIN] = new Integer(habiliter.sante[GAIN]);
		sante[ACTUEL] = new Integer(habiliter.sante[ACTUEL]);
		sante[MAX] = new Integer(habiliter.sante[MAX]);

		energie[GAIN] = new Integer(habiliter.energie[GAIN]);
		energie[ACTUEL] = new Integer(habiliter.energie[ACTUEL]);
		energie[MAX] = new Integer(habiliter.energie[MAX]);
		
		vitesse = habiliter.vitesse;
		degat = habiliter.degat;
		saut = habiliter.saut;
		acceleration = habiliter.acceleration;
		portee = habiliter.portee;
	}

	public void gererGain(int[] stat) {
		stat[ACTUEL] = stat[ACTUEL] + stat[GAIN];
		if (stat[ACTUEL] < 0) {
			stat[ACTUEL] = 0;
		} else if (stat[ACTUEL] > stat[MAX]) {
			stat[ACTUEL] = stat[MAX];
		}
	}

}
