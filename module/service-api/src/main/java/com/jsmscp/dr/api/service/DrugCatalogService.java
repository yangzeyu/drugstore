package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.api.vo.DrugVO;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.mapper.DrugBaseMapper;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.ManufactureMapper;
import com.jsmscp.dr.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class DrugCatalogService {

    private DrugCatalogMapper drugCatalogMapper;

    private DrugBaseMapper drugBaseMapper;

    private ManufactureMapper manufactureMapper;


    /**
     * 对码
     *
     * @param drug          药品信息
     * @param store         药店
     * @param insuranceCode 医保编码
     * @param drugCode      药店药品编码
     * @param stock         存量
     */
    @Transactional
    public DrugVO register(DrugVO drug, DrugStore store, String insuranceCode,
                           String drugCode, BigDecimal stock) throws ServiceException { //更新实时库存

        DrugCatalog catalog = findCatalog(drugCode, store.getId());
        if (catalog != null) {
            throw new ServiceException("药品编码已存在");
        }

        DrugBase drugBase = null;
        if (!StringUtils.isBlank(insuranceCode)) {
            drugBase = drugBaseMapper.findByInsuranceCode(insuranceCode);
        }

        Manufacture manufacture = null;
        String manufacturerName = drug.getManufacturer();
        if (drugBase == null && !StringUtils.isBlank(manufacturerName)) {
            manufacture = manufactureMapper.findByName(manufacturerName);
        }
        if (manufacture == null) {
            createDrugCatalog(drug, null, store, drugCode, stock, manufacturerName);
        } else {
            String drugName = drug.getDrugName();
            String spec = drug.getSpec();
            String unit = drug.getUnit();
            String dosageForm = drug.getDosageForm();
            if (drugBase == null && manufacture != null && !(StringUtils.isBlank(drugName)
                    || StringUtils.isBlank(spec)
                    || StringUtils.isBlank(dosageForm)
                    || StringUtils.isBlank(unit))) {
                drugBase = drugBaseMapper.findDrug(drugName, dosageForm, spec, unit, manufacture.getId());
            }
            createDrugCatalog(drug, drugBase, store, drugCode, stock, manufacturerName);
        }
        if (drugBase != null && null != drugBase.getMaxOnceNumber()
                && StringUtils.isBlank(drugBase.getPlatformCode())) {
            drug.setMaxOnceNumber(drugBase.getMaxOnceNumber().toString());
            drug.setPlatformCode(drugBase.getPlatformCode());
        }

        return drug;
    }


    public Manufacture selectById(Integer id) {
        return manufactureMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据药店和药品编码查询药品目录
     *
     * @param drugCode
     * @param storeId
     * @return
     */
    public DrugCatalog findCatalog(String drugCode, Integer storeId) {
        return drugCatalogMapper.findByDrugCode(drugCode, storeId);
    }

    /**
     * 添加药品目录
     *
     * @param drug
     * @param base
     * @param store
     * @param drugCode
     * @param stock
     */

    private void createDrugCatalog(DrugVO drug, DrugBase base, DrugStore store,
                                   String drugCode, BigDecimal stock, String manufacture) {

        DrugCatalog catalog = new DrugCatalog();
        catalog.setDrugCode(drugCode);
        if (base != null) {
            catalog.setDrugId(base.getId());
        } else {
            catalog.setManufacture(manufacture);
        }

        catalog.setConversionRatio(1);
        catalog.setUnit(drug.getUnit());
        catalog.setGoodName(drug.getDrugName());
        catalog.setOnceNumber(drug.getUsingNumber());
        catalog.setOnceUnit(drug.getUsingUnit());
        catalog.setFreqName(drug.getFreq());
        catalog.setDosageForm(drug.getDosageForm());
        catalog.setSpec(drug.getSpec());
        catalog.setMaxOnceNumber(Integer.valueOf(drug.getMaxOnceNumber()));
        catalog.setDirection(drug.getDirection());
        catalog.setStandSpecRate(drug.getStandSpecRate());
        catalog.setStoreId(store.getId());
        catalog.setStatus(DrugCatalog.STATUS_NORMAL);
        catalog.setInitStock(stock);
        catalog.setLowerLimit(0);
        catalog.setUpperLimit(0);
        catalog.setCreateAt(new Date());
        catalog.setUpdateAt(new Date());
        drugCatalogMapper.insert(catalog);
    }

    @Autowired
    public void setDrugCatalogMapper(DrugCatalogMapper drugCatalogMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
    }

    @Autowired
    public void setDrugBaseMapper(DrugBaseMapper drugBaseMapper) {
        this.drugBaseMapper = drugBaseMapper;
    }

    @Autowired
    public void setManufactureMapper(ManufactureMapper manufactureMapper) {
        this.manufactureMapper = manufactureMapper;
    }
}
