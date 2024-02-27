//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2022   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package blockChain.chain;

import blockChain.miner.Miner;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import myUtils.MerkleTree;
import myUtils.Serializer;

/**
 * Created on 22/08/2022, 09:23:49
 *
 * Block with consensus of Proof of Work
 *
 * @author IPT - computer
 * @version 1.0
 */
public class Block implements Serializable {

    String previousHash; // link to previous block
    String merkleRoot;         // data in the block
    
    
    String merkleTree; // full merkle tree with elements
    
    int zeros;           //number of zeros  
    int nonce;           // proof of work 
    String currentHash;  // Hash of block
    /**
     * construtor por defeito
     * @param previousHash hash do bloco anterior
     * @param data
     * @param zeros
     * @throws Exception 
     */
    public Block(String previousHash, String data, int zeros) throws Exception {
        this.previousHash = previousHash;
        this.merkleRoot = data;
        this.zeros = zeros;
    }
    /**
     * 
     * @param previousHash hash do bloco anterior
     * @param zeros zeros a minerar
     * @param elements data para a merkletree
     * @throws Exception 
     */
     public Block(String previousHash, int zeros, String ...elements) throws Exception {
        this.previousHash = previousHash;
        this.zeros = zeros;
        
         MerkleTree<String> mk = new MerkleTree<>(elements);
         this.merkleRoot = Base64.getEncoder().encodeToString(mk.getRoot());
         this.merkleTree = Base64.getEncoder().encodeToString(
         Serializer.objectToByteArray(mk)
         );
    }
     
    /**
     * calcula a hash de um nonce
     * @param newNonce nonce
     * @throws Exception 
     */
    public void setNonce(int newNonce) throws Exception {
        String hash = calculateHash(newNonce);
        String zzz = String.format("%0" + zeros + "d", 0);
        //hash is valid
        if ( !hash.startsWith(zzz)) {
            throw new Exception("Wrong nonce");
        }
        this.currentHash = hash;
        this.nonce = newNonce;
    }
    /**
     * retorna o header
     * @return 
     */
    public String getHeader() {
        return previousHash + merkleRoot + zeros;
    }
    /**
     * calcula e retorna o hash
     * @return
     * @throws Exception 
     */
    public String calculateHash() throws Exception {
        return calculateHash(nonce);
    }
    /**
     * calcula e retorna o hash de um dado nonce
     * @param nonce
     * @return
     * @throws Exception 
     */
    public String calculateHash(int nonce) throws Exception {
        return Miner.getHash(getHeader(), nonce);
    }

    @Override
    public String toString() {
        return // (isValid() ? "OK\t" : "ERROR\t")+
                String.format("[ %8s", previousHash) + " <- "
                + String.format("%-10s", merkleRoot) + String.format(" %7d ] = ", nonce)
                + " " + zeros + ""
                + String.format("%8s", currentHash);

    }
    /**
     * retorna true caso o bloco seja válido e false caso não seja
     * @return
     * @throws Exception 
     */
    public boolean isValid() throws Exception {
        try {
            String hash = calculateHash();
            String zzz = String.format("%0" + zeros + "d", 0);
            return currentHash.equals(hash) && hash.startsWith(zzz);
        } catch (Exception e) {
            return false;
        }

    }
    /**
     * retorna a hash do bloco anterior
     * @return 
     */
    public String getPreviousHash() {
        return previousHash;
    }
    /**
     * retorna a data do bloco
     * @return 
     */
    public String getData() {
        return merkleRoot;
    }
    /**
     * retorna o nonce do bloco
     * @return 
     */
    public int getNonce() {
        return nonce;
    }
    /**
     * retorna a hash do bloco
     * @return 
     */
    public String getCurrentHash() {
        return currentHash;
    }
    /**
     * retorna os zeros do bloco
     * @return 
     */
    public int getZeros() {
        return zeros;
    }

    public String getFullInfo() {
        return "Previous :\n\t " + previousHash
                + "\ndata  :\n\t" + merkleRoot
                + "\nNonce : " + nonce
                + "\nZeros : " + zeros
                + "\nHash  :\n\t" + currentHash;
    }
    
    public String getUrl() {
        return merkleRoot.split(";")[0];
    }
    
    public double getAval() {
        System.out.println(merkleRoot.split(";")[1]);
        return Double.parseDouble(merkleRoot.split(";")[1]);
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        return Objects.equals(this.previousHash, other.previousHash);
    }
    
    
    

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202208220923L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2022  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
