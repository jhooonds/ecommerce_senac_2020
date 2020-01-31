package ecommerce.dao;

import ecommerce.entidade.CategoriaProduto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author JOnatan
 */
public class CategoriaProdutoDaoImp implements CategoriaProdutoDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por inserir na tabela categoriaProduto, os
     * dados da categoria do produto como: nome, descrição e ativo.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para salvar a categoria do
     * produto
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        CategoriaProduto cp = (CategoriaProduto) obj;
        boolean flag = false;
        try {
            String query = "INSERT INTO categoriaproduto (nome, descricao, ativo) VALUES(?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, cp.getNome());
            pstm.setString(2, cp.getDescricao());
            pstm.setBoolean(3, cp.isAtivo());
            pstm.executeUpdate();
            flag = true;
        } catch (Exception e) {
            System.out.println("Erro ao salvar a categoria do produto: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por alterar os dados da categoria do produto de
     * acordo com o código informado.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar a categoria do
     * produto
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        CategoriaProduto cp = (CategoriaProduto) obj;
        boolean flag = false;
        try {
            String query = "UPDATE categoriaproduto SET nome = ?, descricao = ?, ativo = ? WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, cp.getNome());
            pstm.setString(2, cp.getDescricao());
            pstm.setBoolean(3, cp.isAtivo());
            pstm.setInt(4, cp.getCodigo());
            pstm.executeUpdate();
            flag = true;
        } catch (Exception e) {
            System.out.println("Erro ao alterar a categoria do produto: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Método abstrato implemtado automaticamente de CategoriaProdutoDao
     *
     * @param id - varivel do tipo int
     * @return retorna um obejo do tipo Object
     * @throws Exception - caso ocorra alguma falha para pesquisar
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método abstrato implemtado automaticamente de CategoriaProdutoDao
     *
     * @param id - varivel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para excluir
     */
    @Override
    public boolean excluir(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Este método é responsável por listar todas as categorias de produtos
     * ativas cadastradas no banco de dados.
     *
     * @return retorna um List do CategoriaProduto
     * @throws Exception - caso ocorra alguma falha para listar as categorias de
     * produtos ativas
     */
    @Override
    public List<CategoriaProduto> listar() throws Exception {
        List<CategoriaProduto> categorias = new ArrayList();
        try {
            String query = "SELECT * FROM categoriaproduto WHERE ativo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setBoolean(1, true);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CategoriaProduto cp = new CategoriaProduto();
                cp.setCodigo(rs.getInt("codigo"));
                cp.setNome(rs.getString("nome"));
                cp.setDescricao(rs.getString("descricao"));
                categorias.add(cp);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar as categorias de produtos ativas: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return categorias;
    }

    /**
     * Este método é responsável por listar todas as categorias cadastradas no
     * banco de dados.
     *
     * @return retorna um List do CategoriaProduto
     * @throws Exception - caso ocorra alguma falha para listar todas as
     * categorias de produtos
     */
    @Override
    public List<CategoriaProduto> listarEdicao() throws Exception {
        List<CategoriaProduto> categorias = new ArrayList();
        try {
            String query = "SELECT * FROM categoriaproduto";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CategoriaProduto cp = new CategoriaProduto();
                cp.setCodigo(rs.getInt("codigo"));
                cp.setNome(rs.getString("nome"));
                cp.setDescricao(rs.getString("descricao"));
                cp.setAtivo(rs.getBoolean("ativo"));
                cp.setAtivoString("sim");
                if (!cp.isAtivo()) {
                    cp.setAtivoString("nao");
                }
                categorias.add(cp);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar todas as categorias de produtos: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return categorias;
    }

}
