package ecommerce.dao;

import ecommerce.entidade.Endereco;
import ecommerce.entidade.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Jonatan
 */

public class EnderecoDaoImp implements EnderecoDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por salvar os dados de endereço da pessoa
     *
     * @param obj - variavel do tipo Object
     * @return retorna boleano
     * @throws Exception - caso ocorra alguma falha para salvar o endereço
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        boolean flag = true;
        Pessoa p = (Pessoa) obj;
        try {
            String query = "INSERT INTO endereco (rua, numero, complemento, bairro, cidade, cep, idPessoa, estado) VALUES (?,?,?,?,?,?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, p.getEndereco().getRua());
            pstm.setInt(2, p.getEndereco().getNumero());
            pstm.setString(3, p.getEndereco().getComplemento());
            pstm.setString(4, p.getEndereco().getBairro());
            pstm.setString(5, p.getEndereco().getCidade());
            pstm.setString(6, p.getEndereco().getCep());
            pstm.setInt(7, p.getCodigo());
            pstm.setString(8, p.getEndereco().getEstado());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar endereço: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por alterar o endereço da pessoa pelo código
     * informado
     *
     * @param obj - variavel do tipo Object
     * @return retorna boleano
     * @throws Exception - caso ocorra alguma falha para alterar o endereço
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        boolean flag = true;
        Pessoa p = (Pessoa) obj;
        try {
            String query = "UPDATE endereco SET rua =?, numero =?, complemento= ?, bairro = ?, cidade = ?, cep = ? WHERE idPessoa = ? AND codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, p.getEndereco().getRua());
            pstm.setInt(2, p.getEndereco().getNumero());
            pstm.setString(3, p.getEndereco().getComplemento());
            pstm.setString(4, p.getEndereco().getBairro());
            pstm.setString(5, p.getEndereco().getCidade());
            pstm.setString(6, p.getEndereco().getCep());
            pstm.setInt(7, p.getCodigo());
            pstm.setInt(8, p.getEndereco().getCodigo());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao alterar o endereço: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por listar o endereço de acordo com o código
     * informado
     *
     * @param id - variavel do tipo int
     * @return retorna um Objet
     * @throws Exception - caso ocorra alguma falha para pesquisar endereço
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        Endereco e = null;
        try {
            String query = "SELECT * FROM endereco WHERE idPessoa = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                e = new Endereco();
                e.setCodigo(rs.getInt("codigo"));
                e.setCep(rs.getString("cep"));
                e.setRua(rs.getString("rua"));
                e.setNumero(rs.getInt("numero"));
                e.setComplemento(rs.getString("complemento"));
                e.setBairro(rs.getString("bairro"));
                e.setCidade(rs.getString("cidade"));
                e.setEstado(rs.getString("estado"));

            }
        } catch (Exception ex) {
            System.out.println("Erro ao pesquisar endereço: " + ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return e;
    }

    /**
     * Este método é responsável por excluir o endereço pelo código informado
     *
     * @param id - variavel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para excluir endereço
     */
    @Override
    public boolean excluir(int id) throws Exception {
        boolean flag = true;
        try {
            String query = "DELETE FORM endereco WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            pstm.executeQuery();
        } catch (Exception e) {
            System.out.println("Erro ao excluir endereço: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

}
