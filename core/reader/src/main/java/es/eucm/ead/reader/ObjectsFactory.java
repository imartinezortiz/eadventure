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

package es.eucm.ead.reader;

import es.eucm.ead.model.elements.BasicElement;
import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.elements.extra.EAdMap;
import es.eucm.ead.model.elements.operations.ElementField;
import es.eucm.ead.model.interfaces.features.Identified;
import es.eucm.ead.model.params.EAdParam;
import es.eucm.ead.model.params.fills.ColorFill;
import es.eucm.ead.model.params.fills.LinearGradientFill;
import es.eucm.ead.model.params.fills.Paint;
import es.eucm.ead.model.params.guievents.DragGEv;
import es.eucm.ead.model.params.guievents.KeyGEv;
import es.eucm.ead.model.params.guievents.MouseGEv;
import es.eucm.ead.model.params.paint.EAdPaint;
import es.eucm.ead.model.params.text.EAdString;
import es.eucm.ead.model.params.util.Matrix;
import es.eucm.ead.model.params.util.Position;
import es.eucm.ead.model.params.util.Rectangle;
import es.eucm.ead.reader.model.ReaderVisitor;
import es.eucm.ead.tools.reflection.ReflectionClass;
import es.eucm.ead.tools.reflection.ReflectionClassLoader;
import es.eucm.ead.tools.reflection.ReflectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ObjectsFactory {

	static private Logger logger = LoggerFactory
			.getLogger(ObjectsFactory.class);
	private ReflectionProvider reflectionProvider;
	private Map<Class<?>, Map<String, Object>> paramsMap;
	private Map<String, Identified> identified;
	private ReaderVisitor readerVisitor;

	public ObjectsFactory(ReflectionProvider reflectionProvider,
			ReaderVisitor readerVisitor) {
		this.reflectionProvider = reflectionProvider;
		this.readerVisitor = readerVisitor;
		identified = new HashMap<String, Identified>();
		paramsMap = new HashMap<Class<?>, Map<String, Object>>();
	}

	public void clear() {
		paramsMap.clear();
		identified.clear();
	}

	public Object getObjectById(String id) {
		if (!identified.containsKey(id)) {
			logger.info("Creating reference for element with id {}", id);
			identified.put(id, new BasicElement(id));
		}
		return identified.get(id);
	}

	public boolean containsIdentified(String id) {
		return identified.containsKey(id);
	}

	/**
	 * Constructs and EAdParam from its literal representation
	 *
	 * @param value text value representing the param
	 * @param clazz the parameter class
	 * @return the param
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	private EAdParam constructEAdParam(String value, Class<?> clazz) {
		EAdParam p = null;
		if (clazz.equals(EAdString.class)) {
			p = new EAdString(value);
		} else if (clazz.equals(ColorFill.class)) {
			p = new ColorFill(value);
		} else if (clazz.equals(LinearGradientFill.class)) {
			p = new LinearGradientFill(value);
		} else if (clazz.equals(Paint.class)) {
			p = new Paint(value);
		} else if (clazz.equals(Position.class)) {
			p = new Position(value);
		} else if (clazz.equals(Rectangle.class)) {
			p = new Rectangle(value);
		} else if (clazz.equals(MouseGEv.class)) {
			p = new MouseGEv(value);
		} else if (clazz.equals(KeyGEv.class)) {
			p = new KeyGEv(value);
		} else if (clazz.equals(DragGEv.class)) {
			p = new DragGEv(value);
		}
		return p;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	private Object constructSimpleParam(String value, Class<?> clazz) {
		if (clazz == String.class) {
			return value;
		} else if (clazz == Integer.class || clazz == int.class) {
			return Integer.parseInt(value);
		} else if (clazz == Boolean.class || clazz == boolean.class) {
			return value.equals("t") || value.equals("true") ? Boolean.TRUE
					: Boolean.FALSE;
		} else if (clazz == Float.class || clazz == float.class) {
			return Float.parseFloat(value);
		} else if (clazz == Character.class || clazz == char.class) {
			return value.charAt(0);
		} else if (clazz == Class.class) {
			return getClassFromName(value);
		} else if (clazz.isEnum()) {
			Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
			return Enum.valueOf(enumClass, value);
		} else if (clazz == Matrix.class) {
			return new Matrix(value);
		}
		return null;
	}

	/**
	 * Returns the class object for the given class string
	 *
	 * @param clazz the string class
	 * @return the class
	 */
	public Class<?> getClassFromName(String clazz) {
		if (clazz.equals("java.lang.Float")) {
			return Float.class;
		} else if (clazz.equals("java.lang.Integer")) {
			return Integer.class;
		} else if (clazz.equals("java.lang.Boolean")) {
			return Boolean.class;
		} else if (clazz.equals("java.lang.Class")) {
			return Class.class;
		} else if (clazz.equals("java.lang.Character")) {
			return Character.class;
		} else if (clazz.equals("java.lang.String")) {
			return String.class;
		} else {
			try {
				return ReflectionClassLoader.getReflectionClass(clazz)
						.getType();
			} catch (Exception e) {
				logger
						.warn(
								"Not match for class {}. Returning 'Object.class' instead",
								clazz);
				return Object.class;
			}
		}
	}

	/**
	 * Create an object of the given class
	 *
	 * @param clazz the class
	 * @param id    the element id
	 * @return the object created
	 */
	public Object createObject(Class<?> clazz, String id) {
		ReflectionClass<?> classType = ReflectionClassLoader
				.getReflectionClass(clazz);
		Object o = identified.get(id);
		if (o == null) {
			if (classType.getConstructor() != null) {
				o = classType.getConstructor().newInstance();
				identified.put(id, (Identified) o);
				((Identified) o).setId(id);
			} else {
				logger.warn("No empty constructor for {}", clazz);
			}
		}
		return o;
	}

	public Object getParam(String textValue, Class<?> clazz) {
		Object result = null;

		Map<String, Object> map = paramsMap.get(clazz);
		if (map == null) {
			map = new HashMap<String, Object>();
			paramsMap.put(clazz, map);
		} else {
			result = map.get(textValue);
		}

		if (result == null) {
			// Check for a EAdParam
			result = constructEAdParam(textValue, clazz);
		}

		// Check for a simple type
		if (result == null) {
			result = constructSimpleParam(textValue, clazz);
		}

		if (result == null) {
			logger.warn("No constructor for parameter class {}", clazz);
		} else {
			map.put(textValue, result);
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	public Object getObject(String value, Class<?> c) {
		Object result = null;
		if (reflectionProvider.isAssignableFrom(Identified.class, c)) {
			result = identified.get(value);
		} else if (reflectionProvider.isAssignableFrom(EAdList.class, c)) {
			EAdList list = new EAdList();
			// Remove [ and final ]
			// XXX
			String[] elements = value.substring(1, value.length() - 2).split(
					",");
			for (String e : elements) {
				if (e.length() > 0) {

				} else {
				}
			}
			result = list;
		} else if (reflectionProvider.isAssignableFrom(EAdMap.class, c)) {
			result = new EAdMap();
			// XXX
			logger.warn("OMG, a map! This needs implementation");
		} else {
			result = getParam(value, c);
			if (result == null) {
				logger.warn("Not possible to parse initial value: {}", value);
			}
		}
		return result;
	}

	public void putIdentified(Identified element) {
		identified.put(element.getId(), element);
	}

	public EAdPaint getPaint(String value) {
		if (value.contains(Paint.SEPARATOR)) {
			return (EAdPaint) getParam(value, Paint.class);
		} else if (value.contains(LinearGradientFill.SEPARATOR)) {
			return (EAdPaint) getParam(value, LinearGradientFill.class);
		} else {
			return (EAdPaint) getParam(value, ColorFill.class);
		}
	}

	public ElementField getField(String element, String varName) {
		return new ElementField(new BasicElement(element), varName);
	}
}
