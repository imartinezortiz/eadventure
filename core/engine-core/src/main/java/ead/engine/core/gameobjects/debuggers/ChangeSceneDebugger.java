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

package ead.engine.core.gameobjects.debuggers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import ead.common.model.assets.drawable.basics.Caption;
import ead.common.model.assets.drawable.basics.shapes.BezierShape;
import ead.common.model.assets.text.BasicFont;
import ead.common.model.elements.EAdAdventureModel;
import ead.common.model.elements.EAdChapter;
import ead.common.model.elements.effects.ChangeSceneEf;
import ead.common.model.elements.effects.variables.ChangeFieldEf;
import ead.common.model.elements.operations.BasicField;
import ead.common.model.elements.operations.EAdField;
import ead.common.model.elements.operations.MathOp;
import ead.common.model.elements.scenes.EAdScene;
import ead.common.model.elements.scenes.SceneElement;
import ead.common.model.params.fills.ColorFill;
import ead.common.model.params.guievents.MouseGEv;
import ead.common.model.params.util.Position;
import ead.common.model.params.util.Position.Corner;
import ead.engine.core.factories.EventGOFactory;
import ead.engine.core.factories.SceneElementGOFactory;
import ead.engine.core.game.GameState;
import ead.engine.core.gameobjects.sceneelements.SceneElementGO;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.AssetHandler;

@Singleton
public class ChangeSceneDebugger extends SceneElementGO {

	private static final int MARGIN_TOP = 20;

	private static final int MARGIN_LEFT = 20;

	private List<EAdScene> scenes;

	private EAdField<Integer> currentScene;

	private EAdField<Integer> totalScenes;

	private EAdField<EAdScene> sceneField;

	private EAdField<String> sceneIdField;

	private int index;

	public ChangeSceneDebugger(AssetHandler assetHandler,
			SceneElementGOFactory sceneElementFactory, GUI gui,
			GameState gameState, EventGOFactory eventFactory) {
		super(assetHandler, sceneElementFactory, gui, gameState, eventFactory,
				null);
		this.gameState = gameState;
		scenes = new ArrayList<EAdScene>();
		index = 0;
		initGOButton();
		initArrows();
		initSceneId();
	}

	private void initGOButton() {
		Caption text = new Caption("GO");
		text.setFont(new BasicFont(10));
		text.setPadding(5);
		text.setBubblePaint(ColorFill.LIGHT_GRAY);
		text.setTextPaint(ColorFill.BLACK);
		SceneElement button = new SceneElement(text);
		button.setPosition(Corner.CENTER, MARGIN_LEFT, MARGIN_TOP);
		sceneField = new BasicField<EAdScene>(button, "scene_field_debugger",
				EAdScene.class, null);
		currentScene = new BasicField<Integer>(button,
				"current_scene_debugger", Integer.class, new Integer(0));
		totalScenes = new BasicField<Integer>(button, "total_scenes_debugger",
				Integer.class, new Integer(1));
		sceneIdField = new BasicField<String>(button,
				"scene_field_id_debugger", String.class, "");

		ChangeSceneEf changeScene = new ChangeSceneEf();
		changeScene.setNextScene(sceneField);
		button.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, changeScene);
		addSceneElement(sceneElementFactory.get(button));
	}

	private void initArrows() {
		int margin = MARGIN_LEFT + 30;
		int size = 20;
		BezierShape triangle = new BezierShape();
		triangle.moveTo(0, 0);
		triangle.lineTo(size, size / 2);
		triangle.lineTo(0, size);
		triangle.setClosed(true);
		triangle.setPaint(ColorFill.LIGHT_GRAY);

		SceneElement leftArrow = new SceneElement(triangle);
		SceneElement rightArrow = new SceneElement(triangle);
		leftArrow.setInitialRotation(-(float) Math.PI);

		leftArrow.setPosition(Corner.CENTER, margin, MARGIN_TOP);

		rightArrow.setPosition(Corner.CENTER, margin + size + 5, MARGIN_TOP);
		ChangeFieldEf goDown = new ChangeFieldEf(currentScene, new MathOp(
				"0 max ([0] - 1)", currentScene));
		ChangeFieldEf goUp = new ChangeFieldEf(currentScene, new MathOp(
				"([0] + 1) min ([1] - 1)", currentScene, totalScenes));

		rightArrow.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, goUp);
		leftArrow.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, goDown);

		addSceneElement(sceneElementFactory.get(rightArrow));
		addSceneElement(sceneElementFactory.get(leftArrow));
	}

	private void initSceneId() {
		int margin = MARGIN_LEFT + 70;
		Caption text = new Caption("Scene id: [0]");
		text.setFont(new BasicFont(10));
		text.getFields().add(sceneIdField);
		text.setBubblePaint(ColorFill.WHITE);
		text.setTextPaint(ColorFill.BLACK);
		text.setPadding(2);

		SceneElement sceneIdText = new SceneElement(text);
		sceneIdText.setPosition(new Position(margin, MARGIN_TOP, 0.0f, 0.5f));
		addSceneElement(sceneElementFactory.get(sceneIdText));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		int newIndex = gameState.getValue(currentScene);
		if (index != newIndex) {
			index = newIndex;
			gameState.setValue(currentScene, index);
			gameState.setValue(sceneField, scenes.get(index));
			gameState.setValue(sceneIdField, scenes.get(index).getId());
		}
	}

	public void setUp(EAdAdventureModel model) {
		for (EAdChapter c : model.getChapters()) {
			for (EAdScene s : c.getScenes()) {
				scenes.add(s);
			}
		}
		gameState.setValue(sceneField, scenes.get(0));
		gameState.setValue(totalScenes, scenes.size());
	}

}