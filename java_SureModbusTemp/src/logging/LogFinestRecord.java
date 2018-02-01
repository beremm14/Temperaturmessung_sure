package logging;

import java.util.logging.Level;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogFinestRecord extends ExtendedLogRecord
{
  public LogFinestRecord (String msg)
  {
    super(Level.FINEST, msg, 2);
  }
  
  public LogFinestRecord (String msg, Throwable th)
  {
    super(Level.FINEST, msg, 2);
    setThrown(th);
  }
  
  LogFinestRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.FINEST, msg, locationStackTraceIndex);
  }
  
  LogFinestRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.FINEST, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogFinestRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINEST, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogFinestRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINEST, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }  
  
}
