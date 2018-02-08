package com.lelasoft.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


public class Util {
	private final static String API_KEY = "AIzaSyCvGVia1lq7vrbF8VDSnb_aMqZ3CWB8x_8";

	public static String sendMulptipleNotifiaction(String title,String message,
			String devices) {
		/**
		 * GCM authentication KEY.
		 */
		Sender sender = new Sender(API_KEY);
		Message msg = new Message.Builder()
		.addData("title", title)
		.addData("message", message)
		.build();
		String str = null;	
		try {
			Result r = sender.send(msg, devices, 5);
			if (r.getMessageId() != null) {
				str = r.getMessageId();
			} else {
				String error = r.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	

	public static String sendChatNotification(String title,String oppid, String message,
			String devices) {
		/**
		 * GCM authentication KEY.
		 */
		Sender sender = new Sender(API_KEY);
		Message msg = new Message.Builder()
		.addData("title", title)
		.addData("message", message)
		.addData("oppid", oppid)
		.build();
		String str = null;	
		try {
			Result r = sender.send(msg, devices, 5);
			if (r.getMessageId() != null) {
				str = r.getMessageId();
			} else {
				String error = r.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}


	public static String updateAvatar(String image64)
			throws FileNotFoundException, IOException {
		// Note preferred way of declaring an array variable
		byte[] picData = DatatypeConverter.parseBase64Binary(image64);
		String path = System.getenv("OPENSHIFT_DATA_DIR") + "photo.jpg";
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(path);
			stream.write(picData);
		} finally {
			if (stream != null)
				stream.close();
		}
		return uploadPic(new File(path));
	}

	private static String uploadPic(File finalFile) {
		Map<String, String> config = new HashMap<String, String>();
		config.put("cloud_name", "khaledlela");
		config.put("api_key", "638646472432677");
		config.put("api_secret", "iywVmtJehP2OkSDUL1JKgPipy1M");
		Cloudinary cloudinary = new Cloudinary(config);
		try {
			Map uploadResult = cloudinary.uploader().upload(finalFile,
					ObjectUtils.emptyMap());
			return uploadResult.get("url").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
