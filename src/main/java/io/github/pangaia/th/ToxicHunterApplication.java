package io.github.pangaia.th;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ToxicHunterApplication extends GameApplication {
    private Entity player;
    private Entity boss;
    private Entity platform0;

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
                .type(EntityType.PLAYER)
                .at(maxX / 2.0, maxY * 2.0 / 3.0)
                .viewWithBBox(new Rectangle(maxX / 25.0, maxY / 10.0, Color.INDIANRED))
                .with(new CollidableComponent(true))
                .buildAndAttach();
        player.setProperty("vspeed", 0.0);
        player.setProperty("stands", true);
        boss = FXGL.entityBuilder()
                .type(EntityType.BOSS)
                .at(maxX * 1.0 / 25.0, maxY * (2.0 / 3.0 - 0.2 + 0.1))
                .view(new Rectangle(maxX / 10.0, maxY / 5.0, Color.BLUE))
                .buildAndAttach();
        platform0 = FXGL.entityBuilder()
                .type(EntityType.PLATFORM)
                .at(0, maxY * (2.0 / 3.0 + 0.1))
                .viewWithBBox(new Rectangle(maxX, maxY * 0.05, Color.SADDLEBROWN))
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Override
    protected void onUpdate(double tpf) {
        double speed = player.getDouble("vspeed");
        if (!player.getBoolean("stands")) {
            player.translateY(speed /2.0);
            player.setProperty("vspeed", speed + tpf*100.0);
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity player, Entity floor) {
                player.setProperty("vspeed", 0.0);
                player.setProperty("stands", Boolean.TRUE);
            }
        });
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Jump") {
            @Override
            protected void onAction() {
                if (player.getBoolean("stands")) {
                    player.translateY(0.0);
                    player.setProperty("stands", false);
                    player.setProperty("vspeed", -25.0);
                }
            }
        }, KeyCode.SPACE);
    }
}