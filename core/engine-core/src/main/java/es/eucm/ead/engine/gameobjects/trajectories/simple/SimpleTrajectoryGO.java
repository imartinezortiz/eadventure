/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.eucm.ead.engine.gameobjects.trajectories.simple;

import com.google.inject.Inject;

import es.eucm.ead.engine.factories.SceneElementFactory;
import es.eucm.ead.engine.game.GameState;
import es.eucm.ead.engine.gameobjects.sceneelements.SceneElementGO;
import es.eucm.ead.engine.gameobjects.trajectories.AbstractTrajectoryGO;
import es.eucm.ead.model.interfaces.features.enums.Orientation;
import es.eucm.ead.model.elements.enums.CommonStates;
import es.eucm.ead.model.elements.trajectories.SimpleTrajectory;

public class SimpleTrajectoryGO extends AbstractTrajectoryGO<SimpleTrajectory> {

	// We want to move 800px every 4 seconds
	private static final int TIME_PER_UNIT = 8000 / 800;

	private float totalTime;

	private float currentTime;

	private float startX;

	private float startY;

	private float diffX;

	private float diffY;

	@Inject
	public SimpleTrajectoryGO(GameState gameState,
			SceneElementFactory sceneElementFactory) {
		super(gameState, sceneElementFactory);
	}

	public void set(SceneElementGO movingElement, float destinationX,
			float destinationY, SceneElementGO target) {
		super.set(movingElement, destinationX, destinationY, target);
		startX = movingElement.getX();
		startY = movingElement.getY();
		if (trajectory.isOnlyHorizontal()) {
			this.destinationY = startY;
		}

		if (!trajectory.isFreeWalk()) {
			this.destinationX = Math.min(trajectory.getRight(), Math.max(
					trajectory.getLeft(), this.destinationX));
			if (!trajectory.isOnlyHorizontal()) {
				this.destinationY = Math.max(trajectory.getTop(), Math.min(
						trajectory.getBottom(), this.destinationY));
			}
		}

		diffX = this.destinationX - startX;
		diffY = this.destinationY - startY;
		float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
		totalTime = distance * TIME_PER_UNIT;
		currentTime = 0;

		// If it's really going to move...
		if (Math.abs(diffX) > 0.1f || Math.abs(diffY) > 0.1f) {
			movingElement.setState(CommonStates.WALKING.toString());

			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (diffX > 0) {
					movingElement.setOrientation(Orientation.E);
				} else {
					movingElement.setOrientation(Orientation.W);
				}
			} else {
				if (diffY > 0) {
					movingElement.setOrientation(Orientation.S);
				} else {
					movingElement.setOrientation(Orientation.N);
				}
			}
		}

		currentPath.clear();
		currentPath.add(startX);
		currentPath.add(startY);
		currentPath.add(this.destinationX);
		currentPath.add(this.destinationY);
	}

	@Override
	public void act(float delta) {
		movingElement = sceneElementFactory.get(sceneElement);
		currentTime += delta;
		float x = (currentTime / totalTime) * diffX;
		float y = (currentTime / totalTime) * diffY;
		movingElement.setX(startX + x);
		movingElement.setY(startY + y);
		if (currentTime >= totalTime) {
			movingElement.setState(CommonStates.DEFAULT.toString());
		}
	}

	@Override
	public boolean isDone() {
		return currentTime >= totalTime;
	}

	@Override
	public boolean isReachedTarget() {
		if (target == null) {
			return true;
		}
		float diffX = target.getCenterX() - movingElement.getCenterX();
		float diffY = target.getCenterY() - movingElement.getCenterY();
		return Math.sqrt(diffX * diffX + diffY * diffY) <= trajectory
				.getDistanceToTarget();
	}

}
