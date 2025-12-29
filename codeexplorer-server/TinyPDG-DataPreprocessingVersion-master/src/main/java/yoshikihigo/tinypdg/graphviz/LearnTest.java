package yoshikihigo.tinypdg.graphviz;

import java.io.File;
import java.io.IOException;

import com.ansj.vec.Learn;

public class LearnTest {

    public static void main(String[] args) throws IOException {
        Learn learn = new Learn();

        int mode = 1;  //cfg
        //int mode = 2;  //pdg

        if(mode == 1){
            learn.learnFile(new File("./outPut_cfg/corpus/cfg_corpus.txt"));
            learn.saveModel(new File("cfg_model.bin"));
        }else if(mode == 2){
            learn.learnFile(new File("./outPut_pdg/corpus/pdg_corpus.txt"));
            learn.saveModel(new File("pdg_model.bin"));
        }

    }
}
