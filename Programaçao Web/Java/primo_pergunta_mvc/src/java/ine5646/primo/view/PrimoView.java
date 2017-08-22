/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ine5646.primo.view;

import java.io.PrintWriter;
import java.util.function.Consumer;

/**
 *
 * @author Pedro
 */
public class PrimoView {
    
    public void render(PrintWriter out, String value) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>INE5646 - primo</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>INE5646 - primo</h1>");
      out.println(value);
      out.println("</body>");
      out.println("</html>");
    }   
}
