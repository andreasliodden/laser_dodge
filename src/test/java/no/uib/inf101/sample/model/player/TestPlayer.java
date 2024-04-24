package no.uib.inf101.sample.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.GameState;

public class TestPlayer {
    private Player player;
    @BeforeEach
    private void initGameModel() {
        player = new Player();
    }

    @Test 
    public void initialPositionAndState() {
        double initPosition = 0.2;
        assertEquals(initPosition, player.getX());
        assertEquals(initPosition, player.getY());
        assertEquals(PlayerState.FRONT_RIGHT, player.getCurrentState());
    } 

    @Test
    public void initialHealth() {
        assertEquals(player.getMaxHealth(), player.getHealth());
    }

    @Test
    public void movePlayerAndGetNext() {
        double nextX = player.getNextX(1);
        player.move(1, 0);

        assertEquals(nextX, player.getX());

        double nextY = player.getNextY(1);
        player.move(0, 1);
        assertEquals(nextY, player.getY());
    }

    @Test
    public void playerBorderCollision() {
        while(player.move(1, 0));
        assertEquals(1, player.getX());

        while(player.move(-1, 0));
        assertEquals(0, player.getX());

        while(player.move(0, 1));
        assertEquals(1, player.getY());

        while(player.move(0, -1));
        assertEquals(0, player.getY());
    }

    @Test
    public void updateStateAfterMove() {
        player.move(1, 0);
        assertEquals(PlayerState.FRONT_RIGHT, player.getCurrentState());

        player.move(-1, 0);
        assertEquals(PlayerState.FRONT_LEFT, player.getCurrentState());

        player.move(0, 1);
        assertEquals(PlayerState.FRONT_LEFT, player.getCurrentState());

        player.move(0, -1);
        assertEquals(PlayerState.BACK_LEFT, player.getCurrentState());

        player.move(1, 0);

        player.move(0, 1);
        assertEquals(PlayerState.FRONT_RIGHT, player.getCurrentState());

        player.move(0, -1);
        assertEquals(PlayerState.BACK_RIGHT, player.getCurrentState());
    }

    @Test
    public void hitByProjectile() {
        GameState gameState = GameState.ACTIVE_ANGRY;
        player.registerHit(gameState);
        assertEquals(player.getMaxHealth() - 10, player.getHealth());

        while (player.getHealth() > 0) {
            player.registerHit(gameState);
        }
        assertEquals(0, player.getHealth());
        player.registerHit(gameState);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void eatingApples() {
        player.registerHit(GameState.ACTIVE_ANGRY);
        int expectedHealth = player.getMaxHealth() - 10;
        assertEquals(expectedHealth, player.getHealth());

        GameState gameState = GameState.ACTIVE_HAPPY;
        player.registerHit(gameState);
        assertEquals(expectedHealth + 2, player.getHealth());

        while (player.getHealth() < player.getMaxHealth()) {
            player.registerHit(gameState);
        }
        assertEquals(player.getMaxHealth(), player.getHealth());

        // Control that player health cant exceed max health
        player.registerHit(gameState);
        assertEquals(player.getMaxHealth(), player.getHealth());
    }

    @Test
    public void resetPlayer() {
        double initialPosition = player.getX();
        PlayerState initialState = PlayerState.FRONT_RIGHT;
        int iterations = 5;

        for (int i = 0; i < iterations; i++) {
            player.move(-1, 0);
            player.move(0, 1);
        }

        assertNotEquals(initialPosition, player.getX());
        assertNotEquals(initialPosition, player.getY());
        assertNotEquals(initialState, player.getCurrentState());

        player.reset();

        assertEquals(initialPosition, player.getX());
        assertEquals(initialPosition, player.getY());
        assertEquals(initialState, player.getCurrentState());
    }
}
