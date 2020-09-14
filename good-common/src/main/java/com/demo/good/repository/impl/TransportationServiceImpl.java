package com.demo.good.repository.impl;

import com.demo.good.entity.Transportation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("transportationService")
public class TransportationServiceImpl extends AbstractBaseRepository<Transportation> {


}
