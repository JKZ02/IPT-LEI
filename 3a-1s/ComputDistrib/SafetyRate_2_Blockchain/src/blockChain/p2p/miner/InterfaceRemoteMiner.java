/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockChain.p2p.miner;

import blockChain.chain.Block;
import blockChain.chain.BlockChain;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceRemoteMiner extends Remote {

    public static final String NAME = "miner";

    /**
     * gets the adress of the remote object
     *
     * @return adress of the remote object
     * @throws java.rmi.RemoteException
     */
    public String getAdress() throws RemoteException;
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::                                                         :::::::::::::
    //:::::                R E D E   M I N E I R A 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * adds a node to the network
     *
     * @param node network node
     * @return true if the node is added
     * @throws RemoteException
     */
    public void addNode(InterfaceRemoteMiner node) throws RemoteException;

    /**
     * gets the network of the node
     *
     * @return list of network miners
     * @throws RemoteException
     */
    public List<InterfaceRemoteMiner> getNetwork() throws RemoteException;

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
    //:::::                                                         :::::::::::::
    //:::::                M I N A R       B L O C O S 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * calculate the nonce of the mssage
     *
     * @param message messagem
     * @param zeros number of zeros
     * @return nonce
     * @throws RemoteException
     */
    public int mine(String message, int zeros) throws RemoteException;

    /**
     * Start mining the message
     *
     * @param message message
     * @param zeros number of zeros
     * @throws RemoteException
     */
    public void startMining(String message, int zeros) throws RemoteException;

    /**
     * Stop mining the message
     *
     * @param nonce nonce of message
     * @throws RemoteException
     */
    public void stopMining(int nonce) throws RemoteException;

    /**
     * verify if miner is mining
     *
     * @return is mining
     * @throws RemoteException
     */
    public boolean isMining() throws RemoteException;

    /**
     * gets the nonce of miner
     *
     * @return
     * @throws RemoteException
     */
    public int getNonce() throws RemoteException;
    
     /**
     * gets the message of miner
     *
     * @return messag
     * @throws RemoteException
     */
    public String getMessage() throws RemoteException;

    /**
     * gets the number to be tested
     *
     * @return
     * @throws RemoteException
     */
    public int getTicketNumber() throws RemoteException;

    /**
     * gets the adress of the remote object
     *
     * @return adress of the remote object
     * @throws java.rmi.RemoteException
     */
    public String getHash() throws RemoteException;
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::                                                         :::::::::::::
    //:::::                BLOCKCHAIN 
    //:::::                                                         :::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  
    public void addBlock(Block b) throws RemoteException;
    
    public void SynChain(InterfaceRemoteMiner node) throws RemoteException;
    
    public int getChainSize() throws RemoteException;
    
    public BlockChain getBlockChain()throws RemoteException;
    
    //public List<Block> getBlocchain(int start )throws RemoteException;
    
}
