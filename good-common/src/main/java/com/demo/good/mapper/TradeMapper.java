

package com.demo.good.mapper;
import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeMapper extends BaseMapper<Trade> {

}

