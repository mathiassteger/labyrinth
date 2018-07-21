package graph;

public class StartVertex extends Vertex{
	Edge edge;
	
	public StartVertex(int x, int y) {
		super(x, y);
		setDistanceToStart(0);
	}

	public void addNorth(Edge e){
		this.edge = e;
	}
	
	public Edge getEdge(){
		return this.edge;
	}
}
