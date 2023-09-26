package org.dromara.maxkey.web;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang3.ArchUtils;
import org.apache.commons.lang3.arch.Processor;
import org.dromara.maxkey.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductEnvironment {
	private static final Logger logger = LoggerFactory.getLogger(ProductEnvironment.class);

	ProductEnvironment(){}
	
    /**
     * List Environment Variables.
     */
    public static void listEnvVars() {
    	logger.info(WebConstants.DELIMITER);
    	logger.info("List Environment Variables ");
        Map<String, String> map = System.getenv();
        SortedSet<String> keyValueSet = new TreeSet<>();
        for (Iterator<String> itr = map.keySet().iterator(); itr.hasNext();) {
            String key = itr.next();
            keyValueSet.add(key);
        }
        // out
        for (Iterator<String> it = keyValueSet.iterator(); it.hasNext();) {
            String key = it.next();
            logger.trace("{}   =   {}" , key , map.get(key));
        }
        logger.info("APP_HOME" + "   =   {}" , PathUtils.getInstance().getAppPath());

        Processor processor = ArchUtils.getProcessor();
        if (Objects.isNull(processor)){
        	processor = new Processor(Processor.Arch.UNKNOWN, Processor.Type.UNKNOWN);
        }
        logger.info("OS      : {}({} {}), version {}",
                    SystemUtils.OS_NAME,
                    SystemUtils.OS_ARCH,
                    processor.getType(),
                    SystemUtils.OS_VERSION
                    
                );
        logger.info("COMPUTER: {}, USERNAME : {}",
                        map.get("COMPUTERNAME") ,
                        map.get("USERNAME")
                );
        logger.info("JAVA    :");
        logger.info("{} java version {}, class {}",
                        SystemUtils.JAVA_VENDOR,
                        SystemUtils.JAVA_VERSION,
                        SystemUtils.JAVA_CLASS_VERSION
                    );
        logger.info("{} (build {}, {})",
                        SystemUtils.JAVA_VM_NAME,
                        SystemUtils.JAVA_VM_VERSION,
                        SystemUtils.JAVA_VM_INFO
                    );
    
        logger.info(WebConstants.DELIMITER);
        
    }
}
