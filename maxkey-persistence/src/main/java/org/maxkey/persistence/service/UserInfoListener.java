package org.maxkey.persistence.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yapeng.li
 */
public class UserInfoListener extends AnalysisEventListener<UserInfo> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserInfoListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 20;
    List<UserInfo> list = new ArrayList<UserInfo>();


    private UserInfoService userInfoService;

    public UserInfoListener(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public void invoke(UserInfo data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        userInfoService.batchInsert(list);
        LOGGER.info("存储数据库成功！");
    }
}
