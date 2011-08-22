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

package es.eucm.eadventure.engine.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.elements.EAdActor;
import es.eucm.eadventure.common.model.elements.EAdChapter;
import es.eucm.eadventure.common.model.elements.EAdScene;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.EffectGO;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.SceneGO;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;

@Singleton
public class GameStateImpl implements GameState {

	private SceneGO<?> scene;

	private List<EffectGO<?>> effects;

	private ValueMap valueMap;

	private GameObjectFactory gameObjectFactory;

	private Stack<EAdScene> previousSceneStack;

	private List<EAdActor> removedActors;

	private List<EAdActor> inventoryActors;

	private EAdChapter currentChapter;
	
	private EAdSceneElement activeElement;

	/**
	 * Queue for effects added
	 */
	private List<EAdEffect> effectsQueue;
	
	/**
	 * Queue for the actions linked to effects
	 */
	private List<GUIAction> actionsQueue;

	private static Logger logger = Logger.getLogger("GameState");

	private boolean paused;

	@Inject
	public GameStateImpl(@Named("LoadingScreen") EAdScene loadingScreen,
			GameObjectFactory gameObjectFactory, ValueMap valueMap) {
		effects = new ArrayList<EffectGO<?>>();
		effectsQueue = Collections.synchronizedList(new ArrayList<EAdEffect>());
		actionsQueue = new ArrayList<GUIAction>();
		this.scene = (SceneGO<?>) gameObjectFactory.get(loadingScreen);
		this.valueMap = valueMap;
		this.gameObjectFactory = gameObjectFactory;
		this.previousSceneStack = new Stack<EAdScene>();
		removedActors = new ArrayList<EAdActor>();
		inventoryActors = new ArrayList<EAdActor>();
	}

	public SceneGO<?> getScene() {
		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#setScene(es.eucm.eadventure.
	 * engine.core.gameobjects.SceneGO)
	 */
	@Override
	public void setScene(SceneGO<? extends EAdScene> newScene) {
		if (this.scene != null && this.scene.getElement() != null){
			valueMap.setValue(this.scene.getElement().sceneLoaded(), Boolean.FALSE);
			if (scene.getElement().isReturnable())
				previousSceneStack.push(scene.getElement());
		}
		this.scene = newScene;
		if (this.scene != null && this.scene.getElement() != null)
			valueMap.setValue(newScene.getElement().sceneLoaded(), Boolean.TRUE);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getEffects()
	 */
	@Override
	public List<EffectGO<?>> getEffects() {
		return effects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getValueMap()
	 */
	@Override
	public ValueMap getValueMap() {
		return valueMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#addEffect(es.eucm.eadventure
	 * .common.model.effects.EAdEffect)
	 */
	@Override
	synchronized public void addEffect(EAdEffect e, GUIAction action) {
		addEffect(effectsQueue.size(), e, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#addEffect(int,
	 * es.eucm.eadventure.common.model.effects.EAdEffect)
	 */
	@Override
	// TODO consider leaving effect initilization for later
	synchronized public void addEffect(int pos, EAdEffect e, GUIAction action) {
		effectsQueue.add(pos, e);
		actionsQueue.add(action);
	}

	@Override
	public EAdScene getPreviousScene() {
		return previousSceneStack.pop();
	}

	@Override
	public List<EAdActor> getRemovedActors() {
		return removedActors;
	}

	@Override
	public List<EAdActor> getInventoryActors() {
		return inventoryActors;
	}

	@Override
	public EAdChapter getCurrentChapter() {
		return currentChapter;
	}

	@Override
	public void setCurrentChapter(EAdChapter currentChapter) {
		this.currentChapter = currentChapter;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public void updateEffectsQueue() {
		int i = 0;
		for (EAdEffect e : effectsQueue) {
			@SuppressWarnings("rawtypes")
			EffectGO effectGO = (EffectGO) gameObjectFactory.get(e);
			effectGO.setGUIAction(actionsQueue.get(i++));
			if (!effects.contains(effectGO)) {
				effectGO.setElement(e);
				effects.add(effectGO);
			}
			logger.info("Added " + effectGO);
		}
		effectsQueue.clear();
		actionsQueue.clear();
		
		
	}

	@Override
	public void addEffect(EAdEffect e) {
		this.addEffect(e, null);
	}

	@Override
	public EAdSceneElement getActiveElement() {
		return activeElement;
	}

	@Override
	public void setActiveElement(EAdSceneElement activeElement) {
		this.activeElement = activeElement;	
	}

}
