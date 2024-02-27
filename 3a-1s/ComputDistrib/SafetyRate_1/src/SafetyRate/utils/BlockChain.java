
package SafetyRate.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BlockChain implements Serializable {
    
    
    public static int nonce;
    CopyOnWriteArrayList<Block> chain = new CopyOnWriteArrayList<>();

    /**
     * Retorna o último bloco de uma dada blockchain
     * @return 
     */
    public String getLastBlockHash() {
        if (chain.isEmpty()) {
            return String.format("%08d", 0);
        }
        return chain.get(chain.size() - 1).currentHash;
    }
    
    /**
     * Retorna o array Data de um bloco dado o url contido nele
     * @param txt
     * @return 
     */
    public String[] search(String txt) {
        String[] vazio = new String[1];
        vazio[0] = "";
        for(Block b : chain) {
            if(b.data[0].contains(txt)) {
                System.out.println("encontrado");
                return b.data;
            }
        }
        return vazio;
    }
   
    /**
     * Retorna a média de avaliações de um website, dado o seu url
     * @param txt - url
     * @return 
     */
    public double getAval(String txt) {
        int naval = 0;
        double media = 0;
        for(Block b : chain) {
            if(b.data[0].contains(txt)) {
                naval++;
                media += Double.parseDouble(b.data[1]);
                System.out.println(b.data[1]);
                System.out.println(media);
            }
        }
        return media/naval;
    }
    
    /**
     * Retorna o total de avaliações de um website, dado o seu url
     * @param txt - url
     * @return 
     */
    public int getAvalTotal(String txt) {
        int total = 0;
        for(Block b : chain) {
            if(b.data[0].contains(txt)) {
                total++;
            }
        }
        return total;
    }
    
    /**
     * Utilizado em debug para mostrar todos os urls avaliados
     */
    public void printAll() {
        for(Block b : chain) {
            System.out.println(b.data[0]);
        }
    }
    
    /**
     * Retorna um array com todos os Urls pesquisados até ao momento
     * @return 
     */
    public CopyOnWriteArrayList<Block> getAll() {
        CopyOnWriteArrayList<Block> urls = new CopyOnWriteArrayList<>();
        for(int i = 0; i <= chain.size() - 1; i++ ) {
            urls.add(chain.get(i));
            System.out.println("Adicionado: " + chain.get(i).data[0]);
        }
        return urls;
    }
    
    
    /**
     * 
     * @param data - url do website
     * @param aval - avaliação dada pelo utilizador
     */
    public void add(String data, String aval, String user) {
        //hash of previous block
        String[] dataArray = new String[3]; 
        dataArray[0] = data;
        dataArray[1] = aval;
        dataArray[2] = user;
        //dataArray[2] = naval;
        String prevHash = getLastBlockHash();
        //mining block
        int cores = Runtime.getRuntime().availableProcessors();
        
        
        ExecutorService exe = Executors.newFixedThreadPool(cores-2);
        
        for(int i = 0; i<cores-2; i++) {
            exe.execute(new Miner(dataArray));
        }
        exe.shutdown();
        try {
            exe.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(BlockChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //build new block
        System.out.println("Nonce adicionado: " + nonce);
        Block newBlock = new Block(prevHash, dataArray, nonce);
        chain.add(newBlock);
    }

    /**
     * retorna um bloco da blockchain dada a sua posição
     * @param index - posição do bloco
     * @return 
     */
    public Block get(int index) {
        return chain.get(index);
    }

    /**
     * representação de um bloco em texto
     * @return 
     */
    public String toString() {
        StringBuilder txt = new StringBuilder();
        txt.append("Blochain size = " + chain.size() + "\n");
        for (Block block : chain) {
            txt.append(block.toString() + "\n");
        }
        return txt.toString();
    }

    /**
     * Guarda a blockchain num ficheiro dado o nome do ficheiro
     * @param fileName - nome do ficheiro
     * @throws Exception 
     */
    public void save(String fileName) throws Exception {
        try ( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(chain);
        }
    }
    
    /**
     * Carrega a blockchain a partir de um ficheiro dado o nome do ficheiro
     * @param fileName - nome do ficheiro
     * @throws Exception 
     */
    public void load(String fileName) throws Exception {
        try ( ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            this.chain = (CopyOnWriteArrayList<Block>) in.readObject();
        }
    }

    /**
     * verifica se a blockchain é válida verificando todos os seus blocos
     * @return 
     */
    public boolean isValid() {
        //Validate blocks
        for (Block block : chain) {
            if (!block.isValid()) {
                return false;
            }
        }
        //validate Links
        for (int i = 1; i < chain.size(); i++) {
            //previous hash !=  hash of previous
            if (chain.get(i).previousHash != chain.get(i - 1).currentHash) {
                return false;
            }
        }
        return true;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202208221009L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2022  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
