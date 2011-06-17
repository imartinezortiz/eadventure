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

package es.eucm.eadventure.engine.core.platform.assets.impl;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;

import es.eucm.eadventure.common.model.params.EAdFont;
import es.eucm.eadventure.common.model.params.EAdFont.Style;
import es.eucm.eadventure.common.model.params.EAdRectangle;
import es.eucm.eadventure.engine.core.platform.RuntimeFont;

public class DesktopEngineFont implements RuntimeFont {

	private EAdFont eadFont;

	private Font font;

	private static FontRenderContext frc = new FontRenderContext(null, true,
			true);
	
	public DesktopEngineFont(EAdFont font) {
		this.eadFont = font;
		if (font == null)
			this.font = Font.getFont(Font.DIALOG);
		else
			this.font = new Font(font.getName(), getStyle(font.getStyle()),
					(int) font.getSize());
	}

	private int getStyle(Style style) {
		switch (style) {
		case BOLD:
			return Font.BOLD;
		case ITALIC:
			return Font.ITALIC;
		case PLAIN:
		default:
			return Font.PLAIN;
		}
	}

	public Font getFont() {
		return font;
	}

	@Override
	public EAdFont getEAdFont() {
		return eadFont;
	}

	@Override
	public int stringWidth(String string) {
		return (int) font.getStringBounds(string, frc).getWidth();
	}

	@Override
	public int lineHeight() {
		return (int) font.getLineMetrics("nothing", frc).getHeight();
	}

	@Override
	public EAdRectangle stringBounds(String string) {
		Rectangle r = font.getStringBounds(string, frc).getBounds();
		return new EAdRectangle(r.x, r.y, r.width, r.height);
	}

}