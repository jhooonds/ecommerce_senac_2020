/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jonatan
 */
public class MD5 {
    
    /**
     * Gera a criptografia das senhas dos usuarios e urls
     * @param senhaGerada
     * @return uma String
     */
     public static String criptografia(String senhaGerada) {

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Erro na criptografia MD5 :" + ex.getMessage());
        }
        m.update(senhaGerada.getBytes(), 0, senhaGerada.length());
        senhaGerada = new BigInteger(1, m.digest()).toString(16);
        return senhaGerada;
    }
}
