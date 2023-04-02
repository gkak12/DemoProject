package com.demo.login.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtil.class);
	
	public static String[] generateRsa(HttpServletRequest request) {
		String[] res = new String[2];
		HttpSession session = request.getSession();
		KeyPairGenerator generator;
		
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			
			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
 
            session.setAttribute("rsaKey", privateKey); // session에 RSA 개인키를 세션에 저장
 
            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

            res[0] = publicKeyModulus;
            res[1] = publicKeyExponent;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.debug(e.toString());
		} catch (InvalidKeySpecException e) {
			LOGGER.debug(e.toString());
		}
		
		return res;
	}
	
	public static String decryptRsa(PrivateKey privateKey, String password) {
		String realPass = null;
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			byte[] enBytePassword = hexToByteArray(password);
			
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] deBytePasword = cipher.doFinal(enBytePassword);
			
			realPass = new String(deBytePasword, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.debug(e.toString());
		} catch (NoSuchPaddingException e) {
			LOGGER.debug(e.toString());
		} catch (InvalidKeyException e) {
			LOGGER.debug(e.toString());
		} catch (IllegalBlockSizeException e) {
			LOGGER.debug(e.toString());
		} catch (BadPaddingException e) {
			LOGGER.debug(e.toString());
		} catch (UnsupportedEncodingException e) {
			LOGGER.debug(e.toString());
		}
		
		return realPass;
	}
	
	public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }
 
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }

}
