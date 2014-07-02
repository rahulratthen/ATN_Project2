
import java.util.*;

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
    
    public HashSet<String> combinations;
    
    public MainClass()
    {
        combinations = new HashSet<>();
    }
    
    public void getAllCombinations(int []array, int length, String curr)
    {
        if(curr.length() == length) {
            //System.out.println(curr);
            char[] chars = curr.toCharArray();
            Arrays.sort(chars);
            combinations.add(new String(chars));
        // Else add each letter from the alphabet to new strings and process these new strings again
        } else {
            for(int i = 0; i < array.length; i++) {
                String oldCurr = curr;
                if(curr.contains(Character.toString(Character.forDigit(array[i],10))))
                    continue;
                curr += array[i];
                getAllCombinations(array,length,curr);
                curr = oldCurr;
            }
        }
    }
    
    public static void main(String[] args) {
        Graph g = new Graph(5,0.1);
        MainClass m = new MainClass();
        int a[] = {0,1,2,3,4};
        m.getAllCombinations(a, 4, "");
        System.out.println(m.combinations);
    /*    Edge e = new Edge(g.vertices.get(0), g.vertices.get(4), 0.1, 1);
        g.vertices.get(0).incidentEdges.remove(e);
        g.computePaths(g.vertices.get(0));
        for(int i=0;i<5;i++)
            System.out.println(g.getShortestPathTo(g.vertices.get(i)));
    */
            }
    
}
