package yoshikihigo.tinypdg.graphviz;

import java.io.File;
import java.util.ArrayList;

public class Scan{
    public ArrayList<String> list = new ArrayList<String>(0);//用arraylist保存扫描到的路径

    public void Scanner(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] filenames = file.list();

        if (filenames == null)
            return;
        for (int i = 0; i < filenames.length; i++) {
            if (files[i].isFile()) {
                if (files[i].getName().endsWith("java"))//只获取带png结尾的文件
                    list.add(files[i].getPath());//获取路径
            } else if (files[i].isDirectory()) {
                Scanner(files[i].getPath());
            }
        }
        //System.out.println("filenames "+list);
    }

}