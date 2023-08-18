package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportRequestTest {

    @Test
    public void testExportRequestModel(){
        ExportRequest exportRequest = ExportRequest.builder()
                .typeFile("csv")
                .keyWord("branch")
                .practiceName("DS")
                .coeCoreTeamName("QuangDo")
                .branchName("VN")
                .sortBy("branch")
                .fromDateStr("2023-05-10")
                .toDateStr("2023-05-15")
                .itemPerPage(10)
                .isAscending(true)
                .build();
        exportRequest.setPageNo(2);
        assertEquals("csv",exportRequest.getTypeFile());
        assertEquals("branch",exportRequest.getKeyWord());
        assertEquals("DS",exportRequest.getPracticeName());
        assertEquals("QuangDo",exportRequest.getCoeCoreTeamName());
        assertEquals("VN",exportRequest.getBranchName());
        assertEquals("branch",exportRequest.getSortBy());
        assertEquals("2023-05-10",exportRequest.getFromDateStr());
        assertEquals("2023-05-15",exportRequest.getToDateStr());
        assertEquals(10,exportRequest.getItemPerPage());
        assertEquals(2,exportRequest.getPageNo());
        assertTrue(exportRequest.isAscending());

    }
}