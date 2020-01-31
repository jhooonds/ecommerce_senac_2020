package ecommerce.dao;

import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Produto;
import ecommerce.entidade.Status;
import ecommerce.entidade.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jonatan
 */
public class VendaDaoImp implements VendaDao {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    /**
     * Este método é responsável por salvar os dados da venda, percorre a lista
     * de produtos para obter o valor total da venda e insere
     *
     * @param obj - variavel do tipo Object
     * @return - retorna a variavel falg do tipo boleana
     * @throws Exception - caso ocorra alguma falha para salvar a venda
     */
    @Override
    public boolean salvar(Object obj) throws Exception {
        double somaValor = 0;
        boolean flag = true;
        Venda v = (Venda) obj;
        //soma valor de todos os produtos
        for (Produto p : v.getProdutos()) {
            somaValor = somaValor + (p.getValorVenda() * p.getQuantidade());
        }
        //seta valor total da venda
        v.setValorTotal(somaValor);
        try {
            conn = Conexao.abrirConexao();
            String query = "insert into venda (protocolo, dataVenda, valorTotal, idPessoa, idStatus, boletoCartao, numeroBoletoCartao) values (?,?,?,?,?,?,?)";
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, v.getProtocolo());
            pstm.setDate(2, new java.sql.Date(v.getDataVenda().getTime()));
            pstm.setDouble(3, v.getValorTotal());
            pstm.setInt(4, v.getPessoa().getCodigo());
            pstm.setDouble(5, v.getStatusVenda().getCodigo());
            pstm.setString(6, v.getBoletoCartao());
            pstm.setString(7, v.getNumeroBoletoCartao());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            rs.next();
            v.setCodigo(rs.getInt(1));
            //salva idVenda e idProduto na tabela pedido
            salvarPedido(v);
        } catch (Exception e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return flag;
    }

    /**
     * Este método é responsável por inserir idVenda e idProduto na tabela
     * pedido que é um relacionamento n menosr m entre a tabela venda e produto
     *
     * @param v - variavel objeto do tipo Venda
     * @throws java.lang.Exception - caso ocorra alguma falha para salvar um pedido
     */
    public void salvarPedido(Venda v) throws Exception {
        try {
            conn = Conexao.abrirConexao();

            //percorre a lista de produtos e salva
            for (Produto p : v.getProdutos()) {
                String query = "insert into pedido (idVenda, idProduto) values (?,?)";
                pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstm.setInt(1, v.getCodigo());
                pstm.setInt(2, p.getCodigo());
                pstm.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar pedido: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
    }

      /**
     * Método abstrato implemtado automaticamente de VendaDao
     *
     * @param obj  - variavel objeto do tipo Object
     * @return - retorna um boleano
     * @throws Exception - caso ocorra alguma falha para alterar
     */
    @Override
    public boolean alterar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

      /**
     * Método abstrato implemtado automaticamente de VendaDao
     *
     * @param id  - variavel do tipo int
     * @return - retorna um  objeto do tipo Object
     * @throws Exception - caso ocorra alguma falha para pesquisar
     */
    @Override
    public Object pesquisar(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     /**
     * Método abstrato implemtado automaticamente de VendaDao
     *
     * @param id  - variavel do tipo int
     * @return - retorna um boleano
     * @throws Exception - caso ocorra alguma falha para excluir
     */
    @Override
    public boolean excluir(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método responsável por listar todas as vendas pendentes.
     *
     * @return um List do tipo Venda
     * @throws Exception - caso ocorra alguma falha para listar as vendas pendentes
     */
    @Override
    public List<Venda> listarVendaPendente() throws Exception {
        List<Venda> vendasPendentes = new ArrayList();
        String query = "SELECT v.*, p.nome , p.cpfCnpj, s.nome FROM venda v JOIN status s ON v.idStatus = s.codigo JOIN pessoa p ON v.idPessoa = p.codigo WHERE s.nome = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, "Pendente");
            rs = pstm.executeQuery();
           while(rs.next()) {
                Pessoa pessoa = new Pessoa();
                Venda venda = new Venda();
                Status status = new Status();
                pessoa.setNome(rs.getString("p.nome"));
                pessoa.setCpfCnpj(rs.getString("p.cpfCnpj"));
                status.setNome(rs.getString("s.nome"));
                venda.setCodigo(rs.getInt("v.codigo"));
                venda.setProtocolo(rs.getString("v.protocolo"));
                venda.setDataVenda(rs.getDate("v.dataVenda"));
                venda.setValorTotal(rs.getDouble("v.valorTotal"));
                venda.setPessoa(pessoa);
                venda.setStatusVenda(status);
                vendasPendentes.add(venda);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar vendas pendentes: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return vendasPendentes;
    }

    /**
     * Método responsável por listar todas as vendas que estão com o status:
     * "despachar".
     *
     * @return retorna um List do tipo Venda
     * @throws Exception - caso ocorra alguma falha para listar as vendas para despache
     */
    @Override
    public List<Venda> listarVendaDespachar() throws Exception {
        List<Venda> vendasDespachar = new ArrayList();
        String query = "SELECT v.*, p.nome , p.cpfCnpj, s.nome FROM venda v JOIN status s ON v.idStatus = s.codigo JOIN pessoa p ON v.idPessoa = p.codigo WHERE s.nome = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setString(1, "Aprovada");
            rs = pstm.executeQuery();
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                Venda venda = new Venda();
                Status status = new Status();
                pessoa.setNome(rs.getString("p.nome"));
                pessoa.setCpfCnpj(rs.getString("p.cpfCnpj"));
                status.setNome(rs.getString("s.nome"));
                venda.setCodigo(rs.getInt("v.codigo"));
                venda.setProtocolo(rs.getString("v.protocolo"));
                venda.setDataVenda(rs.getDate("v.dataVenda"));
                venda.setValorTotal(rs.getDouble("v.valorTotal"));
                venda.setPessoa(pessoa);
                venda.setStatusVenda(status);
                vendasDespachar.add(venda);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar as vendas para despache: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return vendasDespachar;
    }

    /**
     * Método responsável por alterar o status da venda para "aprovada", de
     * acordo com o código informado.
     *
     * @param idVenda - variavel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para aprovar a venda
     */
    @Override
    public boolean aprovarVenda(int idVenda) throws Exception {
        boolean flag = true;
        String query = "UPDATE venda SET idStatus = ? WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, 2);
            pstm.setInt(2, idVenda);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao aprovar venda: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }

        return flag;
    }

    /**
     * Método responsável por alterar o status da venda para "rejeitada", ação
     * permitida apenas pelo admin do sistema.
     *
     * @param idVenda - variavel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para rejeitar a venda
     */
    @Override
    public boolean rejeitarVenda(int idVenda) throws Exception {
        boolean flag = true;
        String query = "UPDATE venda SET idStatus = ? WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, 3);
            pstm.setInt(2, idVenda);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao rejeitar a venda: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

    /**
     * Método responsável por alterar o status da venda para "despachada".
     *
     * @param idVenda - variavel do tipo int
     * @return retorna um boleano
     * @throws Exception - caso ocorra alguma falha para despachar a venda
     */
    @Override
    public boolean despacharVenda(int idVenda) throws Exception {
        boolean flag = true;
        String query = "UPDATE venda SET idStatus = ? WHERE codigo = ?";
        try {
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, 4);
            pstm.setInt(2, idVenda);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao despachar a venda: " + e.getMessage());
            flag = false;
        } finally {
            Conexao.fechaConexao(conn, pstm);
        }
        return flag;
    }

     /**
     * Método responsável por alterar o status da venda para "despachada".
     *
     * @param idUsuario - variavel do tipo int
     * @return retorna um List de Venda
     * @throws Exception - caso ocorra alguma falha para listas compras do Usuario
     */
    @Override
    public List<Venda> comprasUsuario(int idUsuario) throws Exception {
        List<Venda> compras = new ArrayList();
        try {
            String query = "SELECT v.*, s.nome,s.codigo FROM venda v JOIN pessoa p ON p.codigo = v.idPessoa JOIN status s ON s.codigo = v.idStatus WHERE p.codigo = ?";
            conn = Conexao.abrirConexao();
            pstm = conn.prepareCall(query);
            pstm.setInt(1, idUsuario);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Venda v = new Venda();
                Status s = new Status();
                s.setCodigo(rs.getInt("s.codigo"));
                s.setNome(rs.getString("s.nome"));
                v.setCodigo(rs.getInt("v.codigo"));
                v.setProtocolo(rs.getString("v.protocolo"));
                v.setDataVenda(rs.getDate("v.dataVenda"));
                v.setValorTotal(rs.getDouble("v.valorTotal"));
                v.setStatusVenda(s);
                compras.add(v);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listas compras do Usuario: " + e.getMessage());
        } finally {
            Conexao.fechaConexao(conn, pstm, rs);
        }
        return compras;
    }
}
