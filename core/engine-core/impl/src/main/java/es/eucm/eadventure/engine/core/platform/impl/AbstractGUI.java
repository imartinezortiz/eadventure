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

package es.eucm.eadventure.engine.core.platform.impl;

import java.util.logging.Logger;

import es.eucm.eadventure.common.model.guievents.EAdMouseEvent.MouseActionType;
import es.eucm.eadventure.common.resources.assets.drawable.basics.Image;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.KeyboardState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObject;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectManager;
import es.eucm.eadventure.engine.core.gameobjects.impl.sceneelements.SceneElementGOImpl;
import es.eucm.eadventure.engine.core.guiactions.KeyAction;
import es.eucm.eadventure.engine.core.guiactions.MouseAction;
import es.eucm.eadventure.engine.core.guiactions.impl.MouseActionImpl;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.GraphicRendererFactory;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.util.EAdTransformation;
import es.eucm.eadventure.engine.core.util.impl.EAdMatrixImpl;
import es.eucm.eadventure.engine.core.util.impl.EAdTransformationImpl;

/**
 * <p>
 * Abstract implementation of the GUI (Graphic User Interface) for the
 * eAdventure 2 games
 * </p>
 * 
 * @param <T>
 *            A parameter for the graphic context of the GUI (e.g. in AWT Java
 *            it will be Graphics2D)
 */
public abstract class AbstractGUI<T> implements GUI {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger("AbstractGUI");

	/**
	 * Maximum number of events to be processes per cycle
	 */
	private int MAX_EVENTS_PER_CYCLE = 10;

	/**
	 * Maximum number of events that can be in the queue
	 */
	private int MAX_EVENTS_IN_QUEUE = 30;

	/**
	 * Platform configuration parameters
	 */
	protected PlatformConfiguration platformConfiguration;

	/**
	 * Factory for the graphic render elements, parameterized with the graphic
	 * context
	 */
	protected GraphicRendererFactory<T> graphicRendererFactory;

	/**
	 * Game object manager
	 */
	protected GameObjectManager gameObjects;

	/**
	 * The current mouse state
	 */
	protected MouseState mouseState;

	/**
	 * The current keyboard state
	 */
	protected KeyboardState keyboardState;

	protected ValueMap valueMap;

	protected GameState gameState;

	protected GameObjectFactory gameObjectFactory;

	private EAdTransformation initialTransformation = new EAdTransformationImpl();

	@SuppressWarnings({ "unchecked" })
	public AbstractGUI(PlatformConfiguration platformConfiguration,
			GraphicRendererFactory<?> assetRendererFactory,
			GameObjectManager gameObjectManager, MouseState mouseState,
			KeyboardState keyboardState, ValueMap valueMap,
			GameState gameState, GameObjectFactory gameObjectFactory) {
		this.platformConfiguration = platformConfiguration;
		this.graphicRendererFactory = (GraphicRendererFactory<T>) assetRendererFactory;
		this.gameObjects = gameObjectManager;
		this.mouseState = mouseState;
		this.keyboardState = keyboardState;
		this.valueMap = valueMap;
		this.gameState = gameState;
		this.gameObjectFactory = gameObjectFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.GUI#addElement(es.eucm.eadventure
	 * .engine.core.gameobjects.GameObject)
	 * 
	 * The element should not be offset as it is being dragged in the scene
	 */
	@Override
	public void addElement(GameObject<?> element,
			EAdTransformation transformation) {
		EAdTransformation t = element.getTransformation();
		if (t != null) {
			EAdTransformation tResult = addTransformation(transformation, t);
			if (tResult.isVisible()) {
				gameObjects.add(element, tResult);
				element.doLayout(tResult);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#prepareGUI()
	 */
	@Override
	public void prepareGUI() {
		if (gameObjects.getHUD() != null) {
			gameObjects.add(gameObjects.getHUD(), initialTransformation);
			gameObjects.getHUD().update(gameState);
			gameObjects.getHUD().doLayout(initialTransformation);
		}

		gameObjects.swap();

		if (mouseState.getDraggingGameObject() != null) {
			if (!gameObjects.getGameObjects().contains(
					mouseState.getDraggingGameObject())) {
				mouseState.setDraggingGameObject(null);
			} else {
				int pos = gameObjects.getGameObjects().indexOf(
						mouseState.getDraggingGameObject());
				if (pos != -1) {
					gameObjects.getGameObjects().remove(pos);
					gameObjects.getTransformations().remove(pos);
				}
			}
		}
	}

	/**
	 * Process the different sort of inputs received by the game
	 */
	protected void processInput() {

		processMouseMovement();

		processMouseActions();

		processKeyActions();
	}

	/**
	 * Process movements to the mouse pointer
	 */
	private void processMouseMovement() {
		if (mouseState.pullMovedStatus()) {
			GameObject<?> gameObject = mouseState.getGameObjectUnderMouse();
			mouseState.setElementGameObject(null, 0, 0);

			for (int i = gameObjects.getGameObjects().size() - 1; i >= 0
					&& i < gameObjects.getGameObjects().size()
					&& mouseState.getGameObjectUnderMouse() == null; i--) {
				GameObject<?> tempGameObject = gameObjects.getGameObjects()
						.get(i);
				if (tempGameObject instanceof SceneElementGOImpl
						&& tempGameObject.getTransformation().isVisible())
					continue;
				EAdTransformation t = gameObjects.getTransformations().get(i);

				if (graphicRendererFactory.contains(tempGameObject,
						mouseState.getVirtualMouseX(),
						mouseState.getVirtualMouseY(), t)) {
					mouseState.setElementGameObject(tempGameObject, (int) t
							.getMatrix().getOffsetX(), (int) t.getMatrix()
							.getOffsetY());
					if (tempGameObject != gameObject) {
						tempGameObject.processAction(new MouseActionImpl(
								MouseActionType.ENTERED, mouseState
										.getVirtualMouseX(), mouseState
										.getVirtualMouseY()));

					}
				}
			}

			if (gameObject != null)
				if (mouseState.getGameObjectUnderMouse() != gameObject) {
					gameObject.processAction(new MouseActionImpl(
							MouseActionType.EXITED, mouseState
									.getVirtualMouseX(), mouseState
									.getVirtualMouseY()));
				} else {
					gameObject.processAction(new MouseActionImpl(
							MouseActionType.MOVED, mouseState
									.getVirtualMouseX(), mouseState
									.getVirtualMouseY()));
				}
		}
	}

	/**
	 * Process actions (i.e. button presses) of the mouse
	 */
	private void processMouseActions() {
		int j = 0;
		while (!mouseState.getMouseEvents().isEmpty()
				&& (j < MAX_EVENTS_PER_CYCLE || mouseState.getMouseEvents()
						.size() > MAX_EVENTS_IN_QUEUE)) {
			j++;
			MouseAction action = mouseState.getMouseEvents().poll();

			for (int i = gameObjects.getGameObjects().size() - 1; i >= 0; i--) {
				GameObject<?> gameObject = gameObjects.getGameObjects().get(i);
				EAdTransformation t = gameObjects.getTransformations().get(i);
				if (graphicRendererFactory.contains(gameObject,
						action.getVirtualX(), action.getVirtualY(), t)) {
					logger.info("Action "
							+ action
							+ " passed to "
							+ (gameObject.getElement() != null ? gameObject
									.getElement().toString() : ""));
					gameObject.processAction(action);
				}
				if (action.isConsumed())
					break;
			}
		}
	}

	/**
	 * Process keyboard actions
	 */
	private void processKeyActions() {
		int j = 0;
		while (!keyboardState.getKeyActions().isEmpty()
				&& (j < MAX_EVENTS_PER_CYCLE || keyboardState.getKeyActions()
						.size() > MAX_EVENTS_IN_QUEUE)) {
			j++;
			processKeyAction(keyboardState.getKeyActions().poll());
		}
	}

	/**
	 * Process a specific keyboard action
	 * 
	 * @param action
	 *            The keyboard action
	 */
	protected void processKeyAction(KeyAction action) {
		if (gameState.getActiveElement() != null)
			gameObjectFactory.get(gameState.getActiveElement()).processAction(
					action);
		for (int i = gameObjects.getGameObjects().size() - 1; action != null
				&& i >= 0 && !action.isConsumed(); i--) {
			logger.info("Action " + action + " passed to "
					+ gameObjects.getGameObjects().get(i));
			gameObjects.getGameObjects().get(i).processAction(action);
		}
	}

	/**
	 * Render the game objects into the graphic context
	 * 
	 * @param g
	 *            The graphic context
	 * @param interpolation
	 *            The current interpolation between ideal game frames
	 */
	protected void render(T g, float interpolation) {
		synchronized (GameObjectManager.lock) {
			for (int i = 0; i < gameObjects.getGameObjects().size(); i++) {
				EAdTransformation t = gameObjects.getTransformations().get(i);
				graphicRendererFactory.render(g, gameObjects.getGameObjects()
						.get(i), t);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#getWidth()
	 */
	@Override
	public int getWidth() {
		return platformConfiguration.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#getHeight()
	 */
	@Override
	public int getHeight() {
		return platformConfiguration.getHeight();
	}

	public int[] getGameElementGUIOffset(GameObject<?> gameObject) {
		synchronized (GameObjectManager.lock) {
			int pos = gameObjects.getGameObjects().indexOf(gameObject);
			if (pos == -1)
				return null;
			EAdTransformation t = gameObjects.getTransformations().get(pos);
			int[] offset = new int[2];
			offset[0] = (int) t.getMatrix().getOffsetX();
			offset[1] = (int) t.getMatrix().getOffsetY();
			return offset;
		}
	}

	public void changeCursor(Image image) {

	}

	private EAdTransformation addTransformation(EAdTransformation t1,
			EAdTransformation t2) {
		EAdMatrixImpl m = new EAdMatrixImpl();
		m.postMultiply(t1.getMatrix().getFlatMatrix());
		m.postMultiply(t2.getMatrix().getFlatMatrix());
		float alpha = t1.getAlpha() * t2.getAlpha();
		boolean visible = t1.isVisible() && t2.isVisible();
		EAdTransformationImpl t = new EAdTransformationImpl(m, visible, alpha);
		return t;
	}

}
