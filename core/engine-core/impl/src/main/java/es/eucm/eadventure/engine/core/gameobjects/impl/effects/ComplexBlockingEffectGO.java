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

package es.eucm.eadventure.engine.core.gameobjects.impl.effects;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.effects.impl.EAdComplexBlockingEffect;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.evaluators.EvaluatorFactory;
import es.eucm.eadventure.engine.core.gameobjects.SceneElementGO;
import es.eucm.eadventure.engine.core.gameobjects.factories.SceneElementGOFactory;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.util.EAdTransformation;

public class ComplexBlockingEffectGO extends
		AbstractEffectGO<EAdComplexBlockingEffect> {
	
	private EvaluatorFactory evaluatorFactory;

	@Inject
	public ComplexBlockingEffectGO(AssetHandler assetHandler,
			StringHandler stringHandler, SceneElementGOFactory gameObjectFactory,
			GUI gui, GameState gameState, EvaluatorFactory evaluatorFactory ) {
		super(assetHandler, stringHandler, gameObjectFactory, gui, gameState);
		this.evaluatorFactory = evaluatorFactory;
	}
	
	public void initilize( ){
		super.initilize();
		for ( EAdEffect e: element.getInitEffects() ){
			gameState.addEffect(e);
		}
	}
	
	public boolean processAction(GUIAction action) {
		if ( element.isOpaque() ){
			action.consume();
			return true;
		}
		return false;
		
	}

	@Override
	public void doLayout(EAdTransformation t) {
		for (EAdSceneElement e : element.getComponents()) {
			SceneElementGO<?> go = sceneElementFactory.get(e);
			gui.addElement(go, t);
		}

	}

	@Override
	public boolean isVisualEffect() {
		return true;
	}

	@Override
	public boolean isFinished() {
		return evaluatorFactory.evaluate(element.getEndCondition());
	}

	@Override
	public void update() {
		super.update();
		for (EAdSceneElement e : element.getComponents()) {
			sceneElementFactory.get(e).update();
		}
	}

	public void finish() {
		super.finish();
		for (EAdSceneElement e : element.getComponents()) {
			gameState.getValueMap().remove(e);
			sceneElementFactory.remove(e);
		}

	}
	
	public boolean contains(int x, int y) {
		return element.isOpaque();
	}
}
