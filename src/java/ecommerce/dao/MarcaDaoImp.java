package ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ecommerce.entidade.Marca;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jonatan
 */

public class MarcaDaoImp implements MarcaDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por salvar os dados da marca, como: nome,
     * descrição e se está ativa.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para salvar a marca
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        Marca m = (Marca) obj;
        boolean flag = true;
        try {
            String query = "INSERT INTO marca (nome, descricao, ativo) values(?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, m.getNome());
            pstm.setString(2, m.getDescricao());
            pstm.setBoolean(3, m.isAtivo());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar a marca: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por alterar os dados da marca.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar marca
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        Marca m = (Marca) obj;
        boolean flag = true;
        try {
            String query = "UPDATE marca SET nome =?, descricao = ?, ativo = ? WHERE codigo = ? ";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, m.getNome());
            pstm.setString(2, m.getDescricao());
            pstm.setBoolean(3, m.isAtivo());
            pstm.setInt(4, m.getCodigo());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao alterar marca: " + e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * Este método é responsável por pesquisar a marca de acordo com o código
     * informado.
     *
     * @param id - variavel do tipo int
     * @return Object do tipo Marca
     * @throws Exception - caso ocorra alguma falha para pesquisar marca
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        Marca m = null;
        try {
            String query = "SELECT * FROM marca WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                m = new Marca();
                m.setCodigo(rs.getInt("codigo"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));
                m.setAtivo(rs.getBoolean("ativo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar marca: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return m;
    }

    /**
     * Este método é responsável por excluir a marca de acordo com o código
     * informado.
     *
     * @param id - variavel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para excluir marca
     */
    @Override
    public boolean excluir(int id) throws Exception {
        boolean flag = true;
        String query = "DELETE FROM marca WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            pstm.executeQuery();
        } catch (Exception e) {
            System.out.println("Erro ao excluir marca: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por listar todas as marcas ativas no banco de
     * dados.
     *
     * @return List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para listar marcas ativas
     */
    @Override
    public List<Marca> listar() throws Exception {
        List<Marca> marcas = new ArrayList();
        try {
            String query = "SELECT * FROM marca WHERE ativo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setBoolean(1, true);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Marca m = new Marca();
                m.setCodigo(rs.getInt("codigo"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));
                marcas.add(m);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar marcas ativas: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return marcas;
    }

    /**
     * Este método é responsável por listar todas as marcas cadastradas no
     * banco, independente se está ativa ou não para que possam ser editadas.
     *
     * @return List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para listar marcas para
     * edição
     */
    @Override
    public List<Marca> listarEdicao() throws Exception {
        List<Marca> marcas = new ArrayList();
        try {
            String query = "SELECT * FROM marca";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Marca m = new Marca();
                m.setCodigo(rs.getInt("codigo"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));
                m.setAtivo(rs.getBoolean("ativo"));
                if (m.isAtivo()) {
                    m.setAtivoString("sim");
                } else {
                    m.setAtivoString("nao");
                }
                marcas.add(m);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar marcas para edição: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return marcas;
    }
}
