
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
        int minDist = Integer.MAX_VALUE;
        String outcastNoun = null;
        
        for (String noun : nouns) {
            int dist = 0;
            for (String other : nouns) {
                dist += wordNet.distance(noun, other);
            }
            if (dist < minDist) {
                outcastNoun = noun;
                minDist = dist;
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