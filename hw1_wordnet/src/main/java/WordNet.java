
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

public class WordNet {
    private static final String delimeter = ","; 
    
    private Digraph digraph;
    // A Noun can have multiple nodes i.e. ID's with different descriptions!
    private final ST<String, Bag<Integer>> nounST = new ST<String, Bag<Integer>>();
    private final ST<Integer, String> synsetST = new ST<Integer, String>();
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        validateNull(synsets, hypernyms, "WordNet constructor null input");
        
        initializeSynsets(synsets);
        initializeDigraph(hypernyms);
        
        checkDAG();
        checkRooted();
    }

    private void initializeSynsets(String synsets) {
        In in = new In(synsets);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] items = line.split(delimeter);
            
            int id = Integer.valueOf(items[0]);
            String[] nouns = items[1].split(" ");
            synsetST.put(id, items[1]);
            for (String noun : nouns) {
                if (!nounST.contains(noun)) {
                    nounST.put(noun, new Bag<Integer>());
                }
                nounST.get(noun).add(id);
            }
        }
    }
    
    private void initializeDigraph(String hypernyms) {
        In in = new In(hypernyms);
        String[] allLines = in.readAllLines();
        digraph = new Digraph(allLines.length);
        
        for (String line : allLines) {
            String[] ids = line.split(delimeter);
            int v = Integer.valueOf(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                digraph.addEdge(v, Integer.valueOf(ids[i]));
            }
        }
        sap = new SAP(digraph);
    }
    
    private void checkDAG() {
        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle()) {
            throw new IllegalArgumentException("WordNet hypernyms is not a DAG");
        }
    }
    
    private void checkRooted() {
        int root = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (!digraph.adj(i).iterator().hasNext()) {
                if (root == -1) { root = i; }
                else { throw new IllegalArgumentException("Multiple roots " + root + ";" + i); }
            }
        }
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return nounST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        validateNull(word, "WordNet isNoun null input");
        
        return nounST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        validateNull(nounA, nounB, "WorNet sap null input");
        
        Bag<Integer> idA = nounST.get(nounA);
        Bag<Integer> idB = nounST.get(nounB);
        
        validateNull(idA, idB, "WordNet sap nouns does not exits in synset");
        
        return sap.length(nounST.get(nounA), nounST.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        validateNull(nounA, nounB, "WorNet sap null input");

        Bag<Integer> idA = nounST.get(nounA);
        Bag<Integer> idB = nounST.get(nounB);
        
        validateNull(idA, idB, "WordNet sap nouns does not exits in synset");
        
        int ancestorID = sap.ancestor(idA, idB);
        return synsetST.get(ancestorID);
    }
    
    private <T> void validateNull(T x, String message)
    {
        if (x == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    private <T> void validateNull(T x, T y, String message)
    {
        if (x == null || y == null) {
            throw new IllegalArgumentException(message);
        }
    }

    // do unit testing of this class
    public static void main(String[] args)
    {
        System.out.println("Sample Codes here");
    }
}