package com.spacialist.service;

import android.text.format.DateFormat;
import android.util.Log;

import com.spacialist.data.Constants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static Date convertDate(String date) {
        Date convertDate = null;
        try {
            convertDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("String date: " + date + "\tConverted: " + convertDate.toString());
        return convertDate;
    }

    public static String convertToTransactionDateFromDBString(String dbTransactionDate) {
        Date convertDate = null;
        try {
            convertDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_DB).parse(dbTransactionDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strConvert = (String) DateFormat.format(Constants.DATE_TIME_FORMAT, convertDate);
        System.out.println("String date: " + dbTransactionDate + "\tConverted: " + convertDate.toString());
        return strConvert;
    }

    public static String convertFromYYYYMMDD(String dbSelectedDate) {
        Date convertDate = null;
        try {
            convertDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_YYYYMMDD).parse(dbSelectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strConvert = (String) DateFormat.format(Constants.DATE_TIME_FORMAT_DDMMMYYYY, convertDate);
        System.out.println("String date: " + dbSelectedDate + "\tConverted: " + convertDate.toString());
        return strConvert;
    }

    public static String convertToTimeString(String dbSelectedTime) {
        Date convertDate = null;
        try {
            convertDate = new SimpleDateFormat(Constants.TIME_FORMAT).parse(dbSelectedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strConvert = (String) DateFormat.format(Constants.TIME_FORMAT_AMPM, convertDate);
        System.out.println("String date: " + dbSelectedTime + "\tConverted: " + convertDate.toString());
        return strConvert;
    }

    public static Date convertTimeToDate(String time) {
        Date convertDate = null;
        try {
            convertDate = new SimpleDateFormat(Constants.TIME_FORMAT).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("String date: " + time + "\tConverted: " + convertDate.toString());
        return convertDate;
    }

    public static double convertUSDToPeso(double peso) {
        return peso * Constants.PESO_TO_USD;
    }

    public static double convertPesoToUSD(double usd) {
        return usd / Constants.PESO_TO_USD;
    }

    public static String convertDurationToString(Date duration) {
        StringBuilder sb = new StringBuilder();
        String durationStr = (String) DateFormat.format("hh:mm", duration); // 00:30
        if (durationStr.startsWith("12")) {
            sb.append("00").append(durationStr.substring(2));
        }
        return sb.toString();
    }

    public static int getHrOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR);
    }

    public static int getMinsOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static String getDurationTimeStr(String startTime, int hrDuration, int minDuration) {
        String endDurationString = null;
        SimpleDateFormat timeStringFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = timeStringFormat.parse(startTime);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            Calendar cTotal = (Calendar) c1.clone();
            cTotal.add(Calendar.HOUR_OF_DAY, hrDuration);
            cTotal.add(Calendar.MINUTE, minDuration);
            endDurationString = timeStringFormat.format(cTotal.getTime());
            System.out.println("(StartTime) = " + startTime);
            System.out.println("(Duration) = " + endDurationString);
        } catch (ParseException ex) {
            Log.i("Exception", ex.getMessage());
        }

        return endDurationString;
    }

    public static String formatDateToYYYYMMDD(String selectedDate) {
        String dateString = null;
        SimpleDateFormat backendDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        try {
            Date date = parseFormat.parse(selectedDate);
            dateString = backendDateFormat.format(date);
            System.out.println("(DateStringBackend) = " + dateString);
        } catch (ParseException ex) {
            Log.i("Exception", ex.getMessage());
        }

        return dateString;
    }

    public static String calculateUnitPrice(String servicePrice) {
        BigDecimal servicePriceB = new BigDecimal(servicePrice);
        BigDecimal unitPriceB = servicePriceB.divide(Constants.VAT_112_PERCENT, 2, BigDecimal.ROUND_HALF_EVEN);
        System.out.println("UNIT PRICE B= " + unitPriceB.toPlainString());
        unitPriceB = unitPriceB.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        System.out.println("UNIT PRICE Rounded= " + unitPriceB.toPlainString());
        return unitPriceB.toPlainString();
    }

    public static String calculateVatPercentage(String unitPrice) {
        BigDecimal vatPercentage = new BigDecimal(unitPrice)
                .multiply(Constants.VAT_12_PERCENT);
        System.out.println("VAT PERCENT B= " + vatPercentage.toPlainString());
        vatPercentage = vatPercentage.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        String vatPercentageStr = String.valueOf(vatPercentage.toPlainString());
        System.out.println("VAT PERCENT Rounded= " + vatPercentageStr);
        return vatPercentageStr;
    }
}
