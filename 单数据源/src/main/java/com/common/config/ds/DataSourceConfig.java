package com.common.config.ds;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
 * 数据源配置类
 * 
 * @author zjk
 * 2017年12月5日 下午6:07:55
 */
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	//private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
    static final String PACKAGE = "com.rtc.*.mapper";
    static final String LOCATION = "classpath:mapper/*/*.xml";
	
    @Value("${spring.datasource.url}")  //@Value 获取全局配置文件 application.properties 的 kv 配置,并自动装配
    private String dbUrl;  
      
    @Value("${spring.datasource.username}")  
    private String username;  
      
    @Value("${spring.datasource.password}")  
    private String password;  
      
    @Value("${spring.datasource.driverClassName}")  
    private String driverClassName;  
      
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;  
      
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
      
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
      
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
      
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
      
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
      
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;  
      
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
      
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
      
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
      
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
      
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
      
    @Value("${spring.datasource.filters}")  
    private String filters;  
      
    @Value("${spring.datasource.connectionProperties}")  
    private String connectionProperties;
    
    @Value("${spring.datasource.useGlobalDataSourceStat}")
    private boolean useGlobalDataSourceStat;
    
    @Autowired
    WallFilter wallFilter;
    
    @Bean(name = "dataSource")     //声明其为Bean实例  
    //在同样的DataSource中，首先使用被标注的DataSource  
    //@Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。「多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」
    @Primary  
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
          
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
          
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize); 
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter);
		datasource.setProxyFilters(filters);  
        datasource.setConnectionProperties(connectionProperties);
        
        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);  
        return datasource;
    }
    
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConfig.LOCATION));
        return sessionFactory.getObject();
    }
    
   @Bean(name = "wallConfig")
    public WallConfig wallFilterConfig(){
        WallConfig wc = new WallConfig ();
        //druid允许批量更新
        wc.setMultiStatementAllow(true);
        return wc;
    }
    
    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    public WallFilter wallFilter(WallConfig wallConfig){
        WallFilter wfilter = new WallFilter ();
        wfilter.setConfig(wallConfig);
        return wfilter;
    }
}
