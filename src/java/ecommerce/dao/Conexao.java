package ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Jonatan
 */
public class Conexao {

    private static final String nomeDb = "ecommerce";
    private static final String userDb = "root";
    private static final String senhaDb = "";

    /**
     * Método que faz a coenxão com o banco de dados
     * 
     * @return a conexao com o banco de dados
     * @throws Exception - caso tenha falha na conexao
     */
    public static Connection abrirConexao() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nomeDb, userDb, senhaDb);
    }

    /**
     * Método para fechar a conexao com o banco de dados
     * 
     * @param conn - variavel do tipo Connection
     * @param stmt - variavel do tipo Statement
     * @param rs - variavel do tipo ResultSet
     * @throws Exception - caso tenha algum problema para fechar a conexão
     */
    private static void fecha(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

     /**
     * Método que executa o fechamento da conexao com o banco de dados
     * 
     * @param conn - variavel do tipo Connection
     * @param stmt - variavel do tipo Statement
     * @param rs - variavel do tipo ResultSet
     * @throws Exception - caso tenha algum problema para fechar a conexão
     */
    public static void fechaConexao(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        fecha(conn, stmt, rs);
    }

     /**
     * Método que executa o fechamento da conexao com o banco de dados
     * 
     * @param conn - variavel do tipo Connection
     * @param stmt - variavel do tipo Statement
     * @throws Exception - caso tenha algum problema para fechar a conexão
     */
    public static void fechaConexao(Connection conn, Statement stmt) throws Exception {
        fecha(conn, stmt, null);
    }

     /**
     * Método que executa o fechamento da conexao com o banco de dados
     * 
     * @param conn - variavel do tipo Connection
     * @throws Exception - caso tenha algum problema para fechar a conexão
     */
    public static void fechaConexao(Connection conn) throws Exception {
        fecha(conn, null, null);
    }

    
}
