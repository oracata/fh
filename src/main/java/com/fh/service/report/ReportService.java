package com.fh.service.report;

import java.util.List;

import javax.annotation.Resource;

import com.fh.entity.report.ReportDay;
import com.fh.util.DataGridView;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;



@Service("reportService")
public class ReportService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;






    public DataGridView listArea(ReportDay reportday) throws Exception {

        Page<Object> page= PageHelper.startPage(reportday.getPageNumber(),reportday.getPageSize());
        List<ReportDay> data= (List<ReportDay>) dao.findForList("ReportAreaMapper.listReportDay",reportday);
        return new DataGridView(page.getTotal(),data);

    }


}