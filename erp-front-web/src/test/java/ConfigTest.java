import com.haohua.erp.util.ConfigUtil;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void test(){
        System.out.println(ConfigUtil.getProperties("upload.path"));
    }
}
