package com.zz.recycleviewmvptest.widget;

import java.util.Arrays;
import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-20
 * 数据工具类
 */
public class DataUtils {

    public static final String BQ_START = "emoji_";
    /**
     * 获取表情图片
     * @return
     */
    public static List<String> getImageListName() {
        return Arrays.asList("am", "angry", "by", "cx", "cy", "day", "dk", "dq", "dy",
                             "fd", "gg", "haha", "hanx", "hhx", "huaix", "hx", "jio", "jk",
                             "ka", "kk", "kuk", "kx", "lh", "like", "ll", "love", "mm",
                             "ng", "qq", "qy", "sad", "sb", "se", "tians", "tp", "ts",
                             "tsx", "tuu", "wow", "wq", "ws", "wus", "wx", "wy", "xk",
                             "yay", "yj", "yun", "yx", "zk", "zm",
                            "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10", "e11",
                            "e12", "e13", "e14", "e15", "e16", "e17", "e18", "e19", "e20", "e21",
                            "e22", "e23", "e24", "e25", "e26", "e27", "e28", "e29", "e30", "e31",
                            "e32", "e33", "e34", "e35", "e36", "e37", "e38", "e39", "e40", "e41",
                            "e42", "e43", "e44", "e45", "e46", "e47", "e48", "e49", "e50");
    }


    /**
     * 是否是表情
     * @param path
     * @return
     */
    public static boolean isEmoji(String path) {
        if (path.startsWith(BQ_START)) {
            return true;
        }
        return false;
    }
}
