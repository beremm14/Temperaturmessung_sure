/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import javax.swing.SwingWorker;

/**
 *
 * @author emil
 */
public class SingleMeasurementWorker extends SwingWorker<Double, String> {
    
    
    @Override
    protected Double doInBackground() throws Exception {
        return 22.5;
    }
    
    
}
