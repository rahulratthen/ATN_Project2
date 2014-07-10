
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
    public ArrayList<TableEntry> table;
    
    public MainClass()
    {
        table = new ArrayList<>();
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
    
    public double getReliability(double prob, int scenario)
    {
        table.clear();
        int a[] = new int[10];
        
        for(int i=0;i<10;i++)
        {
            a[i]=i;
        }
        for(int i=0;i<10;i++)
        {
            combinations.clear();
            getAllCombinations(a, i , "");
            if(combinations.size()==1)
            {
                //First case when all edges are active
                table.add(new TableEntry());
            }
            else
            {
                for(String s : combinations)
                {
                    Graph g = new Graph(5,prob);
                    TableEntry t = new TableEntry();
                    char badEdge[] = s.toCharArray();
                    for(int j=0;j<s.length();j++)
                    {
                        int index = Character.getNumericValue(badEdge[j]);
                        t.edgeNumber[index] = 0; //set as bad edge
                        Edge e = g.edges.get(index);
                        g.vertices.get(Character.getNumericValue(e.vertexA.name.charAt(1))).incidentEdges.remove(e);
                        g.vertices.get(Character.getNumericValue(e.vertexB.name.charAt(1))).incidentEdges.remove(e);
                    }
                    g.computePaths(g.vertices.get(0));
                    for(int k=0;k<g.vertices.size();k++)
                    {
                        List<Vertex> path = g.getShortestPathTo(g.vertices.get(k));
                        if(path.get(path.size()-1).minDistance == Double.POSITIVE_INFINITY)
                        {
                            t.state = false;
                            break;
                        }
                    }
                    table.add(t);
                }
            }
        }
        TableEntry last = new TableEntry();
        last.state = false;
        for(int m=0;m<10;m++)
        {
            last.edgeNumber[m]=0;
        }
        table.add(last);
        
        //print the table
        //printTable(table);
        
        if(scenario == 2)
        {
            flipAndCalculate(table,prob);
            
        }
        
        return calculateReliability(table, prob);
        
    }
    
    public void flipAndCalculate(ArrayList<TableEntry> t, double prob)
    {
        for(int k=0;k<100;k++)
        {
            double reliability = 0.0;
            for(int n=0;n<100;n++)
            {
                HashSet<Integer> randIndices = getRandomIndices(k);
                //flip
                for(int index : randIndices)
                {
                    t.get(index).state = !t.get(index).state;
                }
                //calculate
                reliability += calculateReliability(t, prob);
                //flip it back to original
                for(int index : randIndices)
                {
                    t.get(index).state = !t.get(index).state;
                }
            }
            System.out.println("Reliability when p=0.95 and k="+k+" is "+(double)Math.round(reliability/100.0*10000)/10000);
            //System.out.println((double)Math.round(reliability/100.0*10000)/10000);
        }
        
        //printTable(t);
        System.exit(0);
    }
    
    public HashSet getRandomIndices(int i)
    {
        HashSet<Integer> hs = new HashSet<Integer>();
        Random rand = new Random();
        do
        {
            int index = rand.nextInt(1024);
            if(!hs.contains(new Integer(index)))
                hs.add(new Integer(index));
        }while(hs.size()<i);
        return hs;
    }
    
    public double calculateReliability(ArrayList<TableEntry> t, double prob)        
    {
        double reliability = 0.0;
        for(TableEntry te : t)
        {
            if(te.state)
            {
                double product=1.0;
                for(int i=0;i<10;i++)
                {
                    if(te.edgeNumber[i]==0)
                     product = product*(1-prob);
                    else
                     product = product*prob;
                }
                reliability += product;      
            }
        }
        
        
        return reliability;
    }
            
    public void printTable(ArrayList<TableEntry> t)
    {
        int i=1;
        int tr=0;
        for(TableEntry te : t)
        {
            if(te.state)
                tr++;
        }
        System.out.println("Total true : "+tr);
        //Now all states are calculated
        for(TableEntry te : t)
        {
            System.out.println(i+++"   "+te.edgeNumber[0]+""+te.edgeNumber[1]+""+te.edgeNumber[2]+""+te.edgeNumber[3]+""+te.edgeNumber[4]+""+te.edgeNumber[5]+""+te.edgeNumber[6]+""+te.edgeNumber[7]+""+te.edgeNumber[8]+""+te.edgeNumber[9]+"   "+te.state);
        }
    }
    
    public static void main(String[] args) {
        MainClass m = new MainClass();
        /*for(double i=0.0;i<=1;i+=0.01)
        {
            System.out.println("Reliability when p = "+(double)Math.round(i*100)/100+" : "+(double)Math.round(m.getReliability((double)Math.round(i*100)/100,1)*1000)/1000);
            //System.out.println((double)Math.round(m.getReliability((double)Math.round(i*100)/100,1)*1000)/1000);
        }
        */
        //scenario 2
        m.getReliability(0.95, 2);
            
        
        /*    int a[] = {0,1,2,3,4};
        m.getAllCombinations(a, 4, "");
        System.out.println(m.combinations);
        Edge e = new Edge(g.vertices.get(0), g.vertices.get(4), 0.1, 1);
        g.vertices.get(0).incidentEdges.remove(e);
        g.computePaths(g.vertices.get(0));
        for(int i=0;i<5;i++)
            System.out.println(g.getShortestPathTo(g.vertices.get(i)));
    */
            }
    
}

class TableEntry{
    int edgeNumber[] = new int[10];
    boolean state;
    
    //default: all edges are good and the system is up.
    public TableEntry()
    {
        state = true;
        for(int i=0;i<10;i++)
        {
            edgeNumber[i]=1;
        }
    }
}
