package logging;

import java.util.logging.Level;

/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogFinerRecord extends ExtendedLogRecord
{
  public LogFinerRecord (String msg)
  {
    super(Level.FINER, msg, 2);
  }
  
  public LogFinerRecord (String msg, Throwable th)
  {
    super(Level.FINER, msg, 2);
    setThrown(th);
  }

  LogFinerRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.FINER, msg, locationStackTraceIndex);
  }
  
  LogFinerRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.FINER, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogFinerRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINER, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogFinerRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.FINER, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }  

}
