package ecommerce.dao;

import ecommerce.entidade.CategoriaProduto;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public interface CategoriaProdutoDao extends BaseDao {

    /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @return - retorna uma List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para listar
     */
    List<CategoriaProduto> listar() throws Exception;

    /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @return - retorna uma List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para litar
     */
    List<CategoriaProduto> listarEdicao() throws Exception;
}
