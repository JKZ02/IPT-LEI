
package SafetyRate.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class Miner extends Thread {

    public static int MAX = (int)1E9;

    
    private String[] data;
    
    static AtomicInteger nonce = new AtomicInteger();
    private int found = 0;
    
    /**
     * construtor dos mineradores
     * @param data 
     */
    public Miner(String[] data) {
        this.data = data;
    }
    
    
    
    @Override
    /**
     * Código executado em todas as threads mineradoras
     * calculam paralelamente o nonce de um dado bloco
     */
    public void run() {
        nonce.set(0);
        String zeros = String.format("%0" + 6 + "d", 0);
        while (nonce.get() < MAX || found != 1) {

            String hash;
            hash = Hash.getHash(nonce.get() + data[0] + data[1] + data[2]);

            //System.out.println(nonce.get() + " " + hash + " thread: " + Thread.currentThread().getName());

            if (hash.endsWith(zeros)) {
                found = 1;
                BlockChain.nonce = nonce.get();
                break;
            }

            nonce.incrementAndGet();
        }

    }

    /**
     * retorna o nonce calculado do último bloco adicionado á blockchain
     * @return 
     */
    public int getNonce() {
        return nonce.get();
    }

    private static final long serialVersionUID = 202209281113L;

}
