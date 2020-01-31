
package ecommerce.util;
/**
 *
 * @author Jonatan
 */
public class Protocolo {
     
  
    public static String getNumeroProtocolo() {
        return String.valueOf(System.currentTimeMillis());
    }

}
