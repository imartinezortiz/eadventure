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

package es.eucm.ead.engine.assets.drawables.shapes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import es.eucm.ead.engine.assets.AbstractRuntimeAsset;
import es.eucm.ead.engine.assets.AssetHandler;
import es.eucm.ead.engine.assets.drawables.RuntimeDrawable;
import es.eucm.ead.engine.canvas.GdxCanvas;
import es.eucm.ead.model.assets.drawable.basics.shapes.AbstractShape;
import es.eucm.ead.model.params.fills.ColorFill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class RuntimeShape<T extends AbstractShape> extends
		AbstractRuntimeAsset<T> implements RuntimeDrawable<T> {

	static private Logger logger = LoggerFactory.getLogger(RuntimeShape.class);

	private TextureRegion textureRegion;

	protected Pixmap pixmapContains;

	private int width = 0;

	private int height = 0;

	public RuntimeShape(AssetHandler assetHandler) {
		super(assetHandler);
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public RuntimeDrawable<?> getDrawable(int time, List<String> states,
			int level) {
		return this;
	}

	public boolean loadAsset() {
		super.loadAsset();
		Pixmap pixmap = generatePixmap();
		textureRegion = new TextureRegion(new Texture(pixmap));
		textureRegion.flip(false, true);
		setWidth(pixmap.getWidth());
		setHeight(pixmap.getHeight());
		pixmap.dispose();
		loaded = true;
		return true;
	}

	protected abstract Pixmap generatePixmap();

	@Override
	public boolean contains(int x, int y) {
		if (x > 0 && y > 0 && x < getWidth() && y < getHeight()) {
			int alpha = pixmapContains.getPixel(x, y) & 255;
			return alpha > 128;
		}
		return false;
	}

	@Override
	public void freeMemory() {
		if (isLoaded()) {
			super.freeMemory();
			if (textureRegion.getTexture() != null) {
				textureRegion.getTexture().dispose();
			}
			textureRegion = null;

			if (pixmapContains != null) {
				this.pixmapContains.dispose();
			}
			pixmapContains = null;
		}

	}

	@Override
	public void render(GdxCanvas c) {
		c.draw(textureRegion, 0, 0);
	}

	protected static float vBx, vBy;

	protected static float vMod2;

	protected static float rI, gI, bI, aI;

	protected static float rE, gE, bE, aE;

	protected static float vcr, vcg, vcb, vca;

	protected void initGradientParams(ColorFill c1, float x0, float y0,
			ColorFill c2, float x1, float y1) {
		vBx = x1 - x0;
		vBy = y1 - y0;
		vMod2 = vBx * vBx + vBy * vBy;
		rI = c1.getRed() / 255.f;
		gI = c1.getGreen() / 255.f;
		bI = c1.getBlue() / 255.f;
		aI = c1.getAlpha() / 255.f;
		rE = c2.getRed() / 255.f;
		gE = c2.getGreen() / 255.f;
		bE = c2.getBlue() / 255.f;
		aE = c2.getAlpha() / 255.f;
		vcr = rE - rI;
		vcg = gE - gI;
		vcb = bE - bI;
		vca = aE - aI;
	}

	protected void setColor(Pixmap p, float x, float y) {
		float proj = (vBx * x + vBy * y) / vMod2;
		if (proj <= 0) {
			p.setColor(rI, gI, bI, aI);
		} else if (proj >= 1) {
			p.setColor(rE, gE, bE, aE);
		} else {
			p.setColor(rI + vcr * proj, gI + vcg * proj, bI + vcb * proj, aI
					+ vca * proj);
		}
	}

	@Override
	public void refresh() {
		// Do nothing
	}

	public Texture getTextureHandle() {
		if (textureRegion != null) {
			return textureRegion.getTexture();
		}
		return null;
	}
}
