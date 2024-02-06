package com.pblgllgs.sb2jaspernutrition.model;
/*
 *
 * @author pblgl
 * Created on 05-02-2024
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacroNutrient {
    private String macroNutrientName;
    private int macroNutrientValue;
}
