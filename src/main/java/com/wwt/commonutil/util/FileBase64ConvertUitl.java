package com.wwt.commonutil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileBase64ConvertUitl {
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */

	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);

	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
//		 base64Code = encodeBase64File("D:/anzai1.jpg");
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();

	}

	/**
	 * 将base64字符保存文本文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void toFile(String base64Code, String targetPath) throws Exception {
		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	public static void main(String[] args) {
		try {
			System.out.println(encodeBase64File("D:/测试照片/c652b0ad-9ad3-4ca9-9556-cd44dcf131bf.png").replace("\r\n",""));
			System.out.println("============");
			System.out.println(encodeBase64File("D:/测试照片/localFile.png").replace("\r\n",""));
//			decoderBase64File(base64Code, "D:/2.tif");
//			toFile(base64Code, "D:\\three.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
