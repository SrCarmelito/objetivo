package com.objetivo.controller;

import org.junit.Test;
import com.objetivo.DefaultTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.hamcrest.Matchers.containsString;
public class PessoaReportControllerTest extends DefaultTest {

    @Test
    public void gerar() throws Exception {
        mockMvc.perform(get("/report/gerar/2"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andExpect(header().string("Content-disposition", containsString("inline; filename=pessoa")));

    }
}
