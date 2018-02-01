package logging;

import java.util.logging.Level;

/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogSevereRecord extends ExtendedLogRecord
{
  public LogSevereRecord (String msg)
  {
    super(Level.SEVERE, msg, 2);
  }
  
  public LogSevereRecord (String msg, Throwable th)
  {
    super(Level.SEVERE, msg, 2);
    setThrown(th);
  }

  LogSevereRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.SEVERE, msg, locationStackTraceIndex);
  }
  
  LogSevereRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.SEVERE, msg, locationStackTraceIndex);
    setThrown(th);
  }

  LogSevereRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.SEVERE, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogSevereRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.SEVERE, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }    
}
