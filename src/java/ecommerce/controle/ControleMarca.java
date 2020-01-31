package ecommerce.controle;

import ecommerce.dao.MarcaDao;
import ecommerce.dao.MarcaDaoImp;
import ecommerce.entidade.Marca;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.faces.model.ListDataModel;

/**
 *
 * @author Gustavo
 */
@ManagedBean
@ViewScoped
public class ControleMarca {

    private Marca marca;

    private MarcaDao mDao;

    private DataModel modelMarca;

    private FacesContext contexto;

    public Marca getMarca() {
        if (marca == null) {
            marca = new Marca();
            marca.setAtivoString("sim");
        }
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public DataModel getModelMarca() {
        return modelMarca;
    }

    /**
     * Metodos que manipulão Marca, criar ou alterar uma marca
     */
    public void salvar() {
        mDao = new MarcaDaoImp();
        if (marca == null) {
            marca = new Marca();
            marca.setAtivoString("sim");
        }
        contexto = FacesContext.getCurrentInstance();
        try {
            if (marca.getAtivoString().equals("sim")) {
                marca.setAtivo(true);
            } else {
                marca.setAtivo(false);
            }
            if (marca.getCodigo() == 0) {
                mDao.salvar(marca);
            } else {
                mDao.alterar(marca);
            }
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!!", null));
        } catch (Exception e) {
            System.out.println("Erro ao salvar/Alterar Marca " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
        marca = null;
        pesquiarMarcas();
    }

    /**
     * Método responsável por listar todas as categorias cadastradas
     */
    public void pesquiarMarcas() {
        mDao = new MarcaDaoImp();
        try {
            List<Marca> marcas = mDao.listarEdicao();
            modelMarca = new ListDataModel(marcas);
        } catch (Exception e) {
            System.out.println("Erro ao listar Marcas Edição " + e.getMessage());
        }
    }

    /**
     * Metodo para pegar a Marca que está na linha databela para alterar
     */
    public void alterarMarca() {
        marca = (Marca) modelMarca.getRowData();
    }

    /**
     * Metodo que carrega todas as marcas cadastradas
     *
     * @return retorna string para redirecionamento
     */
    public String paginaMarca() {
        pesquiarMarcas();
        return "marcas";
    }
}
