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
	public float vitesse = 10f;// 5f
	public float acceleration = 10f;
	public float saut = 270f;
	
	//stat non obligatoire
	public int degat = 1;
	
	//la portée
	public float portee = 10f;
	
	//stat modulable
	public int sante[];
	public int energie[];
	public int oxygene[];
	public int combustible[];
	
	
	public HabiliterGeneral() {
		sante = new int[3];
		energie = new int[3];
		oxygene =  new int[3];
		combustible =  new int[3];
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
		
		oxygene[GAIN] = new Integer(habiliter.oxygene[GAIN]);
		oxygene[ACTUEL] = new Integer(habiliter.oxygene[ACTUEL]);
		oxygene[MAX] = new Integer(habiliter.oxygene[MAX]);
		
		combustible[GAIN] = new Integer(habiliter.combustible[GAIN]);
		combustible[ACTUEL] = new Integer(habiliter.combustible[ACTUEL]);
		combustible[MAX] = new Integer(habiliter.combustible[MAX]);
		
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
