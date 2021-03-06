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

package es.eucm.ead.model.elements.trajectories;

import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.elements.scenes.SceneElement;
import es.eucm.ead.model.interfaces.Element;
import es.eucm.ead.model.interfaces.Param;

/**
 * 
 * Trajectory based on nodes and sides, originally developed in e-Adventure 1.X
 * 
 */
@Element
public class NodeTrajectory extends Trajectory {

	/**
	 * Variable's definition for whether a barrier is on or not
	 */
	public static final String VAR_BARRIER_ON = "barrierOn";

	public static final String VAR_INFLUENCE_AREA = "influence_area";

	/**
	 * Variable for the current side
	 */
	public static final String VAR_CURRENT_SIDE = "currentSide";

	@Param
	private EAdList<Node> nodes;

	@Param
	private EAdList<Side> sides;

	@Param
	private EAdList<SceneElement> barriers;

	@Param
	private Node initial;

	public NodeTrajectory() {
		nodes = new EAdList<Node>();
		sides = new EAdList<Side>();
		barriers = new EAdList<SceneElement>();
		initial = null;
	}

	public Node addNode(String id, int x, int y, float scale) {

		Node node = new Node(id, x, y, scale);
		if (nodes.contains(node)) {
			node = nodes.get(nodes.indexOf(node));
		} else {
			nodes.add(node);
		}
		if (nodes.size() == 1) {
			initial = nodes.get(0);
		}
		return node;
	}

	public Side addSide(String idStart, String idEnd, float length) {

		if (idStart.equals(idEnd))
			return null;
		Node a = getNodeForId(idStart);
		Node b = getNodeForId(idEnd);
		Side side = new Side(a, b);
		if (a != null && b != null) {
			int x = a.getX() - b.getX();
			int y = a.getY() - b.getY();
			if (length == -1)
				side.setLength((float) Math.sqrt(x * x + y * y));
			else
				side.setLength(length);
			side.setRealLength((float) Math.sqrt(x * x + y * y));
		}

		if (sides.contains(side)) {
			return null;
		} else {
			sides.add(side);
		}
		return side;
	}

	public Node getNodeForId(String id) {

		if (id == null)
			return null;
		for (Node node : nodes) {
			if (id.equals(node.getNodeId()))
				return node;
		}
		return null;
	}

	public void setInitial(String id) {
		initial = getNodeForId(id);
	}

	public Node getInitial() {
		return initial;
	}

	public void setInitial(Node initial) {
		this.initial = initial;
	}

	public EAdList<Node> getNodes() {

		return nodes;
	}

	public EAdList<Side> getSides() {

		return sides;
	}

	public void addBarrier(SceneElement barrier) {
		barriers.add(barrier);
	}

	public EAdList<SceneElement> getBarriers() {
		return barriers;
	}

	public void setNodes(EAdList<Node> nodes) {
		this.nodes = nodes;
	}

	public void setSides(EAdList<Side> sides) {
		this.sides = sides;
	}

	public void setBarriers(EAdList<SceneElement> barriers) {
		this.barriers = barriers;
	}

}
