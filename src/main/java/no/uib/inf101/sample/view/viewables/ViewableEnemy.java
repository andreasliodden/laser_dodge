package no.uib.inf101.sample.view.viewables;

import no.uib.inf101.sample.model.enemy.EnemyState;

public interface ViewableEnemy extends ViewableEntity {
    EnemyState getCurrentState();
}
