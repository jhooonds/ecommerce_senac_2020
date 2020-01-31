package ecommerce.controle;

import ecommerce.dao.CategoriaProdutoDao;
import ecommerce.dao.CategoriaProdutoDaoImp;
import ecommerce.entidade.CategoriaProduto;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Jonatan
 */
@ManagedBean
@ViewScoped
public class ControleCategoriaProduto {

    private CategoriaProduto cp;

    private CategoriaProdutoDao cpDao;

    private DataModel modelCategoriaProd;

    private FacesContext contexto;

    public CategoriaProduto getCp() {
        if (cp == null) {
            cp = new CategoriaProduto();
            cp.setAtivoString("sim");
        }
        return cp;
    }

    public void setCp(CategoriaProduto cp) {
        this.cp = cp;
    }

    public DataModel getModelCategoriaProd() {
        return modelCategoriaProd;
    }

    /**
     * Metodo para criar ou alterar categorias
     */
    public void salvar() {
        cpDao = new CategoriaProdutoDaoImp();
        try {
            contexto = FacesContext.getCurrentInstance();
            if (cp.getAtivoString().equals("sim")) {
                cp.setAtivo(true);
            } else {
                cp.setAtivo(false);
            }
            if (cp.getCodigo() == 0) {
                cpDao.salvar(cp);
            } else {
                cpDao.alterar(cp);
            }
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!!", null));
        } catch (Exception e) {
            System.out.println("Erro ao salvar Categorias MSN : " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
        listaCtgEdicao();
    }

    /**
     * Método responsável por listar todas as categorias cadastradas
     */
    public void listaCtgEdicao() {
        cpDao = new CategoriaProdutoDaoImp();
        try {
            List<CategoriaProduto> categorias = cpDao.listarEdicao();
            modelCategoriaProd = new ListDataModel(categorias);
        } catch (Exception e) {
            System.out.println("Erro controle CTG listar Edição MSN :" + e.getMessage());
        }
    }

    /**
     * Metodo para pegar a Categoria que está na linha databela para alterar
     */
    public void alteraCtg() {
        cp = (CategoriaProduto) modelCategoriaProd.getRowData();
    }

    /**
     * Metodo que carrega todas as categorias cadastradas
     * 
     * @return retorna string para redirecionamento
     */
    public String paginaCtg() {
        listaCtgEdicao();
        return "categoria_produto";
    }
}
