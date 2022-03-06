package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.extra.RobotTexture;

public class MainGame extends Game {

	public AssetManager manager;
	public RobotTexture assetManger;

	public  GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public GameMenuScreen gameMenuScreen;
	public GameWinScreen gameWinScreen;

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {
		this.manager = new AssetManager();
		this.assetManger = new RobotTexture();

		manager.load("suelo.png",Texture.class);
		manager.load("overfloor.png",Texture.class);
		manager.load("fondoEdificios.jpg",Texture.class);
		manager.load("misil.png",Texture.class);
		manager.load("temazo.mp3", Music.class);
		manager.load("gameover.png",Texture.class);
		manager.load("win.png",Texture.class);

		manager.finishLoading();

		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		gameMenuScreen = new GameMenuScreen(this);
		gameWinScreen = new GameWinScreen(this);

		setScreen(new GameOverScreen(this));
		setScreen(new GameMenuScreen(this));
		setScreen(new GameWinScreen(this));

		setScreen(gameMenuScreen);
	}
}
