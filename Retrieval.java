import java.util.*;

public class Retrieval {

    public static HashMap<String, Double> retrieve(HashMap<String, HashMap<String, Integer>> inverted_index, HashMap<String, Double> query, String[] uQueryTerms, HashMap<String, Double> docVL){
        
        //create empty hashtable of retrieved docs and score
        //loop through unique query terms
        //take query term and find frequency in query
        //find tf-idf of query term --> (frequency / df)*idf
        //calculate cosineSim with corresponding docs idf of term and add to hashtable

        HashMap<String, Double> doc_scores = new HashMap<>();

        //query vector length
        double sum = 0;

        for (String qk : query.keySet()){
            sum =  sum + Math.pow(query.get(qk), 2);
        }

        double queryVL = Math.sqrt(sum);


        for (String word : uQueryTerms){

            if(inverted_index.containsKey(word)){

                //retrieve docs that query term is in
                HashMap<String, Integer> docs = inverted_index.get(word);

                //iterate through doc occurences
                for ( String doc : docs.keySet() ) {
                    if(!(doc_scores.containsKey(doc))){
                        doc_scores.put(doc, 0.0);
                    }

                    double score = doc_scores.get(doc) + cosineSim(doc, word, inverted_index, query, queryVL, docVL);

                    doc_scores.put(doc, score);
                }
                //check if doc is in doc_scores table and add if not
                //calculate cosineSim of doc and term and add to doc_scores


                

            }
        } 

        System.out.println(doc_scores);

        return doc_scores;



    }

    public static double idf(String qt, HashMap<String, HashMap<String, Integer>> inverted_index){

        int occurences = 0; 
        int numDocs = 3;

        if (inverted_index.containsKey(qt)){
            occurences = inverted_index.get(qt).size();
        }

        return (Math.log((double) numDocs/ (double) occurences) / Math.log(2));

    }

    public static double tf_idf(String qt, String docId, HashMap<String, HashMap<String, Integer>> inverted_index){

        int tf = 0;
        
        if (inverted_index.containsKey(qt)){
        
            tf = (inverted_index.get(qt)).get(docId);
        
        }

        return tf * idf(qt, inverted_index);
    }
    
    //calculate cosine similarity of query word and doc
    public static double cosineSim(String doc, String word, HashMap<String, HashMap<String, Integer>> inverted_index, HashMap<String, Double> query, double query_VL, HashMap<String, Double> docVL){

        double tf_idf_word = tf_idf(word, doc, inverted_index);
        double tf_idf_queryT = query.get(word);
        double vl = docVL.get(doc);

        return (tf_idf_word * tf_idf_queryT) / (vl * query_VL) ;

    }

    public static HashMap<String, Double> docVL(HashMap<String, HashMap<String, Integer>> inverted_index){

        HashMap<String, Double> docVL = new HashMap<>();

        for (String term : inverted_index.keySet()){
            //retrieve docs that query term is in
            HashMap<String, Integer> docs = inverted_index.get(term);

            //iterate through doc occurences
            for ( String doc : docs.keySet() ) {
                if(!(docVL.containsKey(doc))){
                    docVL.put(doc, 0.0);
                }

                double vl = docVL.get(doc) + Math.pow(tf_idf(term, doc, inverted_index), 2);

                System.out.println(term + " " + doc + " " + vl );

                docVL.put(doc, vl);
            }
            //check if doc is in doc_scores table and add if not
            //calculate cosineSim of doc and term and add to doc_score
            
        } 

        //square root all
        for (String doc : docVL.keySet()){

            double sqrt = Math.sqrt(docVL.get(doc));
            docVL.put(doc, sqrt);
        }

        return docVL;




    }

    public static void main(String[] args){

        HashMap<String, HashMap<String, Integer>> inverted_index = new HashMap<>();

        HashMap<String, Integer> angeles_doc = new HashMap<>();
        angeles_doc.put("doc3", 1);

        HashMap<String, Integer> los_doc = new HashMap<>();
        los_doc.put("doc3", 1);

        HashMap<String, Integer> new_doc = new HashMap<>();
        new_doc.put("doc1", 1);
        new_doc.put("doc2", 1);

        HashMap<String, Integer> post_doc = new HashMap<>();
        post_doc.put("doc2", 1);

        HashMap<String, Integer> times_doc = new HashMap<>();
        times_doc.put("doc1", 1);
        times_doc.put("doc3", 1);
        
        HashMap<String, Integer> york_doc = new HashMap<>();
        york_doc.put("doc1", 1);
        york_doc.put("doc2", 1);

        
        // HashMap<HashMap<String, Integer>, Double> angeles = new HashMap<>();
        // angeles.put(angeles_doc, 1.584);

        // HashMap<HashMap<String, Integer>, Double> los = new HashMap<>();
        // los.put(los_doc, 1.584);

        // HashMap<HashMap<String, Integer>, Double> new_v = new HashMap<>();
        // new_v.put(new_doc, 0.584);

        // HashMap<HashMap<String, Integer>, Double> post = new HashMap<>();
        // post.put(post_doc, 1.584);

        // HashMap<HashMap<String, Integer>, Double> times = new HashMap<>();
        // times.put(times_doc, 0.584);

        // HashMap<HashMap<String, Integer>, Double> york = new HashMap<>();
        // york.put(york_doc, 0.584);



        inverted_index.put("angeles", angeles_doc);
        inverted_index.put("los", los_doc);
        inverted_index.put("new", new_doc);
        inverted_index.put("post", post_doc);
        inverted_index.put("times", times_doc);
        inverted_index.put("york", york_doc);


        System.out.println(inverted_index);

        System.out.println(tf_idf("post", "doc2", inverted_index));

        
        HashMap<String, Double> query = new HashMap<>();

        query.put("new", (2.0/2.0)*(idf("new", inverted_index)));
        query.put("times", (1.0/2.0)*(idf("times", inverted_index)));

        System.out.println(query);


        String[] queryTerms = {"new","times"};

        retrieve(inverted_index, query, queryTerms, docVL(inverted_index));

        // System.out.print(docVL(inverted_index));




    }
    
}
