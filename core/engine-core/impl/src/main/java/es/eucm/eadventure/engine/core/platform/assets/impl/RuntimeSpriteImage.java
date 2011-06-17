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

import java.util.logging.Logger;

import com.google.inject.Inject;

import es.eucm.eadventure.common.resources.assets.drawable.Drawable;
import es.eucm.eadventure.common.resources.assets.drawable.SpriteImage;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.DrawableAsset;

/**
 * Represents a runtime engine sprite image, associated with an {@link AssetDescritpor}
 * 
 */
public abstract class RuntimeSpriteImage implements DrawableAsset<SpriteImage> {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger("RuntimeSpriteImage");

	/**
	 * The image asset descriptor
	 */
	protected SpriteImage assetDescriptor;

	/**
	 * The asset handler
	 */
	protected AssetHandler assetHandler;
	
	private int rows;
	
	private int cols;
	
	@Inject 
	public RuntimeSpriteImage(AssetHandler assetHandler ){
		this.assetHandler = assetHandler;
		logger.info("New instance");
	}
	
	@Override
	public void setDescriptor(SpriteImage descriptor) {
		this.assetDescriptor = (SpriteImage) descriptor;
		this.rows = (int) Math.sqrt(assetDescriptor.getTotalSprites());
		this.cols = (int) Math.sqrt(assetDescriptor.getTotalSprites());
	}

	@Override
	public void update(GameState state) {		
		assetHandler.getRuntimeAsset(assetDescriptor.getImage()).update(state);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends Drawable> DrawableAsset<S> getDrawable() {
		return (DrawableAsset<S>) this;
	}
	
	public int getWidth() {
		return ((RuntimeImage) assetHandler.getRuntimeAsset(assetDescriptor.getImage())).getWidth() / rows;
	}

	public int getHeight() {
		return ((RuntimeImage) assetHandler.getRuntimeAsset(assetDescriptor.getImage())).getHeight() / cols;
	}

	@Override
	public boolean loadAsset() {
		assetHandler.getRuntimeAsset(assetDescriptor.getImage()).loadAsset();
		return assetHandler != null;
	}

	@Override
	public void freeMemory() {
		assetHandler.getRuntimeAsset(assetDescriptor.getImage()).freeMemory();
	}

	@Override
	public boolean isLoaded() {
		return assetHandler.getRuntimeAsset(assetDescriptor.getImage()).isLoaded();
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}

}