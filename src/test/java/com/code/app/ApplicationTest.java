package com.code.app;


import com.code.app.dto.MedCodeDetails;
import com.code.app.repo.MedCodeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MedCodeRepository medCodeRepository;
    
    ObjectMapper objectMapper = new ObjectMapper();

    List<MedCodeDetails> medCodeDetails = null;

    @Before
    public void init() throws IOException, ParseException {
        medCodeDetails = prepareTestData("src\\main\\resources\\exercise.csv");
    }

    @Test
    public void testUpload() throws Exception {
        String requestBody = prepareReq(medCodeDetails);
        mockMvc.perform(post("/code/upload").content(requestBody).contentType("application/json"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testFetchAll() throws Exception {

        medCodeRepository.saveAll(medCodeDetails);

        mockMvc.perform(get("/code/fetchAll").contentType("application/json")).
                 andDo(print())
                .andExpect(status().isOk());

        List<MedCodeDetails> result = medCodeRepository.findAll();

        Assert.assertEquals(medCodeDetails.size(), result.size());

    }

    @Test
    public void testFetchById()  throws Exception {

        medCodeRepository.saveAll(medCodeDetails);

        mockMvc.perform(get("/code/fetchById").param("code","271636001").contentType("application/json")).
                andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void testDelete() throws Exception {
        medCodeRepository.saveAll(medCodeDetails);
        mockMvc.perform(delete("/code/deleteAll")). andDo(print())
                .andExpect(status().isOk());
    }

    private String prepareReq(List<MedCodeDetails> medCodeDetails) throws JsonProcessingException {
        return objectMapper.writeValueAsString(medCodeDetails);
    }

    private List<MedCodeDetails> prepareTestData(String csvFilePath) throws IOException, ParseException {
        List<MedCodeDetails> modelList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> csvData = reader.readAll();

            // Skip the header row if it exists
            boolean skipHeader = true;

            for (String[] row : csvData) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                // Create a new Model object and populate its fields
                String dateString = "dd-MM-yyyy";
                MedCodeDetails model = MedCodeDetails.builder().

                        source(row[0]).
                        codeListCode(row[1]).
                        code(row[2]).
                        displayValue(row[3]).
                        longDescription(row[4]).
                        fromDate(convertStringToDate(dateString,row[5])).
                        toDate(convertStringToDate(dateString,row[6])).
                        sortingPriority(isEmpty(row[7])?null:Integer.parseInt(row[7])).
                        build();

                modelList.add(model);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return modelList;
    }

    public static Date convertStringToDate(String dateString, String value) throws ParseException {
        if (isEmpty(value)) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);
        return dateFormat.parse(value);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
