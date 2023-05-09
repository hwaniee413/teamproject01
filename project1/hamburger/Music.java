package project1.hamburger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javazoom.jl.player.Player;

public class Music extends Thread {
	private Player player;
	private boolean isLoop;
	private File file;
	private InputStream is;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedReader br;
	
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			
			File file = new File(name);
			is = this.getClass().getClassLoader().getResourceAsStream("src/music/" + name);
			
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			//br = new BufferedReader(new InputStreamReader(is));
			player = new Player(bis);
		}catch(Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	public int getTime() {
		if(player == null)
			return 0;
		return player.getPosition();
	}
	
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt();
	}
	
	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			}while(isLoop);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
}
