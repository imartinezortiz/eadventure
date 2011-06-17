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

package es.eucm.eadventure.common.model.events.impl;

import es.eucm.eadventure.common.model.EAdElementList;
import es.eucm.eadventure.common.model.EAdMap;
import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.events.EAdEvent;
import es.eucm.eadventure.common.model.impl.AbstractEAdElement;
import es.eucm.eadventure.common.model.impl.EAdElementListImpl;
import es.eucm.eadventure.common.model.impl.EAdMapImpl;

/**
 * <p>Abstract implementation of an eAdventure event.</p>
 * 
 */
public abstract class AbstractEAdEvent extends AbstractEAdElement implements EAdEvent {

	/**
	 * List of effects
	 */
	private EAdMap<Enum<?>, EAdElementList<EAdEffect>> effects;
	
	public AbstractEAdEvent(String id) {
		super(id);
		effects = new EAdMapImpl<Enum<?>, EAdElementList<EAdEffect>>(id + "_map", Enum.class, EAdElementList.class);
	}
	
	/* (non-Javadoc)
	 * @see es.eucm.eadventure.common.model.elements.EAdEvent#getEffects()
	 */
	@Override
	public EAdElementList<EAdEffect> getEffects(Enum<?> event) {
		return effects.get(event);
	}
	
	/* (non-Javadoc)
	 * @see es.eucm.eadventure.common.model.events.EAdEvent#addEffect(java.lang.Enum, es.eucm.eadventure.common.model.effects.EAdEffect)
	 */
	@Override
	public void addEffect(Enum<?> event, EAdEffect effect) {
		EAdElementList<EAdEffect> effects = this.effects.get(event);
		if (effects == null) {
			effects = new EAdElementListImpl<EAdEffect>(EAdEffect.class);
			this.effects.put(event, effects);
		}
		effects.add(effect);
	}

}