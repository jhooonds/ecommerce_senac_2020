package ecommerce.dao;

import ecommerce.entidade.Marca;
import java.util.List;

/**
 *
 * @author Jonatan
 */
public interface MarcaDao extends BaseDao {

     /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @return - retorna uma List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para listar
     */
    List<Marca> listar() throws Exception;

     /**
     * Método abstrato implemtado automaticamente de BaseDao
     *
     * @return - retorna uma List do tipo Marca
     * @throws Exception - caso ocorra alguma falha para litar
     */
    List<Marca> listarEdicao() throws Exception;

}
