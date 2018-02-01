package logging;

import java.util.logging.Level;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogInfoRecord extends ExtendedLogRecord
{
  LogInfoRecord (String msg)
  {
    super(Level.INFO, msg, 2);
  }
  
  LogInfoRecord (String msg, Throwable th)
  {
    super(Level.INFO, msg, 2);
    setThrown(th);
  }

  LogInfoRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.INFO, msg, locationStackTraceIndex);
  }
  
  LogInfoRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.INFO, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogInfoRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.INFO, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogInfoRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.INFO, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }    
}
