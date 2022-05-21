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
public class form_data_supplier extends javax.swing.JFrame {
    Connection conn;
    Statement stat;
    ResultSet rs;
    public Connection koneksi;
    String sql;
    /**
     * Creates new form form_data_supplier
     */
    public form_data_supplier() {
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
            String sql = "select right(kode_supplier,2)"
                    + "as no_terakhir from "
                    + "tabel_supplier";
            rs = stat.executeQuery(sql);
            if (rs.first() == false){
                txt_kode_supplier.setText("SP0001");
            }else
            rs.last();
            int no = rs.getInt(1) +1;
            String cno = String.valueOf(no);
            int pjg_cno = cno.length();
            int i = 0;
            if (i < 2 - pjg_cno) {
                cno = "000" + cno;
                txt_kode_supplier.setText("SP" + cno);
            }else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                txt_kode_supplier.setText("SP" + cno);
            }else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                txt_kode_supplier.setText("SP" + cno);
            }else if (i < 5 - pjg_cno){
                cno = "" + cno;
                txt_kode_supplier.setText("SP" + cno);
            }
            txt_kode_supplier.setText("SP" + cno);
        } catch (Exception DBException) {
            System.err.println("error =" + DBException);
        }
    }
    
    private void simpan(){
        if (txt_kode_supplier.getText().length() == 0
                || txt_nama_supplier.getText().length() == 0
                || txt_alamat_supplier.getText().length() == 0
                || txt_telepon_supplier.getText().length() == 0
                || txt_email_supplier.getText().length() == 0) {
        JOptionPane.showMessageDialog(rootPane, "isi data dengan benar");
    }
        else{
    }        
        try {
            stat.executeUpdate("insert into tabel_supplier values("
                +""+""+"'"+txt_kode_supplier.getText()+"',"
                +""+""+"'"+txt_nama_supplier.getText()+"',"
                +""+""+"'"+txt_alamat_supplier.getText()+"',"
                +""+""+"'"+txt_telepon_supplier.getText()+"',"
                +""+""+"'"+txt_email_supplier.getText()+"')");
            JOptionPane.showMessageDialog (null,"Data Berhasil di Simpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        autonumber();
        tampil();
    }
    
    private void tampil(){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("Kode Suplier");
        tabel.addColumn("Nama Suplier");
        tabel.addColumn("Alamat Suplier");
        tabel.addColumn("Telepon Suplier");
        tabel.addColumn("Email Suplier");
        tabel_supplier.setModel(tabel);
        try {
            rs=stat.executeQuery("select * from tabel_supplier order by kode_supplier");
            while (rs.next())
                tabel.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4) ,    
                rs.getString(5)          
               
            });
                       tabel_supplier.setModel(tabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ubah(){
        try {
            stat.executeUpdate("update tabel_supplier set "
            + "nama_supplier='" + txt_nama_supplier.getText() +"',"
            + "alamat='" + txt_alamat_supplier.getText() +"',"
            + "no_telp='" + txt_telepon_supplier.getText() +"',"
            + "email='" + txt_email_supplier.getText() +"'"
            + "where "
            + "kode_supplier='" + txt_kode_supplier.getText() +"'"
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
        txt_nama_supplier.setText("");
        txt_alamat_supplier.setText("");
        txt_telepon_supplier.setText("");
        txt_email_supplier.setText("");
        txt_kode_supplier.requestFocus();
    }
           
              private void hapus(){
        if(JOptionPane.showConfirmDialog(this,
                "Apakah Anda Yakin akan menghapus Data ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION){
           
    try {
                stat.executeUpdate("delete from tabel_supplier where "
                        + "kode_supplier='" + txt_kode_supplier.getText() + "'");
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
                      tabel.addColumn("Kode Supplier");
                      tabel.addColumn("Nama Supplier");
                      tabel.addColumn("Alamat Supplier");
                      tabel.addColumn("Telepon Supplier");
                      tabel.addColumn("Email Supplier");
                      
                      tabel_supplier.setModel(tabel);
                      try {
                          rs = stat.executeQuery(sql);
                          while (rs.next()){
                              tabel.addRow(new Object[]{
                               rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5)
                              }); 
                              tabel_supplier.setModel(tabel);
                          }
                      } catch (Exception e) {
                          JOptionPane.showConfirmDialog(rootPane, "Ada Kesalahan!" + e);
                      }
                  }
              }
              
              private void ComboBox (){
                  if(cb_cari.getSelectedIndex() == 1) {
                      sql = "select * from tabel_supplier "
                              + "where kode_supplier like'"
                              + txt_cari.getText() + "%' ORDER BY kode_supplier";
                  }else if(cb_cari.getSelectedIndex() == 2) {
                      sql = "select * from tabel_supplier "
                              + "where nama_supplier like'"
                              + txt_cari.getText() + "%' ORDER BY nama_supplier";
                  }else if(cb_cari.getSelectedIndex() == 3) {
                      sql = "select * from tabel_supplier "
                              + "where alamat_supplier like'"
                              + txt_cari.getText() + "%' ORDER BY alamat_supplier";
                  }else if(cb_cari.getSelectedIndex() == 4) {
                      sql = "select * from tabel_supplier "
                              + "where telepon_supplier like'"
                              + txt_cari.getText() + "%' ORDER BY telepon_supplier";
                  }else if(cb_cari.getSelectedIndex() == 5) {
                      sql = "select * from tabel_supplier "
                              + "where email_supplier like'"
                              + txt_cari.getText() + "%' ORDER BY email_supplier";
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_kode_supplier = new javax.swing.JTextField();
        txt_nama_supplier = new javax.swing.JTextField();
        txt_telepon_supplier = new javax.swing.JTextField();
        txt_email_supplier = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat_supplier = new javax.swing.JTextPane();
        btn_simpan = new javax.swing.JButton();
        btn_bersih = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        cb_cari = new javax.swing.JComboBox();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_supplier = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(704, 356));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Form Input Data Supplier");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Kode Supplier");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nama Supplier");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Alamat");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("No Telepon");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("E-mail");

        jScrollPane1.setViewportView(txt_alamat_supplier);

        btn_simpan.setText("Save");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_bersih.setText("Refresh");
        btn_bersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bersihActionPerformed(evt);
            }
        });

        btn_ubah.setText("Update");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setText("Delete");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cari Berdasarkan", "Kode Supplier", "Nama Supplier" }));

        btn_cari.setText("Search");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        tabel_supplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_supplierMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_supplier);

        jButton6.setText("Keluar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_bersih)
                                .addGap(10, 10, 10)
                                .addComponent(btn_ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_hapus)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_kode_supplier))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(txt_nama_supplier)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_email_supplier)
                                    .addComponent(txt_telepon_supplier))))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_kode_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_nama_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_telepon_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_email_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_simpan)
                            .addComponent(btn_bersih)
                            .addComponent(btn_ubah)
                            .addComponent(btn_hapus))
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
ubah();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
hapus();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_bersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bersihActionPerformed
bersih();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_bersihActionPerformed

    private void tabel_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_supplierMouseClicked
    try {
       int row = tabel_supplier.rowAtPoint(evt.getPoint());
       String kode_supplier = tabel_supplier.getValueAt(row, 0).toString();
            String nama_supplier = tabel_supplier.getValueAt(row, 1).toString();
            String alamat_supplier = tabel_supplier.getValueAt(row, 1).toString();
            String telepon_supplier = tabel_supplier.getValueAt(row, 2).toString();
            String email_supplier = tabel_supplier.getValueAt(row, 3).toString();
            txt_kode_supplier.setText(kode_supplier);
            txt_nama_supplier.setText(nama_supplier);
            txt_alamat_supplier.setText(alamat_supplier);
            txt_telepon_supplier.setText(telepon_supplier);
            txt_email_supplier.setText(email_supplier);
            } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
        tampil();
    }//GEN-LAST:event_tabel_supplierMouseClicked

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
ComboBox();
        CariData();// TODO add your handling code here:
    }//GEN-LAST:event_btn_cariActionPerformed

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
            java.util.logging.Logger.getLogger(form_data_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_data_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_data_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_data_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_data_supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bersih;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel_supplier;
    private javax.swing.JTextPane txt_alamat_supplier;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_email_supplier;
    private javax.swing.JTextField txt_kode_supplier;
    private javax.swing.JTextField txt_nama_supplier;
    private javax.swing.JTextField txt_telepon_supplier;
    // End of variables declaration//GEN-END:variables
}
