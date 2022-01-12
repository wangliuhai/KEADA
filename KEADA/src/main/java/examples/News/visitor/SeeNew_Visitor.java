package examples.News.visitor;

import examples.News.DBoperation.QueryAllDiscussById;
import examples.News.DBoperation.*;
import examples.News.entity.news;

import javax.swing.*;
import java.awt.*;
import examples.News.DBoperation.QueryNew_Content;
import java.util.List;
import examples.News.entity.*;

public class SeeNew_Visitor extends JFrame   {
    public Object[][] rowData;
    public Object[] column = { "标题", "类别" , "点击量", "发布人"}; // 表头
    public Object[][] rowData1;
    public Object[] column1 = {"评论内容", "评论时间", "评论人"}; // 表头

    public SeeNew_Visitor(int d) {

        JPanel Npanel = new JPanel();
        JPanel Spanel = new JPanel();
        JLabel Jldate = new JLabel();
        this.setTitle("查询新闻");
        this.setLayout(null);
        JTextArea jTextArea = new JTextArea(3,20);
        JLabel Jl1 = new JLabel(" ");
        this.setLocation(400, 400);
        queryData(d);
        queryData2(d);
        JTable table = new JTable(rowData, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            ;

        };
        JTable table1 = new JTable(rowData1, column1) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            ;

        };

        // 设置表格内容颜色
        table.setForeground(Color.BLACK); // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
        table.setSelectionBackground(Color.DARK_GRAY); // 选中后字体背景
        table.setSelectionForeground(Color.LIGHT_GRAY); // 选中后字体颜色
        table.setGridColor(Color.GRAY); // 网格颜色

        // 设置表头
        // 设置表头字体样式
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 15));
        // 表头名称字体颜色
        table.getTableHeader().setForeground(Color.RED);
        // 不允许手动改变列宽
        table.getTableHeader().setResizingAllowed(true);
        // 不允许拖动重新排列各列
        table.getTableHeader().setReorderingAllowed(false);
        // 设置行高30
        table.setRowHeight(30);
        // 列宽设置为100


        // 设置滚动面板视口大小
        table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        // 设置表格内容颜色
        table1.setForeground(Color.BLACK); // 字体颜色
        table1.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
        table1.setSelectionBackground(Color.DARK_GRAY); // 选中后字体背景
        table1.setSelectionForeground(Color.LIGHT_GRAY); // 选中后字体颜色
        table1.setGridColor(Color.GRAY); // 网格颜色
        // 设置表头
        // 设置表头字体样式
        table1.getTableHeader().setFont(new Font(null, Font.BOLD, 15));
        // 表头名称字体颜色
        table1.getTableHeader().setForeground(Color.RED);
        // 不允许手动改变列宽
        table1.getTableHeader().setResizingAllowed(true);
        // 不允许拖动重新排列各列
        table1.getTableHeader().setReorderingAllowed(false);
        // 设置行高30
        table1.setRowHeight(30);
        // 列宽设置为100
        table1.getColumnModel().getColumn(1).setPreferredWidth(200);
        // 设置滚动面板视口大小
        table1.setPreferredScrollableViewportSize(new Dimension(550, 200));

        Jl1.setText("内容");
        QueryNew_Content querynew_content = new QueryNew_Content();
        QueryNewDate queryNewDate = new QueryNewDate();
        jTextArea.setText(querynew_content.querynew_content(d));
        Jl1.setBounds(220,80,100,20);

        Jl1.setFont(new Font(null, Font.BOLD, 22));
        Jl1.setForeground(Color.RED);
        Jldate.setText( " 发布时间："+queryNewDate.queryNewDate(d));
        Jldate.setBounds(260,110,550,20);
       JScrollPane scrollPane = new JScrollPane(table);
        Npanel.add(scrollPane);
        Npanel.setBounds(0,0,550,60);
        this.add(Npanel);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);        //激活自动换行功能
        jTextArea.setWrapStyleWord(true);            // 激活断行不断字功能
        JScrollPane js = new JScrollPane(jTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        js.setBounds(0,130,550,200);
        this.add(Jl1);
        this.add(Jldate);
        this.add(js);
       JScrollPane scrollPane1 = new JScrollPane(table1);
        Spanel.add(scrollPane1);
        Spanel.setBounds(0,330,550,200);
        this.add(Spanel);
        this.setBounds(550,500,550,520);
        this.setVisible(true);
    }

    public Object[][] queryData(int d) {
        QueryNewsById queryNewsById = new QueryNewsById();
        List<news> list = queryNewsById.queryNewsByKey(d);
        rowData = new Object[list.size()][column.length];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < column.length; j++) {
                rowData[i][0] = list.get(i).getN_title();
                rowData[i][1] = list.get(i).getN_category();
                rowData[i][2] = list.get(i).getN_clicknum();
                rowData[i][3] = list.get(i).getN_admin();
            }
        }
        return rowData;
    }

    public Object[][] queryData2(int d) {

        QueryAllDiscussById queryAllDiscussById = new QueryAllDiscussById();

        List<discuss> list1 = queryAllDiscussById.queryAllDiscussById(d);
        rowData1 = new Object[list1.size()][column1.length];
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < column1.length; j++) {
                rowData1[i][0] = list1.get(i).getD_content();
                rowData1[i][1] = list1.get(i).getD_date();
                rowData1[i][2] = list1.get(i).getD_username();

            }

        }
        return rowData1;
    }
}
