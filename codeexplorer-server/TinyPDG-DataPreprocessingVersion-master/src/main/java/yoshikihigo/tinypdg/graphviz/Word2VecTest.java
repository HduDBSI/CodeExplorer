package yoshikihigo.tinypdg.graphviz;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import com.ansj.vec.Word2vec;
import com.ansj.vec.util.WordKmeans;
import com.ansj.vec.util.WordKmeans.Classes;

public class Word2VecTest {


    public static void main(String[] args) throws IOException {
        Word2vec vec = new Word2vec();

        //vec.loadJavaModel("cfg_model.bin");
        //String WordMapPath = "./outPut_cfg/cfgWordMap.json";  //cfg

        vec.loadJavaModel("pdg_model.bin");
        String WordMapPath = "./outPut_pdg/pdgWordMap.json";  //pdg

        //vec.loadJavaModel("pdg_model.bin");
        // 距离最近的词
        System.out.println(vec.distance("i"));
        System.out.println(vec.distance("j"));
        System.out.println(vec.distance("k"));
        System.out.println(vec.distance(Arrays.asList("i", "j")));
        System.out.println("word vec "+ Arrays.toString(vec.getWordVector("i")));
        System.out.println("word vec "+ Arrays.toString(vec.getWordVector("j")));

        System.out.println("vec.getWords()"+vec.getWordMap().size());
        JSONObject wordMap = new JSONObject();
        for (Map.Entry<String, float[]> entry : vec.getWordMap().entrySet()) {
            wordMap.put(entry.getKey(),vec.getWordVector(entry.getKey()));
        }
        FileUtils.write(new File(WordMapPath), wordMap.toString(), StandardCharsets.UTF_8, true);
        // // 计算词之间的距离
        HashMap<String, float[]> map = vec.getWordMap();
        float[] center1 = map.get("copy");
        float[] center2 = map.get("copyFile");
        System.out.println(center1.length);
        double dics = 0;
        for (int i = 0; i < center1.length; i++) {
            dics += center1[i] * center2[i];
        }
        System.out.println(dics);
        /*
        // 距离计算
        System.out.println(vec.analogy("毛泽东", "邓小平", "毛泽东思想"));
        System.out.println(vec.analogy("女人", "男人", "女王"));
        System.out.println(vec.analogy("北京", "中国", "巴黎"));
        // 聚类
        WordKmeans wordKmeans = new WordKmeans(vec.getWordMap(), 50, 50);
        Classes[] explain = wordKmeans.explain();
        for (int i = 0; i < explain.length; i++) {
            System.out.println("--------" + i + "---------");
            System.out.println(explain[i].getTop(10));
        }*/
    }
}




