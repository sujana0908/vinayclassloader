package util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BeanContainer {
    private final Map<String, Object> beans = new HashMap<>();
    private final Logger logger = Logger.getLogger(BeanContainer.class.getName());

    public void registerBean(String name, Object instance) {
        beans.put(name, instance);
        logger.info("Registered bean: " + name);
    }

    public Object getBean(String name) {
        return beans.get(name);
    }
}
