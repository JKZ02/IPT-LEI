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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 22/08/2022, 10:09:17
 *
 * @author IPT - computer
 * @version 1.0
 */
public class BlockChain implements Serializable {

    ArrayList<Block> chain = new ArrayList<>();

    public ArrayList<Block> getChain() {
        return chain;
    }

    public BlockChain() {
        try {
            chain = new ArrayList<>();
            //add(Block.createGenesys());
        } catch (Exception ex) {
        }
    }

    /**
     * gets the last block hash of the chain
     *
     * @return last hash in the chain
     */
    public String getLastBlockHash() {
        //Genesis block
        if (chain.isEmpty()) {
            return String.format("%08d", 0);
        }
        //hash of the last in the list
        return chain.get(chain.size() - 1).currentHash;
    }

    /**
     * adds block to the blockChain
     *
     * @param b new block
     */
    public void add(Block b) throws Exception {
        //verify the linktotheprevious
        if (chain.size() > 1 && !getLastBlockHash().equals(b.previousHash)) {
            throw new Exception("Previous link not match");
        }
        //verify block hash
        if (!b.isValid()) {
            throw new Exception("Block not valid");
        }
        chain.add(b);
    }

    /**
     * adds data to the blockChain
     *
     * @param data data to add in the block
     * @param dificulty dificulty of block to miners (POW)
     */
    public void add(String data, int dificulty) throws Exception {
        //hash of previous block
        String prevHash = getLastBlockHash();
        //mining block
        int nonce = new Miner(null).mine(prevHash + data + dificulty, dificulty);
        //build new block
        Block newBlock = new Block(prevHash, data, dificulty);
        newBlock.setNonce(nonce);
        //add new block to the chain
        chain.add(newBlock);
    }

    public Block get(int index) {
        return chain.get(index);
    }

    public String toString() {
        StringBuilder txt = new StringBuilder();
        txt.append("Blochain size = " + chain.size() + "\n");
        for (Block block : chain) {
            txt.append(block.toString() + "\n");
        }
        return txt.toString();
    }

    public void save(String fileName) throws Exception {
        try ( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(chain);
        }
    }

    public void load(String fileName) throws Exception {
        try ( ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            this.chain = (ArrayList<Block>) in.readObject();
        }
    }

    public boolean isValid() throws Exception {
        //Validate blocks
        for (Block block : chain) {
            if (!block.isValid()) {
                return false;
            }
        }
        //validate Links
        //starts in the second block
        for (int i = 1; i < chain.size(); i++) {
            //previous hash !=  hash of previous
            if (chain.get(i).previousHash != chain.get(i - 1).currentHash) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for(Block b : chain) {
            urls.add(b.getUrl());
        }
        
        return urls;
    }
    
    public double getAverageUrl(String url) {
        String formatUrl = url.replaceAll("http://","");
        formatUrl = formatUrl.replaceAll("https://", "");
        formatUrl = formatUrl.replaceAll("www.", "");
        double average= 0.0;
        int n = 0;
        for(Block b : chain) {
            if (b.getUrl().contains(formatUrl)) {
                average += b.getAval();
            }
        }
        
        return average/n;
    }
    
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202208221009L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2022  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
