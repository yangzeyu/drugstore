package com.jsmscp.dr.service;

import com.jsmscp.dr.entity.Manufacture;

import java.util.List;

public interface ManufactureService {
    Manufacture findByName(String name);

    List<Manufacture> findManufacture(String keyword);

    List<Manufacture> findAllManufacture();

    Manufacture selectById(Integer id);
}
