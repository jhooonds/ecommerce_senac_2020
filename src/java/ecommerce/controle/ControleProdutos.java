package ecommerce.controle;

import ecommerce.dao.CategoriaProdutoDao;
import ecommerce.dao.CategoriaProdutoDaoImp;
import ecommerce.dao.FotosProdutoDao;
import ecommerce.dao.FotosProdutoDaoImp;
import ecommerce.dao.MarcaDao;
import ecommerce.dao.MarcaDaoImp;
import ecommerce.dao.ProdutoDao;
import ecommerce.dao.ProdutoDaoImp;
import ecommerce.entidade.CategoriaProduto;
import ecommerce.entidade.FotosProduto;
import ecommerce.entidade.Marca;
import ecommerce.entidade.Produto;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jonatan
 */
@ManagedBean
@SessionScoped
public class ControleProdutos {

    private Produto produto;
    private Marca marca;
    private CategoriaProduto cp;
    private FotosProduto fotoProd;
    private String ativo;
    private List<Marca> marcas;
    private List<CategoriaProduto> categoProdutos;
    private List<Produto> produtosSite;
    private List<Produto> carrinhoCompra;
    private StreamedContent file;

    private ProdutoDao pDao;
    private FotosProdutoDao fpDao;
    private MarcaDao mDao;

    private DataModel modelImgProd;
    private DataModel modelProduto;
    private DataModel modelMarca;

    private FacesContext contexto;
    private static final long serialVersionUID = 1L;

    private String limpaCarrinho;

    public DataModel getModelProduto() {
        return modelProduto;
    }

    public Produto getP() {
        if (produto == null) {
            produto = new Produto();
        }
        produto.setDataCadastro(new Date());
        return produto;
    }

    public void setP(Produto p) {
        this.produto = p;
    }

    public FotosProduto getFotoProd() {
        if (fotoProd == null) {
            fotoProd = new FotosProduto();
        }
        return fotoProd;
    }

    public void setFotoProd(FotosProduto fotoProd) {
        this.fotoProd = fotoProd;
    }

    public DataModel getModelImgProd() {
        return modelImgProd;
    }

    public DataModel getModelMarca() {
        return modelMarca;
    }

    public String getAtivo() {
        ativo = "sim";
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Marca getM() {
        if (marca == null) {
            marca = new Marca();
        }
        return marca;
    }

    public void setM(Marca m) {
        this.marca = m;
    }

    public CategoriaProduto getCp() {
        if (cp == null) {
            cp = new CategoriaProduto();
        }
        return cp;
    }

    public void setCp(CategoriaProduto cp) {
        this.cp = cp;
    }

    public List<Produto> getProdutosSite() {
        return produtosSite;
    }

    public List<Produto> getCarrinhoCompra() {
        return carrinhoCompra;
    }

    public String getLimpaCarrinho() {
        return limpaCarrinho;
    }

    public void setLimpaCarrinho(String limpaCarrinho) {
        this.limpaCarrinho = limpaCarrinho;
    }

    /**
     * Método responsável por listar todas as marcas cadastradas para o Combobox
     *
     * @return retorna um List do tipo SelectItem
     */
    public List<SelectItem> getComboboxMarcas() {
        List<SelectItem> listMarcas = new ArrayList<SelectItem>();
        MarcaDao mDao = new MarcaDaoImp();
        try {
            for (Marca marca : mDao.listar()) {
                listMarcas.add(new SelectItem(marca.getCodigo(), marca.getNome()));
            }
            return listMarcas;
        } catch (Exception e) {
            System.out.println("Erro listar Marcas \nMetodo getComboboxMarcas() \nMSG :" + e.getMessage());
        }
        return null;
    }

    /**
     * Método responsável por listar todas as categorias cadastradas para o
     * Combobox
     *
     * @return retorna um List do tipo SelectItem
     */
    public List<SelectItem> getComboboxCtgProd() {
        CategoriaProdutoDao cpDao = new CategoriaProdutoDaoImp();
        List<SelectItem> listCategorias = new ArrayList<SelectItem>();
        try {
            for (CategoriaProduto categoria : cpDao.listar()) {
                listCategorias.add(new SelectItem(categoria.getCodigo(), categoria.getNome()));
            }
            return listCategorias;
        } catch (Exception e) {
            System.out.println("Erro listar Categorias \nMetodo getComboboxCtgProd() \nMSG :" + e.getMessage());
        }
        return null;
    }

    /**
     * Metodo que executa a alteração ou criação de um produto
     */
    public void salvar() {
        pDao = new ProdutoDaoImp();
        produto.setCategoria(cp);
        produto.setMarca(marca);
        if (ativo.equals("sim")) {
            produto.setAtivo(true);
        } else {
            produto.setAtivo(false);
        }
        try {
            contexto = FacesContext.getCurrentInstance();
            if (produto.getCodigo() == 0) {
                produto = pDao.salvarProduto(produto);
            } else {
                pDao.alterar(produto);
            }
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com secesso!!", null));
        } catch (Exception e) {
            System.out.println("Erro salvar Produto \nMetodo salvar() \nMSG :" + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
    }

    /**
     * Metodo responsavel por pegar o caminho relativo da pasta onde sera salvo
     * o aqruivo
     *
     * @return context.getRealPath
     */
    public String getRealPath() {
        ExternalContext externalContext
                = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
        return context.getRealPath("/");
    }

    /**
     * Metodo responsavel por pegar o aquivo(Imagem do produto) da tela
     *
     * @param event - parametro do tipo FileUploadEvent
     */
    public void handleFileUploadAction(FileUploadEvent event) {
        fpDao = new FotosProdutoDaoImp();
        fotoProd = new FotosProduto();

        UploadedFile file = event.getFile();
        fotoProd.setNome(file.getFileName());
        byte[] conteudo;
        try {
            conteudo = IOUtils.toByteArray(file.getInputstream());
            String caminho = getRealPath() + "\\imagensProdutos\\" + fotoProd.getNome();
            System.out.println("CAMINHO :" + caminho);
            File e = new File(getRealPath() + "\\imagensProdutos\\");
            e.mkdirs();
            criaArquivo(conteudo, caminho);
            fotoProd.setCaminho(caminho);
            fotoProd.setTamanho(conteudo.length);
        } catch (Exception ex) {
            System.out.println("ERRRRRRRRRRRRRRROooo >>>>>>>>>>" + ex.getMessage() + "\n\n" + ex.getLocalizedMessage());
        }
        String nomeArquivo = fotoProd.getNome();
        int e = nomeArquivo.lastIndexOf(".");
        fotoProd.setTipo(nomeArquivo.substring(e));
        fotoProd.setIdProduto(produto.getCodigo());
        fotoProd.setPrincipal(false);
        try {
            fpDao.salvar(fotoProd);
        } catch (Exception ex) {
            System.out.println("Erro ao salvar Fotos do produto !!!!!!!!!!! " + ex.getMessage());
        }
        // System.out.println("NOME " + cb.getNome() + "\ncaminho" + cb.getCaminho() + "\n tamanho" + cb.getTamanho());
        // downloadComprovante();
        pesquisarImagensPriduto();
    }

    /**
     * Metodo responsavel por salvar o arquivo na pasta comprovantesBancarios
     *
     * @param bytes - variavel do tipo byte[]
     * @param arquivo - variavel do tipo String
     * @throws java.io.IOException - caso ocorra alguma falha ou exceção gravar
     * o arquivo
     */
    public void criaArquivo(byte[] bytes, String arquivo) throws IOException {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException   >>>>> " + ex.getMessage() + "\n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println("IOException   >>>>> " + ex.getMessage() + "\n" + ex.getLocalizedMessage());
        }
    }

    /**
     * Metodo para deletar a imagem do produto
     */
    public void deletaImagenProduto() {
        fpDao = new FotosProdutoDaoImp();
        try {
            fotoProd = (FotosProduto) modelImgProd.getRowData();
            fotoProd = (FotosProduto) fpDao.pesquisar(fotoProd.getCodigo());
            fpDao.excluir(fotoProd.getCodigo());
            new File(fotoProd.getCaminho()).delete();
            pesquisarImagensPriduto();
        } catch (Exception ex) {
            System.out.println("Erro ao excluir Imagen " + ex.getMessage());
        }
    }

    /**
     * Metodo para pesquisar a imagem do produto
     */
    public void pesquisarImagensPriduto() {
        fpDao = new FotosProdutoDaoImp();
        try {
            List<FotosProduto> imgs = fpDao.pesquisaImgProduto(produto.getCodigo());
            modelImgProd = new ListDataModel(imgs);
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro pesquisar imagen do produto \npesquisarImagensPriduto() \nMSG :" + e.getMessage());
        }
    }

    /**
     * Metodo para listar todos os produtos
     */
    public void listarTodosProdutos() {
        pDao = new ProdutoDaoImp();
        try {
            List<Produto> produtos = pDao.listarProdutos();
            modelProduto = new ListDataModel(produtos);
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro controle listar Produtos MSN" + e.getMessage());
        }
    }

    /**
    * Metodo usado para chamar a pagina para aalterar o  produto selecionado n omodal do administrativo
     */
    public void alterarProduto() {
        produto = (Produto) modelProduto.getRowData();
        marca = produto.getMarca();
        cp = produto.getCategoria();
        pesquisarImagensPriduto();
    }

    /**
     * metodo para limpar atributos de produto , marca e cp
     */
    private void limpa() {
        produto = null;
        marca = null;
        cp = null;
    }

    /**
     * Metodo usado para chamar a pagina para adicionar um novo produto no administrativo
     *
     * @return retorna uma String para redirecionamento
     */
    public String paginaProduto() {
        limpa();
        produto = new Produto();
        marca = new Marca();
        cp = new CategoriaProduto();
        return "produtos";
    }

    /**
     * Metodo responsanvel por pesquiar os 8 (oito) produtos mais acessados
     *
     * @return retorna uma List de produto
     */
    public List<Produto> listaProdutosSiteAcessos() {
        pDao = new ProdutoDaoImp();
        try {
            produtosSite = null;
            produtosSite = pDao.listarProdutosAtivosSiteAcessos();
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro ao listar produto site : " + e.getMessage());
        }
        return produtosSite;
    }

    /**
     * Metodo responsanvel por pesquiar os 8 (oito) produtos que foram inseridos
     * recentemente
     *
     * @return retorna uma List de produto
     */
    public List<Produto> listaProdutosSiteUltimosSalvos() {
        pDao = new ProdutoDaoImp();
        try {
            produtosSite = null;
            produtosSite = pDao.listarProdutosAtivosSiteRecentes();
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro ao listar produto site : " + e.getMessage());
        }
        return produtosSite;
    }

    /**
     * Metodo para adicionar imagem principal ao produto
     */
    public void adicionarImagemPrincipal() {
        try {
            fotoProd = (FotosProduto) modelImgProd.getRowData();
            fpDao = new FotosProdutoDaoImp();
            fpDao.ativarImgPrincipal(produto.getCodigo(), fotoProd.getCodigo());
            pesquisarImagensPriduto();
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro ativar a imagem " + e.getMessage());
        }
    }

    /**
     * Método responsável por pesquisar os dados do produto selecionado.
     *
     * @param idProduto variavel do tipo int
     * @return retorna uma String para redirecionamento
     */
    public String produtoSelectSite(int idProduto) {
        try {
            pDao = new ProdutoDaoImp();
            produto = pDao.pesqProdutoSelectSite(idProduto);
        } catch (Exception e) {
            contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("Erro controle produtoSelecionado() MSN :" + e.getMessage());
        }
        return "produto_selecionado.faces";
    }

    /**
     * Método responsável por listar produtos pelo filtro escolhido pelo
     * administador na área administrativa do sistema
     *
     */
    public void filtroProdutoAdmin() {
        try {
            modelProduto = null;
            pDao = new ProdutoDaoImp();
            List<Produto> prods = pDao.filtroProdutoAdmin(cp.getCodigo(), marca.getCodigo(), produto.getAtivoString());
            modelProduto = new ListDataModel(prods);
        } catch (Exception e) {
            System.out.println("Erro controle filtroProdutoAdmin() MSN :" + e.getMessage());
        }
        cp = null;
        marca = null;
        ativo = null;
    }

    /**
     * Metodo para adicionar produtos no carrinho, verifica se a quantidade
     * pedida é maior que a quantidade em estoque
     */
    public void adicionarProdutoCarrinho() {
        if (carrinhoCompra == null) {
            carrinhoCompra = new ArrayList();
        }
        try {
            contexto = FacesContext.getCurrentInstance();
            boolean primeiroProdCarrinho = false;
            Produto p = null;
            if (!carrinhoCompra.isEmpty()) {
                for (Produto prod : carrinhoCompra) {
                    if (prod.getCodigo() == produto.getCodigo()) {
                        int qtd = produto.getQuantidade();
                        ++qtd;
                        primeiroProdCarrinho = true;
                        if (pDao.verificaQuantidadeProduto(produto.getCodigo(), qtd)) {
                            // p = new Produto();
                            p = new Produto();
                            p = prod;
                            p.setQuantidade(qtd);
                            carrinhoCompra.remove(prod);
                            break;
                        } else {
                            System.out.println("Ultrapassou o estoque porra");
                            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ultrapassou o limite do estoque", null));
                        }
                    }
                }
                if (primeiroProdCarrinho) {
                    carrinhoCompra.add(p);
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto adicionado", null));
                } else {
                    produto.setQuantidade(1);
                    carrinhoCompra.add(produto);
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto adicionado", null));
                }
            } else {
                produto.setQuantidade(1);
                carrinhoCompra.add(produto);
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto adicionado", null));
            }
            quantidadeItensCarrinho();

        } catch (Exception ex) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
            System.out.println("DEU erro nesta merda MSG :" + ex.getMessage());
        }
    }

    /**
     * Metodo para listar produtos no carrinho
     *
     * @return retorna um List de Produtos
     */
    public List<Produto> listarCarrinho() {
        return carrinhoCompra;
    }

    /**
     * Metodo para adicionar mais produto no estoqe, aumentar a quantidade do
     * produto em estoque recebe o id do produto e a quantidade
     *
     * @param idProduto variavel do tipo ind
     * @param quantidade variavel do tipo ind
     */
    public void addMiasProduto(int idProduto, int quantidade) {
        pDao = new ProdutoDaoImp();
        ++quantidade;
        try {
            contexto = FacesContext.getCurrentInstance();
            Produto p;
            if (pDao.verificaQuantidadeProduto(idProduto, quantidade)) {
                p = new Produto();
                for (Produto prod : carrinhoCompra) {
                    if (prod.getCodigo() == idProduto) {
                        p = prod;
                        carrinhoCompra.remove(prod);
                        break;
                    }
                }
                p.setQuantidade(quantidade);
                carrinhoCompra.add(p);
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto adicionado", null));
            } else {
                System.out.println("Ultrapassa o estoque porra");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ultrapassou o limite do estoque", null));
            }
        } catch (Exception e) {
            System.out.println("FUDEU DEU ERRO MSG : " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
        listarCarrinho();
    }

    /**
     * Metodo para remover produtos do carrinho
     *
     * @param idProduto variavel do tipo int
     * @param quantidade variavel do tipo int
     */
    public void removeProdutoCarrinho(int idProduto, int quantidade) {
        --quantidade;
        contexto = FacesContext.getCurrentInstance();
        if (quantidade > 0) {
            Produto p;
            p = new Produto();
            for (Produto prod : carrinhoCompra) {
                if (prod.getCodigo() == idProduto) {
                    p = prod;
                    carrinhoCompra.remove(prod);
                    break;
                }
            }
            p.setQuantidade(quantidade);
            carrinhoCompra.add(p);

        } else {
            for (Produto prod : carrinhoCompra) {
                if (prod.getCodigo() == idProduto) {
                    carrinhoCompra.remove(prod);
                    break;
                }
            }
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto removido", null));
        listarCarrinho();
    }

    /**
     * Metodo para verificar a quantidade de produtos no carrinho
     *
     * @return a quantidade de itens no carrinho
     */
    public int quantidadeItensCarrinho() {
        if (limpaCarrinho != null) {
            if (limpaCarrinho.equals("limpaCarrinho")) {
                carrinhoCompra = null;
                this.limpaCarrinho = "";
            }
        }
        if (carrinhoCompra == null) {
            return 0;
        } else {
            return carrinhoCompra.size();
        }
        // return 0;
    }

    /**
     * Metodo para somar o valor de todoso os produtos adicionado no carrinho
     *
     * @return retorna um double da soma do carrinho
     */
    public double somaValorCarrinho() {
        double valorCarrinho = 0;
        try {
            for (Produto p : carrinhoCompra) {
                valorCarrinho = valorCarrinho + (p.getQuantidade() * p.getValorVenda());
            }
            return valorCarrinho;
        } catch (Exception ex) {
            System.out.println("Erro ao somar carrinho " + ex.getMessage());
        }
        return 0;
    }
}
