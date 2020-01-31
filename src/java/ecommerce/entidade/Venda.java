package ecommerce.entidade;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public class Venda {

    private int codigo;
    private String protocolo;
    private Date dataVenda;
    private double valorTotal;
    private Status statusVenda;
    private List<Produto> produtos;
    private Pessoa pessoa;
    private String boletoCartao;
    private String numeroBoletoCartao;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Status getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(Status statusVenda) {
        this.statusVenda = statusVenda;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getBoletoCartao() {
        return boletoCartao;
    }

    public void setBoletoCartao(String boletoCartao) {
        this.boletoCartao = boletoCartao;
    }

    public String getNumeroBoletoCartao() {
        return numeroBoletoCartao;
    }

    public void setNumeroBoletoCartao(String numeroBoletoCartao) {
        this.numeroBoletoCartao = numeroBoletoCartao;
    }

}
