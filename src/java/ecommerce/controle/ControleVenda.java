package ecommerce.controle;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import ecommerce.dao.VendaDao;
import ecommerce.dao.VendaDaoImp;
import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Produto;
import ecommerce.entidade.Status;
import ecommerce.entidade.Venda;
import ecommerce.util.MD5;
import ecommerce.util.Protocolo;
import ecommerce.util.SessionContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jonatan
 */
@ManagedBean
public class ControleVenda {

    private List<Produto> carrinho;
    private Pessoa pessoa;
    private Venda venda;
    private DataModel modelVendaPendente;
    private DataModel modelVendaDespachar;
    private VendaDao vDao;
    private FacesContext contexto;
    private StreamedContent file;
    private String caminhoBoleto;
    private boolean vendaCartao = false;

    /**
     * Metodo para listar as vendas despachadas e pendentes ao acessar o administrativo do sistema
     */
    @PostConstruct
    public void inicia() {
        Pessoa p = (Pessoa) SessionContext.getInstance().getUsuarioLogado();
        if (p != null) {
            if (p.getUsuario().getTpUsuario().equals("admin")) {
                listarVendasDespache();
                listarVendasPendentes();
            }
        }
    }

    public DataModel getModelVendaPendente() {
        return modelVendaPendente;
    }

    public DataModel getModelVendaDespachar() {
        return modelVendaDespachar;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Venda getVenda() {
        if (venda == null) {
            venda = new Venda();
        }
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public String getCaminhoBoleto() {
        return caminhoBoleto;
    }

    public void setCaminhoBoleto(String caminhoBoleto) {
        this.caminhoBoleto = caminhoBoleto;
    }

    public boolean isVendaCartao() {
        return vendaCartao;
    }

    public void setVendaCartao(boolean vendaCartao) {
        this.vendaCartao = vendaCartao;
    }

    public StreamedContent getFile() {
        return file;
    }

    /**
     * Método que redireciona usuario para finalizar a venda no produto, caso
     * nao este logado ao clicar em em "Comprar" é redirecionado para finalizar
     * compra se nao estiver logado é direcionado para o carrinho
     *
     * @return
     */
    public String redirecionaPaginaVenda() {
        Pessoa p = SessionContext.getInstance().getUsuarioLogado();
        String url;
        if (p != null) {
            url = "finaliza.faces?cmd=" + MD5.criptografia("finalizarCompra");
        } else {
            url = "venda.faces";
        }
        return url;
    }

    /**
     * Metodo para salvar venda do tipo Cartão
     * 
     * @param p variavel objeto do tipo Pessoa
     * @param carrinho variavel List do tipo Produto
     */
    public void salvarVendaCartao(Pessoa p, List<Produto> carrinho) {
        vDao = new VendaDaoImp();
        System.out.println("Entrou");
        try {
            // venda = new Venda();
            venda.setPessoa(p);
            venda.setProdutos(carrinho);
            venda.setDataVenda(new Date());
            Status s = new Status();
            s.setCodigo(1);
            venda.setStatusVenda(s);
            venda.setProtocolo(Protocolo.getNumeroProtocolo());
            venda.setBoletoCartao("Cartão");
            vDao.salvar(venda);
            vendaCartao = true;
            this.carrinho = null;
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + e.getMessage());
        }
    }

    /**
     * Metodo para ativar o campo para inserir o numero do cartão
     */
    public void ativaCampoNumCartao() {
        vendaCartao = true;
    }

    /**
     * Metodo para salvar a venda por boleto
     *
     * @param p - objeto do tipo pessoa
     * @param carrinho - lista de produto que está no carrinho
     */
    public void salvarVendaBoleto(Pessoa p, List<Produto> carrinho) {
        vDao = new VendaDaoImp();
        System.out.println("Entrou");
        try {
            venda = new Venda();
            venda.setPessoa(p);
            venda.setProdutos(carrinho);
            venda.setDataVenda(new Date());
            Status s = new Status();
            s.setCodigo(1);
            venda.setStatusVenda(s);
            venda.setProtocolo(Protocolo.getNumeroProtocolo());
            venda.setBoletoCartao("Boleto");
            String nomeBoletoNumero = Protocolo.getNumeroProtocolo();
            venda.setNumeroBoletoCartao(nomeBoletoNumero);
            vDao.salvar(venda);
            gerarBoleto(nomeBoletoNumero);
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + e.getMessage());
        }
    }

    /**
     * Metodo que gera o boleto
     *
     * @param nome - variavel do tipo String
     */
    private void gerarBoleto(String nome) {
        Datas datas = Datas.novasDatas()
                .comDocumento(1, 5, 2008)
                .comProcessamento(1, 5, 2008)
                .comVencimento(2, 5, 2008);

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Servidão Julia Alexandre Florindo, 90")
                .comBairro("Barra da Lagoa")
                .comCep("88061-423")
                .comCidade("Florianópolis")
                .comUf("SC");

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("Gustavo Humberto Agostinho")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro(venda.getPessoa().getEndereco().getRua() + "," + venda.getPessoa().getEndereco().getNumero())
                .comBairro(venda.getPessoa().getEndereco().getBairro())
                .comCep(venda.getPessoa().getEndereco().getCep())
                .comCidade(venda.getPessoa().getEndereco().getCidade())
                .comUf(venda.getPessoa().getEndereco().getEstado());

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome(venda.getPessoa().getNome())
                .comDocumento(venda.getPessoa().getCpfCnpj())
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();
        String[] prod = new String[venda.getProdutos().size()];
        int i = 0;
        for (Produto p : venda.getProdutos()) {
            prod[i] = "Produto : " + p.getNome() + " Quantidade : " + p.getQuantidade() + " Valor Unidade : R$" + p.getValorVenda();
            i++;
        }
        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto(venda.getValorTotal())
                .comNumeroDoDocumento(nome)
                .comInstrucoes(prod)
                .comLocaisDePagamento("Caixa Econômica Federal", "Banco do Brasil");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        // Para gerar um array de bytes a partir de um PDF  
        byte[] bPDF = gerador.geraPDF();

        try {
            File e = new File(getRealPath() + "\\tempBoleto\\");
            e.mkdirs();
            FileOutputStream fos = new FileOutputStream(getRealPath() + "\\tempBoleto\\" + nome + ".pdf");
            fos.write(bPDF);
            fos.close();
            caminhoBoleto = nome + ".pdf";
            carregaArquivoDownload();
        } catch (Exception ex) {
            System.out.println("Erro ao gravar Boleto MSG " + ex.getMessage());
        }
    }

    /**
     * Metodo resposanvel por disponibilizar o arquivo para download
     */
    public void carregaArquivoDownload() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/tempBoleto/" + caminhoBoleto);
        file = new DefaultStreamedContent(stream, null, caminhoBoleto);
    }

    /**
     * Metodo resposanvel por disponibilizar o cminho real do path
     *
     * @return retorna o caminho real do path
     */
    public String getRealPath() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
        return context.getRealPath("/");
    }

    /**
     * Metodo responsavel por listar os vendas pendentes
     */
    public void listarVendasPendentes() {
        vDao = new VendaDaoImp();
        try {
            List<Venda> vendas = vDao.listarVendaPendente();
            modelVendaPendente = new ListDataModel(vendas);
        } catch (Exception e) {
            System.out.println("Erro controle MSG : " + e.getMessage());
        }
    }

    /**
     * Metodo responsavel por listar os vendas que estão com estatos de
     * pagamento OK e ainda nao foram para entrega
     */
    public void listarVendasDespache() {
        vDao = new VendaDaoImp();
        try {
            List<Venda> vendas = vDao.listarVendaDespachar();
            modelVendaDespachar = new ListDataModel(vendas);
        } catch (Exception e) {
            System.out.println("Erro ao listar despache: " + e.getMessage());
        }
    }

    /**
     * Metodo para carregar a lista de vendas num modal
     *
     * @param model variavel do tipo DataModel
     * @return retorna objeto do tipo Venda
     */
    private Venda carregaModalVenda(DataModel model) {
        Venda v = (Venda) model.getRowData();
        return v;
    }

    /**
     * Metodo responsavel por modificar o estatus da venda para aprovado
     */
    public void aprovarVanda() {
        vDao = new VendaDaoImp();
        Venda v = carregaModalVenda(modelVendaPendente);
        try {
            contexto = FacesContext.getCurrentInstance();
            if (vDao.aprovarVenda(v.getCodigo())) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com secesso!!", null));
            } else {
                System.out.println("DEU RUIM CACHOEIRA!!");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao  realizada operação!!", null));
            }
            listarVendasDespache();
            listarVendasPendentes();
        } catch (Exception e) {
            System.out.println("Erro ao aprovar a venda: " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
    }

    /**
     * Metodo responsavel por rejeitar a venda especifica informado pelo id da
     * venda
     */
    public void rejeitarVenda() {
        vDao = new VendaDaoImp();
        Venda v = carregaModalVenda(modelVendaPendente);
        try {
            contexto = FacesContext.getCurrentInstance();
            if (vDao.rejeitarVenda(v.getCodigo())) {
                System.out.println("BOA CACHOEIRA!!");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com secesso!!", null));
            } else {
                System.out.println("DEU RUIM CACHOEIRA!!");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao  realizada operação!!", null));
            }
        } catch (Exception e) {
            System.out.println("Erro ao rejeitar a venda: " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
    }

    /**
     * Metodo responsavel por despachar a Venda mudar os estatos da venda para
     * enviado
     */
    public void despacharVenda() {
        vDao = new VendaDaoImp();
        Venda v = carregaModalVenda(modelVendaDespachar);
        try {
            contexto = FacesContext.getCurrentInstance();
            if (vDao.despacharVenda(v.getCodigo())) {
                System.out.println("BOA CACHOEIRA!!");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com secesso!!", null));
            } else {
                System.out.println("DEU RUIM CACHOEIRA!!");
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao  realizada operação!!", null));
            }
            listarVendasDespache();
        } catch (Exception e) {
            System.out.println("Erro ao despachar venda:" + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro critico!!!\nContate o administrador do sitema!!", null));
        }
    }

    /**
     * Metodo responsavel por gerar a lista de compras do usuário
     *
     * @return retorna uma List de Venda
     */
    public List<Venda> comprasUsuario() {
        vDao = new VendaDaoImp();
        try {
            Pessoa p = SessionContext.getInstance().getUsuarioLogado();
            List<Venda> compras = vDao.comprasUsuario(p.getCodigo());
            return compras;
        } catch (Exception e) {
            System.out.println("Erro ao listar compras do usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método responsável por verificar se o usuario esta logado para
     * redirecionamento de pagina.
     *
     * @return String - retorna a página
     */
    public String validacoesParaCompra() {
        Pessoa p = SessionContext.getInstance().getUsuarioLogado();
        if (p == null) {
            return "/login.faces?faces-redirect=true&cmd=" + MD5.criptografia("compra"); // bloco de tela que pede para o usuario logar ou se cadastrar;
        } else {
            return "/finaliza.faces?faces-redirect=true&cmd=" + MD5.criptografia("finalizarCompra"); // bloco de tela que finaliza a venda;
        }
    }

}
