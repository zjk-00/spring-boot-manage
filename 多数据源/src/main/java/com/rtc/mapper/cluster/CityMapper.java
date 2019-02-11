package com.rtc.mapper.cluster;

import com.rtc.entity.City;

/**
 * 城市mapper接口类（连接从库）
 * 
 * @author zjk
 * 2017年12月5日 下午5:48:14
 */
public interface CityMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(City city);

    int insertSelective(City city);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City city);

    int updateByPrimaryKey(City city);
}