package ead.common.test.resources.assets;

import ead.common.params.fills.ColorFill;
import ead.common.params.fills.Paint;
import ead.common.params.paint.EAdPaint;
import ead.common.resources.assets.drawable.basics.EAdShape;
import ead.common.resources.assets.drawable.basics.shapes.BezierShape;
import ead.common.resources.assets.drawable.basics.shapes.RectangleShape;
import ead.common.test.EqualsHashCodeTest;
import java.util.ArrayList;

public class BezierShapeTest extends EqualsHashCodeTest<EAdShape> {

	@Override
	public EAdShape[] getObjects() {
		ArrayList<EAdShape> shapes = new ArrayList<EAdShape>(20);

		EAdPaint p1 = ColorFill.BLUE;
		EAdPaint p2 = Paint.BLACK_ON_WHITE;
		int i = 0;
		// Shape with the same object
		BezierShape shape = new BezierShape( );
		shape.moveTo(0, 0);
		shape.lineTo(10, 20);
		shape.lineTo(50, 60);
		shape.quadTo(50, 90, 12, 222000);
		shape.setClosed(true);
		shape.setPaint(p1);
		shapes.add(shape);
		shapes.add(shape);

		// Same shape, but not closed
		shape = new BezierShape( );
		shape.moveTo(0, 0);
		shape.lineTo(10, 20);
		shape.lineTo(50, 60);
		shape.quadTo(50, 90, 12, 222000);
		shape.setClosed(false);
		shape.setPaint(p1);
		shapes.add(shape);
		shapes.add(shape);

		// Same shape, but with another paint
		shape = new BezierShape( );
		shape.moveTo(0, 0);
		shape.lineTo(10, 20);
		shape.lineTo(50, 60);
		shape.quadTo(50, 90, 12, 222000);
		shape.setClosed(true);
		shape.setPaint(p2);
		shapes.add(shape);
		shapes.add(shape);
/*
		// Same shape, but with paint as vector with true
		shape = new BezierShape( );
		shape.moveTo(0, 0);
		shape.lineTo(10, 20);
		shape.lineTo(50, 60);
		shape.quadTo(50, 90, 12, 222000);
		shape.setClosed(true);
		shape.setPaint(p1);
		shapes.add(shape);
		shapes.add(shape);
*/
		// Shape with rectangle
		shapes.add(new RectangleShape( 800, 600, p2));
		shapes.add(new RectangleShape( 800, 600, p2));

		shape = new BezierShape( );
		shape.moveTo(0, 0);
		shape.lineTo(800, 0);
		shape.lineTo(800, 600);
		shape.lineTo(0, 600);
		shape.setClosed(true);
		shape.setPaint(p2);
		shapes.add(shape);
		shapes.add(shape);

		// devuelta
		return shapes.toArray(new EAdShape[shapes.size()]);
	}
}