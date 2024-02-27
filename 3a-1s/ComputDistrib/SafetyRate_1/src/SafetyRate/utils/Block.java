
package SafetyRate.utils;

import java.io.Serializable;

public class Block implements Serializable {
    //link to previous block
    String previousHash; 
    // data in the block
    String[] data = new String[3];    
    // proof of work 
    int nonce;       
    // Hash of block    
    String currentHash;  

    /**
     * Construtor de blocos
     * @param previousHash - hash do bloco anterior
     * @param data - data que será contida no bloco
     * @param nonce - nonce do bloco
     */
    public Block(String previousHash, String[] data, int nonce) {
        this.previousHash = previousHash;
        this.data = data;
        this.nonce = nonce;
        this.currentHash = calculateHash();
    }

    /**
     * calcula a hash de um bloco
     * @return 
     */
    public String calculateHash() {
        return Hash.getHash(nonce + previousHash + data);
    }
    
    /**
     * retorna o array data de um bloco
     * @return 
     */
    public String[] getData() {
        return this.data;
    }

    /**
     * retorna o url de um bloco
     * @return 
     */
    public String toString() {
        return data[0];

    }

    /**
     * verifica se a hash do bloco é válida
     * @return 
     */
    public boolean isValid() {
        return currentHash.equals(calculateHash());
    }

    private static final long serialVersionUID = 202208220923L;


}
