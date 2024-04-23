package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.enemy.EnemyState;

public interface ViewableEnemy extends ViewableEntity {
    /**
     * Gets the current state of the enemy.
     * 
     * @return the current enemy state.
     */
    EnemyState getCurrentState();
}
