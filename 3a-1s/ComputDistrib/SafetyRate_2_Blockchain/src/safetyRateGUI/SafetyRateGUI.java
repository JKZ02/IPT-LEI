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
package safetyRateGUI;

import blockChain.chain.Block;
import blockChain.p2p.miner.InterfaceRemoteMiner;
import java.awt.Toolkit;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import safetyRate.SafetyRateChain;
import safetyRate.User;

/**
 *
 * @author manso
 */
public class SafetyRateGUI extends javax.swing.JFrame {

    public static String storage = "chain.obj";
    public static Boolean isMining = false;
    SafetyRateChain chain;
    User myUser;

    public SafetyRateGUI(User user) {
        initComponents();
        this.myUser = user;
        try {
            chain = new SafetyRateChain();
            try {

                InterfaceRemoteMiner miner = user.getMiner();

                if (miner.getChainSize() <= 0) {
                    miner.mine("http://example.com/;0.0;System", 3);
                }

                chain.setBlockChain(user.getMiner().getBlockChain());
                new Thread(
                        () -> {
                            //codigo run da thread
                            while (true) {
                                try {
                                    if (miner.getChainSize() > chain.getBlockChain().getChain().size()) {
                                        chain.setBlockChain(miner.getBlockChain());
                                        chain.save(storage);
                                        SwingUtilities.invokeLater(() -> {

                                            try {
                                                tpBlockchainStateChanged(null);
                                                pnTransactionsUserStateChanged(null);
                                                //txtLeger.setText(chain.toString());
                                                chain.save(storage);
                                            } catch (IOException ex) {
                                                Logger.getLogger(SafetyRateGUI.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                                }
                                        );

                                    }
                                    Thread.sleep(1000);
                                } catch (Exception ex) {
                                }

                            }
                        }
                ).start();

            } catch (Exception e) {
            }
            
            displayUser();

            setSize(800, 800);
            setLocationRelativeTo(null);
        } catch (Exception e) {
        }
    }

    public void displayUser() {
        List<User> lst = User.getUserList();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(lst);
        //carrega as chaves do user para serem mostradas na tab user
        String pubB64 = Base64.getEncoder().encodeToString(
                myUser.getPubKey().getEncoded());
        txtInfo.setText(myUser.getInfo());
        txtPrivateKey.setText(Base64.getEncoder().encodeToString(
                myUser.getPrivKey().getEncoded()));
        txtPublicKey.setText(pubB64);
        txtSecretKey.setText(Base64.getEncoder().encodeToString(
                myUser.getKey().getEncoded()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnUserTransactions = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        lstUserTransactions = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtUserTransactions = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        txtBalance = new javax.swing.JLabel();
        tpBlockchain = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLeger = new javax.swing.JTextArea();
        pnBlockChain = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstBlockchain = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtBlock = new javax.swing.JTextArea();
        pnTransactionsUser = new javax.swing.JTabbedPane();
        pnTrans = new javax.swing.JPanel();
        pnTransaction = new javax.swing.JPanel();
        txtFrom = new javax.swing.JTextField();
        txtValue = new javax.swing.JTextField();
        btRegister = new javax.swing.JButton();
        btListAll = new javax.swing.JButton();
        progLabel = new javax.swing.JLabel();
        listPanel = new javax.swing.JPanel();
        pnUser = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtInfo = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtPublicKey = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtPrivateKey = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtSecretKey = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuLogout = new javax.swing.JMenu();
        logoutButton = new javax.swing.JMenuItem();

        pnUserTransactions.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 10, 10));

        jScrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder("Transactions"));

        lstUserTransactions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(lstUserTransactions);

        jPanel1.add(jScrollPane9);

        jScrollPane10.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction Detail"));

        txtUserTransactions.setColumns(20);
        txtUserTransactions.setRows(5);
        jScrollPane10.setViewportView(txtUserTransactions);

        jPanel1.add(jScrollPane10);

        pnUserTransactions.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        txtBalance.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        txtBalance.setText("0");
        txtBalance.setBorder(javax.swing.BorderFactory.createTitledBorder("Balance"));
        jPanel2.add(txtBalance, java.awt.BorderLayout.CENTER);

        pnUserTransactions.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SafetyRate");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imgs/logoBG.png")));

        tpBlockchain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpBlockchainStateChanged(evt);
            }
        });

        txtLeger.setEditable(false);
        txtLeger.setColumns(20);
        txtLeger.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtLeger.setRows(5);
        jScrollPane1.setViewportView(txtLeger);

        tpBlockchain.addTab("Vote Info", new javax.swing.ImageIcon(getClass().getResource("/imgs/info.png")), jScrollPane1); // NOI18N

        pnBlockChain.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(200, 146));

        lstBlockchain.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lstBlockchain.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstBlockchain.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstBlockchainValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstBlockchain);

        pnBlockChain.add(jScrollPane3, java.awt.BorderLayout.WEST);

        txtBlock.setEditable(false);
        txtBlock.setColumns(20);
        txtBlock.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtBlock.setLineWrap(true);
        txtBlock.setRows(5);
        txtBlock.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txtBlock);

        pnBlockChain.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        tpBlockchain.addTab("BlockChainExplorer", new javax.swing.ImageIcon(getClass().getResource("/imgs/Block.png")), pnBlockChain); // NOI18N

        getContentPane().add(tpBlockchain, java.awt.BorderLayout.CENTER);

        pnTransactionsUser.setMinimumSize(new java.awt.Dimension(160, 500));
        pnTransactionsUser.setPreferredSize(new java.awt.Dimension(360, 500));
        pnTransactionsUser.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnTransactionsUserStateChanged(evt);
            }
        });

        pnTrans.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnTrans.setLayout(new java.awt.BorderLayout());

        txtFrom.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtFrom.setBorder(javax.swing.BorderFactory.createTitledBorder("URL"));

        txtValue.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtValue.setText("0.0");
        txtValue.setBorder(javax.swing.BorderFactory.createTitledBorder("Avaliação"));

        btRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/votar.png"))); // NOI18N
        btRegister.setText("Avaliar                ");
        btRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterActionPerformed(evt);
            }
        });

        btListAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/votar.png"))); // NOI18N
        btListAll.setText("Listar Avaliações");
        btListAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btListAllMousePressed(evt);
            }
        });
        btListAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btListAllActionPerformed(evt);
            }
        });

        progLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/voteYes.gif"))); // NOI18N

        javax.swing.GroupLayout pnTransactionLayout = new javax.swing.GroupLayout(pnTransaction);
        pnTransaction.setLayout(pnTransactionLayout);
        pnTransactionLayout.setHorizontalGroup(
            pnTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTransactionLayout.createSequentialGroup()
                .addComponent(txtValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progLabel)
                .addGap(21, 21, 21))
            .addGroup(pnTransactionLayout.createSequentialGroup()
                .addGroup(pnTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btListAll, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnTransactionLayout.setVerticalGroup(
            pnTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTransactionLayout.createSequentialGroup()
                .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(pnTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValue)
                    .addComponent(progLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRegister)
                .addGap(5, 5, 5)
                .addComponent(btListAll))
        );

        pnTrans.add(pnTransaction, java.awt.BorderLayout.NORTH);

        listPanel.setLayout(new java.awt.GridLayout(1, 0));
        pnTrans.add(listPanel, java.awt.BorderLayout.CENTER);

        pnTransactionsUser.addTab("Transaction", new javax.swing.ImageIcon(getClass().getResource("/imgs/logo_small.png")), pnTrans); // NOI18N

        pnUser.setLayout(new java.awt.BorderLayout());

        txtInfo.setColumns(20);
        txtInfo.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtInfo.setLineWrap(true);
        txtInfo.setRows(5);
        txtInfo.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txtInfo);

        jTabbedPane2.addTab("Info", jScrollPane5);

        txtPublicKey.setColumns(20);
        txtPublicKey.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtPublicKey.setLineWrap(true);
        txtPublicKey.setRows(5);
        txtPublicKey.setWrapStyleWord(true);
        jScrollPane6.setViewportView(txtPublicKey);

        jTabbedPane2.addTab("Public Key", jScrollPane6);

        txtPrivateKey.setColumns(20);
        txtPrivateKey.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtPrivateKey.setLineWrap(true);
        txtPrivateKey.setRows(5);
        txtPrivateKey.setWrapStyleWord(true);
        jScrollPane7.setViewportView(txtPrivateKey);

        jTabbedPane2.addTab("Private Key", jScrollPane7);

        txtSecretKey.setColumns(20);
        txtSecretKey.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtSecretKey.setLineWrap(true);
        txtSecretKey.setRows(5);
        txtSecretKey.setWrapStyleWord(true);
        jScrollPane8.setViewportView(txtSecretKey);

        jTabbedPane2.addTab("Secret Key", jScrollPane8);

        pnUser.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        pnTransactionsUser.addTab("User", new javax.swing.ImageIcon(getClass().getResource("/imgs/user.png")), pnUser); // NOI18N

        getContentPane().add(pnTransactionsUser, java.awt.BorderLayout.WEST);

        menuLogout.setText("Menu");
        menuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLogoutMouseClicked(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutButtonMousePressed(evt);
            }
        });
        menuLogout.add(logoutButton);

        jMenuBar1.add(menuLogout);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterActionPerformed
        try {
            String url = txtFrom.getText();
            String aval = txtValue.getText();
            try {
                //limites para a avaliação, 0.0<x<10.0
                if (Double.parseDouble(aval) < 0 || Double.parseDouble(aval) > 10) {
                JOptionPane.showMessageDialog(this, "A avaliação tem que estar contida entre 0.0 e 10.0.");
            } else {
                String msg = url + ";" + aval + ";" + myUser.getName();
                System.out.println(url);
                progLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/loading.gif")));
                myUser.getMiner().startMining(msg, 4);
                chain.save(storage);
                JOptionPane.showMessageDialog(this, "Avaliação efetuada.");
                DefaultListModel model = new DefaultListModel();
                model.addAll(chain.getBlockChain().getChain());
            }
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "A avaliação tem que ser numérica.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(SafetyRateGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btRegisterActionPerformed

    private void pnTransactionsUserStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnTransactionsUserStateChanged
        /*if (pnTransactionsUser.getSelectedComponent() == pnUsersBalance) {
            DefaultListModel model = new DefaultListModel();
            model.addAll(chain.getUsers());
            lstUsers.setModel(model);
        }*/
    }//GEN-LAST:event_pnTransactionsUserStateChanged

    private void tpBlockchainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpBlockchainStateChanged
        if (tpBlockchain.getSelectedComponent() == pnBlockChain) {
            DefaultListModel model = new DefaultListModel();
            model.addAll(chain.getBlockChain().getChain());
            lstBlockchain.setModel(model);
            progLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/voteYes.gif")));
        }
    }//GEN-LAST:event_tpBlockchainStateChanged

    private void lstBlockchainValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstBlockchainValueChanged
        if (lstBlockchain.getSelectedIndex() >= 0) {
            Block b = (Block) lstBlockchain.getSelectedValues()[0];
            txtBlock.setText(b.getFullInfo());
        }
    }//GEN-LAST:event_lstBlockchainValueChanged

    private void btListAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btListAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btListAllActionPerformed

    /**
     * Carrega e mostra a lista de todos os votos
     * @param evt 
     */
    private void btListAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btListAllMousePressed
        // TODO add your handling code here:
        ArrayList<Block> blocos = chain.getBlockChain().getChain();
        //list model para criar a lista com os dados dos votos
        DefaultListModel model = new DefaultListModel();
        model.addAll(chain.getBlockChain().getUrls());
        System.out.println(chain.getBlockChain().getUrls());
        //criar a lista com o model anterior
        JList avalList = new JList(model);
        avalList.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        
        avalList.addListSelectionListener((ListSelectionEvent e) -> {
            //procura o elemento da lista selecionado na blockchain
            Block b = blocos.get(avalList.getSelectedIndex());
            String data = b.getData();
            //divide a string para ir buscar os dados todos (bad practice :O)
            String[] dataS = data.split(";");
            //atualiza os dados do elemento selecionado
            txtLeger.setText("User:" + dataS[2] + "\nURL: " + dataS[0] + "\nAvaliação: " + dataS[1] + "\nMédia do URL: " + chain.getAverageUrl(dataS[0]));
        });   
        
        
        
        
        
        //reconstruir a lista
        listPanel.removeAll();
        listPanel.add(new JScrollPane(avalList));
        listPanel.repaint();
        listPanel.revalidate();
        
    }//GEN-LAST:event_btListAllMousePressed

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void menuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLogoutMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuLogoutMouseClicked

    private void logoutButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMousePressed
        // TODO add your handling code here:
        //logout ao clicar no botao do menu
        this.dispose();
        new Autentication().setVisible(true);
    }//GEN-LAST:event_logoutButtonMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btListAll;
    private javax.swing.JButton btRegister;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel listPanel;
    private javax.swing.JMenuItem logoutButton;
    private javax.swing.JList<String> lstBlockchain;
    private javax.swing.JList<String> lstUserTransactions;
    private javax.swing.JMenu menuLogout;
    private javax.swing.JPanel pnBlockChain;
    private javax.swing.JPanel pnTrans;
    private javax.swing.JPanel pnTransaction;
    private javax.swing.JTabbedPane pnTransactionsUser;
    private javax.swing.JPanel pnUser;
    private javax.swing.JPanel pnUserTransactions;
    private javax.swing.JLabel progLabel;
    private javax.swing.JTabbedPane tpBlockchain;
    private javax.swing.JLabel txtBalance;
    private javax.swing.JTextArea txtBlock;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextArea txtInfo;
    private javax.swing.JTextArea txtLeger;
    private javax.swing.JTextArea txtPrivateKey;
    private javax.swing.JTextArea txtPublicKey;
    private javax.swing.JTextArea txtSecretKey;
    private javax.swing.JTextArea txtUserTransactions;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
