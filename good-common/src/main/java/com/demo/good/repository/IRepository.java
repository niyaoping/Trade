package com.demo.good.repository;

import java.util.List;
import java.util.Map;


public interface IRepository<T>{
     int insert(T t) ;
     int delete(T t);
     int deleteById(List<String> id,Class<T>  t);
     int update(T t);
     List<T> select(T t);
     T getOne(T t) ;
     int updateByParameter(T t,Map<String,String> setMap, Map<String,String> whereMap);
     List<T> selectByParameter(Class<T> t, Map<String,Object> whereMap) ;

     List<T> selectById(List<String> id,Class<T>  t);

     public List<T> select(Map<String,Object> whereMap);
}
