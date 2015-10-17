package cf.bookmark.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogbackTest {

	private static final Logger logger = LoggerFactory.getLogger(LogbackTest.class);
	
	public static void main(String[] args) {
		logger.trace("trace");
		logger.debug("debug"); // debug
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}
}