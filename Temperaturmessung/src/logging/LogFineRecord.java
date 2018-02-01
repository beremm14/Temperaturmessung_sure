package logging;

import java.util.logging.Level;

/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogFineRecord extends ExtendedLogRecord
{
  public LogFineRecord (String msg)
  {
    super(Level.FINE, msg, 2);
  }
  
  public LogFineRecord (String msg, Throwable th)
  {
    super(Level.FINE, msg, 2);
    setThrown(th);
  }
  
  LogFineRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.FINE, msg, locationStackTraceIndex);
  }

  LogFineRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.FINE, msg, locationStackTraceIndex);
    setThrown(th);
  }

  LogFineRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINE, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogFineRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINE, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }  
}
