package com.tao.utils;

public class NumberFormatUtil {



    public static   double formartDigit(double number , int digit){
        String format = String.valueOf(number);
        if (!format.contains("."))
            return Double.valueOf(format );

        String[] split = format.split("\\.");

        if (split[1].length()==0||split[1].length()<=digit )
            return Double.valueOf(format);
        String s = split[1];
        int len = s.length();

        if (len - digit <=0 )
            return Double.valueOf(format );

        String substring = s.substring(0, digit);
        String substring1 = s.substring(digit, digit + 1);
        if (Integer.valueOf(substring1) >=5)
            substring =String.valueOf(Integer.valueOf(substring)+1);

        String s1 = split[0] + "." + substring;



        return Double.valueOf(s1);
    }

    public static  void main(String [] a){

        double  i1 = 12.000;
        double  i2 = 12.024;
        double  i3 = 12.025;
        double  i4 = 12.1253;
        double  i5 = 12.0256;
        double  i6 = 12.3546846;


        System.err.println( " " + i1 + " 格式化：" + formartDigit ( i1,3));
        System.err.println( " " + i2 + " 格式化：" + formartDigit ( i2,3));
        System.err.println( " " + i3 + " 格式化：" + formartDigit ( i3,3));
        System.err.println( " " + i4 + " 格式化：" + formartDigit ( i4,3));
        System.err.println( " " + i5 + " 格式化：" + formartDigit ( i5,3));
        System.err.println( " " + i6 + " 格式化：" + formartDigit ( i6,3));
        System.err.println( " " + 123 + " 格式化：" + formartDigit ( 123,3));
        System.err.println( " " + 123 + " 格式化：" + formartDigit ( 0,3));
        System.err.println( " " + 123 + " 格式化：" + formartDigit ( 0.1,3));
        System.err.println( " " + 123 + " 格式化：" + formartDigit ( 0.0001,3));


    }



}
