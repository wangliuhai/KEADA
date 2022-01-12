package examples.News.admin;

import examples.News.DBoperation.*;


import examples.News.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeeAdminBySuper extends JFrame {
    public Object[][] rowData;
    public Object[] column = {"id", "用户名", "密码", "姓名"}; // 表头
    public SeeAdminBySuper(){
        JFrame jf = new JFrame("查询结果");
        jf.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 表格所有行
        Object[][] rowData = queryData();
        JTable table = new JTable(rowData, column){
            public boolean isCellEditable(int row, int column) {return false;};
        };


        // 设置表格内容颜色
        table.setForeground(Color.BLACK); // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
        table.setSelectionBackground(Color.DARK_GRAY); // 选中后字体背景
        table.setSelectionForeground(Color.LIGHT_GRAY); // 选中后字体颜色
        table.setGridColor(Color.GRAY); // 网格颜色

        // 设置表头
        // 设置表头字体样式
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));
        // 表头名称字体颜色
        table.getTableHeader().setForeground(Color.RED);
        // 不允许手动改变列宽
        table.getTableHeader().setResizingAllowed(false);
        // 不允许拖动重新排列各列
        table.getTableHeader().setReorderingAllowed(false);

        // 设置行高30
        table.setRowHeight(30);
        // 列宽设置为100
        table.getColumnModel().getColumn(1).setPreferredWidth(80);

        // 设置滚动面板视口大小
        table.setPreferredScrollableViewportSize(new Dimension(550, 400));
        // 把表格放到滚动面板中（自动添加到顶部）
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        jf.add(panel);

        jf.pack();
        jf.setContentPane(panel);
        jf.setVisible(true);
        //设置右键菜单

        JPopupMenu m_popupMenu = new JPopupMenu();


        JMenuItem alterMenItem = new JMenuItem();
        alterMenItem.setText("  修改  ");
        JMenuItem deleteMenItem = new JMenuItem();
        deleteMenItem.setText("  删除  ");
        m_popupMenu.add(alterMenItem);
        m_popupMenu.add(deleteMenItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //通过点击位置找到点击为表格中的行
                    int focusedRowIndex = table.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    //弹出菜单
                    m_popupMenu.show(table, e.getX(), e.getY());
                } }
        });

        alterMenItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //该操作需要做的事
                int a = table.getSelectedRow();
                Object c = table.getValueAt(a,0);
                int d = (int )c;
                jf.setVisible(false);
                new AlterAdmin(d);

            }
        });
        deleteMenItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int a = table.getSelectedRow();
                Object c = table.getValueAt(a,0);
                int d = (int )c;
                new DeleteAdmin(d);
                jf.setVisible(false);
                new SeeAdminBySuper();
            }
        });


    }
    /**
     * 生成表格数据
     *
     * @return 表格数据二维数组
     */
    public Object[][] queryData() {
        QueryAllAdmin queryAllAdmin = new QueryAllAdmin();
        java.util.List<admin> list = queryAllAdmin.queryAllAdmin();

        rowData = new Object[list.size()][column.length];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < column.length; j++) {
                rowData[i][0] = list.get(i).getA_id();
                rowData[i][1] = list.get(i).getA_loginname();
                rowData[i][2] = list.get(i).getA_password();
                rowData[i][3] = list.get(i).getA_name();
            }
        }
        return rowData;




    }
}
