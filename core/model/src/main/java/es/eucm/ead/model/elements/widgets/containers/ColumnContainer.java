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

package es.eucm.ead.model.elements.widgets.containers;

import es.eucm.ead.model.elements.effects.DragEf;
import es.eucm.ead.model.elements.effects.variables.ChangeFieldEf;
import es.eucm.ead.model.elements.events.SceneElementEv;
import es.eucm.ead.model.elements.events.enums.SceneElementEvType;
import es.eucm.ead.model.elements.operations.ElementField;
import es.eucm.ead.model.elements.scenes.SceneElement;
import es.eucm.ead.model.elements.scenes.GroupElement;
import es.eucm.ead.model.params.guievents.MouseGEv;
import es.eucm.ead.model.params.util.Position.Corner;

public class ColumnContainer extends GroupElement {

	private SceneElement lastAdded;

	public ColumnContainer() {
		this.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, new DragEf());
	}

	public void add(SceneElement element) {
		element.setPosition(Corner.TOP_LEFT, 0, 0);
		if (lastAdded != null) {
			ElementField fieldBottom = new ElementField(lastAdded,
					SceneElement.VAR_Y);
			ElementField fieldY = new ElementField(element, SceneElement.VAR_Y);

			SceneElementEv event = new SceneElementEv();
			ChangeFieldEf updateField = new ChangeFieldEf(fieldY, fieldBottom);
			event.addEffect(SceneElementEvType.ALWAYS, updateField);
			element.addEvent(event);
		}
		getSceneElements().add(element);
		lastAdded = element;
	}

}
