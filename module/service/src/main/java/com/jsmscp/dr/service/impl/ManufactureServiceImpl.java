package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.mapper.ManufactureMapper;
import com.jsmscp.dr.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufactureServiceImpl implements ManufactureService {

    private ManufactureMapper manufactureMapper;

    @Autowired
    public void setManufactureMapper(ManufactureMapper manufactureMapper) {
        this.manufactureMapper = manufactureMapper;
    }

    /**
     * 根据名称查询生产厂家
     * @param name
     * @return
     */
    @Override
    public Manufacture findByName(String name) {
        return manufactureMapper.findByName(name);
    }

    /**
     * 根据关键字查询生产厂家
     * @param keyword
     * @return
     */
    @Override
    public List<Manufacture> findManufacture(String keyword) {
        List<Manufacture> list = manufactureMapper.findManufacture(keyword);
        return list;
    }

    @Override
    public List<Manufacture> findAllManufacture() {
        return manufactureMapper.findAllManufacture();
    }

    @Override
    public Manufacture selectById(Integer id) {
        return manufactureMapper.selectByPrimaryKey(id);
    }


}
