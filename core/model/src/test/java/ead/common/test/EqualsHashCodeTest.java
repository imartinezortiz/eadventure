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

package ead.common.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test equals and hashcode methods
 * 
 */
public abstract class EqualsHashCodeTest<T> {

	protected T[] objects;

	@Before
	public void setUp() {
		objects = getObjects();
	}

	/**
	 * Data must be an array with length divisible by 2, and grouped by equals
	 * pairs, i.e. object[i].equals(object[i+1]) must be true,
	 * object[i+2].equals(object[i+3]) must be true, and so on. No pair can be
	 * repeated
	 * 
	 * @return
	 */
	public abstract T[] getObjects();

	@Test
	public void testHashCode() {
		for (int i = 0; i < objects.length; i += 2) {
			assertTrue(objects[i].hashCode() == objects[i + 1].hashCode());
			for (int j = 0; j < objects.length; j++) {
				if (j != i && j != i + 1) {
					assertTrue(objects[i].hashCode() != objects[j].hashCode());
				}
			}
		}
	}

	@Test
	public void testEqualsObject() {
		for (int i = 0; i < objects.length; i += 2) {
			assertTrue(objects[i].equals(objects[i + 1]));
			for (int j = 0; j < objects.length; j++) {
				if (j != i && j != i + 1) {
					assertTrue(
							"expected " + objects[i] + " "
									+ objects[i].getClass() + " and "
									+ objects[j] + " " + objects[i].getClass()
									+ " to be different!", !objects[i]
									.equals(objects[j]));
					assertTrue("expected " + objects[j] + " and " + objects[i]
							+ " to be different!", !objects[j]
							.equals(objects[i]));
				}
			}
		}
	}

}
