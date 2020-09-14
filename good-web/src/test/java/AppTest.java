import com.demo.good.Application;
import com.demo.good.common.OperationEnum;
import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;
import com.demo.good.mapper.TradeMapper;
import com.demo.good.mapper.TransportationMapper;
import com.demo.good.service.ITradeServcie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AppTest {
    @Autowired
    ITradeServcie tradeServcieImpl;


    @Test
    public void createTrade() {
        System.out.println("test1");
    }

    /**
     * 获取id为8的交易表记录
     */
    @Test
    public void getTrade() {
        List<Trade> l=tradeMapper.select(new Trade(8));
        if(l!=null & l.size()==1){
            System.out.println(l.get(0).toString());
            System.out.println("交易id为：8的总数量："+String.valueOf(l.get(0).getTotalQuantity()));
        }

    }

    /**
     * 获取交易id为8的运输表记录
     */
    @Test
    public void getTransportation() {
        Transportation transportation= new Transportation();
        transportation.setDelFlag(0);
        transportation.setTradeId(8);
        List<Transportation> lst=transportationMapper.select(transportation);
        for(Transportation t:lst) {
            System.out.println("*****************");
            System.out.println(t.toString());
            System.out.println("交易id为：8的每次运输的数量为："+String.valueOf(t.getQuantity()));
            System.out.println("运输记录id为："+String.valueOf(t.getId()));
        }
    }
    @Autowired
    TransportationMapper transportationMapper;

    @Autowired
    TradeMapper tradeMapper;

    /**
     * 拆分运输表中的记录为多条
     */
    @Test
    public void split() {
        List<Integer> lst=new ArrayList();
        lst.add(100);
        lst.add(96);
        tradeServcieImpl.split(37,lst);
        //37表示transportation表中的id，
        // lst中的100和96表示原先37这个id所在行的数量是196，现在拆分成2条，一条是100 一条是96
    }


    /**
     * 表示将指定id记录合并成一条记录，
     */
    @Test
    public void merge() {
        List<String> ids=new ArrayList();
        ids.add("38");
        ids.add("39");
        tradeServcieImpl.merge(ids);
        //38和39表示运输记录表中的id，将这两个行进行合并，最终会 生成一个新行，新行的数量就是这2个行的数量之和

    }



    /**
     *进行整体加 id=8表示是交易表中id，现在整体加19，这个19更新到交易表，然后在均分到运输记录表
     *
     */
    @Test
    public void updateQuantityAdd() {
        tradeServcieImpl.updateQuantity(8,19, OperationEnum.add);
    }



    /**
     *进行整体减id=8表示是交易表中id，现在整体加19，这个19更新到交易表，然后在均分到运输记录表
     */
    @Test
    public void updateQuantitySubtract() {
        tradeServcieImpl.updateQuantity(8,19, OperationEnum.subtract);

    }


    /**
     *进行整体加倍本例是表示对于id为8的交易记录，整体增加2倍数量

     */
    @Test
    public void  updateQuantityMultiply() {
        tradeServcieImpl.updateQuantity(8,2, OperationEnum.multiply);
    }

    /**
     *进行整体除，必须全部都能除够

     */
    @Test
    public void updateQuantityDivide() {
        tradeServcieImpl.updateQuantity(8,2, OperationEnum.divide);

    }
}