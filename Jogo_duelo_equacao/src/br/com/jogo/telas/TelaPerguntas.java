/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jogo.telas;

import java.util.Random;
import javax.swing.JOptionPane;
import java.sql.*;
import br.com.jogo.dal.ModeloConexao;
import br.com.jogo.telas.TelaCadastroJogador;

/**
 *
 * @author Julimar
 */
public class TelaPerguntas extends javax.swing.JFrame {

    /**
     * Creates new form TelaInicial
     */
    public TelaPerguntas() {
        initComponents();
    }

    Random rand = new Random();
    

    int n1 = 0, n2 = 0, operacao = 0, ponto = 0;
    float resultado = 0, respostaUsuario = 0;

    int contadorPerguntas = 0;
    final int MAX_PERGUNTAS = 9;

    // Função para sortear números aleatorio
    public void aleatorio() {
        n1 = rand.nextInt(10) + 1;
        n2 = rand.nextInt(10) + 1;
        operacao = rand.nextInt(4) + 1;
    }

    // Função para mostrar as perguntas 
    public void perguntas() {
        aleatorio();
        if (operacao == 1) {
            lblPergunta.setText(n1 + " + " + n2 + "?");
        }
        if (operacao == 2) {
            lblPergunta.setText(n1 + " - " + n2 + "?");
        }
        if (operacao == 3) {
            lblPergunta.setText(n1 + " x " + n2 + "?");
        }
        if (operacao == 4) {
            lblPergunta.setText(n1 + " / " + n2 + "?");
        }
    }

    // Função fazer o calculo correto 
    public float calculoRespostaCorreta() {
        switch (operacao) {
            case 1:
                return n1 + n2;
            case 2:
                return n1 - n2;
            case 3:
                return n1 * n2;
            case 4:
                resultado = (float) n1 / n2;
                return Math.round(resultado * 10) / 10.0f; 
            default:
                return 0;
        }
    }

    // Teste para verificar a resposta correta
    public void verificandoRespostaCerta() {

        try {
            String textoResposta = txtResposta.getText().trim().replace(",", ".");
            respostaUsuario = Float.parseFloat(textoResposta);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "DIGITE UM NÚMERO VÁLIDO", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            txtResposta.requestFocus();
            return;
        }

        float resultadoCorreto = calculoRespostaCorreta();
        if (Math.abs(respostaUsuario - resultadoCorreto) < 0.0001f) {
            ponto += 1;
            JOptionPane.showMessageDialog(null, "Acertou!");
            lblPontos.setText(String.valueOf(ponto));
        } else {
            ponto -= 1;
            JOptionPane.showMessageDialog(null, "Errou! O correto era: " + resultadoCorreto);
            lblPontos.setText(String.valueOf(ponto));
        }

        // Colocar a cor nos pontos
        if (ponto < 0) {
            lblPontos.setForeground(java.awt.Color.RED);
        } else {
            lblPontos.setForeground(java.awt.Color.BLUE);
        }

        // Contagem para terminar o jogo
        if (contadorPerguntas < MAX_PERGUNTAS) {
            contadorPerguntas++;
            txtResposta.setText("");
            perguntas();
        } else {
            JOptionPane.showMessageDialog(null, "FIM DO JOGO! Sua Pontuação Final é: " + ponto);
            salvarPontuacaoFinal();
            txtResposta.setText(null);
            lblPergunta.setText(null);
            TelaInicial inicial = new TelaInicial();
            inicial.setVisible(true);
            this.setVisible(false);
        }
    }

    public void salvarPontuacaoFinal() {
    
    // O comando UPDATE atualiza a coluna 'total' na linha específica do jogador.
    String sql = "UPDATE tbjogador SET total = ? WHERE id = ?"; 
    
    // Verifica se o ID foi capturado. Se for 0, houve erro no cadastro inicial.
    if (TelaCadastroJogador.idJogadorAtual == 0) {
        JOptionPane.showMessageDialog(null, "ERRO CRÍTICO: ID do jogador não foi encontrado para salvar a pontuação.", "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Tenta conectar e executar a atualização
    try (Connection conexao = ModeloConexao.conector(); // Use seu método de conexão
         java.sql.PreparedStatement pst = conexao.prepareStatement(sql)) {
        
        // 1. Define o valor da pontuação final (Primeiro '?')
        pst.setInt(1, ponto); 
        
        // 2. Define o ID da linha a ser atualizada (Segundo '?')
        pst.setInt(2,TelaCadastroJogador.idJogadorAtual); 
        
        // Executa o UPDATE
        int linhasAfetadas = pst.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("Pontuação final (" + ponto + ") salva com sucesso para o ID: " + TelaCadastroJogador.idJogadorAtual);
        } else {
            System.out.println("Atenção: Nenhuma linha atualizada. ID pode não existir.");
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar pontuação no banco: " + e.getMessage(), "Erro de Banco", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPergunta = new javax.swing.JLabel();
        txtResposta = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblPontos = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Duelo de Equação - Página Principal");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/img/teacher.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lblPergunta.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jPanel1.add(lblPergunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 210, 90));

        txtResposta.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jPanel1.add(txtResposta, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 230, 50));

        btnConfirmar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, -1, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setText("Pontos:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        lblPontos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblPontos.setText("-");
        jPanel1.add(lblPontos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 170, 490, 430);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/img/background.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(-170, 0, 750, 750);

        setSize(new java.awt.Dimension(545, 767));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        txtResposta.requestFocus();
        verificandoRespostaCerta();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // Comando para função pergunta
        perguntas();
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(TelaPerguntas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPerguntas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPerguntas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPerguntas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPerguntas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPergunta;
    private javax.swing.JLabel lblPontos;
    private javax.swing.JTextField txtResposta;
    // End of variables declaration//GEN-END:variables
}
