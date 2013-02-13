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

package ead.engine.core.platform.gdx;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import ead.common.model.assets.drawable.basics.EAdShape;
import ead.common.model.assets.text.EAdFont;
import ead.common.model.params.fills.ColorFill;
import ead.common.model.params.util.Matrix;
import ead.common.model.params.util.Rectangle;
import ead.engine.core.gdx.assets.GdxFont;
import ead.engine.core.platform.FontHandler;
import ead.engine.core.platform.assets.RuntimeDrawable;
import ead.engine.core.platform.rendering.AbstractCanvas;
import ead.tools.reflection.ReflectionProvider;

@Singleton
public class GdxCanvas extends AbstractCanvas {

	private Stack<Matrix4> matrixes;

	private GdxFont font;

	private SpriteBatch batch;

	@Inject
	public GdxCanvas(FontHandler fontHandler, ReflectionProvider provider) {
		super(fontHandler, new GdxFilterFactory(provider));
		matrixes = new Stack<Matrix4>();
	}

	public void setGraphicContext(Object g) {
		super.setGraphicContext(g);
		batch = (SpriteBatch) g;
	}

	public void setMatrix(Matrix m) {
		batch.setTransformMatrix(convertMatrix(m));
	}

	/**
	 * Creates a {@link Matrix4} compatible with Gdx equivalent to
	 * {@link EAdMatrix}
	 * 
	 * @param m
	 * @return
	 */
	public Matrix4 convertMatrix(Matrix m) {
		float[] val = new float[16];

		float[] mat = m.getFlatMatrix();

		val[0] = mat[0];
		val[1] = mat[1];
		val[2] = mat[2];
		val[3] = 0;
		val[4] = mat[3];
		val[5] = mat[4];
		val[6] = mat[5];
		val[7] = 0;
		val[8] = 0;
		val[9] = 0;
		val[10] = 1;
		val[11] = 0;
		val[12] = mat[6];
		val[13] = mat[7];
		val[14] = 0;
		val[15] = mat[8];
		return new Matrix4(val);
	}

	@Override
	public void drawShape(RuntimeDrawable<? extends EAdShape> shape) {
		shape.render(this);
	}

	@Override
	public void drawText(String text, int x, int y) {
		y -= font.getBitmapFont().getAscent();
		x += font.getBitmapFont().getSpaceWidth() / 2;
		// Border
		if (paint.getBorder() instanceof ColorFill) {
			ColorFill c = (ColorFill) paint.getBorder();
			font.getBitmapFont().setColor(c.getRed() / 255.0f,
					c.getGreen() / 255.0f, c.getBlue() / 255.0f,
					batch.getColor().a * c.getAlpha() / 255.0f);
			font.getBitmapFont().draw(batch, text, x, y);
			font.getBitmapFont().draw(batch, text, x + 1, y + 1);
			font.getBitmapFont().draw(batch, text, x - 1, y + 1);
			font.getBitmapFont().draw(batch, text, x + 1, y - 1);
			font.getBitmapFont().draw(batch, text, x - 1, y - 1);
		}

		if (paint.getFill() instanceof ColorFill) {
			ColorFill c = (ColorFill) paint.getFill();
			font.getBitmapFont().setColor(c.getRed() / 255.0f,
					c.getGreen() / 255.0f, c.getBlue() / 255.0f,
					batch.getColor().a * c.getAlpha() / 255.0f);
			font.getBitmapFont().draw(batch, text, x, y);
		}
	}

	@Override
	public void setFont(EAdFont font) {
		this.font = (GdxFont) fontHandler.get(font);

	}

	@Override
	public void setClip(Rectangle rectangle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		matrixes.push(batch.getTransformMatrix().cpy());
	}

	@Override
	public void restore() {
		Matrix4 m = matrixes.pop();
		batch.setTransformMatrix(m);
	}

	@Override
	public void translate(int x, int y) {
		batch.setTransformMatrix(batch.getTransformMatrix().translate(x, y, 0));

	}

	@Override
	public void scale(float scaleX, float scaleY) {
		batch.setTransformMatrix(batch.getTransformMatrix().scale(scaleX,
				scaleY, 1));

	}

	@Override
	public void rotate(float angle) {
		batch.setTransformMatrix(batch.getTransformMatrix().rotate(0, 0, 1,
				angle));
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

}