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
 * 主数据源配置类
 * 
 * @author zjk
 * 2017年12月5日 下午6:07:55
 */
@Configuration
//扫描 Mapper 接口并容器管理(sqlSessionFactoryRef 表示定义了 key ，表示一个唯一 SqlSessionFactory 实例)
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
	//private Logger logger = LoggerFactory.getLogger(MasterDataSourceConfig.class);
	
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.rtc.mapper.master";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
	
    @Value("${master.datasource.url}")  //@Value 获取全局配置文件 application.properties 的 kv 配置,并自动装配
    private String dbUrl;  
      
    @Value("${master.datasource.username}")  
    private String username;  
      
    @Value("${master.datasource.password}")  
    private String password;  
      
    @Value("${master.datasource.driverClassName}")  
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
    
/*    @Autowired
    private WallFilter wallFilter;*/
    
    @Bean(name = "masterDataSource")     //声明其为Bean实例  
    //在同样的DataSource中，首先使用被标注的DataSource  
    //@Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。「多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」
    @Primary  
    public DataSource masterDataSource(){  
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
    
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }
    
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
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
