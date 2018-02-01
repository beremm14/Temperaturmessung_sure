package logging;

import java.util.logging.Level;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogConfigRecord extends ExtendedLogRecord
{
  LogConfigRecord (String msg)
  {
    super(Level.CONFIG, msg, 2);
  }
  
  LogConfigRecord (String msg, Throwable th)
  {
    super(Level.CONFIG, msg, 2);
    setThrown(th);
  }

  LogConfigRecord (String msg, int locationStackTraceIndex)
  {
    super(Level.CONFIG, msg, locationStackTraceIndex);
  }
  
  LogConfigRecord (String msg, Throwable th, int locationStackTraceIndex)
  {
    super(Level.CONFIG, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogConfigRecord (String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.CONFIG, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogConfigRecord (String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(Level.CONFIG, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }  
  
}
