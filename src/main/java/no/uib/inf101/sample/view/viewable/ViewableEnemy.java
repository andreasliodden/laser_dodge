package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.enemy.EnemyState;

/**
 * Interface that extends ViewableEntity and provides a method 
 * for retrieving the current state of the viewable enemy.
 */
public interface ViewableEnemy extends ViewableEntity {
    /**
     * Gets the current state of the enemy.
     * 
     * @return the current enemy state.
     */
    EnemyState getEnemyState();
}
