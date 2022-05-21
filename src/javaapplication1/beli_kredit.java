/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;
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
public class beli_kredit extends javax.swing.JFrame {
    Connection con;
    Statement stat;
    ResultSet rs;
    String sql;
    /**
     * Creates new form Cash
     */
    public beli_kredit() {
        initComponents();
        koneksi DB = new koneksi();
        DB.config();
        con = DB.con;
        stat = DB.stm;
        bersih();
        tampil();
        autonumber();
        tampil_customer();
        tampil_motor();
        ktp.setMinimumSize(new Dimension(352, 478));
        ktp.setMaximumSize(new Dimension(325, 478));
        motor.setMinimumSize(new Dimension(325, 478));
        motor.setMaximumSize(new Dimension(325, 478));
    }
    private void autonumber(){
        try {
            String sql = "select right(kridit_kode,2)"
                    + "as no_terakhir from "
                    + "beli_kridit";
            rs = stat.executeQuery(sql);
            if (rs.first() == false){
                txt_kode.setText("CA0001");
            }else
            rs.last();
            int no = rs.getInt(1) +1;
            String cno = String.valueOf(no);
            int pjg_cno = cno.length();
            int i = 0;
            if (i < 2 - pjg_cno) {
                cno = "000" + cno;
                txt_kode.setText("CA" + cno);
            }else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                txt_kode.setText("CA" + cno);
            }else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                txt_kode.setText("CA" + cno);
            }else if (i < 5 - pjg_cno){
                cno = "" + cno;
                txt_kode.setText("CA" + cno);
            }
            txt_kode.setText("CA" + cno);
        } catch (Exception DBException) {
            System.err.println("error =" + DBException);
        }
    }
    public void bersih() {
        txt_kode.setText("");
        txt_cash.setText("");
        txt_dp.setText("");
        txt_cicilan.setText("");
        jLabel14.setText("");
        txt_ktp.setText("");
        txt_motor.setText("");
        autonumber();
    }
    
    private void tampil(){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("Kredit Kode");
        tabel.addColumn("No KTP");
        tabel.addColumn("Motor Kode");
        tabel.addColumn("Cash");
        tabel.addColumn("DP");
        tabel.addColumn("Jumlah Cicilan");
        tabel.addColumn("Nilai Cicilan");
        tabel.addColumn("Tanggal");         
        tbl_cash.setModel(tabel);
        try {
            rs=stat.executeQuery("select * from beli_kridit order by kridit_kode");
            while (rs.next())
                tabel.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8)
               
            });
                    tbl_cash.setModel(tabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void tampil_customer() {
         DefaultTableModel tbl = new DefaultTableModel();
         //judul kolom\\
         tbl.addColumn("No KTP");
         tbl.addColumn("Nama");
         tbl.addColumn("Alamat");
         tbl.addColumn("Telepon");
         
         tbl_pelanggan.setModel(tbl);
         try {
          rs = stat.executeQuery("select * from pembeli order by pembeli_no_ktp");
                while (rs.next()) {
                    tbl.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                    });
                    tbl_pelanggan.setModel(tbl);
                }
                  
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
         }
     }
    private void tampil_motor() {
         DefaultTableModel tbl = new DefaultTableModel();
         //judul kolom\\
         tbl.addColumn("Kode Motor");
         tbl.addColumn("Merk Motor");
         tbl.addColumn("Type Motor");
         tbl.addColumn("Warna Motor");
         tbl.addColumn("Harga Motor");
         
         tbl_motor.setModel(tbl);
          try {
               rs = stat.executeQuery("select * from tbl_motor order by motor_kode");
                while (rs.next()) {
                    tbl.addRow(new Object[]{
                      rs.getString("motor_kode"),
                      rs.getString("motor_merk"),
                      rs.getString("motor_type"),
                      rs.getString("motor_warna_pilihan"),
                      rs.getString("motor_harga")
                    });
                    tbl_motor.setModel(tbl);
                }
          } catch (Exception e) {
          }
    }
    public void simpan(){
        SimpleDateFormat dateFormat_1 = new
          SimpleDateFormat("yyyy-mm-dd");
          String tanggal = dateFormat_1.format
          (jd_tanggal.getDate());
        if (txt_kode.getText().length() == 0
                || txt_ktp.getText().length() == 0
                || txt_motor.getText().length() == 0
                || txt_cash.getText().length() == 0
                || txt_dp.getText().length() == 0
                || jLabel14.getText().length() == 0
                || txt_cicilan.getText().length() == 0) {
        JOptionPane.showMessageDialog(rootPane, "isi data dengan benar");
    }
        else{
    }        
        try {
            stat.executeUpdate("insert into beli_kridit values("
                +""+""+"'"+txt_kode.getText()+"',"
                +""+""+"'"+txt_ktp.getText()+"',"
                +""+""+"'"+txt_motor.getText()+"',"
                +""+""+"'"+txt_cash.getText()+"',"
                +""+""+"'"+txt_dp.getText()+"',"
                +""+""+"'"+jLabel14.getText()+"',"
                +""+""+"'"+txt_cicilan.getText()+"',"
                +""+""+"'"+ tanggal+ "')");
            JOptionPane.showMessageDialog (null,"Data Berhasil di Simpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            tampil();
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        ktp = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_pelanggan = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        motor = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_motor = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_kode = new javax.swing.JTextField();
        txt_dp = new javax.swing.JTextField();
        txt_cicilan = new javax.swing.JTextField();
        jd_tanggal = new com.toedter.calendar.JDateChooser();
        checkbox1 = new java.awt.Checkbox();
        checkbox2 = new java.awt.Checkbox();
        checkbox3 = new java.awt.Checkbox();
        jLabel11 = new javax.swing.JLabel();
        txt_cash = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_cash = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txt_motor = new javax.swing.JTextField();
        txt_ktp = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        satu = new javax.swing.JRadioButton();
        dua = new javax.swing.JRadioButton();
        tiga = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();

        tbl_pelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pelangganMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_pelanggan);

        jLabel12.setFont(new java.awt.Font("Chiller", 0, 30)); // NOI18N
        jLabel12.setText("Pelanggan");

        javax.swing.GroupLayout ktpLayout = new javax.swing.GroupLayout(ktp.getContentPane());
        ktp.getContentPane().setLayout(ktpLayout);
        ktpLayout.setHorizontalGroup(
            ktpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ktpLayout.createSequentialGroup()
                .addGroup(ktpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ktpLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ktpLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ktpLayout.setVerticalGroup(
            ktpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ktpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbl_motor.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_motor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_motorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_motor);

        jLabel13.setFont(new java.awt.Font("Chiller", 0, 30)); // NOI18N
        jLabel13.setText("Motor");

        javax.swing.GroupLayout motorLayout = new javax.swing.GroupLayout(motor.getContentPane());
        motor.getContentPane().setLayout(motorLayout);
        motorLayout.setHorizontalGroup(
            motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(motorLayout.createSequentialGroup()
                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(motorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(motorLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        motorLayout.setVerticalGroup(
            motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, motorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 480));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("Kridit Kode");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("No KTP");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Motor Kode");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("DP");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Jumlah Cicilan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Nilai Cicilan");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Kridit Tanggal");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Fotocopy KK");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("Fotocopy KTP");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Fotocopy Slip Gaji");

        txt_kode.setEnabled(false);

        txt_dp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dpActionPerformed(evt);
            }
        });
        txt_dp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dpKeyReleased(evt);
            }
        });

        txt_cicilan.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Cash");

        txt_cash.setEnabled(false);

        tbl_cash.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_cash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cashMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_cashMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_cash);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txt_motor.setEnabled(false);

        txt_ktp.setEnabled(false);

        jButton5.setText("Cari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Cari");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        buttonGroup1.add(satu);
        satu.setText("12");

        buttonGroup1.add(dua);
        dua.setText("24");

        buttonGroup1.add(tiga);
        tiga.setText("36");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cash)
                            .addComponent(txt_cicilan, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(txt_kode)
                            .addComponent(txt_dp, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(jd_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkbox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkbox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkbox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_motor)
                                    .addComponent(txt_ktp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(satu)
                                .addGap(18, 18, 18)
                                .addComponent(dua)
                                .addGap(18, 18, 18)
                                .addComponent(tiga)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_ktp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_dp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(satu, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(dua, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tiga)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(txt_cicilan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jd_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8))
                                    .addComponent(checkbox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addComponent(checkbox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        simpan();  
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ktp.setVisible(true);
        ktp.setLocationRelativeTo(this);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    motor.setVisible(true);
    motor.setLocationRelativeTo(this);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tbl_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pelangganMouseClicked
    try {
            int row = tbl_pelanggan.rowAtPoint(evt.getPoint());

            String ktp = tbl_pelanggan.getValueAt(row, 0).toString();

            txt_ktp.setText(String.valueOf(ktp));
        } catch (Exception e) {
        }ktp.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_pelangganMouseClicked

    private void tbl_motorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_motorMouseClicked
    try {
            int row = tbl_motor.rowAtPoint(evt.getPoint());

            String motor = tbl_motor.getValueAt(row, 0).toString();
            String harga = tbl_motor.getValueAt(row, 4).toString();

            txt_motor.setText(String.valueOf(motor));
            txt_cash.setText(String.valueOf(harga));
        } catch (Exception e) {
        }motor.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_motorMouseClicked

    private void txt_dpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpKeyReleased
              
              // TODO add your handling code here:
    }//GEN-LAST:event_txt_dpKeyReleased

    private void txt_dpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dpActionPerformed
              if(satu.isSelected()==true){
                jLabel14.setText("12");
                }
              else if(dua.isSelected()==true){
                jLabel14.setText("24");
                } 
              else {
                jLabel14.setText("36");
                }
            String jumlah_barang = txt_cash.getText();
            String barang_keluar = txt_dp.getText();
            String jumcil = jLabel14.getText();

            int jml_brg = Integer.parseInt(jumlah_barang);
            int brg_msk = Integer.parseInt(barang_keluar);
            int cil = Integer.parseInt(jumcil);
            int jml_ttl = (jml_brg - brg_msk) / cil;
            txt_cicilan.setText(Integer.toString(jml_ttl));// TODO add your handling code here:
    }//GEN-LAST:event_txt_dpActionPerformed

    private void tbl_cashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cashMouseClicked
    try {
            int row = tbl_cash.rowAtPoint(evt.getPoint());

            String kode_kridit = tbl_cash.getValueAt(row, 0).toString();
            String ktp = tbl_cash.getValueAt(row, 1).toString();
            String motor = tbl_cash.getValueAt(row, 2).toString();
            String cash = tbl_cash.getValueAt(row, 3).toString();
            String dp = tbl_cash.getValueAt(row, 4).toString();
            String jumcil = tbl_cash.getValueAt(row, 5).toString();
            String cicilan = tbl_cash.getValueAt(row, 6).toString();

            txt_kode.setText(kode_kridit);
            txt_ktp.setText(ktp);
            txt_motor.setText(motor);
            txt_cash.setText(cash);
            txt_dp.setText(dp);
            jLabel14.setText(jumcil);
            txt_cicilan.setText(cicilan);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_cashMouseClicked

    private void tbl_cashMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cashMousePressed
    if (evt.getClickCount() == 1){
            jd_tanggal.setDate(getTanggalFromTable(tbl_cash, 7));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_cashMousePressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
if (JOptionPane.showConfirmDialog(this, 
                "Apakah Anda Yakin Ingin Menghapus Data ini ?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION) {
        try {
            stat.executeUpdate ("DELETE FROM beli_kridit where "
                    + "kridit_kode='" + txt_kode.getText() + "'");
            JOptionPane.showMessageDialog(null, "Data User Berhasil Di Hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan Error :" + e);
        }
    }
        autonumber();
        tampil();
        bersih();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
        String tanggal = dateFormat_1.format(jd_tanggal.getDate());
        try {
            stat.executeUpdate("update beli_kridit set "
                + "pembeli_no_ktp='" + txt_ktp.getText() + "',"
                + "motor_kode='" + txt_motor.getText() + "',"
                + "cash='" + txt_cash.getText() + "',"
                + "dp='" + txt_dp.getText() + "',"
                + "jumcil='" + jLabel14.getText() + "',"
                + "nilcil='" + txt_cicilan.getText() + "',"
                + "kridit_tanggal='" + tanggal + "'"
                + "where "
                + "kridit_kode='" + txt_kode.getText() + "'"
            );
            
            JOptionPane.showMessageDialog (null,"Data Berhasil di Ubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan Error :" + e);
        }
        autonumber();
        tampil();
        bersih();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
bersih();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(beli_kredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(beli_kredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(beli_kredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(beli_kredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new beli_kredit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private java.awt.Checkbox checkbox1;
    private java.awt.Checkbox checkbox2;
    private java.awt.Checkbox checkbox3;
    private javax.swing.JRadioButton dua;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JDateChooser jd_tanggal;
    private javax.swing.JDialog ktp;
    private javax.swing.JDialog motor;
    private javax.swing.JRadioButton satu;
    private javax.swing.JTable tbl_cash;
    private javax.swing.JTable tbl_motor;
    private javax.swing.JTable tbl_pelanggan;
    private javax.swing.JRadioButton tiga;
    private javax.swing.JTextField txt_cash;
    private javax.swing.JTextField txt_cicilan;
    private javax.swing.JTextField txt_dp;
    private javax.swing.JTextField txt_kode;
    private javax.swing.JTextField txt_ktp;
    private javax.swing.JTextField txt_motor;
    // End of variables declaration//GEN-END:variables
}
