
package SafetyRate.utils;

public class Hash {

    /**
     * transforma um valor inteiro em uma string
     * @param n - valor inteiro
     * @return 
     */
    public static String toHexString(int n) {
        return Integer.toHexString(n).toUpperCase();
    }

    /**
     * transforma uma dada string na sua hash
     * @param data - string para transformar em hash
     * @return 
     */
    public static String getHash(String data) {
        return toHexString(data.hashCode());
    }

    private static final long serialVersionUID = 202209281102L;

}
