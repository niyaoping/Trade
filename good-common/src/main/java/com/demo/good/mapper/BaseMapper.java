package com.demo.good.mapper;


import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 通用mapper create by niyaoping
 */
public interface BaseMapper<T> {

	@SelectProvider(type=SqlFactoryProvider.class,method="select")
	List<T> select(@Param("bean") T t);

	
	@Options(useGeneratedKeys = true, keyProperty = "bean.id",keyColumn = "id")
	@InsertProvider(type=SqlFactoryProvider.class,method="insert")
    int insert(@Param("bean") T t);

	@UpdateProvider(type=SqlFactoryProvider.class,method="update")
    int update(@Param("bean") T t);


	@DeleteProvider(type=SqlFactoryProvider.class,method="delete")
	int delete(@Param("bean") T t);


	@UpdateProvider(type=SqlFactoryProvider.class,method="updateByParameter")
	int updateByParameter(@Param("bean") T t, @Param("setMap") Map<String, String> setMap, @Param("whereMap") Map<String, String> whereMap);

	@SelectProvider(type=SqlFactoryProvider.class,method="selectByParameter")
	List<T> selectByParameter(@Param("bean") Class<T> t, @Param("whereMap") Map<String, Object> whereMap);



	@DeleteProvider(type=SqlFactoryProvider.class,method="deleteById")
	int deleteById(@Param("bean") Class<T> t,@Param("lst") List<String> ids);

	@SelectProvider(type=SqlFactoryProvider.class,method="selectById")
	List<T> selectById(@Param("bean") Class<T> t,@Param("lst") List<String> ids);

}

