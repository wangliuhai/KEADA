package examples.News.DBoperation;

import examples.News.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryNewDate {
    public String queryNewDate(int d){

        String sql = "select n_date from news where n_id ="+d;
        String content = null;
        try {

            Connection conn = DBConnection.getCon();
            // 获取结果集
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           if (rs.next())
            {content = rs.getString(1);}
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;

    }
}
