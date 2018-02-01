package logging;

import java.util.logging.Level;

/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogWarningRecord extends ExtendedLogRecord
{
  public LogWarningRecord (String msg)
  {
    super(Level.WARNING, msg, 2);
  }
  
  public LogWarningRecord (String msg, Throwable th)
  {
    super(Level.WARNING, msg, 2);
    setThrown(th);
  }

  LogWarningRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.WARNING, msg, locationStackTraceIndex);
  }
  
  LogWarningRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.WARNING, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogWarningRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.WARNING, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogWarningRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.WARNING, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }  
}
