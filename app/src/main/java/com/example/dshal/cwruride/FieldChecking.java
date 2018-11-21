package com.example.dshal.cwruride;

public class FieldChecking {
    public static boolean checkTime(String input) {
        input = input.replaceAll(" ", "");
        if(!input.contains(":"))
            return false;
        String hours = input.substring(0,input.indexOf(":"));
        String minutes = input.substring(input.indexOf(":")+1,input.indexOf(":")+3);
        String ampm = input.substring(input.indexOf(":"+3));
        int numHours, numMinutes;
        try{
            numHours = Integer.parseInt(hours);
            numMinutes = Integer.parseInt(minutes);
        }
        catch (java.lang.NumberFormatException letters) {
            return false;
        }
        return( numHours <= 12 &&
                numHours > 0 &&
                numMinutes < 60 &&
                (ampm.equals("am") || ampm.equals("pm"))
        );
    }

    public static boolean checkDate(String input) {
        input = input.replaceAll(" ","");
        int firstSlash = input.indexOf("/");
        int secondSlash = input.indexOf("/",firstSlash+1);
        if (firstSlash == -1 || secondSlash == -1) {
            return false;
        }
        String month = input.substring(0,firstSlash);
        String day = input.substring(firstSlash+1, secondSlash);
        String year = input.substring(secondSlash+1, input.length());
        int numMonth, numDay, numYear;
        try {
            numDay = Integer.parseInt(day);
            numMonth = Integer.parseInt(month);
            numYear = Integer.parseInt(year);
        }
        catch (java.lang.NumberFormatException letters){
            return false;
        }
        if (numYear <= 99) {
            numYear = numYear + 2000;
        }
        return( numMonth <=12 &&
                numMonth > 0 &&
                numDay <=31 &&
                numDay > 0 &&
                numYear >= 2018
        );
    }

    public boolean checkAddress(String input) {
        //Comes from Google Maps?
        return true;
    }
}
