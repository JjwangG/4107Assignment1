import java.util.Hashtable;

public class Retrieval {

    public static Hashtable<String, Double> retrieve(List<String> query){
        
        //create empty hashtable of retrieved docs and score
        //loop through unique query terms
        //take query term and find frequency in query
        //find tf-idf of query term --> (frequency / df)*idf
        //calculate cosineSim with corresponding docs idf of term and add to hashtable

    }
    
    //calculate cosine similarity of query word and doc
    public static double cosineSimilarity(double doc_weight, double q_weight, double len_doc, double len_q){

        return (doc_weight*q_weight) / (len_doc * len_q);

    }

    public static void main(String[] args){

        Hashtable<String, > inverted_index = new Hashtable<String, Integer[][]>;



    }
    
}
