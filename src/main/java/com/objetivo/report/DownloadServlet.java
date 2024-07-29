package com.objetivo.report;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response, JasperPrint file) throws ServletException, IOException {

        String id = request.getParameter("id");

        String fileName = "";
        String fileType = "";
        // Find this file id in database to get file name, and file type

        // You must tell the browser the file type you are going to send
        // for example application/pdf, text/plain, text/html, image/jpg
        response.setContentType(fileType);

        // Make sure to show the download dialog
        response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf

      //  File my_file = new File(fileName);

        // This should send the file to browser
        OutputStream out = response.getOutputStream();

        FileInputStream in = new FileInputStream(String.valueOf(file));
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}