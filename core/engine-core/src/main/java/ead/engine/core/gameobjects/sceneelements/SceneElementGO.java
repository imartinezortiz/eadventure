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

package ead.engine.core.gameobjects.sceneelements;

import java.util.List;

import ead.common.interfaces.features.Oriented;
import ead.common.model.elements.scenes.EAdSceneElement;
import ead.common.resources.assets.AssetDescriptor;
import ead.common.util.EAdPosition;
import ead.engine.core.gameobjects.GameObject;
import ead.engine.core.gameobjects.InputActionProcessor;
import ead.engine.core.platform.assets.RuntimeDrawable;
import ead.engine.core.util.EAdTransformation;

/**
 * 
 */
public interface SceneElementGO<T extends EAdSceneElement> extends
		GameObject<T>, Oriented, InputActionProcessor {

	/**
	 * Returns the parent of the element
	 * 
	 * @return
	 */
	SceneElementGO<?> getParent();

	/**
	 * Sets the parent for the scene element
	 * 
	 * @param parent
	 *            the parent
	 */
	void setParent(SceneElementGO<?> parent);

	/**
	 * Returns the children of this scene element
	 * 
	 * @return
	 */
	List<SceneElementGO<?>> getChildren();

	/**
	 * Returns the child for the given element
	 * 
	 * @param element
	 * @return
	 */
	SceneElementGO<?> getChild(EAdSceneElement element);

	/**
	 * Adds an scene element as a child of this element
	 * 
	 * @param sceneElement
	 */
	void addSceneElement(SceneElementGO<?> sceneElement);

	/**
	 * Remove the scene element from this element
	 * 
	 * @param sceneElement
	 */
	void removeSceneElement(SceneElementGO<?> sceneElement);

	/**
	 * Returns the game object in the given relative coordinates
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	SceneElementGO<?> getFirstGOIn(int x, int y);

	/**
	 * Returns a list with all the game objects in the given relative
	 * coordinates. The returned list shouldn't be modified nor expected to be
	 * consistent for more than update
	 * 
	 * @param x
	 * @param y
	 * @param list TODO
	 */
	void getAllGOIn(int x, int y, List<SceneElementGO<?>> list);

	/**
	 * Sets position for this element
	 * 
	 * @param position
	 */
	void setPosition(EAdPosition position);

	/**
	 * Returns the position for the drawable. Changing this position dosen't
	 * change the drawable position
	 * 
	 * @return
	 */
	EAdPosition getPosition();

	/**
	 * Sets x position for this element
	 * 
	 * @param x
	 */
	void setX(int x);

	/**
	 * Sets y position for this element
	 * 
	 * @param y
	 */
	void setY(int y);

	/**
	 * Returns x position for this element
	 * 
	 * @return
	 */
	int getX();

	/**
	 * Returns y position for this element
	 * 
	 * @return
	 */
	int getY();

	/**
	 * Returns the x coordinate of scene element center, using the scale
	 * 
	 * @return
	 */
	int getCenterX();

	/**
	 * Returns the x coordinate of scene element center, using the scale
	 * 
	 * @return
	 */
	int getCenterY();

	/**
	 * Sets scale for this element
	 * 
	 * @param scale
	 */
	void setScale(float scale);

	/**
	 * Returns the current scale of the element
	 * 
	 * @return
	 */
	float getScale();

	/**
	 * Returns the width of this element
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * Returns the height of this element
	 * 
	 * @return
	 */
	int getHeight();

	/**
	 * Sets the alpha for this element
	 * 
	 * @param alpha
	 */
	void setAlpha(float alpha);

	/**
	 * Sets if this element is enabled to receive interactions
	 * 
	 * @param b
	 */
	void setEnabled(boolean b);

	/**
	 * Collects all scene elements contained by this element
	 * 
	 * @param elements
	 */
	void collectSceneElements(List<EAdSceneElement> elements);

	/**
	 * Returns if this element is visible
	 * 
	 * @return
	 */
	boolean isVisible();

	/**
	 * Sets if this element is visible
	 * @param visible
	 */
	void setVisible(boolean visible);

	/**
	 * Sets an input processor for this element. This processor will process the
	 * actions before
	 * {@link SceneElementGO#processAction(ead.engine.core.input.InputAction)}
	 * 
	 * @param processor
	 */
	void setInputProcessor(InputActionProcessor processor);

	/**
	 * The the draggable element
	 * 
	 * @return The game object that is draggable
	 */
	SceneElementGO<?> getDraggableElement();

	/**
	 * Resets the current transformation, deleting any parent's transformation
	 * effect
	 */
	void resetTransfromation();

	/**
	 * Returns if this game object is enable for user interactions
	 * 
	 * @return if this game object is enable for user interactions
	 */
	boolean isEnable();

	/**
	 * <p>
	 * Adds the assets used by this game object to the list and returns it
	 * </p>
	 * <p>
	 * This method is used to manage memory consumed by assets, allowing the
	 * releasing or pre-caching of assets as required.
	 * </p>
	 * 
	 * @param assetList
	 *            The list where to add the assets
	 * @param allAssets
	 *            If true all assets are added, if false only required ones are
	 * @return The list of assets with the ones of this game object added
	 */
	List<AssetDescriptor> getAssets(List<AssetDescriptor> assetList,
			boolean allAssets);

	/**
	 * Returns the drawable that represents this element
	 * @return
	 */
	RuntimeDrawable<?, ?> getDrawable();

	/**
	 * Returns if this renderable contains coordinate x and y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean contains(int x, int y);

	/**
	 * Returns the transformation (translation, rotation, scale, etc.) of this
	 * game object
	 * 
	 * @return the transformation
	 */
	EAdTransformation getTransformation();

}