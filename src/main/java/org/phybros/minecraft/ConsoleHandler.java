package org.phybros.minecraft;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.phybros.thrift.ConsoleLine;

public class ConsoleHandler extends Handler {

	private final SwiftApiPlugin plugin;

    public ConsoleHandler(SwiftApiPlugin plugin) {
        this.plugin = plugin;
    }
	
	@Override
	public synchronized void publish(LogRecord record) {
		if(plugin.last500.size() > 500) {
			plugin.last500.remove(0);
		}
		
		ConsoleLine line = new ConsoleLine();
		line.message = record.getMessage();
		line.timestamp = record.getMillis();
		
		plugin.last500.add(line);
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

}
