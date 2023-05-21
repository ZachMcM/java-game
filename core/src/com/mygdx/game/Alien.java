package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Alien {
    public Vector2 position;
    public Sprite sprite;
    public boolean alive = true;
    Vector2 initPosition;

    Alien(Vector2 position, Texture img) {
        this.position = position;
        initPosition = this.position;
        sprite = new Sprite(img);
        sprite.setScale(0.15f);
    }

    public void Draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
