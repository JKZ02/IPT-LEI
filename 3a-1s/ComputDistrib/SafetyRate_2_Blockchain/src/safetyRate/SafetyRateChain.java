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
package safetyRate;

import blockChain.chain.Block;
import blockChain.chain.BlockChain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author manso
 */
public class SafetyRateChain implements Serializable {
    //data structure to suport  chain
    //private final ArrayList<Transaction> chain;

    private BlockChain chain;

    public BlockChain getBlockChain() {
        return chain;
    }

    public void setBlockChain(BlockChain b) {

        chain = b;
    }

    public SafetyRateChain() throws Exception {
        chain = new BlockChain();

    }

    
    public void save(String fileName) throws IOException {
        try ( ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    /**
     * load chain from the file
     *
     * @param fileName name of the file
     * @return SafetyRateChain
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static SafetyRateChain load(String fileName) throws IOException, ClassNotFoundException {
        try ( ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {
            return (SafetyRateChain) in.readObject();
        }
    }

    public double getAverageUrl(String url) {
        ArrayList<Block> chains = chain.getChain();
        String formatUrl = url.replaceAll("http://","");
        formatUrl = formatUrl.replaceAll("https://", "");
        formatUrl = formatUrl.replaceAll("www.", "");
        double average= 0.0;
        int n = 0;
        for(Block b : chains) {
            if (b.getUrl().contains(formatUrl)) {
                average += b.getAval();
                n++;
            }
        }
        
        return average/n;
    }
    
    public static int DIFICULTY = 3;

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202211011644L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2022  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
