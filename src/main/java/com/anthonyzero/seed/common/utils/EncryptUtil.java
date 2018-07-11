package com.anthonyzero.seed.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class EncryptUtil {
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String UTF_8 = "UTF-8";
	public static final char PADDING_CHAR = '\0';
	static String IV = "abcabcabcabcabca";
	static String encryptionKey = "abcdef0123456789";

	public static String enc(String s, String salt) {
		String md5s = MD5(s);
		return Sha256Hash(md5s, salt);
	}

	public static String Sha256Hash(String s, String salt) {
		return new Sha256Hash(s, salt).toHex();
	}

	/**
	 * 生成32位小写md5码
	 * 
	 * @param s
	 * @return
	 */
	public static String md5(String s) {

		try {
			// 得到一个信息摘要器
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(s.getBytes());
			StringBuffer buffer = new StringBuffer();
			// 把每一个byte 做一个与运算 0xff;
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;// 加盐
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					buffer.append("0");
				}
				buffer.append(str);
			}

			// 标准的md5加密后的结果
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 生成32位大写MD5码
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encrypt(String plainText) throws Exception {
		plainText = padding(plainText);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(UTF_8), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UTF_8)));
		byte[] bytes = cipher.doFinal(plainText.getBytes(UTF_8));
		String s = new String(bytes, ISO_8859_1);
		return s;
	}

	public static String decrypt(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(UTF_8), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UTF_8)));
		byte[] decoded = cipherText.getBytes(ISO_8859_1);
		String s = new String(cipher.doFinal(decoded), UTF_8);
		return unpadding(s);
	}

	static String padding(String data) {
		StringBuilder sb = new StringBuilder(data);
		int lengthFactor = 16; // length has to be multiple of 16
		while (sb.length() % lengthFactor != 0) {
			sb.append(PADDING_CHAR);
		}
		return sb.toString();
	}

	static String unpadding(String data) {
		StringBuilder sb = new StringBuilder(data);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == PADDING_CHAR) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(md5("123456"));
		System.out.println(MD5("123456"));
	}

}