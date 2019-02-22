import com.haohua.erp.util.ConfigUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import util.FileUtil;

import java.io.*;
import java.util.UUID;

public class IOTest {

    @Test
    public void ioTest() throws IOException {
        File file = new File(ConfigUtil.getProperties("upload.path"));
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("is directory!");
            } else if (file.isFile()) {
                System.out.println("is file");
            }
        } else {
            file.mkdirs();
        }

        FileInputStream in = new FileInputStream(new File("D:\\javaEE26\\document\\htmldata\\local\\image\\8.jpg"));
        FileOutputStream out = new FileOutputStream(new File(file, UUID.randomUUID().toString() + ".png"));
        IOUtils.copy(in, out);
    }

    @Test
    public void readFile() {

        System.out.println(new File(ConfigUtil.getProperties("upload.path") + "\\.dfb38740815d55901.mp3.sms").canExecute());
        File file = new File(ConfigUtil.getProperties("upload.path"));
        //从file路径下的文件中过滤符合条件的文件名到list中
        String[] list = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".doc");
            }
        });
        for (String s : list) {
            System.out.println(s);
        }
        //过滤文件名符合的文件，并把虚拟的路径放入files中
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        for (File f : files) {
            System.out.println(f.getName());
        }

    }

    @Test
    public void delFile() {
        FileUtil.delFile(new File(ConfigUtil.getProperties("upload.path")));
    }



}
