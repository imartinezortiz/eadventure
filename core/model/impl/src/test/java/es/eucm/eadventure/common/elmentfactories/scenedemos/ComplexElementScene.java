package es.eucm.eadventure.common.elmentfactories.scenedemos;

import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdComposedElementImpl;
import es.eucm.eadventure.common.params.fills.impl.EAdBorderedColor;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;

public class ComplexElementScene extends EmptyScene {
	
	public ComplexElementScene( ){
		EAdComposedElementImpl complex = new EAdComposedElementImpl("complex");
		RectangleShape rectangle = new RectangleShape(400, 400);
		rectangle.setFill(EAdBorderedColor.BLACK_ON_WHITE);
		complex.getResources().addAsset(complex.getInitialBundle(), EAdBasicSceneElement.appearance, rectangle);
		complex.setBounds(400, 400);
		complex.setPosition(new EAdPositionImpl( 100, 100 ));
		
		this.getElements().add(complex);
	}
	
	@Override
	public String getDescription() {
		return "A scene to show complex elements";
	}
	
	public String getDemoName(){
		return "Complex Element Scene";
	}

}
