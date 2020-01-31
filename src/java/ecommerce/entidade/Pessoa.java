package ecommerce.entidade;

import java.util.Date;

/**
 * Classe contém todos os dados da tabela pessoa, contém também objetos
 * do tipo endereço, usuario e venda.
 * 
 * @author Jonatan
 */
public class Pessoa {

    private int codigo;
    private String nome;
    private String cpfCnpj; 
    private String sexo; 
    private Date dataNascimento;
    private int dddRes;
    private int telRes;
    private int dddCel;
    private int telCel;
    private Date dataCadastro;
    private Usuario usuario;
    private Endereco endereco; 
    private Venda venda; 

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getDddRes() {
        return dddRes;
    }

    public void setDddRes(int dddRes) {
        this.dddRes = dddRes;
    }

    public int getTelRes() {
        return telRes;
    }

    public void setTelRes(int telRes) {
        this.telRes = telRes;
    }

    public int getDddCel() {
        return dddCel;
    }

    public void setDddCel(int dddCel) {
        this.dddCel = dddCel;
    }

    public int getTelCel() {
        return telCel;
    }

    public void setTelCel(int telCel) {
        this.telCel = telCel;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
