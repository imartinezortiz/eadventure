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

import es.eucm.ead.model.assets.drawable.basics.shapes.RectangleShape;
import es.eucm.ead.model.elements.effects.InterpolationEf;
import es.eucm.ead.model.elements.effects.enums.InterpolationLoopType;
import es.eucm.ead.model.elements.effects.enums.InterpolationType;
import es.eucm.ead.model.elements.effects.variables.ChangeFieldEf;
import es.eucm.ead.model.elements.events.SceneElementEv;
import es.eucm.ead.model.elements.events.enums.SceneElementEvType;
import es.eucm.ead.model.elements.operations.ElementField;
import es.eucm.ead.model.elements.operations.ValueOp;
import es.eucm.ead.model.elements.scenes.GroupElement;
import es.eucm.ead.model.elements.scenes.SceneElement;
import es.eucm.ead.model.params.fills.ColorFill;
import es.eucm.ead.model.params.fills.Paint;
import es.eucm.ead.model.params.guievents.MouseGEv;
import es.eucm.ead.model.params.util.Position;
import es.eucm.ead.model.params.util.Position.Corner;
import es.eucm.ead.techdemo.elementfactories.EAdElementsFactory;

public class ComplexElementScene extends EmptyScene {

	public ComplexElementScene() {
		this.setId("ComplexElementScene");
		RectangleShape rectangle = new RectangleShape(400, 400);
		rectangle.setPaint(Paint.BLACK_ON_WHITE);
		GroupElement complex = new GroupElement(rectangle);
		complex.setBounds(400, 400);
		complex.setPosition(new Position(Corner.CENTER, 400, 300));

		RectangleShape r1 = new RectangleShape(400, 400, ColorFill.BLUE);
		RectangleShape r2 = new RectangleShape(400, 400, ColorFill.RED);
		SceneElement e = EAdElementsFactory.getInstance()
				.getSceneElementFactory().createSceneElement(r1, r2, 40, 40);

		e.setScale(0.1f);
		e.putProperty(VAR_ROTATION, 30.0f);
		e.setPosition(new Position(Corner.CENTER, 50, 50));

		complex.getSceneElements().add(e);

		getSceneElements().add(complex);

		ElementField rotation = new ElementField(complex, VAR_ROTATION);

		InterpolationEf effect = new InterpolationEf(rotation, 0, 360.0f,
				10000, InterpolationLoopType.RESTART, InterpolationType.LINEAR);

		SceneElementEv event = new SceneElementEv();
		event.addEffect(SceneElementEvType.INIT, effect);

		complex.addEvent(event);

		ElementField rotation2 = new ElementField(e, VAR_ROTATION);

		e.addBehavior(MouseGEv.MOUSE_RIGHT_PRESSED, new ChangeFieldEf(rotation,
				new ValueOp((float) 10.0f)));

		InterpolationEf effect2 = new InterpolationEf(rotation2, 0, 360, 1000,
				InterpolationLoopType.RESTART, InterpolationType.LINEAR);

		SceneElementEv event2 = new SceneElementEv();
		event2.addEffect(SceneElementEvType.INIT, effect2);

		e.addEvent(event2);

		ElementField scale = new ElementField(complex, VAR_SCALE);

		complex.setScale(0.5f);
		InterpolationEf effect3 = new InterpolationEf(scale, 0.0f, 1.5f, 5000,
				InterpolationLoopType.REVERSE, InterpolationType.LINEAR);

		event2.addEffect(SceneElementEvType.INIT, effect3);

	}

}
