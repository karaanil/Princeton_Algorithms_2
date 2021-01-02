
public class Outcast {
    
    private final WordNet wordNet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)
    {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)
    {
        int maxDist = Integer.MIN_VALUE;
        String outcastNoun = null;
        
        for (String noun : nouns) {
            int dist = 0;
            for (String other : nouns) {
                dist += wordNet.distance(noun, other);
            }
            if (dist > maxDist) {
                outcastNoun = noun;
                maxDist = dist;
            }
        }
        return outcastNoun;
    }
    
    // see test client below
    public static void main(String[] args)
    {
        System.out.println("Sample codes here");
    }
}