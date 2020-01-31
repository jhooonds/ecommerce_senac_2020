package ecommerce.dao;

import ecommerce.entidade.Produto;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public interface ProdutoDao extends BaseDao {

    /**
     * Método responsável por salvar dados do produto retornando o código
     * inserido.
     *
     * @param produto - variavel objeto do tipo Produto
     * @return retorna um objeto do tipo Produto
     * @throws Exception - caso ocorra alguma falha para salvar o produto
     */
    Produto salvarProduto(Produto produto) throws Exception;

    /**
     * Método responsável por listar todos os produtos cadastrados no banco de
     * dados.
     *
     * @return retorna um List do tipo Produto
     * @throws Exception - caso ocorra alguma falha para listar produtos
     * cadastrados
     */
    List<Produto> listarProdutos() throws Exception;

    /**
     * Metodo responsanvel por pesquiar os 8 (oito) produtos mais acessados
     *
     * @return retorna um List Produto
     * @throws Exception - caso ocorra alguma falha ou exceção
     */
    List<Produto> listarProdutosAtivosSiteAcessos() throws Exception;

    /**
     * Metodo responsanvel por pesquiar os 8 (oito) produtos que foram inseridos
     * recentemente
     *
     * @return retorna um List Produto
     * @throws Exception - caso ocorra alguma falha ou exceção
     */
    List<Produto> listarProdutosAtivosSiteRecentes() throws Exception;

    /**
     * Método responsável por listar produtos pelo filtro escolhido pelo
     * administador.
     *
     * @param idCategoria - variavel do tipo int
     * @param idMarca - variavel do tipo int
     * @param ativo - variavel do tipo String
     * @return retorna um List do tipo Produto
     * @throws Exception - caso ocorra alguma falha para filtrar produtos
     */
    List<Produto> filtroProdutoAdmin(int idCategoria, int idMarca, String ativo) throws Exception;

    /**
     * Método responsável por pesquisar os dados do produto selecionado.
     *
     * @param idProduto - variavel do tipo int
     * @return retorna um objeto do tipo Produto
     * @throws Exception - caso ocorra alguma falha para pesquisar dados do
     * produto
     */
    Produto pesqProdutoSelectSite(int idProduto) throws Exception;

    /**
     * Metodo responsavel por verificar a quantidade do produto quanto o usuario
     * esta na tebela do carrinho de compras e adiciona mais um produto do mesmo
     * tipo se a quantidade fornecida pelo o usuario for maior que a quantidade
     * em estoque o metodo retorna false.
     *
     * @param idProduto - variavel do tipo int
     * @param quantidade - variavel do tipo int
     * @return retorna um boeano
     * @throws Exception - caso ocorra alguma falha ou exceção
     */
    boolean verificaQuantidadeProduto(int idProduto, int quantidade) throws Exception;
}
