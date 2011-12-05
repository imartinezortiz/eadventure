package es.eucm.eadventure.common.impl.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.extra.EAdList;

public class DepthManager {

	private List<EAdList<Object>> lists;
	
	private List<EAdElement> stored;
	
	private Map<String, String> classAliases;

	private Map<String, String> aliasMap;
	
	private static final boolean USE_DEPTH_CONTROL = true;
	
	private static final int MAX_LEVEL = 4;
	
	EAdList<EAdElement> depthControlList;
	
	private int level;
	
	public DepthManager(EAdList<EAdElement> eAdList) {
		lists = new ArrayList<EAdList<Object>>();
		stored = new ArrayList<EAdElement>();
		classAliases = new HashMap<String, String>();
		aliasMap = new HashMap<String, String>();
		depthControlList = eAdList;
		depthControlList.clear();
	}
	
	public void addList(EAdList<Object> list) {
		lists.add(list);
	}
	
	public void removeList(EAdList<Object> list) {
		lists.remove(list);
	}

	public Map<String, String> getClassAliases() {
		return classAliases;
	}
	
	public boolean inPreviousList(EAdElement o) {
		if (USE_DEPTH_CONTROL) {
			for (int i = 0; i < lists.size() - 1; i++)
				if (lists.get(i).contains(o))
					return true;
			
			if (level > MAX_LEVEL) {
				depthControlList.add(o);
				return true;
			}
		}
		return false;
	}
	
	public boolean isStored(EAdElement o) {
		return stored.contains(o);
	}
	
	public void setStored(EAdElement o) {
		stored.add(o);
	}

	public Map<String, String> getAliasMap() {
		return aliasMap;
	}

	public EAdElement getInstanceOfElement(EAdElement element) {
		return stored.get(stored.indexOf(element));
	}
	
	public void levelUp() {
		level++;
	}
	
	public void levelDown() {
		level--;
	}

	public void clear() {
		lists.clear();
		stored.clear();
		classAliases.clear();
		aliasMap.clear();
		depthControlList.clear();
	}
}