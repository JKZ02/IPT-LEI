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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import static blockChain.miner.Miner.hashAlgorithm;

/**
 * Created on 02/11/2021, 18:28:06
 *
 * @author IPT - computer
 */
public class MinerThread extends Thread {

    //atributos da thread
    MiningListener listener;                  // Listener da thread
    private final AtomicInteger sharedNonce;  // referência para o global nonce
    private final AtomicInteger sharedTicket; // distribuidor de tickets
    private final String message;             // mensagem do bloco
    private final int zeros;                  // número de zeros
    private final MessageDigest hasher;       // calculador de  hashs da thread

    /**
     * Thread para minar uma mensagem
     *
     * @param globalNonce objeto partilhado com o nonce global
     * @param ticket objeto partilhado do sistema de tickets
     * @param message mensagem a minar
     * @param zeros número de zeros do hash
     * @param listener listenar do mineiro
     * @throws NoSuchAlgorithmException
     */
    public MinerThread(AtomicInteger globalNonce, AtomicInteger ticket, String message, int zeros, MiningListener listener) throws NoSuchAlgorithmException {
        this.sharedNonce = globalNonce;
        this.sharedTicket = ticket;
        this.message = message;
        this.zeros = zeros;
        this.listener = listener;
        //criar um objeto para a thread calcular hashs
        this.hasher = MessageDigest.getInstance(hashAlgorithm);
    }

    @Override
    public void run() {
        try {
            //notificar o listener
            if (listener != null) {
                listener.onStartMining(Thread.currentThread().getName()+ " Start mining ", zeros);
            }
            //Zeros no inicio do hash
            String prefix = String.format("%0" + zeros + "d", 0);
            //enquanto não for encontrado o nonce ( nonce <= 0 )
            while (sharedNonce.get() <= 0) {
                //tirar um ticket e testá-lo
                int number = sharedTicket.getAndIncrement();
                //verificar se o hash esta correto
                if (getThreadHash(message, number).startsWith(prefix)) {
                    //atualizar o nonce e terminar as threads
                    sharedNonce.set(number);
                    //notifificar os listeners
                    if (listener != null) {
                        listener.onNounceFound(number);
                    }
                }
                //notificar os listeners a cada 9973 numeros
                if (listener != null && number % 9973 == 0) {
                    listener.onMining(number);
                }
            }
            //notificar os listeners que a thread terminou
            if (listener != null) {
                //nome da thread e o nonce
                listener.onStopMining(sharedNonce.get());
            }
        } catch (Exception ex) {
            //alguma coisa deu errado   
            Logger.getLogger(MinerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * calcula a hash da mensagem com o nonce em Base64
     *
     * @param message mensagem
     * @param nonce nonce
     * @return hash(mensagem + nonce)
     * @throws Exception
     */
    public String getThreadHash(String message, int nonce) throws Exception {
        return Base64.getEncoder().encodeToString(hasher.digest((message + nonce).getBytes()));
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202111021828L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2021  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
