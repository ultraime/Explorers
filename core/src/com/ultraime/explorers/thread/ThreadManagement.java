package com.ultraime.explorers.thread;

import com.ultraime.game.gdxtraime.monde.Monde;

public class ThreadManagement {

	private Thread threadAlien;
	private Thread threadMonde;
	public void initAllThread(final Monde monde) {
		ThreadAlien threadAlienReal = new ThreadAlien(monde);
		threadAlien = new Thread(threadAlienReal);
		threadAlien.start();
//		
//		ThreadMonde threadMondeReal = new ThreadMonde(monde);
//		threadMonde = new Thread(threadMondeReal);
//		threadMonde.start();
	}

}
