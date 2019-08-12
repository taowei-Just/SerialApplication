package com.tao.utilslib.data;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Tao} on 2017-11-1111.
 */

public class MD5Util {

 
    public  static  String   md5FromFile (String path, boolean upper) throws IOException {
        FileInputStream fis = new FileInputStream(path);
//        String md5 = new String(Hex.encodeHex(DigestUtils.md5(getByteFromFile(fis))));
        String md5ByFile = getMd5ByFile(new File(path));
        return upper ?md5ByFile.toUpperCase() : md5ByFile.toLowerCase();
    } 
    
    public  static  String   md5FromStream(InputStream fis, boolean upper) throws IOException {
        String md5 = new String(Hex.encodeHex(DigestUtils.md5(fis)));
        return upper ? md5.toString().toUpperCase() : md5.toString();
    }


    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            byte[] digest = md5.digest();
            StringBuffer buffer = new StringBuffer();
            for (byte b : digest) {
                // 0xff 表示低8 位
                int number = b & 0xff;
                // int 32 位一个int 是四个字节一个字节8 位
                // 任何一个值& 等于0
                // & 1 = 1
                String hexString = Integer.toHexString(number);
                if (hexString.length() == 1) {
                    buffer.append("0" + hexString);
                } else {
                    buffer.append(hexString);
                }
            }

    
//            BigInteger bi = new BigInteger(1, digest);
//            value = bi.toString(16);
            value =buffer.toString().toUpperCase();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }




    /**Md5字符串加密
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 对字符串BASE64编码
     *
     * @param res
     *            源字符串
     * @return String
     */
    public static String encode(String res) {
        try {
            // android
//            Base64 base = new Base64();
            if ((res != null) && (!"".equals(res))) {
//                return new String(base.encode(res.getBytes()));
                // java
                return Base64Object.stringToBase64(res);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Base64编码
     * @param strSrc 需要编码的字符串
     * @return 编码后的字符串
     */
//       public static String encrypt(String strSrc){
//               String strBse64 ="";
//           //    byte[] bytes = Base64.encode(strSrc.getBytes(), Base64.NO_WRAP);
//
//               strBse64 = new String(bytes);
//               return strBse64;
//            }

    /**
     * BASE64字符串解码
     *
     * @param str
     *            BASE64字符串
     * @return String
     */
    public static String decode(String str) {
        try {
            byte[] bytes= new Base64().decode(str.getBytes());
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
