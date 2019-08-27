package com.study.newsclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.bean.NewsFeed;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wyy on 2017/4/26.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "newclient_news.db";
    private static int DATABASE_VERSION = 1;
    private Context  mContext;

    private static ArrayList<Channel> mChannels = new ArrayList<>();
    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    static {
        mChannels.add(new Channel(1, "推荐", 1, 1,5));
        mChannels.add(new Channel(44, "视频", 2, 1,3));
        mChannels.add(new Channel(21, "搞笑", 3, 1,3));
        mChannels.add(new Channel(26, "美女", 4, 1,3));
        mChannels.add(new Channel(2, "社会", 5, 1,3));
        mChannels.add(new Channel(17, "养生", 6, 1,3));
        mChannels.add(new Channel(8, "军事", 7, 1,3));
        mChannels.add(new Channel(6, "体育", 8, 1,3));
        mChannels.add(new Channel(5, "汽车", 9, 1,3));
        mChannels.add(new Channel(4, "科技", 10, 1,3));
        mChannels.add(new Channel(7, "财经", 11, 1,3));
        mChannels.add(new Channel(22, "互联网", 12, 1,3));
        mChannels.add(new Channel(11, "游戏", 13, 1,3));
        mChannels.add(new Channel(30, "影视", 14, 1,3));
        mChannels.add(new Channel(23, "趣图", 15, 1,3));
        mChannels.add(new Channel(9, "国际", 16, 1,3));
        mChannels.add(new Channel(10, "时尚", 17, 1,3));
        mChannels.add(new Channel(3, "娱乐", 18, 1,3));
        mChannels.add(new Channel(18, "故事", 19, 1,3));

        /**默认用户未选择的频道,并可选添加*/
        mChannels.add(new Channel(31, "奇闻", 1, 0,4));
        mChannels.add(new Channel(12, "旅游", 2, 0,4));
        mChannels.add(new Channel(39, "帅哥", 3, 0,4));
        mChannels.add(new Channel(24, "健康", 4, 0,4));
        mChannels.add(new Channel(15, "美食", 5, 0,4));
        mChannels.add(new Channel(20, "股票", 6, 0,4));
        mChannels.add(new Channel(25, "科学", 7, 0,4));
        mChannels.add(new Channel(19, "美文", 8, 0,4));
        mChannels.add(new Channel(32, "萌宠", 9, 0,4));
        mChannels.add(new Channel(37, "风水玄学", 10, 0,4));
        mChannels.add(new Channel(13, "历史", 11, 0,4));
        mChannels.add(new Channel(16, "育儿", 12, 0,4));
        mChannels.add(new Channel(14, "探索", 13, 0,4));
        mChannels.add(new Channel(36, "自媒体", 14, 0,4));
        mChannels.add(new Channel(35, "点集", 15, 0,4));
    }

    /**
     * 创建数据库
     * @param sqLiteDatabase
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, NewsFeed.class);
            TableUtils.createTableIfNotExists(connectionSource, Channel.class);
            BaseDao<Channel,Integer> mChannel=new BaseDao<>(mContext,Channel.class);
            mChannel.save(mChannels);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据库
     * @param sqLiteDatabase
     * @param connectionSource
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, NewsFeed.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static DatabaseHelper instance;
    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public synchronized <D extends Dao<T, ?>, T> D getDao(Class<T> clazz)
            throws SQLException {
        Dao<T, ?> dao = DaoManager.createDao(getConnectionSource(), clazz);
        dao.setObjectCache(true);
        return (D) dao;
    }



    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();
        DaoManager.clearDaoCache();
        OpenHelperManager.releaseHelper();
    }


}
