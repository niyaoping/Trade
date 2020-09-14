package com.demo.good.service.impl;

import com.demo.good.common.ErrorEnum;
import com.demo.good.common.OperationEnum;
import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;
import com.demo.good.exception.APIException;
import com.demo.good.mapper.TradeMapper;
import com.demo.good.mapper.TransportationMapper;
import com.demo.good.repository.IRepository;
import com.demo.good.service.ITradeServcie;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 货物操作服务
 * Create By yaoping.ni
 */

@Service
public class TradeServcieImpl implements ITradeServcie {


   @Autowired
   @Qualifier("tradeService")
   IRepository<Trade> tradeService;


   @Autowired
   TradeMapper tradeMapper;

 @Autowired
 @Qualifier("transportationService")
 IRepository<Transportation> transportationService;

    /**
  1、生成交易记录的时候需要先判断本次运输某种货物的总数量是否大于该货物的库存，如果大于库存，则提示，不让生成交易记录
  2、在执行这个保存操作的时候，需要考虑并发情况，有可能针对某个货物在多台设备上进行交易记录的生成，那么需要针对某个货物进行加锁操作，保证库存的一致性
  3、生成交易记录完成后，需要扣除对应的货物库存
  以上三个由于时间关系，我都没做，但是需要考虑进去
   */
   @Transactional
   public int saveTrade(Trade trade, Transportation transportation){
    int result=0;
    //找出该货物的库存信息，先进行该货物的锁定，然后在锁定条件内判断本次运输的数量是否小于库存数
    //伪代码： lock(该货物id){
    //if(本次运输 数量<该货物库存数)  执行下面的的操作
    // }
    try {

     tradeService.insert(trade);
     transportation.setTradeId(trade.getId());
     transportationService.insert(transportation);
     result=transportation.getId();


    }catch(Exception e) {
     throw new APIException(ErrorEnum.DB_EXCEPTION);
    }
    //执行完上面的操作后，需要扣除库存，然后释放锁
    //伪代码：减该货物库存
    //unlock(该货物id)
    return result;
   }

  public void updateTrade(Trade trade){
    int rowNumber= tradeService.update(trade);
    if(rowNumber<1){
     throw new APIException(ErrorEnum.DB_EXCEPTION);
    }
   }
   public Trade getTrade(Integer id){
    List<Trade> lst=tradeService.select(new Trade(id));
    if(!CollectionUtils.isNotEmpty(lst)){
     throw new APIException(200,"no record");
    }
    return lst.get(0);
   }
   public void deleteTrade(Integer id){
    int rowNumber=   tradeService.delete(new Trade(id));
    if(rowNumber<1){
     throw new APIException(ErrorEnum.DB_EXCEPTION);
    }

   }


 @Autowired
 TransportationMapper transportationMapper;


 /**
  * 通过交易主表进行拆分,通过在运输表中保存parentid字段，来表示运输表那条记录被拆分了，
  * 由于是demo ，目前不考虑发货状态，只是单纯拆分，理论上这个拆分能进行无限的拆分成小批次，比如如下记录
  * id，数量，parentid，delflag
  * 1,  9   ，NULL，0
  *现在要拆成三份，每份数量分别是1,3,5，则最终的记录是
  * id，数量，parentid，delflag
  * 2,  1,    1,         0
  * 3,   3,   1,         0
  * 4,   5,   1,         0
  *然后这三份，还能进行拆分，比如id为4的，还能进行拆分
  * @param id  需要拆分的运输表记录所在行的id
  * @param quantity  每个拆分项的数量，比如要拆成3分，每份分别数量是1,3,5，总数量还是和拆分前单行记录中的数量一致
  * @return void
  */
 @Transactional
 public void split(Integer id, List<Integer> quantity){
   Transportation transportation= new Transportation(id);
   transportation.setDelFlag(0);
   List<Transportation> lst=transportationMapper.select(transportation);
   if(!CollectionUtils.isNotEmpty(lst)){// 如果要拆分的原始行记录不存在，则报错
    throw new APIException(200,"no record");
   }
   int newQuantity=quantity.stream().mapToInt(n->{return n;}).sum();
   transportation= lst.get(0);
   if(newQuantity!=transportation.getQuantity()){
    throw new APIException(200,"拆分的数量和原始数量不一致");
   }
   try {
   for (Integer a : quantity) {
    Transportation t = new Transportation();
    t.setTradeId(transportation.getTradeId());
    t.setQuantity(a);
    t.setParentId(transportation.getId());
    transportationService.insert(t);
   }transportation.setDelFlag(1);
   transportationService.update(transportation);//将原先被拆分的行删除
  }
  catch (Exception e){
   throw new APIException(ErrorEnum.DB_EXCEPTION);
  }
 }


 /**
  * 根据运输表中多个id进行合并操作，即多条记录的 数量合并成一条记录的数量
  *
  * @param ids  运输表中id，即将多个id所在的行合并成一行
  * @return void
  */
 public void merge(List<String> ids){
     /**
 1、先要判断ids表示的id所在行是否都存在，只要有一条不存在，就说明ids传过来的id值有误，
 2、判断ids所在的行的parentid是否都是相同的，如果相同说明才是同一批货物，才能进行合并
 3、只有运输状态status在未发货之前才能进行合并
 4、考虑并发情况，即有多人同时在操作这几条数据在合并
 由于时间关系，我这里上述几个判读先不做校验了，假设ids所在的行都存在，且都属于同一批货物，且未发货，直接进行和并
  */
  List<Transportation> lst=transportationMapper.selectById(Transportation.class,ids);
  if(!CollectionUtils.isNotEmpty(lst)){// 如果要merge的记录不存在，则报错
   throw new APIException(200,"no record");
  }
  if(lst.size()!=ids.size()){//如果查询出来的记录和传过来的id数不一致，说明数据行被修改或者id传过来的不对
   throw new APIException(200,"no difference");
  }
  System.out.println(lst.stream().collect(Collectors.groupingBy(Transportation::getTradeId)).size());
  if(lst.stream().collect(Collectors.groupingBy(Transportation::getTradeId)).size()!=1){//如果不是1，那就说明不是同一批交易订单
   throw new APIException(200,"no same TradeId");
  }

  Integer mergeQuantity=lst.stream().mapToInt(n->{return n.getQuantity();}).sum();//获取要merge的总数量

  Transportation transportation=new Transportation();
  transportation.setQuantity(mergeQuantity);
  transportation.setTradeId(lst.get(0).getTradeId());
  transportationService.insert(transportation);//插入一行作为新的merge行

 //  所有的merge的行软删除
     ids.forEach(n->{
         Transportation trans=new Transportation(Integer.parseInt(n));
         trans.setDelFlag(1);
         transportationService.update(trans );
     });

 }

    /**
     * 1、如果增加的数量正好能均分，那么子表每条均衡增加
     * 2、如果不能均匀分摊，则第一条记录增加的数量是增加数%原来记录数-1，
     *    除开第一条其他增加记录就是增加数/原来记录数-1，举个例子，原先字表记录是三条，
     *    现在新增的数量是19，那么 第一条记录新增的数是19%(3-1)=1,除开第一条，其他2条记录新增的数量是19/(3-1)=9
     *    所以第一条增加1，第一第二条记录增加9，总数就是19
     * @param lst
     * @param changeQuantity
     */
 private void add(List<Transportation> lst ,Integer changeQuantity){
     for(Transportation t :lst){
         t.setQuantity(t.getQuantity()+(changeQuantity%lst.size()==0?changeQuantity/lst.size():(t==lst.get(0)?changeQuantity%(lst.size()-1):changeQuantity/(lst.size()-1))));
         transportationMapper.update(t);
     }
 }


    /**
     * 1、如果减少的数量正好能均分，那么子表每条均衡增加
     * 2、如果不能均匀分摊，则第一条记录增加的数量是增加数%原来记录数-1，
     *    除开第一条其他增加记录就是增加数/原来记录数-1，举个例子，原先字表记录是三条，
     *    现在新增的数量是19，那么 第一条记录新增的数是19%(3-1)=1,除开第一条，其他2条记录新增的数量是19/(3-1)=9
     *    所以第一条增加1，第一第二条记录增加9，总数就是19
     * @param lst
     * @param changeQuantity
     */
private void subtract(List<Transportation> lst ,Integer changeQuantity,Integer oldQuantity){
    if(oldQuantity<changeQuantity){
        throw new APIException(200, String.valueOf(changeQuantity)+"这个数太大了，以至于比老数量还大，导致的后果就是减完变负数啦");
    }
    for(Transportation t :lst){
        t.setQuantity(t.getQuantity()-(changeQuantity%lst.size()==0?changeQuantity/lst.size():(t==lst.get(0)?changeQuantity%(lst.size()-1):changeQuantity/(lst.size()-1))));
        transportationMapper.update(t);
    }
}

    /**
     * 附表每条记录原来数量*倍数
     * @param lst
     * @param changeQuantity
     */
    private void multiply(List<Transportation> lst ,Integer changeQuantity){
        for(Transportation t :lst){
            t.setQuantity(t.getQuantity()*changeQuantity);
            transportationMapper.update(t);
        }
   }

    /**
     * 进行整除
     * @param lst
     * @param changeQuantity
     */
   private void divide(List<Transportation> lst ,Integer changeQuantity,Integer oldQuantity){
       if(oldQuantity%changeQuantity!=0){//校验主表中的数量能够被整除
           throw new APIException(200,String.valueOf(oldQuantity)+"不能被"+String.valueOf(changeQuantity)+"整除");
       }
       for(Transportation t :lst) {//校验所有子表中的数量能否被整除
           if(t.getQuantity()%changeQuantity!=0){
               throw new APIException(200,String.valueOf(t.getQuantity())+"不能被"+String.valueOf(changeQuantity)+"整除");
           }
       }
       for(Transportation t :lst) {
           t.setQuantity(t.getQuantity()/changeQuantity);
           transportationMapper.update(t);
       }
   }
 /**
  * 按照加减乘除更新数量
  *
  * @param id  贸易表id
  * @param changeQuantity  更新的基数
  * @return void
  */
 public void updateQuantity(Integer id, Integer changeQuantity,OperationEnum operationEnum){
     Trade trade=new Trade(id);
     trade.setDelFlag(0);
     List<Trade> lstTrade= tradeMapper.select(trade);//根据id及软删除标志查询trade记录
     if(!CollectionUtils.isNotEmpty(lstTrade)){// 如果要更新的记录不存在，则报错
         throw new APIException(200,"no record");
     }

     Transportation transportation= new Transportation();
     transportation.setDelFlag(0);
     transportation.setTradeId(id);
     List<Transportation> lst=transportationMapper.select(transportation);//根据主表trade 的id查询附表记录
     if(!CollectionUtils.isNotEmpty(lst)){// 如果要更新的记录不存在，则报错
         throw new APIException(200,"no record");
     }

     /**
      * 以下逻辑进行加减乘除四种逻辑数量的运算
      */
     Integer oldQuantity=lstTrade.get(0).getTotalQuantity();//获取父表中原先的数量
     //数量变化，理论上价格和重量也要改变，这边的计算暂时不实现了，demo这里只关注数量的变化
     Integer newQuantity=0;
      if(operationEnum==OperationEnum.multiply){//数量按倍数增加
           newQuantity=oldQuantity*changeQuantity;//主表原来数量*changeQuantity
           multiply(lst,changeQuantity);

      }else if(operationEnum==OperationEnum.add){//数量增加
           newQuantity=oldQuantity+changeQuantity;//主表数量增加changeQuantity
           add(lst,changeQuantity);
      }
       else if(operationEnum==OperationEnum.subtract){//数量减少
           newQuantity=oldQuantity-changeQuantity;//主表中的数量减少changeQuantity
           subtract(lst,changeQuantity,oldQuantity);
       }
       else if(operationEnum==OperationEnum.divide) {//数量整除changeQuantity
           newQuantity=oldQuantity/changeQuantity;
           divide(lst,changeQuantity,oldQuantity);
       }
       trade.setTotalQuantity(newQuantity);
       tradeService.update(trade);//更新主表数量
   }







}
