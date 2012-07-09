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

package ead.editor;

import ead.utils.i18n.I18N;
import java.util.Set;
import java.util.TreeSet;

/**
 * Resource index for this package (statically compiled).
 *
 * This is an AUTOMATICALLY-GENERATED file - 
 * Run class ead.utils.i18n.ResourceCreator with parameters: 
 *    "core/editor-core" "ead.editor" "etc/LICENSE.txt" "core/editor-core/src/main/java/ead/editor/R.java"
 * to re-create or update this class
 */
public class R {

	public static class Drawable {
		public static String EditorIcon128x128_png;
		public static String EditorIcon16x16_png;
		public static String EditorIcon32x32_png;
		public static String EditorIcon64x64_png;
		public static String SplashScreenLogo_png;
		public static String addNode_png;
		public static String deleteNode_png;
		public static String duplicateNode_png;
		public static String edit_png;
		public static String information_png;
		public static String loading_png;

		static {
			Set<String> files = new TreeSet<String>();

			files.add("EditorIcon128x128.png");
			files.add("EditorIcon16x16.png");
			files.add("EditorIcon32x32.png");
			files.add("EditorIcon64x64.png");
			files.add("SplashScreenLogo.png");
			files.add("addNode.png");
			files.add("conditions/flag16.png");
			files.add("conditions/flags.png");
			files.add("conditions/group-2.png");
			files.add("conditions/notOff.png");
			files.add("conditions/notOn.png");
			files.add("conditions/var16.png");
			files.add("conditions/vars.png");
			files.add("deleteNode.png");
			files.add("duplicateNode.png");
			files.add("edit.png");
			files.add("information.png");
			files.add("loading.png");

			I18N.initializeResources(Drawable.class.getName(), Drawable.class, files);
		}
	}
}

