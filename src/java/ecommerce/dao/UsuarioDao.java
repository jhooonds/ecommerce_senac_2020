package ecommerce.dao;

import ecommerce.entidade.Pessoa;
import ecommerce.entidade.Usuario;

/**
 *
 * @author Jonatan
 */
public interface UsuarioDao extends BaseDao {

    /**
     * Metodo responsavel por salvar usuário
     *
     * @param u - variavel objeto do tipo Usuario
     * @return - retorna um objeto do tipo Usuario
     * @throws Exception - caso ocorra alguma falha para salvar usuário
     */
    Usuario salvar(Usuario u) throws Exception;

    /**
     * Metodo responsavel por autenticar usuário
     *
     * @param u - variavel objeto do tipo Usuario
     * @return retorna um objeto do tipo Pessoa
     * @throws Exception - caso ocorra alguma falha para autenticar usuário
     */
    Pessoa autenticar(Usuario u) throws Exception;

    /**
     * Metodo responsavel por salvar usuário
     *
     * @param usuario - variavel objeto do tipo Usuario
     * @return - retorna um boleano
     * @throws Exception - caso ocorra alguma falha para salvar usuário
     */
    boolean alteraDadosUsuario(Usuario usuario) throws Exception;
}
