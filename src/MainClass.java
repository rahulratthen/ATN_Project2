/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahulrdhanendran
 */
public class MainClass {
    public static void main(String[] args) {
        Graph g = new Graph(5,0.1);
        Edge e = new Edge(g.vertices.get(0), g.vertices.get(4), 0.1, 1);
        g.vertices.get(0).incidentEdges.remove(e);
        g.computePaths(g.vertices.get(0));
        for(int i=0;i<5;i++)
            System.out.println(g.getShortestPathTo(g.vertices.get(i)));
    }
    
}
