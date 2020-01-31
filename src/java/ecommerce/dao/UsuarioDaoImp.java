package ecommerce.dao;

import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Jonatan
 */
public class UsuarioDaoImp implements UsuarioDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Método abstrato implemtado automaticamente de UsuarioDao
     *
     * @param obj - uma variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso caia em alguma exceção 
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     * Método abstrato implemtado automaticamente de UsuarioDao
     *
     * @param obj - uma variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso caia em alguma exceção 
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     * Método responsável por salvar dados do usuário, como: email, senha e o
     * tipo do usuário.
     *
     * @param id - variavel do tipo int
     * @return retorna um objeto do tipo Object
        * @throws Exception - caso ocorra alguma falha para pesquisar usuario
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        Usuario user = null;
        String query = "SELECT email FROM usuario WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                user = new Usuario();
                user.setCodigo(rs.getInt("codigo"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("senha"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar usuario: " + e.getMessage());
        }
        return user;
    }

    /**
     * Método responsável por desativar usuário de acordo com o código
     * informado.
     *
     * @param id - variavel do tipo int
     * @return retorna um boleado 
     * @throws Exception - caso ocorra alguma falha para desativar o usuário
     */
    @Override
    public boolean excluir(int id) throws Exception {
        boolean flag = true;
        try {
            String query = "UPDATE usuario SET ativo = false WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao desativar o usuário: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Método responsável por salvar dados do usuário, como: email, senha e o
     * tipo do usuário inserido.
     *
     * @param u - variavel objeto do tipo Usuario
     * @return Retorna um objeto do tipo Usuario
     * @throws Exception - caso ocorra alguma falha para salvar o usuário
     */
    @Override
    public Usuario salvar(Usuario u) throws Exception {
        try {
            String query = "INSERT INTO usuario (email,senha,tipoUsuario) VALUES(?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, u.getEmail());
            pstm.setString(2, u.getSenha());
            pstm.setString(3, u.getTpUsuario());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            rs.next();
            u.setCodigo(rs.getInt(1));
        } catch (Exception e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());

        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return u;
    }

    /**
     * Método responsável por autenticar o login do usuário.
     *
     * @param u - variavel objeto do tipo Usuario
     * @return retorna um objeto do tipo Pessoa
     * @throws Exception - caso ocorra alguma falha para autenticar o login
     */
    @Override
    public Pessoa autenticar(Usuario u) throws Exception {
        Pessoa p = null;
        try {
            String query = "SELECT p.nome, p.codigo, u.email, u.codigo, u.tipoUsuario FROM usuario u JOIN pessoa p ON u.codigo = p.idUsuario WHERE u.email = ? AND u.senha = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, u.getEmail());
            pstm.setString(2, u.getSenha());
            rs = pstm.executeQuery();
            if (rs.next()) {
                p = new Pessoa();
                u.setCodigo(rs.getInt("u.codigo"));
                u.setEmail(rs.getString("u.email"));
                u.setTpUsuario(rs.getString("u.tipoUsuario"));
                p.setCodigo(rs.getInt("p.codigo"));
                p.setNome(rs.getString("p.nome"));
                p.setUsuario(u);
            }
        } catch (Exception e) {
            System.out.println("Erro ao autenticar login: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return p;
    }

     /**
     * Método responsável por alterar dados do usuário
     *
     * @param usuario - variavel objeto do tipo Usuario
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar dados do usuario
     */
    @Override
    public boolean alteraDadosUsuario(Usuario usuario) throws Exception {
        boolean flag = true;
        String queryTudo = "UPDATE usuario SET email = ?, senha = ? WHERE codigo = ?";
        String querySenha = "UPDATE usuario SET senha = ? WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            if (usuario.getEmail().equals("")) {
                pstm = conn.prepareCall(querySenha);
                pstm.setString(1, usuario.getSenha());
                pstm.setInt(2, usuario.getCodigo());
                pstm.executeUpdate();
            } else {
                pstm = conn.prepareCall(queryTudo);
                pstm.setString(1, usuario.getEmail());
                pstm.setString(2, usuario.getSenha());
                pstm.setInt(3, usuario.getCodigo());
                pstm.executeUpdate();
            }
        } catch (Exception e) {
            flag = false;
            System.out.println("Erro ao alterar dados do usuario : " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }

        return flag;
    }

}
