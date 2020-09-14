package com.demo.good.mapper;


import com.demo.good.annotation.Column;
import com.demo.good.annotation.Table;
import com.demo.good.common.ErrorEnum;
import com.demo.good.exception.APIException;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作统一provider类create by niyaoping
 */
public class SqlFactoryProvider {
	 public String select(Map<String,Object> para) throws IllegalArgumentException, IllegalAccessException{
	  
	   String tableName;	
	   Object obj; 
	   if((obj =para.get("bean"))==null){
		   throw new APIException(ErrorEnum.FAILED);
	   }
	   ArrayList<Class> lst =new ArrayList<Class>();
	   getAllClass(obj.getClass(),lst);
	   tableName=getTableName(obj);	//得到表名，表名有可能是实体类名，也有可能是实体类中Table注解中的值
	   SQL sql = new SQL(); 
	   sql.FROM(tableName);
	   String value;
	   for(Class t :lst) {
		   for (Field field : t.getDeclaredFields()) {
			   field.setAccessible(true);//设置字段可见
			   Column column = (Column) field.getAnnotation(Column.class);//遍历实体类总所有字段，是否有column注解
			   if (!(Modifier.toString(field.getModifiers()).contains("static") || Modifier.toString(field.getModifiers()).contains("final"))) {//去除static,final修饰的字段
				   if (column != null) {
					   sql.SELECT(column.name().length() == 0 ? field.getName() : column.name() + " as " + field.getName());
				   } else {
					   sql.SELECT(field.getName());
				   }
				   if (field.get(obj) != null) {
					   value = field.get(obj).toString();
					   if (field.getType().getName().equals("java.lang.String") || field.getType().getName().equals("java.util.Date")) {
						   value = "'" + field.get(obj).toString() + "'";
					   }
					   if (column != null) {
						   sql.WHERE((column.name().length() == 0 ? field.getName() : column.name()) + "=" + value);
					   } else {
						   sql.WHERE(field.getName() + "=" + value);
					   }
				   }
			   }
		   }
	   }
		   System.out.println(sql.toString());
		   return sql.toString(); 	  
    }
	 public String delete(Map<String,Object> para) throws IllegalArgumentException, IllegalAccessException {
		  Object obj =para.get("bean"); 
	       String tableName;	 	  
	 	   if(obj ==null){
	 		   throw new APIException(ErrorEnum.FAILED);
	 	   }	 	  
	 	   tableName=getTableName(obj);
		   SQL sql = new SQL();
		 sql.DELETE_FROM(tableName);
		 ArrayList<Class> lst =new ArrayList<Class>();
		 getAllClass(obj.getClass(),lst);
		 lst.forEach(n->{
			     Arrays.stream(n.getDeclaredFields()).forEach(t->{
				 t.setAccessible(true);
				 Column column=(Column)t.getAnnotation(Column.class);
				 if(column!=null && column.autoIncrementKey()){
					 String  value= null;
					 try {
						 value = t.get(obj).toString();
						  } catch (IllegalAccessException e) {
						 e.printStackTrace();
					 }
					 if(!column.name().isEmpty() ){
						 sql.WHERE(column.name()+"="+value);

					 }
					 else{
						 sql.WHERE(t.getName()+"="+value);
					 }
				 }
			 });
		 });
		 System.out.println(sql.toString());
		 return sql.toString();
	    }
	 
	 private String getTableName(Object obj){
		 Class cl=obj.getClass();
		 return getTableName(cl);
	 }
	 private String getTableName(Class cl){
		 Table annotation;
		 String  tableName=cl.getSimpleName();
		 if((annotation=(Table)cl.getAnnotation(Table.class))!=null){
			 tableName=annotation.value();
		 }
		 return tableName;
	 }
	 public String insert(Map<String,Object> para) throws IllegalArgumentException, IllegalAccessException{
	       Object obj =para.get("bean"); 
	       String tableName;	 	  
	 	   if(obj ==null){
	 		   throw new APIException(ErrorEnum.FAILED);
	 	   }	 	  
	 	   tableName=getTableName(obj);		
	        SQL sql = new SQL(); 
	        sql.INSERT_INTO(tableName);
	        String value;
	        for(Field field : obj.getClass().getDeclaredFields()){
				   field.setAccessible(true);
				   Column column=(Column)field.getAnnotation(Column.class);
				   if(field.get(obj)==null|| (column!=null &&(column.autoIncrementKey() || column.defaultValue() || !column.setValue()))){//值为NULL不更新
					   continue;
				   }
				   value=field.get(obj).toString();
				   if(field.getType().getName().equals("java.lang.String")  || field.getType().getName().equals("java.util.Date")){
					   value="'"+field.get(obj).toString()+"'";
				   } 
						 
				   if(column!=null && !column.name().isEmpty() ){
					   sql.VALUES(column.name(), value);
					   System.out.println(column.name()+":"+value);
				   }
				   else{
					   sql.VALUES(field.getName(), value);
					   System.out.println(field.getName()+":"+value);
				   }
	        }
		 System.out.println(sql.toString());
	        return sql.toString();
	}

	private void getAllClass(Class cls,ArrayList<Class> lst){
		lst.add(cls);
		if(cls.getSuperclass()==null){
			return;
		}else{
			getAllClass(cls.getSuperclass(),lst);
		}
	}
	 
	 public String update(Map<String,Object> para) throws IllegalArgumentException, IllegalAccessException {
		 Object obj =para.get("bean"); 	       	  
	 	 if(obj ==null){
	 		   throw new APIException(ErrorEnum.FAILED);
	 	 }	 	  
	 	 String tableName=getTableName(obj);

	  	String value;
	 	 SQL sql = new SQL(); 
	 	 sql.UPDATE(tableName);
		 ArrayList<Class> lst =new ArrayList<Class>();
		 getAllClass(obj.getClass(),lst);
		 String setValue;
		 for(Class cls :lst) {
			 for (Field field : cls.getDeclaredFields()) {
				 field.setAccessible(true);
				 if (field.get(obj) == null || Modifier.toString(field.getModifiers()).contains("static") || Modifier.toString(field.getModifiers()).contains("final")) {//去除static,final修饰的字段
					 continue;
				 }
				 Column column = (Column) field.getAnnotation(Column.class);
				 value = field.get(obj).toString();
				 if (field.getType().getName().equals("java.lang.String") || field.getType().getName().equals("java.util.Date")) {
					 value = "'" + field.get(obj).toString() + "'";
				 }
				 setValue=field.getName() + "=" + value;
				 if (column != null ) {
				 	if(!column.name().isEmpty()) {
						setValue = column.name() + "=" + value;
					}
					if(column.autoIncrementKey()){
						sql.WHERE(setValue);
						continue;
					}
				 }
				 sql.SET(setValue);
			 }
		 }
	 	 System.out.println(sql.toString());
	 	 return sql.toString();
	 }





	public String updateByParameter(Map<String,Object> para)  {
		Object obj =para.get("bean");
		String tableName=getTableName(obj);
		SQL sql = new SQL();
		sql.UPDATE(tableName);
		Map<String,String> setMap =(Map<String,String>)para.get("setMap");
		for(String fieldName : setMap.keySet()) {
			if (setMap.get(fieldName) != null && !setMap.get(fieldName).equals("")) {
				sql.SET(fieldName + "=" + setMap.get(fieldName));
			}
		}
		Map<String,String> whereMap =(Map<String,String>)para.get("whereMap");
		for(String fieldName : whereMap.keySet()) {
			if (whereMap.get(fieldName) != null && !whereMap.get(fieldName).equals("")) {
				sql.WHERE(fieldName + "=" + whereMap.get(fieldName));
			}
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	public String selectByParameter(Map<String,Object> para)  {
		Class cls =(Class)para.get("bean");
		String tableName=getTableName(cls);
		SQL sql = new SQL();
		sql.FROM(tableName);
		ArrayList<Class> lst =new ArrayList<Class>();
		getAllClass(cls,lst);
		Map<String,Object> whereMap =(Map<String,Object>)para.get("whereMap");
		for(Class cl :lst) {
			Arrays.stream(cl.getDeclaredFields()).parallel().forEach(n -> {
				Column column = (Column) n.getAnnotation(Column.class);
				if(!(Modifier.toString(n.getModifiers()).contains("static") || Modifier.toString(n.getModifiers()).contains("final"))){//去除static,final修饰的字段
				   if(column!=null){
						sql.SELECT(column.name().length()==0?n.getName():column.name()+" as "+n.getName());
					}
					else{
						sql.SELECT(n.getName());
					}
				}
				n.setAccessible(true);
				whereMap.keySet().stream().forEach(k -> {
					if (n.getName().equals(k)) {//匹配穿过来的字段名是否和数据库中的字段名一样

						String value = whereMap.get(k).toString();
						if (whereMap.get(k).getClass().getTypeName().equals("java.lang.String") || whereMap.get(k).getClass().getTypeName().equals("java.util.Date")) {
							value = "'" + whereMap.get(k).toString() + "'";
						}
						if (column != null && !column.name().equals("")) {
							sql.WHERE(column.name() + "=" + value);
						} else {
							sql.WHERE(k + "=" + value);
						}
					}
				});
			});
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	public String deleteById(Map<String,Object> para){
		Class cls =(Class)para.get("bean");
		String tableName;
		if(cls ==null){
			throw new APIException(ErrorEnum.FAILED);
		}
		StringBuilder where =new StringBuilder();
		where.append(" where 1=1 ");
		tableName=getTableName(cls);
		SQL sql = new SQL();
		sql.DELETE_FROM(tableName);
		ArrayList<Class> lst =new ArrayList<Class>();
		getAllClass(cls,lst);
		lst.parallelStream().forEach(n->{
			Arrays.stream(n.getDeclaredFields()).parallel().forEach(t->{
				t.setAccessible(true);
				Column column=(Column)t.getAnnotation(Column.class);
				if(column!=null && column.autoIncrementKey()){
					List<String> whereList =(List<String>)para.get("lst");
					String ids=String.join(",", whereList);
					if(column!=null && column.autoIncrementKey()){
						if(!ids.isEmpty()){
							if(!column.name().isEmpty() ){
								where.append("and "+column.name()+" in ("+ids+") ");
							}else {
								where.append("and "+t.getName() +" in ("+ids+") ");
							}
						}
					}

				}
			});
		});


		System.out.println(sql.toString() +where.toString());
		return sql.toString()+where.toString();
	}







	public String selectById(Map<String,Object> para){
		Class cls =(Class)para.get("bean");
		String tableName;
		if(cls ==null){
			throw new APIException(ErrorEnum.FAILED);
		}
		StringBuilder where =new StringBuilder();
		where.append(" where 1=1");
		tableName=getTableName(cls);
		SQL sql = new SQL();
		sql.FROM(tableName);
		ArrayList<Class> lst =new ArrayList<Class>();
		getAllClass(cls,lst);
		lst.parallelStream().forEach(n->{
			Arrays.stream(n.getDeclaredFields()).parallel().forEach(t->{
				t.setAccessible(true);
				Column column=(Column)t.getAnnotation(Column.class);
				if(!(Modifier.toString(t.getModifiers()).contains("static") || Modifier.toString(t.getModifiers()).contains("final"))){//去除static,final修饰的字段
					if(column!=null){
						sql.SELECT(column.name().length()==0?t.getName():column.name()+" as "+t.getName());
					}
					else{
						sql.SELECT(t.getName());
					}
				}
				List<String> whereList =(List<String>)para.get("lst");
				String ids=String.join(",", whereList);
				if(column!=null && column.autoIncrementKey()){
					if(!ids.isEmpty()){
						if(!column.name().isEmpty() ){
							where.append(" and "+column.name()+" in ("+ids+") ");
						}else {
							where.append(" and "+t.getName() +" in ("+ids+") ");
						}
					}
				}
			});
		});

		System.out.println(sql.toString() +where.toString());
		return sql.toString()+where.toString();
	}
}
