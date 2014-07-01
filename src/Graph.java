
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahulrdhanendran
 */

//This class creates a complete graph for the given number of vertices
public class Graph {
      
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    
    public Graph(int n, double p)
    {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        for(int i=0; i<n ; i++)
        {
            Vertex v = new Vertex("V"+i);
            v.incidentEdges = new ArrayList<>();
            vertices.add(v);
        }
        for(int i=0; i<n ; i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i==j)
                    continue;
                Edge e=new Edge(vertices.get(i),vertices.get(j),p,1);
                   
                
                if(!edges.contains(e))
                {
                    edges.add(e);
                }
                vertices.get(i).incidentEdges.add(e);
                    
            }
        }
        
        
    }
    
    public void computePaths(Vertex source)
    {
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.incidentEdges)
            {
                Vertex v = e.vertexB;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
        
        
    }

    public List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
}

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public ArrayList<Edge> incidentEdges;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(int n)
    {
        name = Integer.toString(n);
    }
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge 
{
    public final Vertex vertexA;
    public final Vertex vertexB;
    public final double weight;
    public final double probability;
    public Edge(Vertex a,Vertex b,double p, double argWeight)
    { 
        vertexA = a;
        vertexB = b;
        weight = argWeight;
        probability = p;
    }

    @Override
    public boolean equals(Object o) {
        Edge o1 = (Edge) o;
        if((o1.vertexA==this.vertexA && o1.vertexB==this.vertexB) || (o1.vertexA==this.vertexB && o1.vertexB==this.vertexA) )
            return true;
            
    return false;
    }
    
    @Override
    public int hashCode(){
        return 1;
    }
}
