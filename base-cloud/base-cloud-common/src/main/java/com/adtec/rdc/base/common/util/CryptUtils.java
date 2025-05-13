package com.adtec.rdc.base.common.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class CryptUtils {
	
	private static final Base64.Decoder decoder = Base64.getDecoder();
	private static final Base64.Encoder encoder = Base64.getEncoder();
	
	private final static String encoding = "UTF-8";
	public static final String key = "MjAxMDAxMDE=";
	public static final String AES_KEY = "RDC_AES_CBC_KEY_";
	public static final String AES_IV = "RDC_AES_CBC_IV__";
	
	/**
	 * 加密字符串
	 */
	public static String getEncString(String str, String key) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = symmetricEncrypto(str.getBytes(encoding), key);
				result = encoder.encodeToString(encodeByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密字符串
	 */
	public static String getDesString(String str, String key) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = decoder.decode(str);
				byte[] decoder = CryptUtils.symmetricDecrypto(encodeByte, key);
				result = new String(decoder, encoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 对称加密方法
	 * 
	 * @param byteSource
	 *            需要加密的数据
	 * @return 经过加密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricEncrypto(byte[] byteSource, String strKey) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 对称解密方法
	 * 
	 * @param byteSource
	 *            需要解密的数据
	 * @return 经过解密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricDecrypto(byte[] byteSource, String strKey) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 取随机KEY
	 */
	public static String getRandomKey(){
		String key = String.valueOf(Math.round(Math.random()*100000000));
		return encoder.encodeToString(key.getBytes());
	}
	/**
	 * AES CBC模式加密
	 * @param code
	 * @param key
	 * @param iv
	 * @return
	 */
	public static String getCBCEncrypt(String code, String key, String iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			
			byte[] dataBytes = code.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
 
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
 
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
 
            return encoder.encodeToString(encrypted);
		}catch(Exception e) {
			return code;
		}
	}
	/**
	 * AES CBC模式解密（不支持空格）
	 * @param data
	 * @param key null表示使用默认的
	 * @param iv null表示使用默认的
	 * @return
	 */
	public static String getCBCDesEncrypt(String data, String key, String iv) {
		if (key == null) {
			key = AES_KEY;
		}
		if (iv == null) {
			iv = AES_IV;
		}
        try {
            byte[] encrypted1 = decoder.decode(data);
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            return data;
        }
    }
	
	
	public static void main(String[] args) throws Exception {
		String strEnc = CryptUtils.getEncString("admin", key);// 加密字符串,
//		// 返回String的密文
		System.out.println(strEnc);
		String strDes = CryptUtils.getDesString(strEnc,key);// 把String
//		// 类型的密文解密
		System.out.println(strDes);
		
		String strEnc1 = CryptUtils.getCBCEncrypt("admin", AES_KEY, AES_IV);// 加密字符串,
//		// 返回String的密文
		System.out.println(strEnc1);
		String strDes1 = CryptUtils.getCBCDesEncrypt(strEnc1, AES_KEY, AES_IV);// 把String
//		// 类型的密文解密
		System.out.println("["+ strDes1+"]");
	}
}