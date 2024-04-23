package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.enemy.EnemyState;

public interface ViewableEnemy extends ViewableEntity {
    EnemyState getCurrentState();
}
