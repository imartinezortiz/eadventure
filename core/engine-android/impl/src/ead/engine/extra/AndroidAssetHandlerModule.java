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

package ead.engine.extra;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import ead.common.resources.StringHandler;
import ead.common.resources.assets.AssetDescriptor;
import ead.common.resources.assets.drawable.basics.Caption;
import ead.common.resources.assets.drawable.basics.CaptionImpl;
import ead.common.resources.assets.drawable.basics.ImageImpl;
import ead.common.resources.assets.drawable.basics.SpriteImageImpl;
import ead.common.resources.assets.drawable.basics.shapes.BezierShape;
import ead.common.resources.assets.drawable.basics.shapes.RectangleShape;
import ead.common.resources.assets.drawable.compounds.ComposedDrawable;
import ead.common.resources.assets.drawable.compounds.ComposedDrawableImpl;
import ead.common.resources.assets.drawable.compounds.DisplacedDrawable;
import ead.common.resources.assets.drawable.compounds.DisplacedDrawableImpl;
import ead.common.resources.assets.drawable.filters.FilteredDrawable;
import ead.common.resources.assets.drawable.filters.FilteredDrawableImpl;
import ead.common.resources.assets.multimedia.Sound;
import ead.common.resources.assets.multimedia.SoundImpl;
import ead.common.resources.assets.multimedia.Video;
import ead.engine.AndroidAssetHandler;
import ead.engine.assets.AndroidBezierShape;
import ead.engine.assets.AndroidEngineCaption;
import ead.engine.assets.AndroidEngineImage;
import ead.engine.assets.AndroidSound;
import ead.engine.assets.specialassetrenderers.AndroidVideoRenderer;
import ead.engine.core.platform.AssetHandler;
import ead.engine.core.platform.RuntimeAsset;
import ead.engine.core.platform.SpecialAssetRenderer;
import ead.engine.core.platform.StringHandlerImpl;
import ead.engine.core.platform.assets.RuntimeComposedDrawable;
import ead.engine.core.platform.assets.RuntimeDisplacedDrawable;
import ead.engine.core.platform.assets.RuntimeFilteredDrawable;
import ead.engine.core.platform.assets.RuntimeSpriteImage;

public class AndroidAssetHandlerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StringHandler.class).to(StringHandlerImpl.class);
		bind(AssetHandler.class).to(AndroidAssetHandler.class);
		
		//Bind to AndroidVideoRenderer (uses API mediaplayer) or RockPlayerAndroidVideoRenderer
		bind(new TypeLiteral<SpecialAssetRenderer<Video, ?>>(){}).to(AndroidVideoRenderer.class);
	}
	
	@SuppressWarnings("unchecked")
	@Provides
	@Singleton
	Map<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<? extends AssetDescriptor>>> provideMap() {
		Map<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<? extends AssetDescriptor>>> map = new HashMap<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<? extends AssetDescriptor>>>( );
		map.put(ImageImpl.class, AndroidEngineImage.class);
		map.put(ImageImpl.class, AndroidEngineImage.class);
		map.put(Caption.class, (Class<? extends RuntimeAsset<?>>) AndroidEngineCaption.class);
		map.put(CaptionImpl.class, (Class<? extends RuntimeAsset<?>>) AndroidEngineCaption.class);
		map.put(ComposedDrawable.class, (Class<? extends RuntimeAsset<?>>) RuntimeComposedDrawable.class);
		map.put(ComposedDrawableImpl.class, (Class<? extends RuntimeAsset<?>>) RuntimeComposedDrawable.class);
		map.put(RectangleShape.class, AndroidBezierShape.class);
		map.put(BezierShape.class, AndroidBezierShape.class);
		map.put(DisplacedDrawable.class, (Class<? extends RuntimeAsset<?>>) RuntimeDisplacedDrawable.class);
		map.put(DisplacedDrawableImpl.class, (Class<? extends RuntimeAsset<?>>) RuntimeDisplacedDrawable.class);
		map.put(SpriteImageImpl.class, (Class<? extends RuntimeAsset<?>>) RuntimeSpriteImage.class);
		map.put(FilteredDrawable.class, (Class<? extends RuntimeAsset<?>>) RuntimeFilteredDrawable.class);
		map.put(FilteredDrawableImpl.class, (Class<? extends RuntimeAsset<?>>) RuntimeFilteredDrawable.class);
		map.put(Sound.class, AndroidSound.class);
		map.put(SoundImpl.class, AndroidSound.class);
		//TODO Sprite image
		//map.put(SpriteImageImpl.class, AndroidEngineSpriteImage.class);
		
		return map;
	}

	
	
}
