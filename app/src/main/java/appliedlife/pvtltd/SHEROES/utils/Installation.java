package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.UUID;

public class Installation {
	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	public synchronized static String id(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists()) {
					writeInstallationFile(installation);
				}
				sID = readInstallationFile(installation);
			} catch (Exception e) {
				// throw new RuntimeException(e);
				e.printStackTrace();
			}
		}
		return sID;
	}

	private static String readInstallationFile(File installation) throws IOException {
		RandomAccessFile f = null;
		byte[] bytes = null;
		try {
			f = new RandomAccessFile(installation, "r");
			bytes = new byte[(int) f.length()];
			f.readFully(bytes);
		} finally {
			if (f != null){
				f.close();
			}
		}
		return new String(bytes, Charset.forName("UTF-8"));
	}

	private static void writeInstallationFile(File installation) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(installation);
			String id = UUID.randomUUID().toString();
			out.write(id.getBytes(Charset.forName("UTF-8")));
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
