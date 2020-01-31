package ecommerce.controle;

import ecommerce.dao.PessoaDao;
import ecommerce.dao.PessoaDaoImp;
import ecommerce.entidade.Endereco;
import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Usuario;
import ecommerce.util.MD5;
import ecommerce.util.SessionContext;
import ecommerce.util.WebServiceCep;
import javax.faces.bean.ManagedBean;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jonatan
 */
@ManagedBean
@SessionScoped
public class ControleCliente {

    private Pessoa pessoa;
    private Endereco endereco;
    private Usuario usuario;
    private PessoaDao pDao;
    private String cmd;
    private FacesContext contexto;

    public Pessoa getPessoa() {
        if (pessoa == null) {
            pessoa = new Pessoa();
            pessoa.setDataCadastro(new Date());
        }
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * Metodo para criar ou alterar cadatro do usuário
     * 
     * @return retorna caminho para redirecionamento
     */
    public String salvar() {
        pDao = new PessoaDaoImp();
        pessoa.setEndereco(endereco);
        try {
            contexto = FacesContext.getCurrentInstance();
            if (pessoa.getCodigo() == 0) {
                usuario.setSenha(MD5.criptografia(usuario.getSenha()));
                usuario.setTpUsuario("usuario");
                pessoa.setUsuario(usuario);
                pDao.salvar(pessoa);
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro criado com sucesso!!", null));
                SessionContext.getInstance().setAttribute("usuarioLogado", pessoa);
                if (cmd.equals(MD5.criptografia("primeiroCadastro"))) {
                    return "/index.faces?faces-redirect=true";
                } else {
                    return "/venda.faces?faces-redirect=true&cmd=" + MD5.criptografia("finalizarCompra");
                }
            } else {
                pDao.alterar(pessoa);
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro Alterado com sucesso!!", null));
            }
            pessoa = null;
            usuario = null;
            endereco = null;
        } catch (Exception e) {
            System.out.println("Erro ao salvar Pessoa " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
        return null;
    }

    /**
     * Metodo para pesquisar endereço apartir do cep digitado
     */
    public void pesquisaCep() {
        if (endereco.getCep() != null) {
            try {
                WebServiceCep wsc = WebServiceCep.searchCep(endereco.getCep());
                endereco.setRua(wsc.getLogradouroFull());
                endereco.setCidade(wsc.getCidade());
                endereco.setBairro(wsc.getBairro());
                endereco.setEstado(wsc.getUf());
            } catch (Exception e) {
                System.out.println("Erro ao pesquisar Cep : " + e.getMessage());
            }
        }
    }

    /**
     * Metodo que busca as informações do usuário logado no sistema
     *
     * @return retorna um objeto do tipo Pessoa
     */
    public Pessoa pesquisaDadosPessoaisFenalizaVenda() {
        Pessoa p = SessionContext.getInstance().getUsuarioLogado();
        pDao = new PessoaDaoImp();
        Pessoa pp = null;
        try {
            pp = (Pessoa) pDao.pesquisar(p.getCodigo());
        } catch (Exception e) {
            System.out.println("Erro ao pesquiar Dados do usuario logado : " + e.getMessage());
        }
        return pp;
    }

    /**
     * Metodo que busca as informações do usuário logado no sistema
     *
     * @return retorna o caminho para dados do usuario
     */
    public String pesquisaDadosUsuarioLogado() {
        Pessoa p = SessionContext.getInstance().getUsuarioLogado();
        pDao = new PessoaDaoImp();
        try {
            pessoa = (Pessoa) pDao.pesquisar(p.getCodigo());
            endereco = pessoa.getEndereco();
            return "dados_do_usuario.faces";
        } catch (Exception e) {
            System.out.println("Erro ao pesquiar Dados do usuario logado : " + e.getMessage());
        }
        return null;
    }
}
