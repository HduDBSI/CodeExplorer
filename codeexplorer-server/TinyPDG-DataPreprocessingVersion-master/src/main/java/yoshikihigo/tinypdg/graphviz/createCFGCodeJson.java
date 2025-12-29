package yoshikihigo.tinypdg.graphviz;

import com.ansj.vec.Word2vec;

import java.io.File;
import java.io.IOException;

public class createCFGCodeJson {
    public static Word2vec vec = new Word2vec();
    public static void main(String[] args) throws IOException {
        vec.loadJavaModel("cfg_model.bin");
        //can shu
        String sourcePath = "./bigclonebenchdata";
        //String sourcePath = "./googlejam4_src";

        String graphType = "cfg";
        WriterCfgToJsonTest w = new WriterCfgToJsonTest();

        File file_corpus_cfg= new File("./outPut_"+graphType+"/corpus/cfg_corpus.txt");

        File cfgDotFile = new File("./outPut_"+graphType+"/cfgDot");
        String codePath = "";
        String cfgPath = "";
        String[] cmd = {"-d", "./Test001.java", "-c", "test001cfg.dot"};
        //
        if(!file_corpus_cfg.exists()){
            System.out.println("file_corpus_cfg文件不存在");
        }else{
            System.out.println("file_corpus_cfg存在文件");
            file_corpus_cfg.delete();
        }


        Scan S = new Scan();
        S.Scanner(sourcePath);

        if(!cfgDotFile.exists()){//如果文件夹不存在
            cfgDotFile.mkdir();//创建文件夹
        }
        System.out.println("S.list.size()"+S.list.size());

        double start = System.currentTimeMillis();
        for(int i=0; i<S.list.size(); i++){
            codePath = S.list.get(i);
            cfgPath = cfgDotFile+"/"+(codePath.split("/")[3])+".cfg.dot";
            cmd[1] = codePath;
            cmd[3] = cfgPath;

            try{
                w.main(cmd);
                //System.out.println(i);
                //System.out.println(S.list.get(i));
            }catch (Exception e){
                System.out.println("触发异常 : "+e);
            }

        }
        double end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
    }
}
