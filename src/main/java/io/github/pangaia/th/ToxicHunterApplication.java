package io.github.pangaia.th;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ToxicHunterApplication extends GameApplication {
    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Toxic Hunter");
        settings.setVersion("0.1");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        int maxX = FXGL.getAppWidth();
        int maxY = FXGL.getAppHeight();

        player = FXGL.entityBuilder()
                .at(maxX/2.0,maxY*2.0/3.0)
                .view(new Rectangle(maxX/25.0, maxY/10.0, Color.INDIANRED))
                .buildAndAttach();
    }
}