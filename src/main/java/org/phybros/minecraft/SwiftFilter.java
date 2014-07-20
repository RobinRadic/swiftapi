package org.phybros.minecraft;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.phybros.thrift.ConsoleLine;
import org.phybros.thrift.SwiftApi;

/**
 * Created by will on 2014-07-11.
 */
public class SwiftFilter implements Filter {

    private SwiftApiPlugin plugin;

    public SwiftFilter(SwiftApiPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Result getOnMismatch() {
        return null;
    }

    @Override
    public Result getOnMatch() {
        return null;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object... objects) {
        return null;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object o, Throwable throwable) {
        return null;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return null;
    }

    @Override
    public Result filter(LogEvent logEvent) {
        if(SwiftApiPlugin.getApi().getConsoleBuffer().size() > 1000) {
            SwiftApiPlugin.getApi().getConsoleBuffer().remove(0);
        }

        ConsoleLine line = new ConsoleLine();
        line.message = logEvent.getMessage().getFormattedMessage();
        line.timestamp = logEvent.getMillis();
        line.level = logEvent.getLevel().toString();

        SwiftApiPlugin.getApi().getConsoleBuffer().add(line);

        return Result.NEUTRAL;
    }
}
