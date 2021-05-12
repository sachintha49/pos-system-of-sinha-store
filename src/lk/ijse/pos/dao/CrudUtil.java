package lk.ijse.pos.dao;

import lk.ijse.pos.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    static Connection connection;

    //<T> t means all type of object can be returned....
    public CrudUtil(){
    }

    public static Connection getConnection(){
        return connection;
    }
    public static <T> T execute(String sql,Object... params) throws SQLException, ClassNotFoundException {
        connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stm.setObject((i+1),params[i]);
        }
        if (sql.startsWith("SELECT")){
            //executeQuery();
            return (T)stm.executeQuery();

        }else{
            //executeUpdate();
            return ((T)(Boolean)(stm.executeUpdate()>0));
        }
    }
}
