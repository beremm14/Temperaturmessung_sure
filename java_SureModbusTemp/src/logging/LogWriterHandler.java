package logging;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class LogWriterHandler extends Handler
{
  private final PrintWriter out;
  private final PrintWriter err;
  private final PrintStream outStream;
  private final PrintStream errStream;

  public LogWriterHandler (PrintWriter out, PrintWriter err)
  {
    if (out == null || err == null)
      throw new NullPointerException();
    this.out = out;    
    this.err = err;
    this.outStream = null;
    this.errStream = null;
  }

  public LogWriterHandler (PrintStream out, PrintStream err)
  {
    if (out == null || err == null)
      throw new NullPointerException();
    this.outStream = out;
    this.errStream = err;
    this.out = new PrintWriter(out);
    this.err = new PrintWriter(err);
  }
  
  @Override
  public void publish (LogRecord record)
  {
    if (record.getLevel().intValue() < getLevel().intValue())
      return;
      
    int l1 = Level.INFO.intValue();
    int l2 = record.getLevel().intValue();

    PrintWriter pw = record.getLevel().intValue() <= Level.INFO.intValue() ? out : err;
    String s = String.format("%1$-7s%2$c%3$tT.%3$tL: ", record.getLevel().getName(), (record instanceof LogDebugRecord ? '*' : ' '), new Date(record.getMillis())); 
    pw.print(s);
    int len = s.length();

    if (record.getMessage() != null)
    {
      String msg = record.getMessage();
      for (int i=0; i<msg.length(); i++)
      {
        char c = msg.charAt(i);
        int x = (int)c;
        if (Character.isISOControl(c))
        {
          pw.print("<");
          pw.print(String.format("%02x", (int)c));
          pw.print(">");
          len +=4;
        }
        else
        {
          pw.print(c);
          len++;
        }
      }
    }
    
    Throwable th = record.getThrown();
    //if (th==null && record instanceof ExtendedLogRecord)
    if (record instanceof ExtendedLogRecord)
    {
      ExtendedLogRecord elr = (ExtendedLogRecord)record;
      StackTraceElement location = elr.getLocation();
      pw.print(" [");
      pw.print(elr.getLocationAsString());
      pw.print("]");
      if (!elr.isCallStackEmpty())
      {
        String format = String.format("%%%dd -> [%%s]", len-3);
        for (int i=0; i<elr.getStackTraceElements().length; i++)
        {
          pw.println();
          pw.print(String.format(format, i+1, elr.getStackTraceElementAsString(i)));
        }
      }
    }

    pw.println();
    if (th != null)
    {
      pw.println("--------------------------------------------------------------------------------");
      th.printStackTrace(pw);
      pw.println("================================================================================");
    }
    
    pw.flush();
  }

  @Override
  public void flush ()
  {
    out.flush();
    err.flush();
  }

  @Override
  public void close () throws SecurityException
  {
    
  }
  
  public PrintWriter getErrorWriter ()
  {
    return err;
  }
  
  public PrintWriter getOutWriter ()
  {
    return out;
  }

    public PrintStream getErrorStream ()
  {
    return errStream;
  }
  
  public PrintStream getOutStream ()
  {
    return outStream;
  }

}
