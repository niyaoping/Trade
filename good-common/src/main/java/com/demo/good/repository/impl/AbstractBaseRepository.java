package com.demo.good.repository.impl;
import com.demo.good.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.demo.good.repository.IRepository;


public abstract  class AbstractBaseRepository<T> implements  IRepository<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseRepository.class);


    @Autowired
    BaseMapper<T> baseMapper;
    public int insert(T t) {
    	return  baseMapper.insert(t);
    }
    public int delete(T t) {
        return baseMapper.delete(t);
    }
    public int deleteById(List<String> id,Class<T> t) {
        return baseMapper.deleteById(t,id);
    }
    public int update(T t) {
        return baseMapper.update(t);
    }
    public List<T> select(T t) {
        return  baseMapper.select(t);
    }
    public T getOne(T t) {
        T tt = null;
        try {
            tt=  Optional.ofNullable(baseMapper.select(t).get(0)).orElse((T)t.getClass().newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
      return  tt;
    }

    public int updateByParameter(T t, Map<String,String> setMap, Map<String,String> whereMap) {
        return baseMapper.updateByParameter(t,setMap,whereMap);
    }

    public List<T> selectByParameter(Class<T> t, Map<String,Object> whereMap) {
        return  baseMapper.selectByParameter(t,whereMap);
    }
    public List<T> selectById(List<String> id,Class<T>  t){
        return baseMapper.selectById(t,id);
    }


    public List<T> select(Map<String,Object> whereMap){
        throw new UnsupportedOperationException();
    }
}
