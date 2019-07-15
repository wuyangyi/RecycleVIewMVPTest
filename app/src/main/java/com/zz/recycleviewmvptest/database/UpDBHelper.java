package com.zz.recycleviewmvptest.database;
import android.content.Context;

import com.zz.recycleviewmvptest.bean.DaoMaster;
import com.zz.recycleviewmvptest.bean.UserInfoBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author LiuChao
 * @describe 数据库升级
 * @date 2017/2/18
 * @contact email:450127106@qq.com
 */

public class UpDBHelper extends DaoMaster.OpenHelper {

    public UpDBHelper(Context context, String name) {
        super(context, name);
    }

    // 注意选择GreenDao参数的onUpgrade方法
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 每次升级，将需要更新的表进行更新，第二个参数为要升级的Dao文件.
        MigrationHelper.getInstance().migrate(db, UserInfoBeanDao.class);
    }
}