package yoshikihigo.tinypdg.graphviz;

import com.ansj.vec.Word2vec;

import java.io.File;
import java.io.IOException;

public class createPDGCodeJson {
    public static Word2vec vec = new Word2vec();
    public static void main(String[] args) throws IOException {
        vec.loadJavaModel("pdg_model.bin");
        //can shu
        String sourcePath = "./bigclonebenchdata";
        //String sourcePath = "./googlejam4_src";

        String graphType = "pdg";
        WriterPdgToJsonTest w = new WriterPdgToJsonTest();

        File file_corpus_pdg= new File("./outPut_"+graphType+"/corpus/pdg_corpus.txt");

        File pdgDotFile = new File("./outPut_"+graphType+"/pdgDot");
        String codePath = "";
        String pdgPath = "";
        String[] cmd = {"-d", "./Test001.java", "-p", "test001pdg.dot"};

        if(!file_corpus_pdg.exists()){
            System.out.println("file_corpus_pdg文件不存在");
        }else{
            System.out.println("file_corpus_pdg存在文件");
            file_corpus_pdg.delete();
        }


        Scan S = new Scan();
        S.Scanner(sourcePath);

        if(!pdgDotFile.exists()){//如果文件夹不存在
            pdgDotFile.mkdir();//创建文件夹
        }
        System.out.println("S.list.size()"+S.list.size());

        double start = System.currentTimeMillis();
        for(int i=0; i<S.list.size(); i++){
            codePath = S.list.get(i);
            pdgPath = pdgDotFile+"/"+(codePath.split("/")[3])+".pdg.dot";
            cmd[1] = codePath;
            cmd[3] = pdgPath;

            try{
                w.main(cmd);
                //System.out.println(i);
                //System.ut.println(S.list.get(i));
            }catch (Exception e){
                System.out.println("触发异常 : "+e);
            }

        }
        double end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
    }
}
