package services;

import java.util.logging.Logger;

public class LoggingService {
    private final Logger logger = Logger.getLogger(LoggingService.class.getName());

    public void log(String message) {
        logger.info(message);
    }
}
