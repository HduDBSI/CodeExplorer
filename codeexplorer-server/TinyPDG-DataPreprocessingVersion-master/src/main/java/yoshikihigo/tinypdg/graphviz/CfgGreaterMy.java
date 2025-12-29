package yoshikihigo.tinypdg.graphviz;
import java.io.File;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class CfgGreaterMy {
    public static void main(String[] args){
        //
        File file_corpus_cfg= new File("./outPut/corpus/cfg_corpus.txt");
        File file_corpus_pdg= new File("./outPut/corpus/pdg_corpus.txt");
        String sourcePath = "./googlejam4_src";
        File pdgDotFile = new File("./outPut/pdgDot");
        File cfgDotFile = new File("./outPut/cfgDot");
        String codePath = "";
        String pdgPath = "";
        String cfgPath = "";
        String[] cmd = {"-d", "./Test001.java", "-p", "test001pdg.dot", "-c", "test001cfg.dot"};
        //
        if(!file_corpus_cfg.exists()){
            System.out.println("file_corpus_cfg文件不存在");
        }else{
            System.out.println("file_corpus_cfg存在文件");
            file_corpus_cfg.delete();
        }
        if(!file_corpus_pdg.exists()){
            System.out.println("file_corpus_pdg文件不存在");
        }else{
            System.out.println("file_corpus_pdg存在文件");
            file_corpus_pdg.delete();
        }

        Writer w = new Writer();
        Scan S = new Scan();
        S.Scanner(sourcePath);

        if(!pdgDotFile.exists()){//如果文件夹不存在
            pdgDotFile.mkdir();//创建文件夹
        }
        if(!cfgDotFile.exists()){//如果文件夹不存在
            cfgDotFile.mkdir();//创建文件夹
        }
        System.out.println("S.list.size()"+S.list.size());

        for(int i=0; i<S.list.size(); i++){
            codePath = S.list.get(i);
            pdgPath = pdgDotFile+"/"+(codePath.split("/")[3])+".pdg.dot";
            cfgPath = cfgDotFile+"/"+(codePath.split("/")[3])+".cfg.dot";
            cmd[1] = codePath;
            cmd[3] = pdgPath;
            cmd[5] = cfgPath;
            /*
            if(i==631 || i==700)
                i++;
            if(i==701 || i==925 || i==1163 || i==1174 || i==1197 || i==1224 || i==1583 || i==1606)
                i++;
             */
            try{
                w.main(cmd);
                System.out.println(i);
                //System.ut.println(S.list.get(i));
            }catch (Exception e){
                System.out.println("触发异常 : "+e);
            }

        }
    }
}
