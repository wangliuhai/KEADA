package examples.News.DBoperation;

import examples.News.database.DBConnection;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDiscussByD_id {
    public DeleteDiscussByD_id(int d){
        String sql = "delete from discuss where d_id = "+d;
        try {     Statement stm = DBConnection.getCon().createStatement();
            int i = stm.executeUpdate(sql);
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "删除成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "删除失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
            }

            stm.close();


        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
