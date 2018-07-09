package me.donlin.wemall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Donlin
 * @Date: Created in 10:44 2018/7/8
 * @Version: 1.0
 * @Description: 定义一个存放本地缓存的Token Cache块，当前用户的session信息就存放在这里
 */
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    //这里采用LRU算法管理缓存
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载数据到本地内存
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("localCache get error", e);
        }
        return null;
    }
}
