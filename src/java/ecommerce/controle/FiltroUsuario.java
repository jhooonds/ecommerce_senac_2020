
package ecommerce.controle;

import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonatan
 */
@WebFilter(filterName = "FiltroUsuario", urlPatterns = {"/usuario/*"})
public class FiltroUsuario implements Filter {
      
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("");
    }
    
    /**
     * * Metodo usado para validar se o usuário comum está logado u não no sistema
     * 
     * @param request variavel do tipo ServletRequest
     * @param response variavel do tipo ServletResponse
     * @param chain variavel do tipo FilterChain
     * @throws IOException - caso ocorra alguma falha para  filtrar
     * @throws ServletException - caso ocorra alguma falha para filtrar
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String cmd = request.getParameter("cmd");
        HttpServletRequest httpReq = null;
        httpReq = (HttpServletRequest) request;
        System.out.println(httpReq.getServletPath());
        if (cmd == null) {
            cmd = "";
        }
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        RequestDispatcher rd;
        Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");
        if (httpReq.getServletPath().equals("login.faces")) {
            chain.doFilter(request, response);
        } else if (usuario == null && cmd.equals("login")) {
            chain.doFilter(request, response);
        } else if ((usuario != null) && (usuario.getUsuario().getTpUsuario().equals("usuario"))) {
            chain.doFilter(request, response);
        } else {            
          //  request.setAttribute("msg", "Você não esta logado no sistema!!!");
            rd = request.getRequestDispatcher("../login.faces?msg=Você não esta logado no sistema!!!");
            rd.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("");
    }
}
