package graph;

public class TargetVertex extends Vertex{
	private Edge edge;
	
	
	public TargetVertex(int x, int y) {
		super(x, y);
	}
	
	public void addSouth(Edge e){
		this.edge = e;
	}
	
	public Edge getEdge(){
		return edge;
	}
}
