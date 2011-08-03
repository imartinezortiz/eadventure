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

package es.eucm.eadventure.engine.core.test.effects;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.eucm.eadventure.common.model.guievents.EAdMouseEvent.MouseActionType;
import es.eucm.eadventure.engine.core.guiactions.impl.MouseActionImpl;

public class GameStateTest extends EffectTest {
	
	@Test
	public void testEffect( ){
		testEngine.update();
		testEngine.getMouseEvents().add(new MouseActionImpl( MouseActionType.LEFT_CLICK, 10, 10 ));
		assertEquals( testEngine.getMouseEvents().size(), 1 );
		testEngine.render(0);
		assertEquals( testEngine.getEffects().size(), 3 );
		// Blocking and opaque wait
		testEngine.update();
		testEngine.render(0);
		testEngine.getMouseEvents().add(new MouseActionImpl( MouseActionType.LEFT_CLICK, 10, 10 ));
		testEngine.render(0);
		assertEquals( testEngine.getEffects().size(), 3 );
		testEngine.update();
		assertEquals( testEngine.getEffects().size(),  2 );
		// Blocking and non opaque
		testEngine.getMouseEvents().add(new MouseActionImpl( MouseActionType.LEFT_CLICK, 10, 10 ));
		testEngine.render(0);
		assertEquals( testEngine.getEffects().size(), 3 );
		
		
	}

}
