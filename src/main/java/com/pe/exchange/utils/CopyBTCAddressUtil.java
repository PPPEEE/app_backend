package com.pe.exchange.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.Security;

/**
 * 仿比特币地址生成,根据用户ID生成
 */
public class CopyBTCAddressUtil {

    private  static   byte[] PRIVATE_VERSION_PREFIX=new byte[]{(byte)0x80};
    private  static   byte[] ADDR_VERSION_PREFIX=new byte[]{0x05};

    static {
        //加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String generateAddress(Integer userId) throws Exception {


        byte[] bytes = SHA256Util.sha256(userId.toString());
        //在私钥前添加版本前缀
        bytes=appendBytes(PRIVATE_VERSION_PREFIX,bytes);
        //得到私钥哈希,这里当公钥HASH用
        byte[] publicHash=encodeRipeMD160(SHA256Util.sha256(bytes));
        //在公钥前添加地址版本前缀
        publicHash=appendBytes(ADDR_VERSION_PREFIX,publicHash);
        //双HASH
       byte[] doubleHash=SHA256Util.sha256(SHA256Util.sha256(publicHash));
        //从双HASH得到4位校验码
        byte[]checkBytes=new byte[4];
        System.arraycopy(doubleHash,0,checkBytes,0,4);
        //最终HASH
         publicHash=appendBytes(publicHash,checkBytes);
         //base58
        return Base58.encode(publicHash);
    }
    /**
     * RipeMD160消息摘要
     * @param data 待处理的消息摘要数据
     * @return byte[] 消息摘要
     * */
    private static byte[] encodeRipeMD160(byte[] data) throws Exception{

        //初始化MessageDigest
        MessageDigest md=MessageDigest.getInstance("RipeMD160");
        //执行消息摘要
        return md.digest(data);

    }
    /**
     * RipeMD160Hex消息摘要
     * @param data 待处理的消息摘要数据
     * @return String 消息摘要
     * **/
    private static String encodeRipeMD160Hex(byte[] data) throws Exception{
        //执行消息摘要
        byte[] b=encodeRipeMD160(data);
        //做十六进制的编码处理
        return new String(Hex.encode(b));
    }


    private static byte[] appendBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }


}
