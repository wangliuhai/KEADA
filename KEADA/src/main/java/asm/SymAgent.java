package asm;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

/**
 * @author NeverH
 * @date 2021-05-31 下午8:33
 * @function
 */
public class SymAgent implements ClassFileTransformer {

    private Logger logger = Logger.getLogger("SymAgent.class");

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new SymAgent());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        
        
        if (className != null && className.indexOf("/") != -1) {
            className = className.replaceAll("/", ".");
        }
        /*
         各软件包名：
         jmeter: org.apache.jmeter
         jedit: org.gjt.sp
         argouml: org.argouml
         jhotdraw: org.jhotdraw
         neuroph: org.neuroph
         mazesolver: maze
         */
        
        if (className == null || !className.startsWith("org.jhotdraw")) {
            return null;
        }
        /*if(className.contains("$")){
            className = className.replace("$",".");
        }*/
        try {
            //1.通过ClassPool获取类并修改内容
            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);
            CtClass ctClass = ClassPool.getDefault().get(className);
            CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
            for (CtMethod ctMethod : declaredMethods) {
                if (ctMethod.isEmpty() == false && !Modifier.isNative(ctMethod.getModifiers())){
                    System.out.println(ctMethod);
                    doMethod(ctMethod);
                }
            }
            return ctClass.toBytecode();


        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
    private void doMethod(CtBehavior method) throws CannotCompileException {
        method.insertAfter("java.io.File file =new java.io.File(\"net.txt\");\n" +
                "        if(!file.exists()){\n" +
                "            file.createNewFile();\n" +
                "        }\n" +
                "        java.io.FileWriter fileWritter = new java.io.FileWriter(file.getName(),true);\n" +
                "        String currentClass = Thread.currentThread().getStackTrace()[1].getClassName();\n" +
                "        String currentMethod = Thread.currentThread().getStackTrace()[1].getMethodName();\n" +
                "        if(!\"main\".equals(currentMethod)){\n" +
                "            String fatherClass = Thread.currentThread().getStackTrace()[2].getClassName();\n" +
                "            String fatherMethod = Thread.currentThread().getStackTrace()[2].getMethodName();\n" +
                "                fileWritter.write(fatherClass + \"\\n\");\n" +
                "                fileWritter.write(currentClass + \"\\n\");\n" +
                "        } else {\n" +
                "            fileWritter.write(currentClass + \"\\n\");\n" +
                "            fileWritter.write(currentClass + \"\\n\");\n" +
                "        }\n" +
                "        fileWritter.close();");
    }
}