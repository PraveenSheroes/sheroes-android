package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author atul.bhardwaj
 * @editor sachin.gupta
 */
public class EncryptionUtils {
	private static EncryptionUtils mInstance;
	private byte[] mEncryptionKey;

	/**
	 * This method need to be called only once. And it is must to call it before
	 * calling getInstance()
	*/
	public static void initialize() {
		if (mInstance == null) {
			mInstance = new EncryptionUtils(AppUtils.getInstance().getApplicationContext());
		}
	}

	/**
	 * @param pContext
	 */
	private EncryptionUtils(Context pContext) {
		mEncryptionKey = generateKey(pContext);
	}

	/**
	 * This method can be called only after the call to initialize() is done.
	 */
	public static EncryptionUtils getInstance() {
		if (mInstance == null) {
			throw new RuntimeException("EncryptionUtils is not initialized yet.");
		}
		return mInstance;
	}

	public String getDecryptedString(String pEncryptedStr) {
		try {
			byte decoString[] = Base64New.decode(pEncryptedStr);
			return getString(decrypt(mEncryptionKey, decoString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getEncryptedString(String pStrToEncrypt) {
		try {
			return Base64New.encodeBytes(encrypt(mEncryptionKey, getBytes(pStrToEncrypt)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getString(byte eee[]) {
		String text = null;
		try {
			text = new String(eee, "UTF-8");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return text;
	}

	private byte[] getBytes(String string) {
		byte b[] = null;
		try {
			b = string.getBytes("UTF-8");
		} catch (Exception e) {
		}
		return b;
	}

	private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private byte[] generateKey(Context context) {
		byte key[] = null;
		byte[] keyStart = getDeviceId(context).getBytes(Charset.forName("UTF-8"));
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom sr;

			sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
			sr.setSeed(keyStart);
			kgen.init(128, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			key = skey.getEncoded();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

    public String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String imei = "";

        if (telephonyManager != null) {
            imei = telephonyManager.getDeviceId();
        }

        if ((imei == null) || (!imei.trim().equals(""))) {

            imei = Installation.id(context);

            if ((imei == null) || (!imei.trim().equals(""))) {
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        // Used hardcoded value for testing of coupons
        return imei;// "123456789";//
    }
}
