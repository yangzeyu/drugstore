package com.jsmscp.dr.service.report;

import java.util.HashMap;

public interface DrugBaseReportService {

    HashMap<String, Object> findDrugBaseReport(String goodName, Long manufactureId, String platFormCode,
                                               String startTime, String endTime, Integer pageNo);
}
