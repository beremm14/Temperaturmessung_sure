package logging;

import java.util.logging.Level;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogDebugRecord extends ExtendedLogRecord
{
  LogDebugRecord (Level level, String msg)
  {
    super(level, msg, 2);
  }
  
  LogDebugRecord (Level level, String msg, Throwable th)
  {
    super(level, msg, 2);
    setThrown(th);
  }

  LogDebugRecord (Level level, String msg, int locationStackTraceIndex)
  {
    super(level, msg, locationStackTraceIndex);
  }
  
  LogDebugRecord (Level level, String msg, Throwable th, int locationStackTraceIndex)
  {
    super(level, msg, locationStackTraceIndex);
    setThrown(th);
  }
  
  LogDebugRecord (Level level, String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(level, msg, locationStackTraceIndex, locationStackTraceDepth);
  }

  LogDebugRecord (Level level, String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(level, msg, locationStackTraceIndex, locationStackTraceDepth);
    setThrown(th);
  }    
}
