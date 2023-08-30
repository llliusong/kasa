package com.pine.kasa.config.database;

import com.pine.kasa.config.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pine
 * @date 2020-08-13 21:21.
 */
public class UserDao {
    public int insert(User user) {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = JDBCTools.getCon();
            String sql = "insert into ws_user values (null, ?, ?, ?, ?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserAccount());
            statement.setString(3, user.getUserPassword());
            statement.setString(4, "0"); //默认离线
            statement.setString(5, user.getRemark());
            return statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement);
        }
        return 0;
    }


    public int delete(Integer userId) {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = JDBCTools.getCon();
            String sql = "delete from ws_user where userid = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            return statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement);
        }
        return 0;
    }

    public int update(User user) {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = JDBCTools.getCon();
            String sql = "update ws_user set user_name = ?, user_account = ?, user_password = ?, user_status = ?,remark=? where user_id = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserAccount());
            statement.setString(3, user.getUserPassword());
            statement.setString(4, user.getUserStatus());
            statement.setString(5, user.getRemark());
            statement.setInt(6, user.getUserId());
            return statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement);
        }
        return 0;
    }

    /**
     * 修改用户状态
     * @param userId
     * @param status
     * @return
     */
    public int updateStatus(Integer userId,String status) {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = JDBCTools.getCon();
            String sql = "update ws_user set user_status = ? where user_id = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, userId);

            return statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement);
        }
        return 0;
    }


    public User select(Integer userId) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            con = JDBCTools.getCon();
            String sql = "select * from ws_user where user_id = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserAccount(rs.getString(3));
                user.setUserPassword(rs.getString(4));
                user.setUserStatus(rs.getString(5));
                user.setRemark(rs.getString(6));
                return user;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement, rs);
        }
        return null;
    }



    public List<User> selectAll() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            con = JDBCTools.getCon();
            String sql = "select * from ws_user order by user_status desc ";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserAccount(rs.getString(3));
                user.setUserPassword(rs.getString(4));
                user.setUserStatus(rs.getString(5));
                user.setRemark(rs.getString(6));
                list.add(user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement, rs);
        }
        return list;
    }


    /**
     * 登录
     * @param name
     * @param pwd
     * @return
     */
    public User selectByLogin(String name,String pwd) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            con = JDBCTools.getCon();
            String sql = "select * from ws_user t where t.user_account=? and t.user_password=? ";
            statement = con.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,pwd);
            System.out.println(sql+"   "+name+"   "+pwd);
            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserAccount(rs.getString(3));
                user.setUserPassword(rs.getString(4));
                user.setUserStatus(rs.getString(5));
                user.setRemark(rs.getString(6));
                return user ;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(con, statement, rs);
        }
        return null;
    }
}
