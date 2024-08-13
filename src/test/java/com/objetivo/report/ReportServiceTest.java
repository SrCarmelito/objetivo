package com.objetivo.report;

import com.objetivo.DefaultTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class ReportServiceTest extends DefaultTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void emissaoRelatorio() {
        assertThat(reportService.exportarPDF(2L).length, greaterThan(1));
    }

}
