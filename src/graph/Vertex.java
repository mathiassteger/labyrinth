package graph;

public class Vertex implements Comparable<Vertex>{
	int x, y;
	private int distanceToStart = Integer.MAX_VALUE;
	private Vertex predecessor = null;
	
	public Vertex(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	@Override
	public String toString(){
		return ("(" + x + ", " + y + ")");
	}
	
	@Override 
	public boolean equals(Object o){
		Vertex v = (Vertex) o;
		return v.getX() == this.x && v.getY() == this.y;
	}

	@Override
	public int compareTo(Vertex o) {
		Vertex v = (Vertex) o;
		
		if(v.getX() > x)
			return 1;
		else if (v.getX() < x)
			return -1;
		else
			return 0;
	}

	public void setDistanceToStart(int distance){
		this.distanceToStart = distance;
	}
	
	public void setPredecessor(Vertex v){
		this.predecessor = v;
	}

	public int getDistanceToStart(){
		return distanceToStart;
	}
	
	public Vertex getPredecessor(){
		return predecessor;
	}
}
