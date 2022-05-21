/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Aplikasi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Recoban10
 */
public class form_data_barang extends javax.swing.JFrame {
    Connection conn;
    Statement stat;
    ResultSet rs;
    public Connection koneksi;
    String sql;
    /**
     * Creates new form form_menu_utama
     */
    public form_data_barang() {
        initComponents();
        koneksi_database();
        autonumber();
        tampil();
    }
    
    public void koneksi_database() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/" + "db_inventory","root","");
            JOptionPane.showMessageDialog(null, "koneksi Berhasil;");
            
            stat = koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void autonumber(){
        try {
            String sql = "select right(kode_barang,2)"
                    + "as no_terakhir from "
                    + "tabel_barang";
            rs = stat.executeQuery(sql);
            if (rs.first() == false){
                txt_kode_barang.setText("B0001");
            }else
            rs.last();
            int no = rs.getInt(1) +1;
            String cno = String.valueOf(no);
            int pjg_cno = cno.length();
            int i = 0;
            if (i < 2 - pjg_cno) {
                cno = "000" + cno;
                txt_kode_barang.setText("B" + cno);
            }else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                txt_kode_barang.setText("B" + cno);
            }else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                txt_kode_barang.setText("B" + cno);
            }else if (i < 5 - pjg_cno){
                cno = "" + cno;
                txt_kode_barang.setText("B" + cno);
            }
            txt_kode_barang.setText("B" + cno);
        } catch (Exception DBException) {
            System.err.println("error =" + DBException);
        }
    }

       private void simpan(){
        if (txt_kode_barang.getText().length() == 0
                || txt_jumlah_barang.getText().length() == 0
                || txt_nama_barang.getText().length() == 0) {
        JOptionPane.showMessageDialog(rootPane, "isi data dengan benar");
    }
        else{
    }        
        try {
            stat.executeUpdate("insert into tabel_barang values("
                +""+""+"'"+txt_kode_barang.getText()+"',"
                +""+""+"'"+txt_nama_barang.getText()+"',"
                +""+""+"'"+txt_jumlah_barang.getText()+"',");
            JOptionPane.showMessageDialog (null,"Data Berhasil di Simpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        autonumber();
        tampil();
    }
       private void tampil(){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Jumlah Barang");
        tabel_barang.setModel(tabel);
        try {
            rs=stat.executeQuery("select * from tabel_barang order by kode_barang");
            while (rs.next())
                tabel.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3)      
               
            });
                       tabel_barang.setModel(tabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
       private void ubah(){
        try {
            stat.executeUpdate("update tabel_barang set "
            + "nama_barang='" + txt_nama_barang.getText() +"',"
            + "jumlah_barang='" + txt_jumlah_barang.getText() +"'"
            + "where "
            + "kode_barang='" + txt_kode_barang.getText() +"'"
            );
            JOptionPane.showMessageDialog(null, "Data User Telah diganti");
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "error");
        }
        tampil();
    }
           private void bersih(){
        tampil();
        autonumber();
        txt_nama_barang.setText("");
        txt_jumlah_barang.setText("");
        txt_kode_barang.requestFocus();
    }
           
              private void hapus(){
        if(JOptionPane.showConfirmDialog(this,
                "Apakah Anda Yakin akan menghapus Data ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION){
           
    try {
                stat.executeUpdate("delete from tabel_barang where "
                        + "kode_barang='" + txt_kode_barang.getText() + "'");
                JOptionPane.showMessageDialog(null, "Data telah dihapus");
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Error " + e);
            }
        tampil();
    }
    }
              
              private void CariData(){
                  if(cb_cari.getSelectedIndex() == 0){
                      JOptionPane.showConfirmDialog(rootPane, "Jenis Pencarian Belum Dipilih");
                      
                  }else{
                      DefaultTableModel tabel = new DefaultTableModel();
                      tabel.addColumn("Kode Barang");
                      tabel.addColumn("Nama Barang");
                      tabel.addColumn("Jumlah Barang");
                      
                      tabel_barang.setModel(tabel);
                      try {
                          rs = stat.executeQuery(sql);
                          while (rs.next()){
                              tabel.addRow(new Object[]{
                               rs.getString(1),
                               rs.getString(2),
                               rs.getString(3)
                              }); 
                              tabel_barang.setModel(tabel);
                          }
                      } catch (Exception e) {
                          JOptionPane.showConfirmDialog(rootPane, "Ada Kesalahan!" + e);
                      }
                  }
              }
              
              private void ComboBox (){
                  if(cb_cari.getSelectedIndex() == 1) {
                      sql = "select * from tabel_barang "
                              + "where kode_barang like'"
                              + txt_cari.getText() + "%' ORDER BY kode_barang";
                  }else if(cb_cari.getSelectedIndex() == 2) {
                      sql = "select * from tabel_barang "
                              + "where nama_barang like'"
                              + txt_cari.getText() + "%' ORDER BY nama_barang";
                  }else if(cb_cari.getSelectedIndex() == 3) {
                      sql = "select * from tabel_barang "
                              + "where jumlah_barang like'"
                              + txt_cari.getText() + "%' ORDER BY jumlah_barang";
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_kode_barang = new javax.swing.JTextField();
        txt_jumlah_barang = new javax.swing.JTextField();
        txt_nama_barang = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        cb_cari = new javax.swing.JComboBox();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 255, 204));

        jPanel2.setBackground(new java.awt.Color(51, 255, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(704, 356));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel1.setText("Kode Barang");

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Form Input Data Barang");

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel3.setText("Nama Barang");

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel4.setText("Jumlah Barang");

        txt_kode_barang.setEditable(false);
        txt_kode_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kode_barangActionPerformed(evt);
            }
        });

        txt_jumlah_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah_barangActionPerformed(evt);
            }
        });

        btn_simpan.setText("Save");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_hapus.setText("Delete");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel_Barang"));

        tabel_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_barang);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cari berdasarkan Table", "Kode Barang", "Nama Barang", "Jumlah Barang" }));
        cb_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_cariActionPerformed(evt);
            }
        });

        btn_cari.setText("Search");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kode_barang)
                            .addComponent(txt_nama_barang)
                            .addComponent(txt_jumlah_barang)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_update)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_hapus)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cari))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(479, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kode_barang))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nama_barang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_jumlah_barang))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_simpan)
                            .addComponent(btn_refresh)
                            .addComponent(btn_update)
                            .addComponent(btn_hapus)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cari)
                            .addComponent(btn_cari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(234, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kode_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kode_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kode_barangActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txt_jumlah_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah_barangActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
                               // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
ubah();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateActionPerformed

    private void tabel_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barangMouseClicked
         try {
            int row = tabel_barang.rowAtPoint(evt.getPoint());
            
            String kode_barang = tabel_barang.getValueAt(row, 0).toString();
            String nama_barang = tabel_barang.getValueAt(row, 1).toString();
            String jumlah_barang = tabel_barang.getValueAt(row, 2).toString();
            txt_kode_barang.setText(kode_barang);
            txt_nama_barang.setText(nama_barang);
            txt_jumlah_barang.setText(jumlah_barang);
            
            
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
        tampil();      // TODO add your handling code here:
    }//GEN-LAST:event_tabel_barangMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
hapus();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
bersih();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
ComboBox();
CariData();// TODO add your handling code here:
    }//GEN-LAST:event_btn_cariActionPerformed

    private void cb_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_cariActionPerformed

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
            java.util.logging.Logger.getLogger(form_data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                form_data_barang mu = new form_data_barang();
                mu.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_jumlah_barang;
    private javax.swing.JTextField txt_kode_barang;
    private javax.swing.JTextField txt_nama_barang;
    // End of variables declaration//GEN-END:variables
}
