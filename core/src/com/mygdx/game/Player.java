package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 500;

    public Vector2 bulletPosition;
    public Sprite bulletSprite;
    public float bulletSpeed = 1000;


    Player(Texture img, Texture bulletImg) {
        sprite = new Sprite(img);
        sprite.setScale(2);
        position = new Vector2(Gdx.graphics.getWidth() / 2, sprite.getScaleY() * sprite.getHeight() / 2);

        bulletSprite = new Sprite(bulletImg);
        bulletSprite.setScale(0.25f);
        bulletPosition = new Vector2(0, 10000);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && bulletPosition.y > Gdx.graphics.getHeight()) {
            bulletPosition.x = position.x - 11;
            bulletPosition.y = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            position.x -= deltaTime * speed;
        }

        if (Gdx.input.isKeyPressed(Keys.D)) {
            position.x += deltaTime * speed;
        }

        if (position.x - (sprite.getWidth() * sprite.getScaleX() / 2) <= 0) {
            position.x = sprite.getWidth() * sprite.getScaleX() / 2;
        }

        if (position.x + (sprite.getWidth() * sprite.getScaleX() / 2) >= Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth() - (sprite.getWidth() * sprite.getScaleX() / 2);
        }

        bulletPosition.y += deltaTime * bulletSpeed;
    }

    public void Draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

        bulletSprite.setPosition(bulletPosition.x, bulletPosition.y);
        bulletSprite.draw(batch);
    }
}
