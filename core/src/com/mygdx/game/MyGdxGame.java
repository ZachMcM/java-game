package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture bulletImg;
	Player player;

	Alien[] aliens;
	int alienWidth = 10;
	int alienHeight = 3;
	int alienSpacing = 40;
	int minXAliens;
	int minYAliens;
	int maxXAliens;
	int maxYAliens;
	Texture alienImg;
	Vector2 offsetAliens;
	int directionAliens = 1;
	float alienSpeed = 100;
	
	@Override
	public void create () {
		offsetAliens = Vector2.Zero;
		batch = new SpriteBatch();
		img = new Texture("Player.png");
		bulletImg = new Texture("Bullet.png");
		player = new Player(img, bulletImg);

		alienImg = new Texture("Alien.png");
		aliens = new Alien[alienWidth * alienHeight];
		int i = 0;
		for (int y = 0; y < alienHeight; y++) {
			for (int x = 0; x < alienWidth; x++) {
				Vector2 position = new Vector2(x * alienSpacing, y * alienSpacing);
				position.x += (Gdx.graphics.getWidth() / 2);
				position.y += Gdx.graphics.getHeight() - 100;

				position.x -= (alienWidth / 2) * alienSpacing;
				position.y -= (alienHeight) * alienSpacing;
				aliens[i] = new Alien(position, alienImg);
				i++;
			}
		}
	}
	int aliveAlienCount = 0;
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		player.Draw(batch);

		for (int i = 0; i < aliens.length; i++) {
			if (aliens[i].alive) {
				if (player.bulletSprite.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle())) {
					player.bulletPosition.y = 1000;
					aliens[i].alive = false;
					break;
				}
			}
		}

		minXAliens = 10000;
		minYAliens = 10000;
		maxXAliens = 0;
		maxYAliens = 0;

		for (int i = 0; i < aliens.length; i++) {
			if (aliens[i].alive) {
				int indexX = i % alienWidth;
				int indexY = i / alienWidth;
	
				if (indexX > maxXAliens) {
					maxXAliens = indexX;
				}
				if (indexX < minXAliens) {
					minXAliens = indexX;
				}
				if (indexY > maxYAliens) {
					maxYAliens = indexY;
				}
				if (indexY < minYAliens) {
					minYAliens = indexY;
				}
				aliveAlienCount++;
			}
		}
		if (aliveAlienCount == 0) {
			for (int i = 0; i < aliens.length; i++) {
				aliens[i].alive = true;
			}
			offsetAliens = new Vector2(0, 0);
			batch.end();
			return;
		}

		offsetAliens.x += directionAliens * deltaTime * alienSpeed;

		if (aliens[maxXAliens].position.x >= Gdx.graphics.getWidth() - 110) {
			directionAliens = -1;
			offsetAliens.y -= aliens[0].sprite.getHeight() * aliens[0].sprite.getScaleY() * 0.25f;
			alienSpeed += 3;
		}

		if (aliens[minXAliens].position.x <= -100) {
			directionAliens = 1;
			offsetAliens.y -= aliens[0].sprite.getHeight() * aliens[0].sprite.getScaleY() * 0.25f;
			alienSpeed += 3;
		}

		if (aliens[minYAliens].position.y <= -10) {
			Gdx.app.exit();
		}

		for (int i = 0; i < aliens.length; i++) {
			aliens[i].position = new Vector2(aliens[i].initPosition.x + offsetAliens.x, aliens[i].initPosition.y + offsetAliens.y);
			if (aliens[i].alive) {
				aliens[i].Draw(batch);
				if (aliens[i].sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
					Gdx.app.exit();
				}
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
