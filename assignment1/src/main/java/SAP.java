import java.util.Iterator;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph digraph;
    
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
       this.digraph = new Digraph(G);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       AncestorNode node = ancestorNode(v, w);
       return node.id == -1 ? -1 : node.minDist;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
       return ancestorNode(v, w).id;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       AncestorNode node = ancestorNode(v, w);
       return node.id == -1 ? -1 : node.minDist;
   }

// a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       return ancestorNode(v, w).id;
   }
   
   private AncestorNode ancestorNode(int v, int w) {
       validateInput(v);
       validateInput(w);
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
       return ancestorNode(bfsV, bfsW);
   }

   private AncestorNode ancestorNode(Iterable<Integer> v, Iterable<Integer> w){
       validateIterable(v);
       validateIterable(w);
       if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
           return new AncestorNode(-1, -1);
       }
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
       return ancestorNode(bfsV, bfsW);
   }



   private AncestorNode ancestorNode(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW){
       int id = -1;
       int minDist = Integer.MAX_VALUE;
       
       for (int i = 0; i < digraph.V(); i++) {
           if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
               final int totalDistToVertice = bfsV.distTo(i) + bfsW.distTo(i);
               if (totalDistToVertice < minDist) { 
                   minDist = totalDistToVertice;
                   id = i;
               }
           }
       }
       return new AncestorNode(id, minDist);
   }
   
   private void validateIterable(Iterable<Integer> v) {
       if(v == null) {
           throw new IllegalArgumentException("SAP Null iterable input is not allowed");
       }
       Iterator<Integer> iter = v.iterator();
       while (iter.hasNext()) {
           validateInput(iter.next());
       }
   }
   
   private void validateInput(Integer v) {
      if (v == null) {
          throw new IllegalArgumentException("SAP Null input is not allowed");
      }
      if (v < 0 || v >= digraph.V()) {
          throw new IllegalArgumentException("SAP No such vertices exists");
      }
   }
   
   private static class AncestorNode {
       private int id;
       private int minDist;
       public AncestorNode(int id, int minDist) {
           this.id = id;
           this.minDist = minDist;
       }
   }
   
   // do unit testing of this class
   public static void main(String[] args)
   {
       System.out.println("Sample Codes here");
   }
}