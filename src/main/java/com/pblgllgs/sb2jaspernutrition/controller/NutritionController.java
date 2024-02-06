package com.pblgllgs.sb2jaspernutrition.controller;
/*
 *
 * @author pblgl
 * Created on 05-02-2024
 *
 */

import com.pblgllgs.sb2jaspernutrition.service.NutritionReportProcessor;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionReportProcessor nutritionReportProcessor;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getNutritionReport(@RequestParam("lang") String language) throws JRException {
        Locale locale = StringUtils.parseLocale(language);
        ByteArrayOutputStream byteArrayOutputStream = nutritionReportProcessor.generateReport(locale);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new ResponseEntity<>(byteArray, httpHeaders, HttpStatus.OK);
    }
}
