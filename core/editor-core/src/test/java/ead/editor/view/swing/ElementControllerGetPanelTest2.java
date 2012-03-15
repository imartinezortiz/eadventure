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

package ead.editor.view.swing;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import static org.mockito.Mockito.*;

import ead.common.model.elements.scene.EAdSceneElementDef;
import ead.common.params.text.EAdString;
import ead.common.util.StringHandler;
import ead.editor.EditorStringHandler;
import ead.editor.control.CommandManager;
import ead.editor.control.ElementController;
import ead.editor.control.elements.EAdSceneElementDefController;
import ead.editor.view.ComponentProvider;
import ead.editor.view.generics.Panel;
import ead.editor.view.swing.SwingProviderFactory;
import ead.gui.EAdFrame;
import ead.gui.EAdGUILookAndFeel;

public class ElementControllerGetPanelTest2 extends EAdFrame {
//
//	private static final long serialVersionUID = 1L;
//
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(EAdGUILookAndFeel.getInstance());
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
//		new ElementControllerGetPanelTest2();
//	}
//
//    public ElementControllerGetPanelTest2() {
//        this.setSize( 600,400 );
//
//        //this.setLayout(new BorderLayout());
//
//        EAdSceneElementDef sceneElementDef = mock(EAdSceneElementDef.class);
//        when(sceneElementDef.getName()).thenReturn(EAdString.newRandomEAdString("testName"));
//        when(sceneElementDef.getDoc()).thenReturn(EAdString.newRandomEAdString("testDocumentation"));
//        when(sceneElementDef.getDesc()).thenReturn(EAdString.newRandomEAdString("testDescription"));
//        when(sceneElementDef.getDetailDesc()).thenReturn(EAdString.newRandomEAdString("testDetailedDescription"));
//
//        CommandManager commandManager = mock(CommandManager.class);
//
//        EAdSceneElementDefController sceneController = new EAdSceneElementDefController();
//        sceneController.setElement(sceneElementDef);
//        Panel panel = sceneController.getPanel(ElementController.View.EXPERT);
//
//        StringHandler stringHandler = new EditorStringHandler();
//
//        SwingProviderFactory swingProviderFactory = new SwingProviderFactory(stringHandler, commandManager);
//        ComponentProvider<Panel, JComponent> componentProvider = swingProviderFactory.getProvider(panel);
//        this.add(componentProvider.getComponent(panel), BorderLayout.CENTER);
//
//        this.setVisible( true );
//        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
//    }

}