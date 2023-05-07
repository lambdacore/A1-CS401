
public class QuickUnion implements UnionFind {
	
	protected int id[];
	protected int count;
	
	public QuickUnion(int n) {
		id = new int[n];
		count = n;
		
		initialize();
	}
	private void initialize() {
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
	}
	public int count(){
		return count;
	}
	@Override
	public void union(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);
		
		if(pRoot == qRoot)
			return;
		
		id[pRoot] = qRoot;
		
		count--;
		
	}
	@Override
	public boolean isConnected(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);
		return pRoot == qRoot;
	}
	@Override
	public int find(int p) {
		return root(p);
	}
	@Override
	public int getConnectedComponentCount() {
		return count;
	}
	protected int root(int p) {
		if (p < 0 || p >= id.length) {
			throw new IllegalArgumentException("Index " + p + " is out of bounds");
		}
		while(id[p] != p) {
			p = id[p];
		}
		
		return p;
	}
}
