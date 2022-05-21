/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Aplikasi;
import java.awt.Dimension;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.Statement;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import javax.swing.JTable;
 import javax.swing.JOptionPane;
 import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Recoban10
 */
public class form_barang_keluar extends javax.swing.JFrame {
Connection conn;
    Statement stat;
    ResultSet rs;
    public Connection koneksi;
    String sql;
    /**
     * Creates new form form_barang_keluar
     */
    public form_barang_keluar() {
        initComponents();
        koneksi_database();
        autonumber();
        tampil_customer();
        tampil_barang();
        tampil_barang_keluar();
        Dialog_barang.setMinimumSize(new Dimension(352, 478));
        Dialog_barang.setMaximumSize(new Dimension(325, 478));
        Dialog_customer.setMinimumSize(new Dimension(325, 478));
        Dialog_customer.setMaximumSize(new Dimension(325, 478));
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
         Date date = new Date();
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
         String tgl = format.format(date);
         try {
             String sql = "select right (kode_transaksi,5) as no_terakhir "
                     + "from tabel_barang_keluar";
             rs = stat.executeQuery(sql);
             if (rs.first() == false) {
                 txt_kode_transaksi.setText("BK" + tgl + "00001");
             }else {
                rs.last();
                int no = rs.getInt(1) + 1;
                String cno = String.valueOf(no);
                int pjg_cno = cno.length();
                int i = 0; 
                 if (i < 2 - pjg_cno) {
                     cno = "0000" + cno;
                     txt_kode_transaksi.setText("BK" + tgl + cno);
                 } else if (i < 3 - pjg_cno) {
                     cno = "000" + cno;
                     txt_kode_transaksi.setText("BK" + tgl + cno);
                 } else if (i < 4 - pjg_cno) {
                     cno = "00" + cno;
                     txt_kode_transaksi.setText("BK" + tgl + cno);
                 } else if (i < 5 - pjg_cno) {
                     cno = "0" + cno;
                     txt_kode_transaksi.setText("BK" + tgl + cno);
                 } else if (i < 6 - pjg_cno) {
                     cno = "" + cno;
                 } 
                 txt_kode_transaksi.setText("BK" + tgl + cno);
             }
        } catch (Exception DbException) {
             System.err.println("error = " + DbException);    
        }
     }
    private void tampil_customer() {
         DefaultTableModel tbl = new DefaultTableModel();
         //judul kolom\\
         tbl.addColumn("kode");
         tbl.addColumn("nama");
         
         tbl_supplier.setModel(tbl);
         try {
          rs = stat.executeQuery("select * from tabel_customer order by kode_customer");
                while (rs.next()) {
                    tbl.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2)
                    });
                    tbl_supplier.setModel(tbl);
                }
                  
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
         }
     }
    private void tampil_barang() {
         DefaultTableModel tbl = new DefaultTableModel();
         //judul kolom\\
         tbl.addColumn("Kode Barang");
         tbl.addColumn("Nama Barang");
         tbl.addColumn("Jumlah Barang");
         
         tbl_barang.setModel(tbl);
          try {
               rs = stat.executeQuery("select * from tabel_barang order by kode_barang");
                while (rs.next()) {
                    tbl.addRow(new Object[]{
                      rs.getString("kode_barang"),
                      rs.getString("nama_barang"),
                      rs.getString("jumlah_barang")
                    });
                    tbl_barang.setModel(tbl);
                }
          } catch (Exception e) {
          }
     }
    private void bersih(){
        autonumber();
        txt_kode_customer.setText("");
        txt_nama_customer.setText("");
        txt_kode_barang.setText("");
        txt_nama_barang.setText("");
        txt_jumlah_barang.setText("");
        txt_barang_keluar.setText(""); 
        txt_total_barang.setText(""); 
    }
    private void simpan(){
          SimpleDateFormat dateFormat_1 = new
          SimpleDateFormat("yyyy-MM-dd");
          String tanggal = dateFormat_1.format
          (jd_tanggal.getDate());
          
          if (txt_kode_transaksi.getText().length() == 0
                  || txt_kode_customer.getText().length() == 0
                  || txt_nama_customer.getText().length() == 0
                  || txt_kode_barang.getText().length() == 0
                  || txt_nama_barang.getText().length() == 0
                  || txt_jumlah_barang.getText().length() == 0) {
              JOptionPane.showMessageDialog(rootPane, "isi data dengan lengkap !","dialog peringatan",JOptionPane.WARNING_MESSAGE);
          }
          else{
              try{
                  stat.executeUpdate("insert into tabel_barang_keluar values("
                          + "" + "'" + txt_kode_transaksi.getText() + "',"
                          + "" + "'" + tanggal + "',"
                          + "" + "'" + txt_kode_customer.getText() + "',"
                          + "" + "'" + txt_nama_customer.getText() + "',"
                          + "" + "'" + txt_kode_barang.getText() + "',"
                          + "" + "'" + txt_nama_barang.getText() + "',"
                          + "" + "'" + txt_jumlah_barang.getText() + "',"
                          + "" + "'" + txt_barang_keluar.getText() + "',"
                          + "" + "'" + txt_total_barang.getText() + "')");
    
              } catch (Exception e ) {
                  JOptionPane.showMessageDialog(null, "keterangan eror: " + e);
              }
              try {
                  stat.executeUpdate("update tabel_barang set "
                    +"jumlah_barang='" + txt_total_barang.getText() + "'"
                    + "where "
                    +"kode_barang='" + txt_kode_barang.getText() + "'");
                  JOptionPane.showMessageDialog(null, "data berhasil disimpann");
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "keterangan eror :" +e);
              }
          }
          tampil_barang_keluar();
          tampil_barang();
    }
    private void tampil_barang_keluar(){
          DefaultTableModel tbl = new DefaultTableModel();
          //judul kolom
          tbl.addColumn("Kode");
          tbl.addColumn("Tanggal");
          tbl.addColumn("Kode Customer");
          tbl.addColumn("Nama Customer");
          tbl.addColumn("kode Barang");
          tbl.addColumn("Nama Barang");
          tbl.addColumn("Jumlah Barang");
          tbl.addColumn("Barang Keluar");
          tbl.addColumn("Total Barang");
          
          tbl_barang_keluar.setModel(tbl);
          try {
              rs=stat.executeQuery("select* from tabel_barang_keluar"
              + " order by kode_transaksi");
                while (rs.next()) {
                    tbl.addRow(new Object[] {
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
              JOptionPane.showMessageDialog(null, e);
          }
      }
     public static Date getTanggalFromTable(JTable table, int kolom){
              JTable tabel = table;
              String str_tgl = String.valueOf(table.getValueAt(tabel.getSelectedRow(), kolom));
              Date Tanggal = null;
              try {
                  Tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(str_tgl);
              } catch (ParseException ex) {
                  Logger.getLogger(JTable.class.getName())
                          .log(Level.SEVERE, null, ex);
              }
              return Tanggal;
          }
      private void ubah () {
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = dateFormat_1.format(jd_tanggal.getDate());
        try {
            stat.executeUpdate("update tabel_barang_keluar set "
                + "tanggal_transaksi='" + tanggal + "',"
                + "kode_customer='" + txt_kode_customer.getText() + "',"
                + "nama_customer='" + txt_nama_customer.getText() + "',"
                + "kode_barang='" + txt_kode_barang.getText() + "',"
                + "nama_barang='" + txt_nama_barang.getText() + "',"
                + "jumlah_barang='" + txt_jumlah_barang.getText() + "',"
                + "barang_keluar='" + txt_barang_keluar.getText() + "',"
                + "total_barang='" + txt_total_barang.getText() + "'"
                + "where "
                + "kode_transaksi='" + txt_kode_transaksi.getText() + "'"
            );
            
            JOptionPane.showMessageDialog (null,"Data Berhasil di Ubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan Error :" + e);
        }
        autonumber();
        tampil_barang_keluar();
        tampil_barang();
        bersih();
    }
      private void hapus () {
        if (JOptionPane.showConfirmDialog(this, 
                "Apakah Anda Yakin Ingin Menghapus Data ini ?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION) {
        try {
            stat.executeUpdate ("DELETE FROM tabel_barang_keluar where "
                    + "kode_transaksi='" + txt_kode_transaksi.getText() + "'");
            JOptionPane.showMessageDialog(null, "Data User Berhasil Di Hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan Error :" + e);
        }
    }
        autonumber();
        tampil_barang_keluar();
        bersih();
              }
      private void CariData () {
        if (cb_cari.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(rootPane, "Jenis Pencarian Belum Dipilih !");
            
        }
        else {
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
    }
      private void ComboBox() {
        if (cb_cari.getSelectedIndex() == 1) {
            sql = "select * from tabel_barang_keluar "
                    + "where kode_transaksi like '%"
                    + txt_cari.getText() + "%' ORDER BY kode_transaksi";
        }
            else if (cb_cari.getSelectedIndex() == 2) {
            sql = "select * from tabel_barang_keluar "
                    + "where nama_customer like '%"
                    + txt_cari.getText() + "%' ORDER BY kode_transaksi";
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

        Dialog_customer = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_supplier = new javax.swing.JTable();
        Dialog_barang = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btn_bersih = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        txt_kode_transaksi = new javax.swing.JTextField();
        txt_kode_customer = new javax.swing.JTextField();
        txt_nama_customer = new javax.swing.JTextField();
        txt_kode_barang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nama_barang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_jumlah_barang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_barang_keluar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_total_barang = new javax.swing.JTextField();
        cb_cari = new javax.swing.JComboBox();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang_keluar = new javax.swing.JTable();
        btn_kode_barang = new javax.swing.JButton();
        btn_cari_customer = new javax.swing.JButton();
        jd_tanggal = new com.toedter.calendar.JDateChooser();

        jPanel3.setBackground(new java.awt.Color(0, 255, 204));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Customer");

        tbl_supplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_supplierMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_supplier);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Dialog_customerLayout = new javax.swing.GroupLayout(Dialog_customer.getContentPane());
        Dialog_customer.getContentPane().setLayout(Dialog_customerLayout);
        Dialog_customerLayout.setHorizontalGroup(
            Dialog_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Dialog_customerLayout.setVerticalGroup(
            Dialog_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dialog_customerLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 255, 204));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Barang");

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_barang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Dialog_barangLayout = new javax.swing.GroupLayout(Dialog_barang.getContentPane());
        Dialog_barang.getContentPane().setLayout(Dialog_barangLayout);
        Dialog_barangLayout.setHorizontalGroup(
            Dialog_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Dialog_barangLayout.setVerticalGroup(
            Dialog_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dialog_barangLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(704, 356));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel1.setText("Form Input Barang Keluar");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("No Transaksi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tanggal");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Kode Customer");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Nama Customer");

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        txt_kode_transaksi.setEnabled(false);
        txt_kode_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kode_transaksiActionPerformed(evt);
            }
        });

        txt_kode_customer.setEnabled(false);

        txt_nama_customer.setToolTipText("");
        txt_nama_customer.setEnabled(false);

        txt_kode_barang.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Kode Barang");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Nama Barang");

        txt_nama_barang.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Jumlah Barang");

        txt_jumlah_barang.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Barang Keluar");

        txt_barang_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_barang_keluarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Total Barang");

        txt_total_barang.setEnabled(false);

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cari Berdasarkan", "Kode Transaksi", "Nomor Supplier" }));
        cb_cari.setToolTipText("");
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
        tbl_barang_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barang_keluarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_barang_keluarMousePressed(evt);
            }
        });
        tbl_barang_keluar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tbl_barang_keluarAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tbl_barang_keluar);

        btn_kode_barang.setText("Cari");
        btn_kode_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kode_barangActionPerformed(evt);
            }
        });

        btn_cari_customer.setText("Cari");
        btn_cari_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cari_customerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_cari)))
                        .addContainerGap(58, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_nama_customer))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jButton1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_bersih)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_ubah)
                                            .addGap(26, 26, 26)
                                            .addComponent(btn_delete))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                                .addComponent(txt_kode_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_cari_customer))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel3))
                                                .addGap(37, 37, 37)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_kode_transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                                    .addComponent(jd_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txt_kode_barang, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_kode_barang))
                                            .addComponent(txt_nama_barang)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8))
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_jumlah_barang)
                                            .addComponent(txt_total_barang)
                                            .addComponent(txt_barang_keluar))))))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_kode_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jd_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_kode_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cari_customer))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_nama_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_kode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_kode_barang))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_jumlah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_barang_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btn_bersih)
                    .addComponent(btn_ubah)
                    .addComponent(btn_delete)
                    .addComponent(jLabel10)
                    .addComponent(txt_total_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_bersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bersihActionPerformed
        bersih ();       // TODO add your handling code here:
    }//GEN-LAST:event_btn_bersihActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        ubah();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        hapus();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_kode_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kode_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kode_transaksiActionPerformed

    private void txt_barang_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_barang_keluarActionPerformed
        String jumlah_barang = txt_jumlah_barang.getText();
        String barang_keluar = txt_barang_keluar.getText();

        int jml_brg = Integer.parseInt(jumlah_barang);
        int brg_msk = Integer.parseInt(barang_keluar);
        int jml_ttl = jml_brg - brg_msk;
        txt_total_barang.setText(Integer.toString(jml_ttl));    // TODO add your handling code here:
    }//GEN-LAST:event_txt_barang_keluarActionPerformed

    private void cb_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_cariActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        ComboBox();
        CariData();// TODO add your handling code here:
    }//GEN-LAST:event_btn_cariActionPerformed

    private void tbl_barang_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barang_keluarMouseClicked
        try {
            int row = tbl_barang_keluar.rowAtPoint(evt.getPoint());

            String kode_barang_keluar = tbl_barang_keluar.getValueAt(row, 0).toString();
            String kode_supplier = tbl_barang_keluar.getValueAt(row, 2).toString();
            String nama_supplier = tbl_barang_keluar.getValueAt(row, 3).toString();
            String kode_barang = tbl_barang_keluar.getValueAt(row, 4).toString();
            String nama_barang = tbl_barang_keluar.getValueAt(row, 5).toString();
            String jumlah_barang = tbl_barang_keluar.getValueAt(row, 6).toString();
            String barang_keluar = tbl_barang_keluar.getValueAt(row, 7).toString();
            String total_barang = tbl_barang_keluar.getValueAt(row, 8).toString();

            txt_kode_transaksi.setText(kode_barang_keluar);
            txt_kode_customer.setText(kode_supplier);
            txt_nama_customer.setText(nama_supplier);
            txt_kode_barang.setText(kode_barang);
            txt_nama_barang.setText(nama_barang);
            txt_jumlah_barang.setText(jumlah_barang);
            txt_barang_keluar.setText(barang_keluar);
            txt_total_barang.setText(total_barang);
        } catch (Exception e) {
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_barang_keluarMouseClicked

    private void tbl_barang_keluarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barang_keluarMousePressed
        if (evt.getClickCount() == 1){
            jd_tanggal.setDate(getTanggalFromTable(tbl_barang_keluar, 1));
        }// TODO add your handling code here:
    }//GEN-LAST:event_tbl_barang_keluarMousePressed

    private void tbl_barang_keluarAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tbl_barang_keluarAncestorAdded
        tampil_barang_keluar();        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_barang_keluarAncestorAdded

    private void btn_kode_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kode_barangActionPerformed
        // TODO add your handling code here:
        Dialog_barang.setVisible(true);
        Dialog_barang.setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_kode_barangActionPerformed

    private void btn_cari_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cari_customerActionPerformed
        // TODO add your handling code here:
        Dialog_customer.setVisible(true);
        Dialog_customer.setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_cari_customerActionPerformed

    private void tbl_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_supplierMouseClicked
        try {
            int row = tbl_supplier.rowAtPoint(evt.getPoint());

            String kode_supplier = tbl_supplier.getValueAt(row, 0).toString();
            String nama_supplier = tbl_supplier.getValueAt(row, 1).toString();

            txt_kode_customer.setText(String.valueOf(kode_supplier));
            txt_nama_customer.setText(String.valueOf(nama_supplier));
        } catch (Exception e) {
        }Dialog_customer.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_supplierMouseClicked

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        try {
            int row = tbl_barang.rowAtPoint(evt.getPoint());

            String kode_barang = tbl_barang.getValueAt(row, 0).toString();
            String nama_barang = tbl_barang.getValueAt(row, 1).toString();
            String jumlah_barang = tbl_barang.getValueAt(row, 2).toString();

            txt_kode_barang.setText(String.valueOf(kode_barang));
            txt_nama_barang.setText(String.valueOf(nama_barang));
            txt_jumlah_barang.setText(String.valueOf(jumlah_barang));
        } catch (Exception e) {
        }Dialog_barang.setVisible(false);    // TODO add your handling code here:
    }//GEN-LAST:event_tbl_barangMouseClicked

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
            java.util.logging.Logger.getLogger(form_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_barang_keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_barang_keluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Dialog_barang;
    private javax.swing.JDialog Dialog_customer;
    private javax.swing.JButton btn_bersih;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cari_customer;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_kode_barang;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JDateChooser jd_tanggal;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTable tbl_barang_keluar;
    private javax.swing.JTable tbl_supplier;
    private javax.swing.JTextField txt_barang_keluar;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_jumlah_barang;
    private javax.swing.JTextField txt_kode_barang;
    private javax.swing.JTextField txt_kode_customer;
    private javax.swing.JTextField txt_kode_transaksi;
    private javax.swing.JTextField txt_nama_barang;
    private javax.swing.JTextField txt_nama_customer;
    private javax.swing.JTextField txt_total_barang;
    // End of variables declaration//GEN-END:variables
}
