package symlab.ust.hk.cache.tree;

public class TreeManager {

	private static IntermediateTree instance = null;
	
	protected TreeManager() {
	}
	
	public static IntermediateTree getInstance() {
	    if(instance == null) {
	       instance = new IntermediateTree();
	    }
	    return instance;
	}
	
}
