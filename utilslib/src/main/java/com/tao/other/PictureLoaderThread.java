package com.tao.other;

public class PictureLoaderThread extends Thread {
 
 
    public static String getNameForURl(String barpicture) {
        String name = "";
        if (barpicture != null) {
            String[] split = barpicture.split("/");
            try {
                name = split[split.length - 1];
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return name;

    }

 
}
