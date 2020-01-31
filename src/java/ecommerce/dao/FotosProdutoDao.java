package ecommerce.dao;

import ecommerce.entidade.FotosProduto;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public interface FotosProdutoDao extends BaseDao {

    /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @param idProduto - variavel do tipo int
     * @return - retorna uma List do tipo FotosProduto
     * @throws Exception - caso ocorra alguma falha para pesquisa imgem produto
     */
    List<FotosProduto> pesquisaImgProduto(int idProduto) throws Exception;

    /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @param idProduto - variavel do tipo int
     * @param idImg  - variavel do tipo int
     * @throws Exception - caso ocorra alguma falha para ativar imagem principal
     */
    void ativarImgPrincipal(int idProduto, int idImg) throws Exception;

    /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @param idProduto - variavel do tipo int
     * @return - retorna uma List do tipo FotosProduto
     * @throws Exception - caso ocorra alguma falha para busca imagem principal
     */
    List<FotosProduto> buscaImgPrincipal(int idProduto) throws Exception;
}
