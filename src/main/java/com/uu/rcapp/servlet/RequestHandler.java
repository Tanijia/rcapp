package com.uu.rcapp.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uu.rcapp.core.RuntimeCompiler;
import com.uu.rcapp.util.RCUtils;


/**
 * @author    liaoyu
 * @created   Jul 15, 2015 
 */
public class RequestHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String result = "success";
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String className = null;
        String fullClassName = null;
        
        try {
            className = RCUtils.getClassName(code);
            fullClassName = RCUtils.getFullClassName(code);
        } catch (Exception e) {
            result = "Can't find class name in content.";
            out.print(result);
            return;
        }

        // Compile source code to tomcat real path
        String realPath = request.getServletContext().getRealPath("/") + "WEB-INF\\classes";
        RuntimeCompiler rc = new RuntimeCompiler("-d", realPath);
        boolean success = rc.compile(className, code);

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        // Tell Java to use your special stream
        System.setOut(printStream);
        
        if (success) {
            try {
                Class.forName(fullClassName).getDeclaredMethod("main", new Class[] {
                    String[].class
                }).invoke(null, new Object[] {
                    null
                });
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                result = "Load class error: " + e;
            }
        }
        
        // flush output stream 
        System.out.flush();
        
        if (success) {
            result = baos.toString();
            out.print(result);
        } else {
            out.print(rc.getTraceMsg());
        }
        out.flush();
    }

}
