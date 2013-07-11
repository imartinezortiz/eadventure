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

package ead.common.model.elements.conditions;

import ead.common.interfaces.Element;
import ead.common.model.elements.EAdCondition;
import ead.common.model.elements.conditions.enums.ConditionOperator;
import ead.common.model.elements.extra.EAdList;

@Element
public class ANDCond extends ListedCond {

	public ANDCond(EAdCondition... condition) {
		super(ConditionOperator.AND, condition);
	}

	public ANDCond(EAdList<EAdCondition> conditions) {
		super(ConditionOperator.AND, conditions);
	}

	public ANDCond() {
		super(ConditionOperator.AND);
	}

	@Override
	public EmptyCond getNullOperator() {
		return EmptyCond.TRUE;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
