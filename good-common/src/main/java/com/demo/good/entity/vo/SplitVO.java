package com.demo.good.entity.vo;

import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SplitVO {
    private Integer id;
    private List<Integer> quantity;

}
