package graph;

public class MiddleVertex extends Vertex {
	Edge[] edges = new Edge[4];
	
	public MiddleVertex(int x, int y) {
		super(x, y);
	}

	public void addNorth(Edge e) {
		edges[0] = e;
	}

	public void addSouth(Edge e) {
		edges[2] = e;
	}

	public void addWest(Edge e) {
		edges[3] = e;
	}

	public void addEast(Edge e) {
		edges[1] = e;
	}
	
	public Edge getNorth(Edge e) {
		return edges[0];
	}

	public Edge getEast(Edge e) {
		return edges[1];
	}
	
	public Edge getSouth(Edge e) {
		return edges[2];
	}

	public Edge getWest(Edge e) {
		return edges[3];
	}
	
	public Edge[] getEdges(){
		return edges;
	}
}
