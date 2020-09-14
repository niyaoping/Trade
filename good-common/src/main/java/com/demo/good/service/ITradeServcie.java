package com.demo.good.service;

import com.demo.good.common.OperationEnum;
import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;

import java.util.List;


/**
 * 货物操作服务接口
 * Create By yaoping.ni
 */

public interface ITradeServcie {
 int saveTrade(Trade trade,Transportation transportation);
 void updateTrade(Trade trade);
 Trade getTrade(Integer id);
 void deleteTrade(Integer id);



 /**
  * 通过交易主表进行拆分
  *
  * @param Id  需要拆分的运输表记录所在行的id
  * @param quantity  每个拆分项的数量，比如要拆成3分，每份分别数量是1,3,5，总数量还是和拆分前单行记录中的数量一致
  * @return void
  */
 void split(Integer Id , List<Integer> quantity);


 /**
  * 根据运输表中多个id进行合并操作
  *
  * @param ids  运输表中id，即将多个id所在的行合并成一行
  * @return void
  */
 void merge(List<String> ids);


 /**
  * 按照比例更新数量
  *
  * @param Id  贸易表id
  * @param changeQuantity  更新的数量
  * @param OperationEnum  是否按照百分比或者普通模式更新
  * @return void
  */
 void updateQuantity(Integer Id ,Integer changeQuantity,OperationEnum OperationEnum);




}
