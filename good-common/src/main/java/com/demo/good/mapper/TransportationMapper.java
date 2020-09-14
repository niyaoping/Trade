

package com.demo.good.mapper;
import com.demo.good.entity.Transportation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TransportationMapper extends BaseMapper<Transportation> {
    @Select(" SELECT express_id as express_Id," +
            " del_flag as delFlag, status as status," +
            " transportation_time as transportation_Time," +
            " parent_id as parentId, quantity as quantity, " +
            "express_no as express_No, trade_id as trade_Id, id, " +
            "create_timestamp as createTimestamp\n" +
            "FROM transportation" +
            " where id in #{ids}")
    List<Transportation> selectForIn(@Param("ids")String ids);

    @Update("UPDATE transportation set del_flag=1 where  id in #{ids}")
    void softDelForIn(@Param("ids")String ids);
}

