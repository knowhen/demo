package com.example.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	private static final String FILE_FOLDER = "photos/";

	public static String getFileExtensionName(String fileName) {
		if (null != fileName) {
			int i = fileName.lastIndexOf('.');
			if (i > -1)
				return fileName.substring(i + 1).toLowerCase();
			else
				return null;
		} else {
			return null;
		}
	}

	public static void uploadFile(byte[] bytes, String fileName) throws IOException {
		File targetFile = new File(FILE_FOLDER);
		if (!targetFile.exists())
			targetFile.mkdirs();
		
		FileOutputStream out = new FileOutputStream(FILE_FOLDER + fileName);
		
		out.write(bytes);
		logger.error(fileName + " has been saved!-> uploadFile");
		
		out.flush();
		out.close();
	}
	
	public static void uploadFiles(String name, List<MultipartFile> files) throws IOException {
		for(MultipartFile file : files) {
			
			byte[] bytes = file.getBytes();
			uploadFile(bytes, FILE_FOLDER + file.getOriginalFilename());
			
			logger.debug(file.getOriginalFilename() + " has been saved!-> uploadFiles");
		}
	}
}
