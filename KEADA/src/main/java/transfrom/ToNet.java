package transfrom;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author NeverH
 * @date 2021-07-17 上午11:15
 * @function
 */
public class ToNet {
    private static final Pattern PATTERN = Pattern.compile("0|([-]?[1-9][0-9]*)");
    public static void main(String[] args) throws Exception {
        
        String txtname = args[0];
        String netname = args[1];
        //读取所有类并将类编号
        File file = new File(txtname);
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        
        FileOutputStream fileOutputStream = new FileOutputStream(netname);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        
        String line = null;
        int count = 1;
        List<String> classnames = new ArrayList<String>();
        while((line = br.readLine())!= null){
            //对内部类的处理
            if(line.contains("$")){
                line = line.replace('$', '.');
                String[] split = line.split("\\.");                       //若为匿名内部类，则改为主类
                line = split[0];
                for (int i = 1; i < split.length; i ++) {
                    if(!PATTERN.matcher(split[i]).matches()){
                        line = line + "." + split[i];
                    }
                }
            }
            
            if(!classnames.contains(line)){
                classnames.add(line);
                bw.write(count + " \"" + line + "\"\n");
                System.out.println(count + " " + line);
                count ++;
            }
        }
        bw.flush();
        br.close();
        
        
        
        //格式化类调用关系
        String caller = null;
        String callee = null;
        int callerint = 0;
        int calleeint = 0;
    
        File file1 = new File(txtname);
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(fileInputStream1));
        
        boolean flag = false;
        List<CallGraph> callGraphs = new ArrayList<>();
        while ((caller = br1.readLine()) != null && (callee = br1.readLine()) != null){
            //对内部类进行处理
            if(caller.contains("$")){
                caller = caller.replace('$', '.');
                String[] split = caller.split("\\.");
                caller = split[0];
                for (int i = 1; i < split.length; i ++) {
                    if(!PATTERN.matcher(split[i]).matches()){
                        caller = caller + "." + split[i];
                    }
                }
            }
            if(callee.contains("$")){
                callee = callee.replace('$', '.');
                String[] split = callee.split("\\.");
                callee = split[0];
                for (int i = 1; i < split.length; i ++) {
                    if(!PATTERN.matcher(split[i]).matches()){
                        callee = callee + "." + split[i];
                    }
                }
            }
            
            callerint = classnames.indexOf(caller) + 1;
            calleeint = classnames.indexOf(callee) + 1;
            for (CallGraph callgraph : callGraphs) {
                if (callgraph.getCaller() == callerint && callgraph.getCallee() == calleeint ) {
                    callgraph.setCount(callgraph.getCount() + 1);
                    System.out.println(callerint + " " + calleeint + " " + callgraph.getCount());
                    flag = true;
                    break;
                }
            }
            if(!flag){
                CallGraph callGraph = new CallGraph(callerint, calleeint, 1);
                System.out.println(callGraph.getCaller() + " " + callGraph.getCallee() + " " + callGraph.getCount());
                callGraphs.add(callGraph);
            }
            flag = false;
        }
        for (CallGraph callgraph :
                callGraphs) {
            bw.write(callgraph.getCaller() + " " + callgraph.getCallee() + " " + callgraph.getCount() + "\n");
        }
        
        br1.close();
        bw.close();

    }
}

class CallGraph{
    int caller;
    int callee;
    int count;

    public CallGraph(int caller, int callee, int count) {
        this.caller = caller;
        this.callee = callee;
        this.count = count;
    }

    public CallGraph() {
    }

    public int getCallee() {
        return callee;
    }

    public void setCallee(int callee) {
        this.callee = callee;
    }

    public int getCaller() {
        return caller;
    }

    public void setCaller(int caller) {
        this.caller = caller;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CallGraph{" +
                "caller=" + caller +
                ", callee=" + callee +
                ", count=" + count +
                '}';
    }
    /*String callee;
    String caller;
    int count;

    public CallGraph(String callee, String caller, int count) {
        this.callee = callee;
        this.caller = caller;
        this.count = count;
    }

    public String getCallee() {
        return callee;
    }

    public String getCaller() {
        return caller;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CallGraph{" +
                "callee='" + callee + '\'' +
                ", caller='" + caller + '\'' +
                ", count=" + count +
                '}';
    }*/
}
