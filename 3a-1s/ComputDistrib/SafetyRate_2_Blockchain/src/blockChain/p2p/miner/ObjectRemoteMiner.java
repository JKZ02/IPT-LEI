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
//::                                                               (c)2021   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package blockChain.p2p.miner;

import blockChain.chain.Block;
import blockChain.chain.BlockChain;
import blockChain.miner.Miner;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ObjectRemoteMiner extends UnicastRemoteObject implements InterfaceRemoteMiner {

    private final String BLOCHCHAIN_FILE = "blockchain.bck";

    private ListenerRemoteMiner listener; // listener
    private Miner miner; //objeto para minar
    private List<InterfaceRemoteMiner> network; // network of miners
    private String address; // nome do servidor

    private BlockChain chain; // blockchain
    private Block incompleteBlock; //

    /**
     * constructor
     *
     * @param port number of the port
     * @param listener
     * @throws RemoteException
     */
    public ObjectRemoteMiner(int port, ListenerRemoteMiner listener) throws RemoteException {
        //passar a porta para superclasse
        super(port);
        try {
            this.listener = listener;
            //criar um mineiro
            miner = new Miner(listener);
            //criar a lista da rede
            network = new CopyOnWriteArrayList<>();
            //atualizar o endereço do objeto remoto
            address = "//" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/" + InterfaceRemoteMiner.NAME;
            // blochain
            chain = new BlockChain();
            try {
                chain.load(BLOCHCHAIN_FILE);
            } catch (Exception e) {
            }

            if (listener != null) {
                listener.onSynchronizeChain("Blockchain", chain.getChain().size() + " elements");
            }

        } catch (UnknownHostException e) {
            address = "unknow" + ":" + port;
        }
        //:::::::::: Notificar o listener ::::::::::::::
        if (listener != null) {
            listener.onStart(this);
        }
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
    //:::::                                                         :::::::::::::
    //:::::               E N C A P S U L A M E N T O 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the nonce of miner
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public int getNonce() throws RemoteException {
        return miner.getNonce();
    }

    /**
     * gets the nonce of miner
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getMessage() throws RemoteException {
        return miner.getMessage();
    }

    /**
     * verify if miner is mining
     *
     * @return is mining
     * @throws RemoteException
     */
    @Override
    public boolean isMining() throws RemoteException {
        return miner.isMining();
    }

    @Override
    public String getHash() throws RemoteException {
        return miner.getHash();
    }

    @Override
    public int getTicketNumber() throws RemoteException {
        return miner.getTicket();
    }

    @Override
    public String getAdress() {
        return address;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::                                                         :::::::::::::
    //:::::                R E D E   M I N E I R A 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @Override
    public void addNode(InterfaceRemoteMiner ir) throws RemoteException {
        //remover os nós que não respondem  
        for (InterfaceRemoteMiner remote : network) {
            try {
                remote.getAdress();
            } catch (Exception ex) {
                //something is wrong
                network.remove(remote);
                if (listener != null) {
                    listener.onException("Remove Node Network", ex);
                }
            }
        }

        //se a rede não tiver o no
        if (!network.contains(ir)) {
            //adicionar o mineiro
            network.add(ir);
            //espalhar o mineiro pela rede
            //para cada no remoto
            for (InterfaceRemoteMiner remote : network) {

                //adicionar o novo no ao remoto
                remote.addNode(ir); // pedir para adiconar o novo nó
                //adicionar o this ao remoto
                remote.addNode(this); // pedir para adcionar o proprio no
                if (listener != null) {
                    listener.onMessage("Add Node to Remote", remote.getAdress());
                }

            }
            //:::::::::: Notificar o listener ::::::::::::::
            if (listener != null) {
                listener.onAddNode(ir);
            }
        }
    }
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * compara se dois objetos remotos são igauis Necessário para verificar se o
     * nó está numa coleção
     *
     * @param remote
     * @return
     */
    @Override
    public boolean equals(Object remote) {
        try {
            //comparar o endereço dos objetos remotos
            return ((InterfaceRemoteMiner) remote).getAdress().equals(getAdress());
        } catch (RemoteException ex) {
            return false;
        }
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * retorna a lista dos nos
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public List<InterfaceRemoteMiner> getNetwork() throws RemoteException {
        return network;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
    //:::::                                                         :::::::::::::
    //:::::                M I N A R       B L O C O S 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    
    /**
     * calculate the nonce of the mssage
     *
     * @param message messagem
     * @param zeros number of zeros
     * @return nonce
     * @throws RemoteException
     */
    @Override
    public int mine(String message, int zeros) throws RemoteException {
        //executar a mineração com o mineiro
        try {
            startMining(message, zeros);
            return miner.waitToNonce();
        } catch (Exception ex) {
            throw new RemoteException("mining", ex);
        }
    }

    /**
     * Start mining the message
     *
     * @param message message
     * @param zeros number of zeros
     * @throws RemoteException
     */
    @Override
    public void startMining(String message, int zeros) throws RemoteException {
        try {
            //se já estiver a minar - Não faz nada
            if (miner.isMining()) {
                return;
            }

            //fazer um novo bloco
            incompleteBlock = new Block(chain.getLastBlockHash(),
                    message, zeros);
            //colocar o mineiro a minar
            miner.startMining(incompleteBlock.getHeader(), zeros);

            //Por todos os mineiros da rede a minar
            for (InterfaceRemoteMiner remote : network) {
                remote.startMining(message, zeros);
            }
            //:::::::::: Notificar o listener ::::::::::::::
            listener.onStartMining(message, zeros);

        } catch (Exception ex) {
            throw new RemoteException("Start mining", ex);
        }
    }

    /**
     * Stop mining the message
     *
     * @param nonce nonce of message
     * @throws RemoteException
     */
    @Override
    public void stopMining(int nonce) throws RemoteException {
        //se o mineiro estiver a minar
        if (miner.isMining()) {
            try {
                //parar o mineiro
                miner.stopMining(nonce);
                //:::::::::: Notificar o listener ::::::::::::::
                listener.onStopMining(nonce);
                listener.onMessage("Stop My Miner", getAdress());
                incompleteBlock.setNonce(nonce);
                addBlock(incompleteBlock);
            } catch (Exception ex) {
                throw new RemoteException(ex.getMessage());
            }

        }
        //parar todos os mineiros da rede rede
        for (InterfaceRemoteMiner remote : network) {
            //se o mineiro remoro estiver a minar
            if (remote.isMining()) {
                //parar o mineiro remoto
                remote.stopMining(nonce);
                listener.onStopMining(remote.getNonce());
                listener.onMessage("Stop Remote Mining", remote.getAdress());
            }
        }

    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::                                                         :::::::::::::
    //:::::                BLOCKCHAIN 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @Override
    public void addBlock(Block b) throws RemoteException {
        try {
            if (!chain.getChain().contains(b)) {
                //verificasr se o bloco encaixa
                // senão sincronizar a chain
                chain.add(b);
                if (listener != null) {
                    listener.onAddNewBlock("Add New Block ", b);
                }
                //guardar a chain
                chain.save(BLOCHCHAIN_FILE);
                
                //espalhar o bloco pela rede
                for (InterfaceRemoteMiner node : network) {
                    node.addBlock(b);
                }
                
                
            }

        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public void SynChain(InterfaceRemoteMiner node) throws RemoteException {
        if (node.getChainSize() > this.getChainSize()) {
            try {
                this.chain = node.getBlockChain();

                //espalhar o bloco pela rede
                for (InterfaceRemoteMiner remote : network) {
                    remote.SynChain(node);
                }
                if (listener != null) {
                    listener.onSynchronizeChain("Sincronoze with", node.getAdress());
                }
                //guardar a chain
                chain.save(BLOCHCHAIN_FILE);
            } catch (Exception ex) {
                throw new RemoteException(ex.getMessage());
            }
        }
    }

    @Override
    public int getChainSize() throws RemoteException {
        return chain.getChain().size();
    }

    @Override
    public BlockChain getBlockChain() throws RemoteException {
        return chain;
    }
    
    
    public void addNewNode(int nonce){
        try {
            incompleteBlock.setNonce(nonce);
            addBlock(incompleteBlock);
        } catch (Exception ex) {
            Logger.getLogger(ObjectRemoteMiner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
