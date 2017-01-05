/**
 * 
 */
package com.fufang.bi.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author jiakai.chen
 *
 */
public class StringCoder {

	static BASE64Encoder be = new BASE64Encoder();
	static BASE64Decoder bd = new BASE64Decoder();

	public static String encoder(String data) {
		byte[] buff = data.getBytes();
		for (int i = 0; i < buff.length; i++) {
			buff[i] = (byte) ~buff[i];
		}
		return be.encode(buff);
	}

	public static String decoder(String data) throws Exception {
		byte[] buff = bd.decodeBuffer(data);
		for (int i = 0; i < buff.length; i++) {
			buff[i] = (byte) ~buff[i];
		}
		return new String(buff);
	}
}
