package com.sprint.dash.api;

public class Demo01 {

    public static void main(String [] args){
        System.out.println(split("nih88ao你好hello", 9));
    }

    public static String split(String str, int length){
        String resultStr = "";
        int splitIndex = 0;

        //计算截取长度
        for(int i = 0; i < str.length() && length > 0; i++ ){
            char charStr = str.charAt(i);
            if(charStr > 65 && charStr < 122){
                length --;
            }else{
                length = length - 2;
            }
            splitIndex ++;
        }

        resultStr = str.substring(0, splitIndex);
        //替换数字
        resultStr = resultStr.replaceAll("\\d+", "");

        return resultStr;
    }

}
