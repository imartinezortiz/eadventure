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

package es.eucm.ead.techdemo.elementfactories.scenes.scenes;

import es.eucm.ead.model.assets.drawable.basics.Caption;
import es.eucm.ead.model.assets.drawable.basics.Image;
import es.eucm.ead.model.assets.drawable.basics.animation.FramesAnimation;
import es.eucm.ead.model.assets.drawable.basics.shapes.RectangleShape;
import es.eucm.ead.model.assets.drawable.compounds.ComposedDrawable;
import es.eucm.ead.model.assets.drawable.compounds.EAdComposedDrawable;
import es.eucm.ead.model.assets.drawable.compounds.EAdStateDrawable;
import es.eucm.ead.model.assets.drawable.compounds.StateDrawable;
import es.eucm.ead.model.elements.enums.CommonStates;
import es.eucm.ead.model.elements.operations.ElementField;
import es.eucm.ead.model.elements.operations.ValueOp;
import es.eucm.ead.model.elements.scenes.SceneElement;
import es.eucm.ead.model.params.fills.ColorFill;
import es.eucm.ead.model.params.fills.LinearGradientFill;
import es.eucm.ead.model.params.fills.Paint;
import es.eucm.ead.model.params.guievents.MouseGEv;
import es.eucm.ead.model.params.paint.EAdFill;
import es.eucm.ead.techdemo.elementfactories.EAdElementsFactory;

public class DrawablesScene extends EmptyScene {

	public DrawablesScene() {
		this.setId("DrawablesScene");
		setBackgroundFill(new ColorFill(240, 255, 255));
		int margin = 50;
		int x = margin;
		int y = margin;

		// eAdventure logo
		Image drawable = new Image("@drawable/eAdventureLogo.png");
		Caption caption = EAdElementsFactory.getInstance().getCaptionFactory()
				.createCaption("Image");
		caption.setPadding(0);
		caption.setTextPaint(ColorFill.BLACK);
		EAdComposedDrawable composed = new ComposedDrawable();
		composed.addDrawable(caption);
		composed.addDrawable(drawable, 0, 40);
		getSceneElements().add(
				EAdElementsFactory.getInstance().getSceneElementFactory()
						.createSceneElement(composed, x, y));

		x += 200;

		// Shaded rectangle
		caption = EAdElementsFactory.getInstance().getCaptionFactory()
				.createCaption("Composed Drawable");
		caption.setPadding(0);
		caption.setTextPaint(ColorFill.BLACK);

		int rWidth = 60;
		int rHeight = 40;

		RectangleShape rectangle = new RectangleShape(rWidth, rHeight);
		rectangle.setPaint(new ColorFill(255, 0, 0));

		RectangleShape rectangleShadow = new RectangleShape(rWidth, rHeight);
		rectangleShadow.setPaint(new ColorFill(100, 100, 100, 100));

		RectangleShape rectangleB = new RectangleShape(rWidth, rHeight);
		rectangleB.setPaint(new Paint(ColorFill.GREEN, ColorFill.BLACK, 2));

		EAdFill p = new LinearGradientFill(ColorFill.BLUE, ColorFill.MAGENTA,
				rWidth, rHeight, false);
		RectangleShape rectangleG = new RectangleShape(rWidth, rHeight);
		rectangleG.setPaint(p);

		RectangleShape rectangle3 = new RectangleShape(rWidth, rHeight);
		rectangle3.setPaint(new Paint(new LinearGradientFill(ColorFill.GREEN,
				ColorFill.YELLOW, rWidth / 2, rHeight / 2, false),
				ColorFill.RED));

		RectangleShape rectangle4 = new RectangleShape(rWidth, rHeight);
		rectangle4.setPaint(new Paint(null, ColorFill.BROWN, 4));

		RectangleShape rectangle5 = new RectangleShape(rWidth, rHeight);
		rectangle5.setPaint(new Paint(ColorFill.WHITE, p, 8));

		EAdComposedDrawable composedDrawable = new ComposedDrawable();
		composedDrawable.addDrawable(rectangleShadow, 6, 6);
		composedDrawable.addDrawable(rectangle);
		composedDrawable.addDrawable(rectangleShadow, rWidth + 16, 6);
		composedDrawable.addDrawable(rectangleB, rWidth + 10, 0);
		composedDrawable.addDrawable(rectangleShadow, 2 * (rWidth + 10) + 6, 6);
		composedDrawable.addDrawable(rectangleG, 2 * (rWidth + 10), 0);
		// Row 2
		int height2 = rHeight + 10;
		composedDrawable.addDrawable(rectangleShadow, 6, 6 + height2);
		composedDrawable.addDrawable(rectangle3, 0, height2);
		composedDrawable.addDrawable(rectangleShadow, rWidth + 16, 6 + height2);
		composedDrawable.addDrawable(rectangle4, rWidth + 10, height2);
		composedDrawable.addDrawable(rectangleShadow, 2 * (rWidth + 10) + 6,
				6 + height2);
		composedDrawable.addDrawable(rectangle5, 2 * (rWidth + 10), height2);

		composed = new ComposedDrawable();
		composed.addDrawable(caption);
		composed.addDrawable(composedDrawable, 0, 40);

		getSceneElements().add(
				EAdElementsFactory.getInstance().getSceneElementFactory()
						.createSceneElement(composed, x, y));

		x += 250;

		// State drawable
		RectangleShape rectangle1 = new RectangleShape(rHeight, rHeight);
		rectangle1.setPaint(new Paint(ColorFill.LIGHT_BROWN,
				ColorFill.LIGHT_BROWN, 8));

		RectangleShape rectangle2 = new RectangleShape(rHeight, rHeight);
		rectangle2
				.setPaint(new Paint(ColorFill.BLACK, ColorFill.LIGHT_BROWN, 8));

		EAdStateDrawable stateDrawable = new StateDrawable();
		stateDrawable.addDrawable(CommonStates.DEFAULT.toString(), rectangle1);
		stateDrawable.addDrawable(CommonStates.TALKING.toString(), rectangle2);

		caption = EAdElementsFactory.getInstance().getCaptionFactory()
				.createCaption("State Drawable");
		caption.setPadding(0);
		caption.setTextPaint(ColorFill.BLACK);

		getSceneElements().add(
				EAdElementsFactory.getInstance().getSceneElementFactory()
						.createSceneElement(caption, x, y));
		SceneElement sceneElement = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(stateDrawable,
						x + 50, y + 40);
		sceneElement.addBehavior(MouseGEv.MOUSE_RIGHT_PRESSED,
				EAdElementsFactory.getInstance().getEffectFactory()
						.getChangeVarValueEffect(
								new ElementField(sceneElement,
										SceneElement.VAR_STATE),
								new ValueOp(CommonStates.DEFAULT.toString())));
		sceneElement.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED,
				EAdElementsFactory.getInstance().getEffectFactory()
						.getChangeVarValueEffect(
								new ElementField(sceneElement,
										SceneElement.VAR_STATE),
								new ValueOp(CommonStates.TALKING.toString())));
		getSceneElements().add(sceneElement);

		// Frames
		String uris[] = new String[2];
		for (int i = 1; i <= 2; i++)
			uris[i - 1] = "@drawable/man_walk_e_" + i + ".png";

		FramesAnimation animation = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(uris, 500);

		caption = EAdElementsFactory.getInstance().getCaptionFactory()
				.createCaption("Frames animation");
		caption.setPadding(0);
		caption.setTextPaint(ColorFill.BLACK);

		getSceneElements().add(
				EAdElementsFactory.getInstance().getSceneElementFactory()
						.createSceneElement(caption, margin, 180));

		SceneElement paniel = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(animation, margin,
						220);
		paniel.setScale(0.8f);
		getSceneElements().add(paniel);

	}
}
