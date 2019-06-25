package com.wss.socket_client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Common {
    /**
     * @Title:bytes2HexString
     * @Description:字节数组转16进制字符串
     * @param b
     *            字节数组
     * @return 16进制字符串
     * @throws
     */
    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    /**
     * @Title:bytes2HexString
     * @Description:字节数组转16进制字符串
     * @param b
     *            字节数组
     * @return 16进制字符串
     * @throws
     */
    public static String bytes2HexKongString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        if(b== null){
            return "";
        }
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(" "+ hex.toUpperCase());
        }
        return result.toString().trim();
    }

    /**
     * @Title:bytes2HexString
     * @Description:字节数组转16进制字符串
     * @param b
     *            字节数组
     * @return 16进制字符串
     * @throws
     */
    public static String bytes2HexStringLen(byte[] b,int len) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < len; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString().trim();
    }


    /**
     * @Title:hexString2Bytes
     * @Description:16进制字符串转字节数组
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * @Title:hexString2Bytes
     * @Description:16进制字符串转字节数组
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static byte[] hexString4Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart
     *            字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart
     *            字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string4HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(addZeroForNum(strHex,4));
        }
        return hexString.toString();
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(str).append("0");// 左(前)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }


    /**
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
        }
        return temp;
    }

    /**
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static String hexString2StringK(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            int mm = Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
            if(mm != 0) {
                temp = temp
                        + (char) mm;
            }
        }
        return temp;
    }



    /**
     * @Title:char2Byte
     * @Description:字符转成字节数据char-->integer-->byte
     * @param src
     * @return
     * @throws
     */
    public static Byte char2Byte(Character src) {
        return Integer.valueOf((int)src).byteValue();
    }

    /**
     * @Title:intToHexString
     * @Description:10进制数字转成16进制
     * @param a 转化数据
     * @param len 占用字节数
     * @return
     * @throws
     */
    public static String intToHexString(int a,int len){
        len<<=1;
        String hexString = Integer.toHexString(a);
        int b = len -hexString.length();
        if(b>0){
            for(int i=0;i<b;i++)  {
                hexString = "0" + hexString;
            }
        }
        return hexString;
    }

    public static byte[] reverseArray(byte[] Array) {
        byte[] new_array = new byte[Array.length];
        for (int i = 0; i < Array.length; i++) {
            // 反转后数组的第一个元素等于源数组的最后一个元素：
            new_array[i] = Array[Array.length - i - 1];
        }
        return new_array;
    }

    public static String listToString(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }

    public static String  getCpnName(String  str){
         int n = str.indexOf("55FF09",0);
         String valueStr = "";
         if(n>= 0){
             valueStr = str.substring(n+92,n+92+32);
             valueStr = Common.hexString2StringK(valueStr);
         }
        return  valueStr;
    }

    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        if(src.length -begin  <count ){
            count  = src.length -begin;
        }
        byte[] bs = new byte[count];


        for (int i=begin;i<begin+count ; i++)
            bs[i-begin] = src[i];
        return bs;
    }

    /**
     * Get XML String of utf-8
     *
     * @return XML-Formed string
     */
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     *
     * @param s 原串
     * @return
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;

                    b = Character.toString(c).getBytes("utf-8");

                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append(Integer.toHexString(k).toUpperCase());
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }

    //Byte数组Copy
    public static void MemcpyByte(byte[] byte1,int start,byte[] byte2,int byte2Len)
    {

        for(int i=0;i<byte2Len;i++)
        {
            byte1[i+start]=byte2[i];
        }
    }

    //Byte数组Copy
    public static void MemcpyByte_1(byte[] byte1,int start,byte[] byte2,int byte2Start,int byte2Len)
    {

        for(int i=0;i<byte2Len;i++)
        {
            byte1[i+start]=byte2[i+byte2Start];
        }
    }


    //Byte内存Copy
    public static void MemmoveByte(byte[] byte1 ,int start,int memStart,int len)
    {
        for(int i=0;i<len;i++)
        {
            byte1[i+start]=byte1[i+memStart];
        }
        /*
        for(int i=len-1;i>=start;i--)
        {
            byte1[i-start]=byte1[i];
        }
        */
    }


    /**
     * int到byte[]
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        //由低位位到高位位
        result[0] = (byte)((i) & 0xFF);
        result[1] = (byte)((i >> 8) & 0xFF);
        result[2] = (byte)((i >> 16) & 0xFF);
        result[3] = (byte)((i >>24 )  & 0xFF);
        return result;
    }

    /**
     * int到byte[]
     * @param i
     * @return
     */
    public static byte[] shortToByteArray(int i) {
        byte[] result = new byte[2];
        //由高位到低位
        result[0] = (byte)((i >> 8) & 0xFF);
        result[1] = (byte)(i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes,int start) {
        int value= 0;
        int shift=0x00;
        int v=0x00;
        //由高位到低位
        for (int i = 0; i < 4; i++) {
            shift=  i * 8;
            v = (bytes[i+start] & 0x000000FF);
            value += v<< shift;//往高位游
        }
        return value;
    }


    /**
     * byte[]转int
     * @param bytes
     * @return
     */
    public static int byteArrayToShort(byte[] bytes,int start) {
        int value= 0;

        int v1=((bytes[start+1]&0xFF)<< 8);
        int v2=((bytes[start]&0xFF));

        value= v1+v2;


        //由高位到低位

        /*for (int i = 0; i < 2; i++) {
            int shift= (2 - 1 - i) * 8;
            value +=(bytes[i+start] & 0x00FF) << shift;//往高位游
        }
        */
        return value;
    }

    public static byte[] getQuyu(String name){
        //取得用户设置的名字
        byte buffers[] = new byte[16];
        name  = Common.convertStringToUTF8(name);
        byte textName[] = Common.hexString2Bytes(Common.string4HexString(name));
        for(int m= 0; m < buffers.length;m++){
            if(m < textName.length){
                buffers[m] =  textName[m];
            }else{
                buffers[m] =  (byte)00;
            }

        }
        return  buffers;
    }

    /**
     * l两个数组进行比较
     * @param src
     * @param des
     * @param length
     * @return
     */
    public  static boolean ByteCmp(byte[] src, byte[] des, int length)
    {
        if(src.length != des.length)
        {
            return false;
        }
        for (int i = 0; i < length; i++)
        {
            if (src[i] != des[i])
                return false;
        }

        return true;


    }

    public  static  int  BitGet(int number,int pos){
       return   number&(0x01<<pos);
    }
    public static String getBitString(int number,int len){
        String strValue ="";
        for(int i =0; i<len;i++){
            if(BitGet(number,i) == 0x00)
            {
                strValue+="0";
            }else {
                strValue+="1";
            }
        }
        return strValue;
    }

    public static  byte[] getFFBuffer(int len){
        byte buffes[] = new byte[len];
        for(int m= 0; m < len;m++){
            buffes[m] =  (byte)0xFF;
        }
        return buffes;
    }
}
