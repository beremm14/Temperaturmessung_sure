package logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public abstract class ExtendedLogRecord extends LogRecord
{
  protected final StackTraceElement location;
  protected final StackTraceElement [] stackTrace;

  public ExtendedLogRecord (Level level, String msg, int locationStackTraceIndex)
  {
    this(level, msg, locationStackTraceIndex+1, 0);
  }
  
  
  public ExtendedLogRecord (Level level, String msg, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(level, msg);
    Throwable th = new Throwable();

//    for (StackTraceElement e : th.getStackTrace())
//      System.out.println(String.format("at %s.%s(%s:%d)",
//                         e.getClassName(), e.getMethodName(),
//                         e.getFileName(), e.getLineNumber()));
    
    location = th.getStackTrace()[locationStackTraceIndex];
        if (locationStackTraceDepth !=0)
    {
      stackTrace = new StackTraceElement[locationStackTraceDepth>0 ? Math.min(locationStackTraceDepth, th.getStackTrace().length-locationStackTraceIndex-1) : th.getStackTrace().length-locationStackTraceIndex-1];
      for (int i=0; i<stackTrace.length; i++)
        stackTrace[i] = th.getStackTrace()[locationStackTraceIndex+i+1];
    }
    else
      stackTrace = null;
  }
    
  
  public ExtendedLogRecord (Level level, String msg, Throwable th, int locationStackTraceIndex)
  {
    this(level, msg, th, locationStackTraceIndex+1, 0);
  }
      
  
  public ExtendedLogRecord (Level level, String msg, Throwable th, int locationStackTraceIndex, int locationStackTraceDepth)
  {
    super(level, msg);
    setThrown(th);
    location = th.getStackTrace()[locationStackTraceIndex];
    if (locationStackTraceDepth != 0)
    {
      stackTrace = new StackTraceElement[locationStackTraceDepth>0 ? locationStackTraceDepth : th.getStackTrace().length-locationStackTraceIndex-1];
      for (int i=0; i<stackTrace.length; i++)
        stackTrace[i] = th.getStackTrace()[locationStackTraceIndex+i+1];
    }
    else
      stackTrace = null;
  }

  public StackTraceElement getLocation ()
  {
    return location;
  }

  public StackTraceElement getStackTraceElement (int index)
  {
    return index>=0 && index<stackTrace.length ? stackTrace[index] : null;
  }
  
  public StackTraceElement [] getStackTraceElements ()
  {
    return stackTrace;
  }
  
  
  public String getLocationAsString ()
  {
    return String.format("at %s.%s(%s:%d)",
                         location.getClassName(), location.getMethodName(),
                         location.getFileName(), location.getLineNumber());
  }


  public String getStackTraceElementAsString (int index)
  {
    StackTraceElement e = stackTrace[index];
    return String.format("at %s.%s(%s:%d)",
                         e.getClassName(), e.getMethodName(),
                         e.getFileName(), e.getLineNumber());
  }
    
  public boolean isCallStackEmpty ()
  {
    return stackTrace==null || stackTrace.length==0;
  }
}
