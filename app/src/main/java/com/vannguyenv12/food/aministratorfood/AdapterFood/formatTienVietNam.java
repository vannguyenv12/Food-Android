package com.vannguyenv12.food.aministratorfood.AdapterFood;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class formatTienVietNam {
    public DecimalFormat kieuTienVietNam()
    {
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalformat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols decimal = new DecimalFormatSymbols();
        decimal.setCurrencySymbol("VNĐ");
        decimalformat.setDecimalFormatSymbols(decimal);
        return decimalformat;
    }
}
