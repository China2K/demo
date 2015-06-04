package com.demo.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUtils {

	protected static Logger logger = Logger.getLogger(FileUtils.class);

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {

		File source = new File(sourceDir);
		File target = new File(targetDir);

		if (!source.exists()) {
			logger.error("source " + sourceDir + " is empty");
			return;
		}

		if (!target.exists()) {
			if (!target.mkdirs()) {
				logger.error("fail to create folder " + targetDir);
				return;
			}
		}

		cleanDirectiory(targetDir);

		for (File file : source.listFiles()) {
			if (file.isFile()) {
				copyFile(file, new File(target, file.getName()));
			}
			if (file.isDirectory()) {
				copyDirectiory(file.getAbsolutePath(), target.getAbsolutePath()
						+ File.separator + file.getName());
			}
		}
	}

	public static void copyFile(File source, File target) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;

		try {
			inBuff = new BufferedInputStream(new FileInputStream(source));

			outBuff = new BufferedOutputStream(new FileOutputStream(target));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				try {
					inBuff.close();
				} catch (IOException e) {
				}
			}

			if (outBuff != null) {
				try {
					outBuff.close();
				} catch (IOException e) {
				}
			}

		}
	}

	public static void cleanDirectiory(String sourceDir) {
		File folder = new File(sourceDir);
		if (folder.exists()) {
			if (folder.isDirectory()) {
				for (File file : folder.listFiles()) {
					if (file.isDirectory()) {
						cleanDirectiory(file.getAbsolutePath());
					}

					file.delete();
				}
			} else {
				logger.warn("'" + sourceDir + "' is not a Directory!");
			}
		}
	}
}
