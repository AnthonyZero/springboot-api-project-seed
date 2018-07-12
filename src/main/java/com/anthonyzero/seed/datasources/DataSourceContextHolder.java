package com.anthonyzero.seed.datasources;

/**
 * 保存一个线程安全的Datasource容器	
 * @author pingjin create 2018年7月12日
 *
 */
public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	public static void setDataSource(String dataSource) {
		contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
