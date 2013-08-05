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

package ead.demos.elementfactories.scenes.scenes;

import es.eucm.ead.model.interfaces.features.enums.Orientation;
import es.eucm.ead.model.assets.drawable.basics.Image;
import es.eucm.ead.model.assets.drawable.basics.animation.FramesAnimation;
import es.eucm.ead.model.assets.drawable.compounds.EAdStateDrawable;
import es.eucm.ead.model.assets.drawable.compounds.StateDrawable;
import es.eucm.ead.model.elements.EAdEffect;
import es.eucm.ead.model.elements.enums.CommonStates;
import es.eucm.ead.model.elements.events.SceneElementEv;
import es.eucm.ead.model.elements.events.enums.SceneElementEvType;
import es.eucm.ead.model.elements.operations.BasicField;
import es.eucm.ead.model.elements.operations.ValueOp;
import es.eucm.ead.model.elements.predef.sceneelements.Button;
import es.eucm.ead.model.elements.scenes.SceneElement;
import es.eucm.ead.model.params.guievents.KeyGEv;
import es.eucm.ead.model.params.guievents.MouseGEv;
import es.eucm.ead.model.params.text.EAdString;
import es.eucm.ead.model.params.util.Position.Corner;
import ead.demos.elementfactories.EAdElementsFactory;
import ead.demos.elementfactories.scenes.normalguy.NgCommon;

public class CharacterScene extends EmptyScene {

	private static String standUris[] = new String[] {
			"@drawable/stand_up.png", "@drawable/red_stand_right.png",
			"@drawable/stand_down.png", "@drawable/red_stand_left.png" };

	private static String talkDownUris[] = new String[] {
			"@drawable/stand_down.png", "@drawable/stand_down_talking.png" };

	private static String talkRightUris[] = new String[] {
			"@drawable/red_stand_right.png",
			"@drawable/red_stand_right_talking.png" };

	private static String talkLeftUris[] = new String[] {
			"@drawable/red_stand_left.png",
			"@drawable/red_stand_left_talking.png" };

	private static String walkDownUris[] = new String[] {
			"@drawable/walking_down.png", "@drawable/walking_down_2.png" };

	private static String walkUpUris[] = new String[] {
			"@drawable/walking_up_1.png", "@drawable/walking_up_2.png" };

	private static String walkRightUris[] = new String[] {
			"@drawable/walking_right_1.png", "@drawable/walking_right_2.png" };

	private static String walkLeftUris[] = new String[] {
			"@drawable/walking_left_1.png", "@drawable/walking_left_2.png" };

	public CharacterScene() {
		this.setId("CharacterScene");

		//		EAdBasicSceneElement element = EAdElementsFactory.getInstance()
		//				.getSceneElementFactory()
		//				.createSceneElement(getStateDrawable(), 100, 300);

		NgCommon.init();
		SceneElement element = new SceneElement(NgCommon.getMainCharacter());
		element.setPosition(Corner.CENTER, 400, 300);

		SceneElementEv event = new SceneElementEv();

		event
				.addEffect(SceneElementEvType.INIT, EAdElementsFactory
						.getInstance().getEffectFactory().getMakeActiveElement(
								element));

		element.getEvents().add(event);

		this.getSceneElements().add(element);

		EAdEffect goUpEffect = EAdElementsFactory.getInstance()
				.getEffectFactory().getChangeVarValueEffect(
						new BasicField<Orientation>(element,
								SceneElement.VAR_ORIENTATION),
						new ValueOp(Orientation.N));
		SceneElement goUpArrow = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(
						new Image("@drawable/arrow_up.png"), 100, 210,
						goUpEffect);
		this.getSceneElements().add(goUpArrow);

		element.addBehavior(KeyGEv.KEY_ARROW_UP, goUpEffect);

		EAdEffect goDownEffect = EAdElementsFactory.getInstance()
				.getEffectFactory().getChangeVarValueEffect(
						new BasicField<Orientation>(element,
								SceneElement.VAR_ORIENTATION),
						new ValueOp(Orientation.S));
		SceneElement goDownArrow = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(
						new Image("@drawable/arrow_down.png"), 100, 320,
						goDownEffect);
		this.getSceneElements().add(goDownArrow);

		element.addBehavior(KeyGEv.KEY_ARROW_DOWN, goDownEffect);

		EAdEffect goLeftEffect = EAdElementsFactory.getInstance()
				.getEffectFactory().getChangeVarValueEffect(
						new BasicField<Orientation>(element,
								SceneElement.VAR_ORIENTATION),
						new ValueOp(Orientation.W));
		SceneElement goLeftArrow = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(
						new Image("@drawable/arrow_left.png"), 0, 260,
						goLeftEffect);
		this.getSceneElements().add(goLeftArrow);

		element.addBehavior(KeyGEv.KEY_ARROW_LEFT, goLeftEffect);

		EAdEffect goRightEffect = EAdElementsFactory.getInstance()
				.getEffectFactory().getChangeVarValueEffect(
						new BasicField<Orientation>(element,
								SceneElement.VAR_ORIENTATION),
						new ValueOp(Orientation.E));
		SceneElement goRightArrow = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(
						new Image("@drawable/arrow_right.png"), 200, 260,
						goRightEffect);
		this.getSceneElements().add(goRightArrow);

		element.addBehavior(KeyGEv.KEY_ARROW_RIGHT, goRightEffect);

		// Change state buttons
		EAdEffect standEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new BasicField<String>(element, SceneElement.VAR_STATE),
						new ValueOp(CommonStates.DEFAULT.toString()));
		//		EAdBasicSceneElement stand = EAdElementsFactory.getInstance()
		//				.getSceneElementFactory()
		//				.createSceneElement("Stand", 300, 10, standEffect);		
		Button stand = new Button(
				new EAdString("techDemo.CharacterScene.Stand"));
		stand.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, standEffect);
		stand.setPosition(Corner.CENTER, 600, 250);
		getSceneElements().add(stand);

		EAdEffect talkEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new BasicField<String>(element, SceneElement.VAR_STATE),
						new ValueOp(CommonStates.TALKING.toString()));
		Button talk = new Button(new EAdString("techDemo.CharacterScene.Talk"));
		talk.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, talkEffect);
		talk.setPosition(Corner.CENTER, 600, 290);
		getSceneElements().add(talk);

		EAdEffect walkEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new BasicField<String>(element, SceneElement.VAR_STATE),
						new ValueOp(CommonStates.WALKING.toString()));
		Button walk = new Button(new EAdString("techDemo.CharacterScene.Walk"));
		walk.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, walkEffect);
		walk.setPosition(Corner.CENTER, 600, 330);
		getSceneElements().add(walk);
	}

	private static StateDrawable getTalkDrawable() {
		StateDrawable oriented = new StateDrawable();
		oriented
				.setDrawable(Orientation.N, new Image("@drawable/stand_up.png"));

		FramesAnimation right = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkRightUris, 500);
		oriented.setDrawable(Orientation.E, right);

		FramesAnimation left = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkLeftUris, 500);
		oriented.setDrawable(Orientation.W, left);

		FramesAnimation down = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkDownUris, 500);
		oriented.setDrawable(Orientation.S, down);

		return oriented;
	}

	private static StateDrawable getWalkDrawable() {
		StateDrawable oriented = new StateDrawable();

		FramesAnimation up = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkUpUris, 500);
		oriented.setDrawable(Orientation.N, up);

		FramesAnimation right = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkRightUris, 500);
		oriented.setDrawable(Orientation.E, right);

		FramesAnimation left = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkLeftUris, 500);
		oriented.setDrawable(Orientation.W, left);

		FramesAnimation down = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkDownUris, 2000);
		oriented.setDrawable(Orientation.S, down);

		return oriented;
	}

	public static EAdStateDrawable getStateDrawable() {
		StateDrawable stand = EAdElementsFactory.getInstance()
				.getDrawableFactory().getOrientedDrawable(standUris);

		StateDrawable stateDrawable = new StateDrawable();
		stateDrawable.addDrawable(CommonStates.DEFAULT.toString(), stand);
		stateDrawable.addDrawable(CommonStates.TALKING.toString(),
				getTalkDrawable());
		stateDrawable.addDrawable(CommonStates.WALKING.toString(),
				getWalkDrawable());

		return stateDrawable;
	}

}
