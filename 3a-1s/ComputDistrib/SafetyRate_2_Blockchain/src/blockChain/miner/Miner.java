//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2021   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package blockChain.miner;

import blockChain.chain.Block;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 02/11/2021, 18:28:06 Updated on 07/12/2021
 *
 * @author IPT - computer
 */
public class Miner {

    //atributos 
    MiningListener listener;            // Listener dos mineiros
    private MinerThread[] threads;      // Threads de calculo de hashs
    private ExecutorService exe;        // Executor das Tbhreads

    private String message;             //  Mensagem a ser minada 
    private AtomicInteger globalNonce;  // Nonce que valida a mensagem
    private AtomicInteger ticket;       // Ticket para as threada 

    public Miner(MiningListener listener) {
        this.listener = listener;
    }

    /**
     * inicia a mineração de uma mensagem
     *
     * @param message mensagem
     * @param zeros número de zeros do hash
     * @throws Exception
     */
    public void startMining(String message, int zeros) throws Exception {
        //está a minar
        if (isMining()) {
            return; // Sair
        }
        //notificar o listener
        if (listener != null) {
            listener.onStartMining(message, zeros);
        }
        this.message = message;
        //inicializar o ticket com um numero aleatórios positivo
        Random rnd = new Random();
        ticket = new AtomicInteger(Math.abs(rnd.nextInt() / 2));
        //configurar os atributos    
        int numCores = 4;//Runtime.getRuntime().availableProcessors();
        threads = new MinerThread[numCores];
        exe = Executors.newFixedThreadPool(numCores);
        //inicializar o globalNonce
        globalNonce = new AtomicInteger();

        //executar as threads
        for (int i = 0; i < numCores; i++) {
            threads[i] = new MinerThread(globalNonce, ticket, message, zeros, listener);
            exe.execute(threads[i]);
        }
        //fechar a pool
        exe.shutdown();

    }

    /**
     * devolve o hash da mensagem + nonce
     *
     * @return hash(mensagem + nonce)
     */
    public String getHash() {
        try {
            getHash(message, globalNonce.get());
        } catch (Exception ex) {
        }
        return "ERROR";
    }

    /**
     * Terminar a mineração
     *
     * @param nonce numero maior que zero
     */
    public void stopMining(int nonce) {
        globalNonce.set(nonce);
    }

    /**
     * Verificar se está a minerar
     *
     * @return está a minerar
     */
    public boolean isMining() {
        return globalNonce != null && globalNonce.get() <= 0;
    }

    /**
     * Devolve o resultado da mineração ou zero
     *
     * @return nonce
     */
    public int getNonce() {
        return globalNonce.get();
    }

    /**
     * mensagem
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Devolve o ticket que está a ser testado
     *
     * @return nonce
     */
    public int getTicket() {
        return ticket.get();
    }

    /**
     * Devolve o resultado da mineração ou zero
     *
     * @return nonce
     * @throws java.lang.InterruptedException
     */
    public int waitToNonce() throws InterruptedException {
        exe.awaitTermination(1, TimeUnit.DAYS);
        return globalNonce.get();
    }

    /**
     * Calcula o valor do nonce da mensagem
     *
     * @param message mensagem
     * @param zeros número de zeros
     * @return
     * @throws Exception
     */
    public int mine(String message, int zeros) throws Exception {
        startMining(message, zeros);
        exe.awaitTermination(1, TimeUnit.DAYS);
        return globalNonce.get();
    }

    /**
     * valida um bloco
     *
     * @param b block
     * @param message mensagem
     * @return
     * @throws Exception
     */
    public Block mine(Block b) throws Exception {
        startMining(b.getHeader(), b.getZeros());
        exe.awaitTermination(1, TimeUnit.DAYS);
        b.setNonce(globalNonce.get());
        return b;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::      I N T E G R I T Y         :::::::::::::::::::::::::::::::::    
    ///////////////////////////////////////////////////////////////////////////
    public static String hashAlgorithm = "SHA3-256";

    /**
     * calcula a hash da mensagem com o nonce em Base64
     *
     * @param data dados
     * @param nonce nonce
     * @return hash(mensagem + nonce)
     */
    public static String getHash(String data, int nonce) {
        try {
            return getHash(data + nonce);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * calcula a hash da mensagem em Base64
     *
     * @param data mensagem
     * @return Base64(hash(data))
     * @throws Exception
     */
    public static String getHash(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        md.update(data.getBytes());
        return Base64.getEncoder().encodeToString(md.digest());
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202111021828L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2021  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
