package logging;

import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


/**
 *
 * @author Manfred Steiner (sx@htl-kaindorf.ac.at)
 */
public class Logger 
{  
  private static final HashMap<String,Logger> loggerMap = new HashMap<>();
  private static Logger parentLogger;
  
  public static synchronized Logger getLogger (String name)
  {
    Logger logger = loggerMap.get(name);
    if (logger == null)
    {
      logger = new Logger(name);
      if (parentLogger != null)
      {
        logger.setParent(parentLogger);
        logger.setUseParentHandlers(true);
      }
      loggerMap.put(name, logger);
    }
    return logger;
  }
  
  public static synchronized Logger getLogger (String name, Logger parent)
  {
    Logger logger = loggerMap.get(name);
    if (logger == null)
    {
      logger = new Logger(name);
      logger.setParent(parent);
      loggerMap.put(name, logger);
    }
    return logger;
  }
  
  public static synchronized void setParentLogger (Logger logger)
  {
    parentLogger = logger;
    for (Logger l : loggerMap.values())
    {
      if (l != logger)
      {
        l.setParent(logger);
        l.setUseParentHandlers(true);
      }
    }
  }
  
  // *********************************************************
  
  protected final java.util.logging.Logger logger;
  protected boolean locationShown = true;
  protected Level debugLevel = Level.INFO;
  
  private  Logger (String name)
  {
    logger = java.util.logging.Logger.getLogger(name);
  }
  
  
  /**
   * Readjusting the logging level of this logger.
   * The level is adjusted to the logging level of the parent and the registered handlers.
   * If no handler is registered and no parent is active, the level is set to Level.OFF
   */
  public synchronized void updateEffectiveLevel ()
  {
    int levelValue = Level.OFF.intValue();
    
    if (logger.getUseParentHandlers() && getParent()!=null)
      levelValue = getParent().getLevel().intValue();
      
    for (Handler h : getHandlers())
      levelValue = Math.min(levelValue, h.getLevel().intValue());
    
    if (levelValue>=Level.OFF.intValue())
      setLevel(Level.OFF);
    else if (levelValue>=Level.SEVERE.intValue())
      setLevel(Level.SEVERE);
    else if (levelValue>=Level.WARNING.intValue())
      setLevel(Level.WARNING);
    else if (levelValue>=Level.INFO.intValue())
      setLevel(Level.INFO);
    else if (levelValue>=Level.CONFIG.intValue())
      setLevel(Level.CONFIG);
    else if (levelValue>=Level.FINE.intValue())
      setLevel(Level.FINE);
    else if (levelValue>=Level.FINER.intValue())
      setLevel(Level.FINER);
    else if (levelValue>=Level.FINEST.intValue())
      setLevel(Level.FINEST);
    else 
      setLevel(Level.ALL);
  }
  
  public synchronized void setLocationShown (boolean showLocation)
  {
    this.locationShown = showLocation;
  }

  public boolean isLocationShown ()
  {
    return locationShown;
  }
  
  public java.util.logging.Logger getParent ()
  {
    return logger.getParent();
  }

  public void setParent (Logger parent)
  {
    logger.setParent(parent.logger);
  }
  
  public void setUseParentHandlers (boolean useParentHandlers)
  {
    logger.setUseParentHandlers(useParentHandlers);
  }

  public boolean getUseParentHandlers ()
  {
    return logger.getUseParentHandlers();
  }
  
  public void addHandler (Handler handler)
  {
    logger.addHandler(handler);
    updateEffectiveLevel();
  }
  
  public java.util.logging.Handler [] getHandlers ()
  {
    return logger.getHandlers();
  }

  public void removeHandler (java.util.logging.Handler handler)
  {
    logger.removeHandler(handler);
  }
  
  
  public void setLevel (Level level)
  {
    logger.setLevel(level);
  }
  
  public void setLevel (String level)
  {
    switch (level)
    {
      case "ALL":     logger.setLevel(Level.ALL); break;
      case "FINEST":  logger.setLevel(Level.FINEST); break;
      case "FINER":   logger.setLevel(Level.FINER); break;
      case "FINE":    logger.setLevel(Level.FINE); break;
      case "CONFIG":  logger.setLevel(Level.CONFIG); break;
      case "INFO":    logger.setLevel(Level.INFO); break;
      case "WARNING": logger.setLevel(Level.WARNING); break;
      case "SEVERE":  logger.setLevel(Level.SEVERE); break;
      case "OFF":     logger.setLevel(Level.OFF); break;
      default:
        int l = Integer.parseInt(level);
        logger.setLevel(new SpecialLevel("Level " + l, l));
        break;
    }
  }

  public Level getLevel ()
  {
    Level rv = logger.getLevel();
    if (rv == null && logger.getParent() != null)
      rv = logger.getParent().getLevel();
    return rv;
  }

  public boolean isLoggable (Level level)
  {
    return logger.isLoggable(level);
  }
  
  public boolean isFinestLogged ()
  {
    return logger.isLoggable(Level.FINEST);
  }

  public boolean isFinerLogged ()
  {
    return logger.isLoggable(Level.FINER);
  }

  public boolean isFineLogged ()
  {
    return logger.isLoggable(Level.FINE);
  }

  public boolean isConfigLogged ()
  {
    return logger.isLoggable(Level.CONFIG);
  }

  public boolean isInfoLogged ()
  {
    return logger.isLoggable(Level.INFO);
  }

  public boolean isWarningLogged ()
  {
    return logger.isLoggable(Level.WARNING);
  }

  public boolean isSevereLogged ()
  {
    return logger.isLoggable(Level.SEVERE);
  }


  public void log (Level l, String msg, Throwable th)
  {
    if (locationShown)
    {
      if      (l.intValue()>=Level.SEVERE.intValue()) logger.log(new LogSevereRecord(msg, th, 3));
      else if (l.intValue()>=Level.WARNING.intValue()) logger.log(new LogWarningRecord(msg, th, 3));
      else if (l.intValue()>=Level.INFO.intValue()) logger.log(new LogInfoRecord(msg, th, 3));
      else if (l.intValue()>=Level.CONFIG.intValue()) logger.log(new LogConfigRecord(msg, th, 3));
      else if (l.intValue()>=Level.FINE.intValue()) logger.log(new LogFineRecord(msg, th, 3));
      else if (l.intValue()>=Level.FINER.intValue()) logger.log(new LogFinerRecord(msg, th, 3));
      else if (l.intValue()>=Level.FINEST.intValue()) logger.log(new LogFinestRecord(msg, th, 3));
    }
    else
    {
      logger.log(l, msg, th);
    }
  }
  
  
  public void log (Level l, String msg)
  {
    if (locationShown)
    {
      if      (l.intValue()>=Level.SEVERE.intValue()) logger.log(new LogSevereRecord(msg, 3));
      else if (l.intValue()>=Level.WARNING.intValue()) logger.log(new LogWarningRecord(msg, 3));
      else if (l.intValue()>=Level.INFO.intValue()) logger.log(new LogInfoRecord(msg, 3));
      else if (l.intValue()>=Level.CONFIG.intValue()) logger.log(new LogConfigRecord(msg, 3));
      else if (l.intValue()>=Level.FINE.intValue()) logger.log(new LogFineRecord(msg, 3));
      else if (l.intValue()>=Level.FINER.intValue()) logger.log(new LogFinerRecord(msg, 3));
      else if (l.intValue()>=Level.FINEST.intValue()) logger.log(new LogFinestRecord(msg, 3));
    }
    else
    {
      logger.log(l, msg);
    }
  }
    
  
  public class SpecialLevel extends Level
  {
    public SpecialLevel (String name, int level)
    {
      super(name, level);
    }
  }

  
  public void log (LogRecord record)
  {
    logger.log(record);    
  }
  

  public void finest (String msg)
  {
    if (locationShown)
      logger.log(new LogFinestRecord(msg, 3));
    else
      logger.finest(msg);
  }

  public void finest (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogFinestRecord(msg, th, 3));
    else
      logger.log(Level.FINEST, msg, th);
  }

  public void finer (String msg)
  {
    if (locationShown)
      logger.log(new LogFinerRecord(msg, 3));
    else
      logger.finer(msg);
  }

  public void finer (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogFinerRecord(msg, th, 3));
    else
      logger.log(Level.FINER, msg, th);
  }

  public void fine (String msg)
  {
    if (locationShown)
      logger.log(new LogFineRecord(msg, 3));
    else
      logger.fine(msg);
  }

  public void fine (String msg, int stackTraceDepth)
  {
    if (locationShown)
      logger.log(new LogFineRecord(msg, 3, stackTraceDepth));
    else
      logger.fine(msg);
  }
  
  public void fine (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogFineRecord(msg, th, 3));
    else
      logger.log(Level.FINE, msg, th);
  }  

  public void config (String msg)
  {
    if (locationShown)
      logger.log(new LogConfigRecord(msg, 3));
    else
      logger.config(msg);
  }
  
  public void config (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogFineRecord(msg, th, 3));
    else
      logger.log(Level.CONFIG, msg, th);
  }  
  
  public void info (String msg)
  {
    if (locationShown)
      logger.log(new LogInfoRecord(msg, 3));
    else
      logger.info(msg);
  }

  public void info (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogInfoRecord(msg, th, 3));
    else
      logger.log(Level.INFO, msg, th);
  }
  
  public void warning (String msg)
  {
    if (locationShown)
      logger.log(new LogWarningRecord(msg, 3));
    else
      logger.warning(msg);
  }

  public void warning (Throwable th)
  {
    if (locationShown)
      logger.log(new LogWarningRecord(null, th, 3));
    else
      logger.log(Level.WARNING, null, th);
  }
  
  public void warning (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogWarningRecord(msg, th, 3));
    else
      logger.log(Level.WARNING, msg, th);
  }
  
  public void severe (String msg)
  {
    if (locationShown)
      logger.log(new LogSevereRecord(msg, 3));
    else
      logger.severe(msg);
  }

  public void severe (Throwable th)
  {
    if (locationShown)
      logger.log(new LogSevereRecord(null, th, 3));
    else
      logger.log(Level.SEVERE, null, th);
  }
  
  public void severe (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogSevereRecord(msg, th, 3));
    else
      logger.log(Level.SEVERE, msg, th);
  }

  public void debug (String msg)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, msg, 3));
    else
      logger.log(new LogRecord(debugLevel, msg));
  }

  public void debug (String msg, int stackTraceDepth)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, msg, 3, stackTraceDepth));
    else
      logger.log(new LogRecord(debugLevel, msg));
  }
  
  public void debug (Throwable th)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, null, th, 3));
    else
    {
      LogRecord r = new LogRecord(debugLevel, null);
      r.setThrown(th);
      logger.log(r);
    }
  }
  
  public void debug (Throwable th, int stackTraceDepth)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, null, 3, stackTraceDepth));
    else
      logger.log(new LogRecord(debugLevel, null));
  }
  
  public void debug (String msg, Throwable th)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, msg, th, 3));
    else
    {
      LogRecord r = new LogRecord(debugLevel, msg);
      r.setThrown(th);
      logger.log(r);
    }
  }
  
  public void debug (String msg, Throwable th, int stackTraceDepth)
  {
    if (locationShown)
      logger.log(new LogDebugRecord(debugLevel, msg, th, 3, stackTraceDepth));
    else
    {
      LogRecord r = new LogRecord(debugLevel, msg);
      r.setThrown(th);
      logger.log(r);
    }
  }
  
}
