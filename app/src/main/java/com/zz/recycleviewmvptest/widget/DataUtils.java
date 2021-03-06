package com.zz.recycleviewmvptest.widget;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.web_page_list.MenuAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: wuyangyi
 * date: 2019-09-20
 * 数据工具类
 */
public class DataUtils {
    public static final String MESSAGE_CONTEXT = "message_context";
    public static final String MESSAGE_IMAGE = "message_image";
    public static final String MESSAGE_SOUND = "message_sound";

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

    /**
     * 手机号验证
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        String check = "^1\\d{10}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    //判断姓名是否规范的正则表达式
    public static boolean checkName(String name) {
        String check = "^(([\\u4e00-\\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(name);
        boolean isMatched = matcher.matches();
        return isMatched;
    }
    //判断邮箱是否规范的正则表达式
    public static boolean checkMail(String mail){
        String check = "^[A-Z0-9a-z._%+-]*@[a-zA-Z0-9][\\w-]*\\.(?:com|cn|net|com.cn|qq.com|com.tw|sina.com|163.com|co.jp|com.hk)$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * UserInfoBean转MyInfoBean
     * @param userInfoBean
     * @return
     */
    public static MyInfoBean doChangeUserInfo(UserInfoBean userInfoBean) {
        MyInfoBean myInfoBean = new MyInfoBean();
        myInfoBean.setPhone(userInfoBean.getPhone());
        myInfoBean.setAge(userInfoBean.getAge());
        myInfoBean.setCreate_time(userInfoBean.getCreate_time());
        myInfoBean.setHead(userInfoBean.getHead());
        myInfoBean.setNickname(userInfoBean.getNickname());
        myInfoBean.setIsAdmin(userInfoBean.isAdmin());
        myInfoBean.setIsLogin(userInfoBean.getIsLogin());
        myInfoBean.setSex(userInfoBean.getSex());
        myInfoBean.setSchool(userInfoBean.getSchool());
        myInfoBean.setId(userInfoBean.getId());
        myInfoBean.setPassword(userInfoBean.getPassword());
        return myInfoBean;
    }

    public static List<MenuAdapter.MenuBean> getMenuData() {
        List<MenuAdapter.MenuBean> data = new ArrayList<>();
        MenuAdapter.MenuBean menuBean1 = new MenuAdapter.MenuBean("扫一扫", R.mipmap.ico_menu_sys);
        data.add(menuBean1);
        MenuAdapter.MenuBean menuBean2 = new MenuAdapter.MenuBean("我的消息", R.mipmap.ico_menu_message);
        data.add(menuBean2);
        return data;
    }

    public static List<MenuAdapter.MenuBean> getChatMenuData(String type) {
        List<MenuAdapter.MenuBean> data = new ArrayList<>();
        MenuAdapter.MenuBean menuBean2 = new MenuAdapter.MenuBean("转发", R.mipmap.ico_menu_message);
        data.add(menuBean2);
        MenuAdapter.MenuBean menuBean3= new MenuAdapter.MenuBean("撤回", R.mipmap.ico_menu_sys);
        data.add(menuBean3);
        MenuAdapter.MenuBean menuBean4 = new MenuAdapter.MenuBean("删除", R.mipmap.ico_menu_message);
        data.add(menuBean4);
        if (type.equals(MESSAGE_CONTEXT)) {
            MenuAdapter.MenuBean menuBean1 = new MenuAdapter.MenuBean("复制", R.mipmap.ico_menu_sys);
            data.add(menuBean1);
        }
        return data;
    }
}
