package yoshikihigo.tinypdg.graphviz;

import org.json.JSONObject;
import com.ansj.vec.Word2vec;
import java.io.IOException;

public class JsonTest{
    public static void main(String[] args) throws IOException {
        Word2vec vec = new Word2vec();

        vec.loadJavaModel("model.bin");

        float[] vector = vec.getWordVector("T");

        JSONObject codeGraphJson = new JSONObject();
        JSONObject nodes = new JSONObject();
        JSONObject edges = new JSONObject();

        nodes.put("0.1",vector);
        nodes.put("0.2","int");
        nodes.put("0.3","float");

        edges.put("0.1 -> 0.2","i");
        edges.put("0.2 -> 0.3","true\"\"");
        edges.put("0.3 -> 0.1","false");
        codeGraphJson.put("223333","true\"\"");
        codeGraphJson.put("nodes",nodes);
        codeGraphJson.put("edges",edges);

        System.out.println();
        System.out.println("sssss"+"xx".split(" ")[0]);
    }

}



