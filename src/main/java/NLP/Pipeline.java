/**
 * The {@code Pipeline} class provides a static method for initializing a Stanford CoreNLP pipeline.
 */
package NLP;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;
public class Pipeline {
    private static Properties properties;
    private static String propertiesName="tokenize,ssplit,pos,lemma,ner,parse,sentiment";
    private static StanfordCoreNLP stanfordCoreNLP;

    private Pipeline(){

    }
    static {
        properties=new Properties();
        properties.setProperty("annotators",propertiesName);
    }

    /**
     * Initializes and returns a Stanford CoreNLP pipeline.
     *
     * @return the initialized StanfordCoreNLP pipeline
     */
    public static StanfordCoreNLP getPipeline(){
        if (stanfordCoreNLP==null){
            stanfordCoreNLP=new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
