package com.ins.csdn.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具
 */
public class MD5 {

    private MD5() {}

    public static String encrypt(String src){
        byte[] input = src.getBytes();

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input);
            input = digest.digest();
            int length = input.length;
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0;i<length;i++)
            {
                //将字符转变成对应的ASSIC值
                int val = ((int)input[i])&0xff;
                //转变成对应的值后小于4位
                if(val<16)
                {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(val));
//              strBuff.append(hexDigits[val%16]);
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return src;
    }

}
