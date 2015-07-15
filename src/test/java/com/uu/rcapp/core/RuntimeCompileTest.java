package com.uu.rcapp.core;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.uu.rcapp.util.RCUtils;

/**
 * @author    liaoyu
 * @created   Jul 15, 2015 
 */
public class RuntimeCompileTest {

    public static void main(String args[]) throws IOException {
        StringBuffer str = new StringBuffer();
        str.append("package com.uu.rcapp.test;");
        str.append("public class HelloWorld {");
        str.append("  public static void main(String args[]) {");
        str.append("    System.out.println(\"This is in another java file\");");
        str.append("  }");
        str.append("}");
        String code = str.toString();
        
        String className = RCUtils.getClassName(code);
        String fullClassName = RCUtils.getFullClassName(code);

        // Compile source code to folder bin
        RuntimeCompiler rc = new RuntimeCompiler("-d", ".\\target\\classes");
        boolean success = rc.compile(className, code);

        if (success) {
            try {
                Class.forName(fullClassName).getDeclaredMethod("main", new Class[] {
                    String[].class
                }).invoke(null, new Object[] {
                    null
                });
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                System.err.println("Load class error: " + e);
            }
        }
    }
}
