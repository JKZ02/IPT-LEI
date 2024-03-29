/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafetyRate.GUI;

import SafetyRate.utils.Block;
import SafetyRate.utils.BlockChain;
import SafetyRate.utils.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Toolkit;

/**
 *
 * @author afsal
 */
public class mainMenu extends javax.swing.JFrame {

    
    BlockChain bc = new BlockChain();
    User myUser;
    /**
     * Creates new form mainMenu
     */
    public mainMenu(User user) {
        try {
            this.myUser = user;
            
        } catch (Exception e) {
        }
        initComponents();
        setLocationRelativeTo(null);
        panelShowAll.setLayout(new BorderLayout());
        panelShowAll.removeAll();
        staticAval.setVisible(false);
        staticUrl.setVisible(false);
        staticAutor.setVisible(false);
        avalProgress.setStringPainted(true);
        //searchProgress.setStringPainted(true);
        try {
            bc.load("blockchain.obj");
        } catch (Exception ex) {
            Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtProcurar = new javax.swing.JTextField();
        buttonProcurar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        staticUrl = new javax.swing.JLabel();
        buttonShowAll = new javax.swing.JButton();
        panelShowAll = new javax.swing.JPanel();
        labelUrl = new javax.swing.JLabel();
        staticAval = new javax.swing.JLabel();
        labelAval = new javax.swing.JLabel();
        searchedUrlLabel = new javax.swing.JLabel();
        searchedLabel = new javax.swing.JLabel();
        avalLabel = new javax.swing.JLabel();
        searchedAvalLabel = new javax.swing.JLabel();
        avalButton = new javax.swing.JButton();
        avalProgress = new javax.swing.JProgressBar();
        labelAvalT = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        staticAutor = new javax.swing.JLabel();
        labelAutor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SafetyRate - " + myUser.getName());
        setIconImage(Toolkit.getDefaultToolkit().getImage(mainMenu.class.getResource("logo.png")));

        txtProcurar.setText("introduza um url...");
        txtProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProcurarActionPerformed(evt);
            }
        });

        buttonProcurar.setText("Procurar");
        buttonProcurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonProcurarMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SafetyRate");

        staticUrl.setText("URL:");

        buttonShowAll.setText("Mostrar todas Avaliações");
        buttonShowAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonShowAllMousePressed(evt);
            }
        });
        buttonShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShowAllActionPerformed(evt);
            }
        });

        panelShowAll.setBackground(new java.awt.Color(204, 255, 255));
        panelShowAll.setMaximumSize(new java.awt.Dimension(145, 163));

        javax.swing.GroupLayout panelShowAllLayout = new javax.swing.GroupLayout(panelShowAll);
        panelShowAll.setLayout(panelShowAllLayout);
        panelShowAllLayout.setHorizontalGroup(
            panelShowAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        panelShowAllLayout.setVerticalGroup(
            panelShowAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        staticAval.setText("Avaliação:");

        searchedUrlLabel.setBackground(new java.awt.Color(204, 255, 255));

        searchedLabel.setText("URL:");

        avalLabel.setText("Avaliação:");

        avalButton.setText("Avaliar");
        avalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                avalButtonMousePressed(evt);
            }
        });

        labelAvalT.setText("Avaliações Totais: ");

        staticAutor.setText("Autor:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(searchedLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(searchedUrlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(avalButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(avalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(avalLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchedAvalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAvalT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonShowAll)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(staticAval)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelAval, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(staticUrl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(staticAutor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonProcurar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(searchedLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchedUrlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avalLabel)
                            .addComponent(searchedAvalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAvalT, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(avalProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                    .addComponent(avalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(buttonShowAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(staticUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(staticAval)
                            .addComponent(labelAval, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(staticAutor)
                            .addComponent(labelAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProcurarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProcurarActionPerformed

    private void buttonProcurarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonProcurarMousePressed

        //procura o website procurado
        String[] blockData = bc.search(txtProcurar.getText());
        //caso o website já tenha sido avaliado pelo menos 1 vez carrega os seus dados
        if (!blockData[0].isBlank()) {
            double media = bc.getAval(txtProcurar.getText());
            int total = bc.getAvalTotal(txtProcurar.getText());
            System.out.println(media);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            String avalFormat = df.format(Double.parseDouble(Double.toString(media)));
            //txtUrlAtual.setText(blockData);
            System.out.println("Is not Blank");
            searchedUrlLabel.setText(blockData[0]);
            searchedAvalLabel.setText(avalFormat);
            labelTotal.setText(Integer.toString(total));
        } else {
            searchedUrlLabel.setText(txtProcurar.getText());
            searchedAvalLabel.setText("N/A");
            labelTotal.setText("0");
            
            
            
            /*searchedUrlLabel.setText("A procurar...");
            System.out.println("Is Blank");
            Thread search = new Thread(() -> {
               bc.add(txtProcurar.getText(), "N/A", "0"); 
            });
            search.start();
            
            Thread prog = new Thread(() -> {
                int counter = 0;
                while(counter<=90) {
                    searchProgress.setValue(counter);
                    counter++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 try {
                    search.join();
                    searchProgress.setValue(100);
                    searchedUrlLabel.setText(txtProcurar.getText());
                    bc.save("blockchain.obj");
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            prog.start();*/
        }
        
            
    }//GEN-LAST:event_buttonProcurarMousePressed

    private void buttonShowAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonShowAllMousePressed
        
        staticAval.setVisible(true);
        staticUrl.setVisible(true);
        staticAutor.setVisible(true);
        CopyOnWriteArrayList<Block> urls = bc.getAll();
        //System.out.println("Blocos:");
        //bc.printAll();
        //System.out.println("URLS:");
        DefaultListModel model = new DefaultListModel();
        //adiciona todos os blocos da blockchain a um modelo
        for(Block s : urls) {
            System.out.println(s);
            model.addElement(s);
            
        }
        //cria uma lista com base no modelo anterior
        JList listShowAll = new JList(model);
        //define o tamanho maximo mostrado de cada elemento da lista
        listShowAll.setPrototypeCellValue("XXXXXXXXXXXXXXX");
        //espera que algum elemento da lista seja selecionado
        listShowAll.addListSelectionListener(new ListSelectionListener() {
                
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //procura o elemento da lista selecionado na blockchain
                Block blocoSelecionado = bc.get(listShowAll.getSelectedIndex());
                System.out.println(listShowAll.getSelectedIndex());
                //atualiza os dados do GUI para mostrar os dados do elemento selecionado
                labelUrl.setText(blocoSelecionado.getData()[0]);
                labelAval.setText(blocoSelecionado.getData()[1]);
                labelAutor.setText(blocoSelecionado.getData()[2]);
            }
        });   
        //re-renderiza o painel onde a está a lista
        panelShowAll.removeAll();
        panelShowAll.add(new JScrollPane(listShowAll));
        panelShowAll.revalidate();
        panelShowAll.repaint();
    }//GEN-LAST:event_buttonShowAllMousePressed

    private void avalButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avalButtonMousePressed
        
        avalProgress.setValue(0);
        //cria um popup que pede a avaliação ao utilizados
        String aval = JOptionPane.showInputDialog(this, "Avaliação 0-10: ", "Avaliação", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(aval);
        //caso a avaliação seja válida procede
        if(isAvalValid(aval)) {
        //formatar a avaliação
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        String avalFormat = df.format(Double.parseDouble(aval));
        System.out.println(avalFormat);


        //cria uma thread que será responsável pela mineração do bloco e criação das respetivas threads de mineração
        Thread search = new Thread(() -> {
               bc.add(txtProcurar.getText(), avalFormat, myUser.getName()); 
            });
            search.start();
            //cria uma thread para processar a barra de progressos do gui
            Thread prog = new Thread(() -> {
                int counter = 0;
                //incremento da barra de progresso
                while(counter<=90) {
                    avalProgress.setValue(counter);
                    counter++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 try {
                     //espera que a thread termine
                    search.join();
                    avalProgress.setValue(100);
                    //guarda a blockchain após o bloco ser adicionado
                    bc.save("blockchain.obj");
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(mainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            prog.start();
            
        } else {
            JOptionPane.showMessageDialog(this, "Formato de avaliação incorreto, introduza um valor de 0.0 a 10.0", "Avaliação inválida.", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_avalButtonMousePressed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShowAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonShowAllActionPerformed
    
    /**
     * verifica se uma avaliação é válida
     * @param n - avaliação
     * @return 
     */
    public boolean isAvalValid(String n) {
        if(n == null) {
            return false;
        }
        
        try {
            double d = Double.parseDouble(n);
        } catch (NumberFormatException nfe) {
            return false;
        }
        
        if(Double.parseDouble(n)<0 || Double.parseDouble(n)>10){
            return false;
        }
        
        return true;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainMenu().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avalButton;
    private javax.swing.JLabel avalLabel;
    private javax.swing.JProgressBar avalProgress;
    private javax.swing.JButton buttonProcurar;
    private javax.swing.JButton buttonShowAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelAutor;
    private javax.swing.JLabel labelAval;
    private javax.swing.JLabel labelAvalT;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelUrl;
    private javax.swing.JPanel panelShowAll;
    private javax.swing.JLabel searchedAvalLabel;
    private javax.swing.JLabel searchedLabel;
    private javax.swing.JLabel searchedUrlLabel;
    private javax.swing.JLabel staticAutor;
    private javax.swing.JLabel staticAval;
    private javax.swing.JLabel staticUrl;
    private javax.swing.JTextField txtProcurar;
    // End of variables declaration//GEN-END:variables
}
