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
import blockChain.miner.MiningListener;

/**
 * Created on 16/11/2021, 18:01:08
 *
 * @author IPT - computer
 */
public interface ListenerRemoteMiner extends MiningListener {

    public void onStart(ObjectRemoteMiner rm);

    public void onAddNode(InterfaceRemoteMiner rm);
    
    public void onException(String title, Exception ex);
    public void onMessage(String title, String message);
    
    public void onSynchronizeChain(String title, String message);
    public void onAddNewBlock(String title, Block b);
    
}
