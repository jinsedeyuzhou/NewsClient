package com.study.newsclient.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wyy on 2017/9/2.
 */

public interface IDao<T,Z> {
    /**
     * 添加
     * @param t
     * @return
     * @throws SQLException
     */
    public int save(T t) throws SQLException;
    /**
     * 删除
     * @param t
     * @return
     * @throws SQLException
     */
    public int delete(T t) throws SQLException;


    /**
     * @param t
     * @return
     * @throws SQLException
     */
    public int update(T t) throws SQLException;

    /**
     * 查询
     * @param z
     * @return
     * @throws SQLException
     */
    public T queryById(Z z) throws SQLException;

    /**
     * @return
     * @throws SQLException
     */
    public List<T> queryAll() throws SQLException;
}
