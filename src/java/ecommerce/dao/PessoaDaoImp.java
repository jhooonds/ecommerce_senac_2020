package ecommerce.dao;

import ecommerce.entidade.Endereco;
import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Jonatan
 */
public class PessoaDaoImp implements PessoaDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por salvar os dados da pessoa e endereço.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para salvar pessoa
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        boolean flag = false;
        Pessoa p = (Pessoa) obj;
        Usuario u = p.getUsuario();
        UsuarioDao uDao = new UsuarioDaoImp();
        try {
            u = uDao.salvar(u);

            EnderecoDao eDao = new EnderecoDaoImp();
            String query = "INSERT INTO pessoa (nome,cpfCnpj,sexo,dataNascimento,dddRes,telRes,dddCel,telCel,dataCadastro,idUsuario) VALUES (?,?,?,?,?,?,?,?,?,?)";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getCpfCnpj());
            pstm.setString(3, p.getSexo());
            pstm.setDate(4, new java.sql.Date(p.getDataNascimento().getTime()));
            pstm.setInt(5, p.getDddRes());
            pstm.setInt(6, p.getTelRes());
            pstm.setInt(7, p.getDddCel());
            pstm.setInt(8, p.getTelCel());
            pstm.setDate(9, new java.sql.Date(new Date().getTime()));
            pstm.setInt(10, u.getCodigo());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            rs.next();
            p.setCodigo(rs.getInt(1));

            //salva endereço
            if (eDao.salvar(p)) {
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar pessoa: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }

        return flag;
    }

    /**
     * Este método é responsável por alterar os dados da pessoa e endereço.
     *
     * @param obj - variavel do tipo Object
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar pessoa
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        boolean flag = false;
        Pessoa p = (Pessoa) obj;
        EnderecoDao eDao = new EnderecoDaoImp();
        try {
            String query = "UPDATE pessoa SET nome = ?, cpfCnpj = ?, sexo = ?, dataNascimento = ? , dddRes = ?, telRes = ?, dddCel = ?, telCel = ? WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getCpfCnpj());
            pstm.setString(3, p.getSexo());
            pstm.setDate(4, new java.sql.Date(p.getDataNascimento().getTime()));
            pstm.setInt(5, p.getDddRes());
            pstm.setInt(6, p.getTelRes());
            pstm.setInt(7, p.getDddCel());
            pstm.setInt(8, p.getTelCel());
         //   pstm.setInt(9, p.getUsuario().getCodigo());
            pstm.setInt(9, p.getCodigo());
            pstm.executeUpdate();

            //altera endereço
            if (eDao.alterar(p)) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            System.out.println("Erro ao alterar pessoa: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Método responsável por pesquisar dados da pessoa de acordo com o código
     * informado.
     *
     * @param id - variavel do tipo int
     * @return retorna um Object
     * @throws Exception - caso ocorra alguma falha para pesquisar pessoa
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        Pessoa p = null;
        EnderecoDao eDao = new EnderecoDaoImp();
        try {
            String query = "SELECT * FROM pessoa WHERE codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                p = new Pessoa();
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setCpfCnpj(rs.getString("cpfCnpj"));
                p.setSexo(rs.getString("sexo"));
                p.setDataNascimento(rs.getDate("dataNascimento"));
                p.setDddRes(rs.getInt("dddRes"));
                p.setTelRes(rs.getInt("telRes"));
                p.setDddCel(rs.getInt("dddCel"));
                p.setTelCel(rs.getInt("telCel"));
                p.setDataCadastro(rs.getDate("dataCadastro"));
                p.setEndereco((Endereco) eDao.pesquisar(p.getCodigo()));
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar pessoa: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return p;
    }

     /**
     * Método responsável por excluir usuário de acordo com o código
     * informado.
     *
     * @param id - variavel do tipo int
     * @return retorna um boleado 
     * @throws Exception - caso ocorra alguma falha para desativar o usuário
     */
    @Override
    public boolean excluir(int id) throws Exception {
        return false;
    }

}
