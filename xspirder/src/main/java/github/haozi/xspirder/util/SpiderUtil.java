package github.haozi.xspirder.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wanghao
 * @Description Spider工具类，复制了Spider类中一些private方法
 * @date 2019-12-17 19:32
 * @see us.codecraft.webmagic.Spider
 */
public class SpiderUtil {
    public static void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
