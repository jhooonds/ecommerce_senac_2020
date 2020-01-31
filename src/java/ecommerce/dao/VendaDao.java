package ecommerce.dao;

import ecommerce.entidade.Venda;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public interface VendaDao extends BaseDao {

    /**
     * Metodo responsavel por listar os vendas pendentes
     *
     * @return - retorna uma lista de Vendas
     * @throws Exception - caso ocorra alguma falha para criar a lista
     */
    List<Venda> listarVendaPendente() throws Exception;

    /**
     * Metodo responsavel por listar os vendas que estão com estatos de
     * pagamento OK e ainda nao foram para entrega
     *
     * @return - retorna uma lista de Vendas
     * @throws Exception - caso ocorra alguma falha para criar a lista
     */
    List<Venda> listarVendaDespachar() throws Exception;

    /**
     * Metodo responsavel por modificar o estatus da venda para aprovado
     *
     * @param idVenda - variavel do tipo int
     * @return - retorna um valor do tipo boleano
     * @throws Exception - caso ocorra alguma falha para gerar o valor boleano
     */
    boolean aprovarVenda(int idVenda) throws Exception;

    /**
     * Metodo responsavel por rejeitar a venda especifica informado pelo id
     *
     * @param idVenda -  variavel do tipo int
     * @return - retorna um valor do tipo boleano
     * @throws Exception - caso ocorra alguma falha para gerar o valor boleano
     */
    boolean rejeitarVenda(int idVenda) throws Exception;

    /**
     * Metodo responsavel por mudar os estatos da venda para enviado
     *
     * @param idVenda -é o id da venda
     * @return - retorna um valor do tipo boleano
     * @throws Exception - caso ocorra alguma falha para gerar o valor boleano
     */
    boolean despacharVenda(int idVenda) throws Exception;

    /**
     * Metodo responsavel por mudar os estatos da venda para enviado
     *
     * @param idUsuario - variavel do tipo int
     * @return - retorna  um List de Venda
     * @throws Exception - caso ocorra alguma falha para gerar o List de Venda
     */
    List<Venda> comprasUsuario(int idUsuario) throws Exception;

}
