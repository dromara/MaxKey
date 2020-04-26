package org.maxkey.cache;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheFactory.
 * @author Crystal.Sea
 *
 */
public class CacheFactory {
    protected static final Logger _logger = LoggerFactory.getLogger(CacheFactory.class);

    private ArrayList<AbstractCache> cache;

    
    /**
     *  CacheFactory.
     */
    public CacheFactory() {

    }
    
    /**
     * start Cache.
     */
    public void start() {

        for (AbstractCache cacheable : cache) {
            _logger.info("Cache " + cacheable.getClass());
            new Thread(cacheable).start();

        }

    }

    public ArrayList<AbstractCache> getCache() {
        return cache;
    }

    public void setCache(ArrayList<AbstractCache> cache) {
        this.cache = cache;
    }

}
