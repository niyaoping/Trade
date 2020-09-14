package com.demo.good.entity;


import com.demo.good.annotation.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * 实体抽象
 Create  By  Yaoping.NI
 */
public abstract class BaseObject {
	
	 //自增ID
    @Column(autoIncrementKey=true,where = true)//表示自增id
    @Getter
   	@Setter
    protected Integer  id;

    @Getter
   	@Setter
	@Column(name = "del_flag")
    private Integer delFlag;
	//当前操作时间
    
	@Column(defaultValue=true,name = "create_timestamp")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Getter
	@Setter
	private String createTimestamp;

	private void getAllClass(Class cls,ArrayList<Class> lst){
		lst.add(cls);
		if(cls.getSuperclass()==null){
			return;
		}else{
			getAllClass(cls.getSuperclass(),lst);
		}
	}

	/**
	 * 重写toString
	 */
	@Override
	public String toString(){
		ArrayList<Class> lst=new ArrayList<>();
		getAllClass(this.getClass(),lst);
		StringBuilder sb=new StringBuilder();
		for(Class t :lst) {
			for (Field field : t.getDeclaredFields()) {
				field.setAccessible(true);
				Column column = (Column) field.getAnnotation(Column.class);
				if (column != null && (column.defaultValue() || !column.setValue() || column.foreignKey())) {
					continue;
				}
				try {
					if (field.get(this) != null) {
						sb.append(field.getName() + "@" + field.get(this).toString() + "|");
					}

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

   @Override
 	public int hashCode(){
    	return toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BaseObject)) {
			return false;
		}
		BaseObject Obj = (BaseObject) obj;
		// 地址相等
		if (this == Obj) {
			return true;
		}
		if (Obj.toString().equals(this.toString())) {
			return true;
		} else {
			return false;
		}
	}

}
