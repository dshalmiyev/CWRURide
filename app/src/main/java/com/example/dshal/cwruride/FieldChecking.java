package com.example.dshal.cwruride;

public class FieldChecking {
    public boolean checkTime(String input) {
        input = input.replaceAll(" ", "");
        if (!input.matches("\\d")) {
            return false;
        }
        String hours = input.substring(0,input.indexOf(":")-1);
        String minutes = input.substring(input.indexOf(":")+1,input.indexOf(":")+2);
        return( Integer.parseInt(hours) <= 12 &&
                Integer.parseInt(hours) > 0 &&
                Integer.parseInt(minutes) < 60 &&
                (input.toLowerCase().contains("am") || input.toLowerCase().contains("pm"))
        );
    }

    public boolean checkDate(String input) {
        int firstSlash = input.indexOf("/");
        int secondSlash = input.indexOf("/",firstSlash+1);
        if (firstSlash == -1 || secondSlash == -1) {
            return false;
        }
        String month = input.substring(0,firstSlash-1);
        String day = input.substring(firstSlash+1, secondSlash-1);
        String year = input.substring(secondSlash+1, input.length()-1);
        int numYear = Integer.parseInt(year);
        if (numYear <= 99) {
            numYear = numYear + 2000;
        }
        return( Integer.parseInt(month) <=12 &&
                Integer.parseInt(month) > 0 &&
                Integer.parseInt(day) <=31 &&
                Integer.parseInt(day) > 0 &&
                numYear >= 2018
        );
    }
}
