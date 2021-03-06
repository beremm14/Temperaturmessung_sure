package gui;

import java.awt.Cursor;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import jssc.SerialPort;
import jssc.SerialPortException;
import logging.Logger;
import workers.SingleMeasurementWorker;

/**
 *
 * @author
 */
public class SureModbusGui extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(SureModbusGui.class.getName());

    private jssc.SerialPort serialPort;  //Ob ein Port geöffnet wurde
    private SwingWorker activeWorker;

    public SureModbusGui() {
        initComponents();
        setLocationRelativeTo(null);
        updateSwingControls();
        jlaTemperatur.setText("--");
        refrehPorts();
    }

    private void showThrowable(Throwable th) {
        th.printStackTrace(System.err);
        String msg = th.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = th.getClass().getSimpleName();
        }
        JOptionPane.showMessageDialog(this,
                msg,
                "Fehler aufgetreten...",
                JOptionPane.ERROR_MESSAGE);
        jtfStatus.setText("Fehler aufgetreten");
    }

    private void refrehPorts() {
        final String[] ports = jssc.SerialPortList.getPortNames();

        String preferedPort = null;  //Suchen nach USB
        for (String p : ports) {
            if (p.contains("USB")) {
                preferedPort = p;
                break;
            }
        }

        jcbSerialDevice.setModel(new DefaultComboBoxModel<String>(ports));  //Implementiert direkt ports

        if (preferedPort != null) {
            jcbSerialDevice.setSelectedItem(preferedPort);  //Auswählen der gewünschten Schnittstelle
        }

        updateSwingControls();

    }

    private void connectPort(String port) {
        serialPort = new jssc.SerialPort(port);
        try {
            if (serialPort.openPort() == false) { //Verbinden mit dem Port
                throw new jssc.SerialPortException(port, "openPort", "return value false");
            }
            if (serialPort.setParams(SerialPort.BAUDRATE_57600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_2, SerialPort.PARITY_NONE) == false) {
                throw new jssc.SerialPortException(port, "setParams", "return value false");
            }
        } catch (Throwable ex) //Wenn man serielle Schnittstellen verwendet, werden JNI-Fehler als Errors ausgegeben, daher muss man ALLES fangen!!!
        {
            showThrowable(new Exception("Serielle Schnittstelle kann nicht geöffnet werden", ex));
            serialPort = null;
        } finally { //Egal, ob Fehler oder nicht
            updateSwingControls();
        }
    }

    private void disconnectPort() {
        if (serialPort == null || !serialPort.isOpened()) {
            showThrowable(new Exception("Interner Fehler!"));
            return;
        }

        try {
            if (serialPort.closePort() == false) {
                throw new jssc.SerialPortException(null, "closePort", "return value false");
            }
        } catch (Throwable th) {
            showThrowable(new Exception("Serielle Schnittstelle kann nicht geschlossen werden"));
        } finally {
            serialPort = null;
            updateSwingControls();
        }
    }

    private void updateSwingControls() {
        jbutRefresh.setEnabled(true);
        jbutConnect.setEnabled(false);
        jbutDisconnect.setEnabled(false);
        jcbSerialDevice.setEnabled(false);
        jbutSingleMeasurement.setEnabled(false);
        jbutContinousMeasurement.setEnabled(false);
        jbutStopMeasurement.setEnabled(false);

        if (serialPort != null && serialPort.isOpened()) {
            jbutRefresh.setEnabled(false);
            jbutDisconnect.setEnabled(true);
            jbutConnect.setEnabled(false);
            jbutSingleMeasurement.setEnabled(true);
            return;  //Damit er nicht unten durchfährt
        }

        if (jcbSerialDevice.getModel().getSize() > 0) {
            jcbSerialDevice.setEnabled(true);
            jbutConnect.setEnabled(true);
        }

        if (activeWorker != null) {
            jbutRefresh.setEnabled(false);
            jbutConnect.setEnabled(false);
            jbutDisconnect.setEnabled(false);
            jcbSerialDevice.setEnabled(false);
            jbutSingleMeasurement.setEnabled(false);
            jbutContinousMeasurement.setEnabled(false);
            jbutStopMeasurement.setEnabled(false);
            jlaTemperatur.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            jlaTemperatur.setEnabled(true);
        }
    }

    private void singleMeasurement() {
        activeWorker = new MySingleMeasurementWorker(serialPort);
        activeWorker.execute();
        updateSwingControls();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    jpanNorth = new javax.swing.JPanel();
    jpanDevice = new javax.swing.JPanel();
    jcbSerialDevice = new javax.swing.JComboBox<>();
    jpanNorthButtons = new javax.swing.JPanel();
    jpanSerialButtons = new javax.swing.JPanel();
    jbutConnect = new javax.swing.JButton();
    jbutDisconnect = new javax.swing.JButton();
    jbutRefresh = new javax.swing.JButton();
    jpanCenter = new javax.swing.JPanel();
    jlaTemperatur = new javax.swing.JLabel();
    jpanStartButtons = new javax.swing.JPanel();
    jpanStartButtonsGrid = new javax.swing.JPanel();
    jbutSingleMeasurement = new javax.swing.JButton();
    jbutContinousMeasurement = new javax.swing.JButton();
    jbutStopMeasurement = new javax.swing.JButton();
    jpanSouth = new javax.swing.JPanel();
    jtfStatus = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("SURE-Board Temperatur");

    jpanNorth.setLayout(new java.awt.BorderLayout());

    jpanDevice.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 5, 7, 1));
    jpanDevice.setLayout(new java.awt.GridLayout());

    jcbSerialDevice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "/dev/ttyUSB0" }));
    jpanDevice.add(jcbSerialDevice);

    jpanNorth.add(jpanDevice, java.awt.BorderLayout.CENTER);

    jpanSerialButtons.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

    jbutConnect.setText("Verbinden");
    jbutConnect.setMargin(new java.awt.Insets(3, 3, 3, 3));
    jbutConnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutConnectActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jbutConnect);

    jbutDisconnect.setText("Trennen");
    jbutDisconnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutDisconnectActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jbutDisconnect);

    jbutRefresh.setText("Aktualisieren");
    jbutRefresh.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutRefreshActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jbutRefresh);

    jpanNorthButtons.add(jpanSerialButtons);

    jpanNorth.add(jpanNorthButtons, java.awt.BorderLayout.EAST);

    getContentPane().add(jpanNorth, java.awt.BorderLayout.NORTH);

    jpanCenter.setBorder(javax.swing.BorderFactory.createTitledBorder("Messung"));
    jpanCenter.setLayout(new java.awt.BorderLayout());

    jlaTemperatur.setFont(new java.awt.Font("DejaVu Sans", 1, 100)); // NOI18N
    jlaTemperatur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jlaTemperatur.setText("20°C");
    jpanCenter.add(jlaTemperatur, java.awt.BorderLayout.CENTER);

    jpanStartButtonsGrid.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

    jbutSingleMeasurement.setText("Einzelmessung");
    jbutSingleMeasurement.setMargin(new java.awt.Insets(5, 5, 5, 5));
    jbutSingleMeasurement.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutSingleMeasurementActionPerformed(evt);
      }
    });
    jpanStartButtonsGrid.add(jbutSingleMeasurement);

    jbutContinousMeasurement.setText("Laufend messen");
    jbutContinousMeasurement.setMargin(new java.awt.Insets(5, 5, 5, 5));
    jbutContinousMeasurement.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutContinousMeasurementActionPerformed(evt);
      }
    });
    jpanStartButtonsGrid.add(jbutContinousMeasurement);

    jbutStopMeasurement.setText("Stop");
    jbutStopMeasurement.setMargin(new java.awt.Insets(5, 5, 5, 5));
    jbutStopMeasurement.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutStopMeasurementActionPerformed(evt);
      }
    });
    jpanStartButtonsGrid.add(jbutStopMeasurement);

    jpanStartButtons.add(jpanStartButtonsGrid);

    jpanCenter.add(jpanStartButtons, java.awt.BorderLayout.NORTH);

    getContentPane().add(jpanCenter, java.awt.BorderLayout.CENTER);

    jpanSouth.setLayout(new java.awt.GridLayout());

    jtfStatus.setEditable(false);
    jpanSouth.add(jtfStatus);

    getContentPane().add(jpanSouth, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jbutConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutConnectActionPerformed
  {//GEN-HEADEREND:event_jbutConnectActionPerformed
      connectPort((String) jcbSerialDevice.getSelectedItem());  //Typecast, da Object nur Strings enthält
  }//GEN-LAST:event_jbutConnectActionPerformed

  private void jbutDisconnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutDisconnectActionPerformed
  {//GEN-HEADEREND:event_jbutDisconnectActionPerformed
      disconnectPort();
  }//GEN-LAST:event_jbutDisconnectActionPerformed

  private void jbutRefreshActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutRefreshActionPerformed
  {//GEN-HEADEREND:event_jbutRefreshActionPerformed
      refrehPorts();
  }//GEN-LAST:event_jbutRefreshActionPerformed

  private void jbutSingleMeasurementActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutSingleMeasurementActionPerformed
  {//GEN-HEADEREND:event_jbutSingleMeasurementActionPerformed
      singleMeasurement();
  }//GEN-LAST:event_jbutSingleMeasurementActionPerformed

  private void jbutContinousMeasurementActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutContinousMeasurementActionPerformed
  {//GEN-HEADEREND:event_jbutContinousMeasurementActionPerformed
      // TODO add your handling code here:
  }//GEN-LAST:event_jbutContinousMeasurementActionPerformed

  private void jbutStopMeasurementActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutStopMeasurementActionPerformed
  {//GEN-HEADEREND:event_jbutStopMeasurementActionPerformed
      // TODO add your handling code here:
  }//GEN-LAST:event_jbutStopMeasurementActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
     * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SureModbusGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SureModbusGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SureModbusGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SureModbusGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        javax.swing.UIManager.getLookAndFeelDefaults().put(
                "ScrollBar.minimumThumbSize", new java.awt.Dimension(30, 30));

        Logger.setParentLogger(LOG);
        LOG.setUseParentHandlers(false);
        logging.LogWriterHandler logWriterHandler = new logging.LogWriterHandler(System.out, System.out);
        logWriterHandler.setLevel(java.util.logging.Level.ALL);
        LOG.addHandler(logWriterHandler);

        /*
     * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SureModbusGui().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jbutConnect;
  private javax.swing.JButton jbutContinousMeasurement;
  private javax.swing.JButton jbutDisconnect;
  private javax.swing.JButton jbutRefresh;
  private javax.swing.JButton jbutSingleMeasurement;
  private javax.swing.JButton jbutStopMeasurement;
  private javax.swing.JComboBox<String> jcbSerialDevice;
  private javax.swing.JLabel jlaTemperatur;
  private javax.swing.JPanel jpanCenter;
  private javax.swing.JPanel jpanDevice;
  private javax.swing.JPanel jpanNorth;
  private javax.swing.JPanel jpanNorthButtons;
  private javax.swing.JPanel jpanSerialButtons;
  private javax.swing.JPanel jpanSouth;
  private javax.swing.JPanel jpanStartButtons;
  private javax.swing.JPanel jpanStartButtonsGrid;
  private javax.swing.JTextField jtfStatus;
  // End of variables declaration//GEN-END:variables

    private class MySingleMeasurementWorker extends SingleMeasurementWorker {

        public MySingleMeasurementWorker(SerialPort serialPort) {
            super(serialPort);
        }

        @Override
        protected void done() {
            try {
                double temp = get();
                jlaTemperatur.setText(String.format("%.1f °C", temp));
                jtfStatus.setText(null);
            } catch (Exception e) {
                showThrowable(new Exception("Einzemessung gescheitert", e));
            } finally {
                activeWorker = null;
                updateSwingControls();
            }
        }

        @Override
        protected void process(List<String> chunks) {
            jtfStatus.setText(chunks.get(chunks.size()-1)); //String aus Publish
        }
        
    }
    
    
}
