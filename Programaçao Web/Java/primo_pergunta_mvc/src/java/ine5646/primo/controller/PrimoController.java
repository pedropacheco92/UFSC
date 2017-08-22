package ine5646.primo.controller;

import ine5646.primo.model.PrimoHelper;
import ine5646.primo.view.PrimoView;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leandro
 */
@WebServlet(name = "ServletVerificador", urlPatterns = {"/verifique"})
public class PrimoController extends HttpServlet {
    
    private PrimoView view;
    
   @PostConstruct
   private void initialize() {
       view = new PrimoView();
   }
    
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
        response.setContentType("text/html;charset=UTF-8");
        view.render(response.getWriter(), processeNumero(request.getParameter("numero")));
  }


  private String processeNumero(String numero) {
    final String COR_PRIMO = "green";
    final String COR_NAO_PRIMO = "orange";
    final String COR_ERRO_NAO_EH_HUMERO = "red";
    StringBuilder sb = new StringBuilder("");
    String cor = COR_ERRO_NAO_EH_HUMERO;
    String msg = "[substituir pela mensagem apropriada]";
    Long value = null;
    try {
        value = Long.valueOf(numero);
    } catch (NumberFormatException e) {
        msg = "não é um numero";
    }
    
    if (value != null) {
        if (!PrimoHelper.isPrimo(value)) {
            msg = "nao é primo";
            cor = COR_NAO_PRIMO;
        } else {
            msg = "é primo";
            cor = COR_PRIMO;
        }        
    }
    
    return sb.append("<h2 style='color : ").append(cor).append("'>").append(numero).append(" : ").append(msg).append("</h2>").toString();
  }  
}