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

package es.eucm.ead.engine.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.inject.Inject;
import es.eucm.ead.engine.factories.SceneElementFactory;
import es.eucm.ead.engine.game.interfaces.GUI;
import es.eucm.ead.engine.gameobjects.sceneelements.SceneElementGO;
import es.eucm.ead.engine.gameobjects.sceneelements.SceneGO;
import es.eucm.ead.model.elements.huds.BottomHud;
import es.eucm.ead.model.elements.huds.MouseHud;
import es.eucm.ead.model.elements.predef.LoadingScreen;
import es.eucm.ead.model.elements.scenes.GroupElement;
import es.eucm.ead.model.elements.scenes.Scene;
import es.eucm.ead.model.elements.scenes.SceneElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public abstract class GUIImpl implements GUI {

	/**
	 * Logger
	 */
	static private Logger logger = LoggerFactory.getLogger(GUIImpl.class);

	protected SceneElementGO root;

	protected SceneElementGO hudRoot;

	protected SceneElementGO sceneRoot;

	/**
	 * Stack with all visited scenes
	 */
	private Stack<Scene> previousSceneStack;

	/**
	 * Current scene
	 */
	private SceneGO scene;

	private Scene loadingScreen;

	private SceneElementFactory sceneElementFactory;

	private Stage stage;

	@Inject
	public GUIImpl(SceneElementFactory sceneElementFactory) {
		this.sceneElementFactory = sceneElementFactory;
		previousSceneStack = new Stack<Scene>();
		this.loadingScreen = new LoadingScreen();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void addHierarchy() {
		root = sceneElementFactory.get(new GroupElement());
		root.getElement().setId("#engine.root");
		hudRoot = sceneElementFactory.get(new GroupElement());
		hudRoot.getElement().setId("#engine.huds");
		sceneRoot = sceneElementFactory.get(new GroupElement());
		sceneRoot.getElement().setId("#engine.sceneContainer");
		root.addSceneElement(sceneRoot);
		root.addSceneElement(hudRoot);
		// Bottom hud
		hudRoot.addSceneElement(sceneElementFactory.get(new BottomHud()));
		// Effects hud
		SceneElement effectsHud = new SceneElement();
		effectsHud.setId(GUI.EFFECTS_HUD_ID);
		hudRoot.addSceneElement(sceneElementFactory.get(effectsHud));
		// Debugger hud
		SceneElement debuggerHud = new SceneElement();
		debuggerHud.setId(GUI.DEBBUGERS_HUD_ID);
		hudRoot.addSceneElement(debuggerHud);
		// Add huds
		hudRoot.addSceneElement(sceneElementFactory.get(new MouseHud()));

		// Add to stage
		stage.clear();
		stage.addActor(root);
		stage.setKeyboardFocus(root);
	}

	@Override
	public Scene getPreviousScene() {
		if (previousSceneStack.isEmpty()) {
			return null;
		}
		return previousSceneStack.pop();
	}

	@Override
	public void addHud(SceneElementGO hud) {
		hudRoot.addSceneElement(hud);
	}

	@Override
	public void removeHUD(SceneElementGO hud) {
		hud.remove();
	}

	public SceneElementGO getHUD(String id) {
		SceneElementGO a = (SceneElementGO) hudRoot.findActor(id);
		if (a == null) {
			logger.warn("Hud with id {} is not present", id);
		}
		return a;
	}

	public SceneElementGO getGameObjectIn(int x, int y, boolean touchable) {
		return root.getFirstGOIn(x, y, touchable);
	}

	@Override
	public SceneGO getScene() {
		if (scene == null) {
			logger.debug("null scene, Loading screen: "
					+ (loadingScreen != null));
			this.scene = (SceneGO) sceneElementFactory.get(loadingScreen);
			previousSceneStack.push(loadingScreen);
		}
		return scene;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.eucm.eadventure.engine.engine.GameState#setScene(es.eucm.eadventure.
	 * engine.engine.gameobjects.SceneGO)
	 */
	@Override
	public void setScene(SceneGO newScene) {

		// We add the scene to stack if it is returnable
		if (this.scene != null
				&& this.scene.getElement() != null
				&& scene.getReturnable()
				&& (previousSceneStack.isEmpty() || previousSceneStack.peek() != scene
						.getElement())) {
			previousSceneStack.push((Scene) scene.getElement());
		}
		// Set the scene
		this.scene = newScene;
		sceneRoot.getChildren().clear();
		sceneRoot.addSceneElement(scene);
	}

	public SceneElementGO getSceneElement(SceneElement element) {
		return sceneElementFactory.get(element);
	}

	public SceneElementGO getRoot() {
		return root;
	}

	public SceneElementGO getGameObjectUnderPointer() {
		Actor a = stage.hit(Gdx.input.getX(), Gdx.input.getY(), true);
		if (a instanceof SceneElementGO) {
			return (SceneElementGO) a;
		}
		return null;
	}

	public void reset() {
		previousSceneStack.clear();
		sceneElementFactory.clean();
		addHierarchy();
	}

}
