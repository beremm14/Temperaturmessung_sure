/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;
import jssc.SerialPort;

/**
 *
 * @author emil
 */
public class SingleMeasurementWorker extends SwingWorker<Double, String> {
    
    private final SerialPort serialPort;

    public SingleMeasurementWorker(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
       
    
    @Override
    protected Double doInBackground() throws Exception {
        
        publish("Einzelmessung gestartet");
        
        //Read LM75: 02 04 00 30 00 01 31 f6 (Modbus-Konfiguration aus README)
        byte [] frame = {0x02, 0x04, 0x00, 0x30, 0x00, 0x01, 0x31, (0xf6-256)};
        //Weil "writeBytes" nur von -128 bis +127 geht.
        
        if (serialPort.writeBytes(frame) == false) {
            throw new Exception("cannot send frame");
        }
                
        TimeUnit.SECONDS.sleep(2);
        byte [] response = serialPort.readBytes();
        System.out.println(response.length);
        byte hb = response[3];
        byte lb = response[4];
        int t = (hb < 0 ? hb+256 : hb) * 256 + (lb < 0 ? lb+256 : lb);
        
        return t/256.0;
    }
    
}
