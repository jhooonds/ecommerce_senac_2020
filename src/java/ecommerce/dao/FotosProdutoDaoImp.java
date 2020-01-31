package ecommerce.dao;

import ecommerce.entidade.FotosProduto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Gustavo
 */
public class FotosProdutoDaoImp implements FotosProdutoDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por salvar as fotos dos produtos.
     * 
     * @param obj - varivel do tipo Object
     * @return retorna um Object
     * @throws Exception  - caso ocorra alguma falha para salvar fotos
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        FotosProduto fp = (FotosProduto) obj;
        boolean flag = false;
        try {
            String query = "INSERT INTO fotosproduto (nome,caminho,tipo,tamanho,idProduto,principal) VALUES(?,?,?,?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, fp.getNome());
            pstm.setString(2, fp.getCaminho());
            pstm.setString(3, fp.getTipo());
            pstm.setInt(4, fp.getTamanho());
            pstm.setInt(5, fp.getIdProduto());
            pstm.setBoolean(6, fp.isPrincipal());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar fotos:" + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Método abstrato implemtado automaticamente de FotosProdutoDao
     *
     * @param obj - varivel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Este método é responsável por pesquisar a foto do produto de acordo
     * com o código informado.
     * 
     * @param id - varivel do tipo int
     * @return retorna um Object
     * @throws Exception - caso ocorra alguma falha para pesquisar fotos
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        FotosProduto fp = null;
        try {
            String query = "SELECT caminho, codigo FROM fotosProduto WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                fp = new FotosProduto();
                fp.setCaminho(rs.getString("caminho"));
                fp.setCodigo(rs.getInt("codigo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar fotos: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return fp;

    }

    /**
     * Este método é responsável por excluir a foto de um produto de acordo
     * com o código informado.
     * 
     * @param id - varivel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para excluir foto
     */
    @Override
    public boolean excluir(int id) throws Exception {
        boolean flag = false;
        try {
            String query = "DELETE FROM fotosproduto WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir foto: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Este método é responsável por pesquisar uma lista de fotos de acordo
     * com o código do produto informado.
     * 
     * @param idProduto - variavel do tipo int
     * @return retorna um List do tipo FotosProduto
     * @throws Exception  - caso ocorra alguma falha para fazer a conexao com as fotos
     */
    @Override
    public List<FotosProduto> pesquisaImgProduto(int idProduto) throws Exception {
        List<FotosProduto> imagens = new ArrayList();
        try {
            String query = "SELECT * FROM fotosproduto WHERE idProduto = ? ";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, idProduto);
            rs = pstm.executeQuery();
            while (rs.next()) {
                FotosProduto fp = new FotosProduto();
                fp.setCodigo(rs.getInt("codigo"));
                fp.setNome(rs.getString("nome"));
                fp.setPrincipal(rs.getBoolean("principal"));
                imagens.add(fp);
            }
        } catch (Exception e) {
            System.out.println("Erro conexão Fotos " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return imagens;
    }

    /**
     * Este método é responsável por ativar a imagem principal do produto,
     * é essa imagem que aparece quando o cliente pesquisa o produto.
     * 
     * @param idProduto - variavel do tipo int
     * @param idImg - variavel do tipo int
     * @throws Exception  - caso ocorra alguma falha para alterar imagem principal
     */
    @Override
    public void ativarImgPrincipal(int idProduto, int idImg) throws Exception {
        try {
            String query1 = "UPDATE fotosproduto SET principal = ? WHERE idProduto = ?";
            String query2 = "UPDATE fotosproduto SET principal = ? WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query1);
            pstm.setBoolean(1, false);
            pstm.setInt(2, idProduto);
            pstm.executeUpdate();
            pstm = conn.prepareCall(query2);
            pstm.setBoolean(1, true);
            pstm.setInt(2, idImg);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao alterar imagem principal: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
    }

    /**
     * Este método é responsável por pesquisar a imagem principal do produto.
     * 
     * @param idProduto - variavel do tipo int
     * @return retorna um list do tipo FotosProduto
     * @throws Exception - caso ocorra alguma falha para lista de imagens
     */
    @Override
    public List<FotosProduto> buscaImgPrincipal(int idProduto) throws Exception {
        List<FotosProduto> fps = new ArrayList();
        try {
            String query = "SELECT * FROM fotosproduto WHERE idProduto = ? AND principal = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, idProduto);
            pstm.setBoolean(2, true);
            rs = pstm.executeQuery();
            if (rs.next()) {
                FotosProduto fp = new FotosProduto();
                fp.setNome(rs.getString("nome"));
                fps.add(fp);
            }
        } catch (Exception e) {
            System.out.println("Erro buscar lista de imagens: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return fps;
    }

}
