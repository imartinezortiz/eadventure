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

package es.eucm.eadventure.engine.core;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.EAdOperation;
import es.eucm.eadventure.common.model.variables.EAdVarDef;
import es.eucm.eadventure.engine.core.operator.OperatorFactory;

/**
 * The value map interfaces allows for the definition of key-value pairs of
 * different classes.
 */
public interface ValueMap {
	
	void setOperatorFactory(OperatorFactory operatorFactory);

	/**
	 * @param var
	 * @param value
	 */
	<S> void setValue(EAdField<S> var, S value);
	
	<S> void setValue(EAdElement element, EAdVarDef<S> varDef, S value);
	
	
	//FIXME this has to change, to dissappear
	void setValue(EAdVarDef<?> varDef, Object value, EAdElement element );

	/**
	 * Sets the variable to the result value of the operation
	 * 
	 * @param var
	 * @param operation
	 */
	<S> void setValue(EAdField<S> var, EAdOperation operation);
	
	<S> void setValue(EAdElement element, EAdVarDef<S> var, EAdOperation operation);
	

	/**
	 * @param <S>
	 * @param var
	 * @return
	 */
	<S> S getValue(EAdField<S> var);
	
	<S> S getValue( EAdElement element, EAdVarDef<S> varDef );

	/**
	 * Removes all fields associated to the given element
	 * 
	 * @param element
	 *            the element
	 */
	void remove(EAdElement element);

}
