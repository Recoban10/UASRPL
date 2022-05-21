/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aplikasi;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Recoban10
 */
public class form_laporan_barang_keluar extends javax.swing.JFrame {
    Connection conn;
    Statement stat;
    ResultSet rs;
    public Connection koneksi;
    String sql;

    /**
     * Creates new form form_laporan_barang
     */
    public form_laporan_barang_keluar() {
        initComponents();
        koneksi_database();
        tampil_barang_keluar();
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(this);
    }
    
    public void koneksi_database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/" + "db_inventory", "root", "");
            JOptionPane.showMessageDialog(null, "Koneksi berhasil.");

            stat = koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void tampil_barang_keluar(){
        DefaultTableModel tbl = new DefaultTableModel();
        
        tbl.addColumn("Kode");
        tbl.addColumn("Tanggal");
        tbl.addColumn("Kode Customer");
        tbl.addColumn("Nama Customer");
        tbl.addColumn("Kode Barang");
        tbl.addColumn("Nama Barang");
        tbl.addColumn("Jumlah Barang");
        tbl.addColumn("Barang Keluar");
        tbl.addColumn("Total Barang");
        
        tbl_barang_keluar.setModel(tbl);
        
        try {
            rs = stat.executeQuery("SELECT * FROM tabel_barang_keluar "
            + " ORDER BY kode_transaksi");
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    rs.getString("kode_transaksi"),
                    rs.getString("tanggal_transaksi"),
                    rs.getString("kode_customer"),
                    rs.getString("nama_customer"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_barang"),
                    rs.getString("barang_keluar"),
                    rs.getString("total_barang")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    private void ComboBox(){
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
        String tanggal1 = dateFormat_1.format(jdate1.getDate());
        SimpleDateFormat dateFormat_2 = new SimpleDateFormat("YYYY-MM-dd");
        String tanggal2 = dateFormat_2.format(jdate2.getDate());
        
        sql = "SELECT * FROM tabel_barang_keluar " 
                + "WHERE tanggal_transaksi BETWEEN '" + tanggal1 + "' AND '"
                + tanggal2 + "' ORDER BY kode_transaksi"; 
        
    }
    
    private void CariData () {
            DefaultTableModel tbl = new DefaultTableModel();
            tbl.addColumn("Kode Transaksi");
            tbl.addColumn("Tanggal Transaksi");
            tbl.addColumn("Nama Customer");
            tbl.addColumn("Kode Customer");
            tbl.addColumn("Nama Barang");
            tbl.addColumn("Kode Barang");
            tbl.addColumn("Jumlah Barang");
            tbl.addColumn("Total Barang");
            
            tbl_barang_keluar.setModel(tbl);
            try {
            rs = stat.executeQuery(sql);
                while (rs.next()) {
                    tbl.addRow(new Object[] {
                        rs.getString("kode_transaksi"),
                        rs.getString("tanggal_transaksi"),
                        rs.getString("nama_customer"),
                        rs.getString("kode_customer"),
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("kode_barang"),
                        rs.getString("jumlah_barang"),
                        rs.getString("total_barang")
            });
            tbl_barang_keluar.setModel(tbl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Ada Kesalahan !" + e);
        }
    }
    
    private void print(){
        try {
            String NamaFile = "./src/report_barang_keluar/report_barang_keluar.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi_database = DriverManager.getConnection("jdbc:mysql://localhost/" + "db_inventory", "root", "");
            HashMap hash = new HashMap(2);
            
            SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
            String tanggal1 = dateFormat_1.format(jdate1.getDate());
            SimpleDateFormat dateFormat_2 = new SimpleDateFormat("YYYY-MM-dd");
            String tanggal2 = dateFormat_2.format(jdate2.getDate());
            
            hash.put("tgl_sa", tanggal1);
            hash.put("tgl_du", tanggal2);
            
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi_database);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang_keluar = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jdate1 = new com.toedter.calendar.JDateChooser();
        jdate2 = new com.toedter.calendar.JDateChooser();
        btn_search = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 153));

        tbl_barang_keluar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_barang_keluar);

        jPanel2.setBackground(new java.awt.Color(51, 255, 204));

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Laporan Barang Keluar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        btn_search.setBackground(new java.awt.Color(204, 255, 204));
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(204, 255, 204));
        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_print.setBackground(new java.awt.Color(204, 255, 204));
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("S/D");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jdate1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdate2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh)
                        .addGap(17, 17, 17)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_search)
                        .addComponent(btn_refresh)
                        .addComponent(btn_print))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jdate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jdate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        print();
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        ComboBox();
        CariData();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
       tampil_barang_keluar();
    }//GEN-LAST:event_btn_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(form_laporan_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_laporan_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_laporan_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_laporan_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_laporan_barang_keluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdate1;
    private com.toedter.calendar.JDateChooser jdate2;
    private javax.swing.JTable tbl_barang_keluar;
    // End of variables declaration//GEN-END:variables
}
