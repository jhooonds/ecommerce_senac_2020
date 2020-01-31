package ecommerce.dao;

/**
 *
 * @author Jonatan
 */
public interface BaseDao {

    /*
    * Metodos resposavel por salvar os objetos no bano de dados
    */
    boolean salvar(Object obj) throws Exception;

    /*
     * Medoto responsavel por alterar os objetos no banco de dados
     */
    boolean alterar(Object obj) throws Exception;

    /*
     Metodo resposavel por pesquisar um objeto pelo id
     */
    Object pesquisar(int id) throws Exception;

    /*
     * Metodo resposavel por excluir o objeto pelo id
     */
    boolean excluir(int id) throws Exception;
}
