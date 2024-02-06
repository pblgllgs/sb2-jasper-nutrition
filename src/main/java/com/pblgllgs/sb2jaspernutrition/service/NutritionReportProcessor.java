package com.pblgllgs.sb2jaspernutrition.service;
/*
 *
 * @author pblgl
 * Created on 05-02-2024
 *
 */

import com.pblgllgs.sb2jaspernutrition.model.Food;
import com.pblgllgs.sb2jaspernutrition.model.MacroNutrient;
import com.pblgllgs.sb2jaspernutrition.model.Nutrition;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NutritionReportProcessor {

    private final MessageSource messageSource;


    public ByteArrayOutputStream generateReport(Locale locale) throws JRException {
        String filePath = "E:\\Spring\\jasper-reports\\sb2-jasper-nutrition\\src\\main\\resources\\templates\\nutritionreport.jrxml";

        Nutrition protein = new Nutrition("Protein", 62, 83, "g");
        Nutrition carbohydrates = new Nutrition("Carbohydrates", 180, 206, "g");
        Nutrition fiber = new Nutrition("Fiber", 20, 38, "g");
        Nutrition sugars = new Nutrition("sugars", 68, 62, "g");
        Nutrition fat = new Nutrition("Fat", 60, 55, "g");
        Nutrition cholesterol = new Nutrition("Cholesterol", 84, 300, "mg");
        Nutrition sodium = new Nutrition("Sodium", 2200, 2300, "mg");
        Nutrition potassium = new Nutrition("Potassium", 2000, 3500, "mg");
        Nutrition calcium = new Nutrition("Calcium", 62, 100, "%");
        Nutrition iron = new Nutrition("Iron", 38, 100, "%");

        List<Nutrition> nutritionList = List.of(
                protein,
                carbohydrates,
                fiber,
                sugars,
                fat,
                cholesterol,
                sodium,
                potassium,
                calcium,
                iron
        );

        MacroNutrient carbMacroNutrient = new MacroNutrient("Carbohydrates", 48);
        MacroNutrient fatMacroNutrient = new MacroNutrient("Fat", 32);
        MacroNutrient proteinMacroNutrient = new MacroNutrient("Protein", 20);

        List<MacroNutrient> macroNutrientList = List.of(carbMacroNutrient, fatMacroNutrient, proteinMacroNutrient);

        JRBeanCollectionDataSource nutritionDatasource = new JRBeanCollectionDataSource(nutritionList);
        JRBeanCollectionDataSource macroNutrientDataSource = new JRBeanCollectionDataSource(macroNutrientList);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "pbl");
        parameters.put("lastName", "gllgs");
        parameters.put("dob", "07/09/1972");
        parameters.put("age", 50);
        parameters.put("nutritionDataset", nutritionDatasource);
        parameters.put(("macroNutrientDataSet"), macroNutrientDataSource);
        parameters.put("foodReport", getFoodReport());
        parameters.put("foodParameter", getFoodParameter());
        setLocalizedParameters(parameters,locale);
        JasperReport report = JasperCompileManager.compileReport(filePath);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter jrPdfExporter = new JRPdfExporter();
        SimplePdfExporterConfiguration simplePdfExporterConfiguration = new SimplePdfExporterConfiguration();
        simplePdfExporterConfiguration.setCompressed(true);
        jrPdfExporter.setConfiguration(simplePdfExporterConfiguration);
        jrPdfExporter.setExporterInput(new SimpleExporterInput(print));
        jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        jrPdfExporter.exportReport();
        return byteArrayOutputStream;

    }

    private void setLocalizedParameters(Map<String, Object> parameters, Locale  locale){
        MessageSourceResourceBundle messageSourceResourceBundle= new MessageSourceResourceBundle(messageSource,locale);
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, messageSourceResourceBundle);
        parameters.put(JRParameter.REPORT_LOCALE,locale);
    }

    private static JRBeanCollectionDataSource getFoodDataSource() {
        Food banana = new Food("banana", "breakfast", 0, 28, 1);
        Food acovado = new Food("acovado", "breakfast", 12, 13, 3);
        Food milk = new Food("milk", "breakfast", 8, 12, 8);
        Food chicken = new Food("chicken", "lunch", 2, 0, 26);
        Food rice = new Food("rice", "lunch", 0, 45, 26);
        Food egg = new Food("egg", "lunch", 5, 0, 6);
        Food potato = new Food("potato", "lunch", 5, 37, 4);
        Food oats = new Food("oats", "dinner", 5, 51, 13);
        List<Food> foodList = List.of(banana, acovado, milk, chicken, rice, egg, potato, oats);

        return new JRBeanCollectionDataSource(foodList);
    }

    private static Map<String, Object> getFoodParameter() {
        Map<String, Object> foodParameter = new HashMap<>();
        foodParameter.put("foodDataset", getFoodDataSource());
        return foodParameter;
    }

    private static JasperReport getFoodReport() {
        String filePath = "E:\\Spring\\jasper-reports\\sb2-jasper-nutrition\\src\\main\\resources\\templates\\food_nutrition.jrxml";
        JasperReport report = null;

        try {
            report = JasperCompileManager.compileReport(filePath);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return report;
    }
}
