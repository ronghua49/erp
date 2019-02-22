package util;    /*
 * @author  Administrator
 * @date 2018/10/13
 */

import com.haohua.erp.util.ConfigUtil;

import java.io.File;

public class FileUtil {

    public static void delFile(File transFile) {
        if (transFile == null) {
            transFile = new File(ConfigUtil.getProperties("upload.path"));
        }
        File[] files = transFile.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                delFile(f);
            }
            f.delete();
        }
    }


}
