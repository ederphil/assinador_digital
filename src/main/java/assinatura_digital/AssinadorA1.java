//package assinatura_digital;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
//import java.security.Security;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.util.Calendar;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
//import com.itextpdf.kernel.pdf.PdfDictionary;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.signatures.PdfSignatureAppearance;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfStamper;
//
//public class AssinadorA1 {
//
//	private String caminhoCertificado;
//
//	public AssinadorA1(String caminhoCertificado) {
//
//		this.caminhoCertificado = caminhoCertificado;
//
//	}
//
//	public void signA1(String src, String dest) {
//
//		try {
//
//			Security.addProvider(new BouncyCastleProvider());
//			String path = "C:\\Temp\\ws-eclipse\\assinatura_digital\\certificados\\1001461546.pfx";
//			String keystore_password =  "120224";
//			String key_password =  "120224";
//			KeyStore ks = KeyStore.getInstance("pkcs12", "BC");
//
//			ks.load(new FileInputStream(path), keystore_password.toCharArray());
//			String alias = ks.aliases().nextElement();
//			PrivateKey key = (PrivateKey) ks.getKey(alias, key_password.toCharArray());
//			java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);
//			
//			PdfReader reader = new PdfReader(src);
//			FileOutputStream os = new FileOutputStream(dest);
//			PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, false);
//
//			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//			PdfDictionary cryptoDictionary = new PdfDictionary();
//			appearance.setCryptoDictionary(cryptoDictionary);
//			//appearance.setCrypto(key, chain, null, PdfSignatureAppearance.CERTIFIED_FORM_FILLING);
//			appearance.setReason("Assinatura Digital.");
//			appearance.setLocation("Sistema Doc Digital");
//			appearance.setSignDate(Calendar.getInstance());
//			appearance.setVisibleSignature(new com.itextpdf.text.Rectangle(10, 0, 300, 30), 1, "Inicio");
//			
//			appearance.preClose(null);
//			appearance.close(cryptoDictionary);
//			stamper.close();
//			reader.close();
//		} catch (UnrecoverableKeyException e) {
//
//			e.printStackTrace();
//		} catch (KeyStoreException e) {
//
//			e.printStackTrace();
//		} catch (NoSuchProviderException e) {
//
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//
//			e.printStackTrace();
//		} catch (CertificateException e) {
//
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		} catch (DocumentException e) {
//
//			e.printStackTrace();
//		}
//	}
//
//	 public static void main(String[] args) throws IOException, DocumentException, GeneralSecurityException {  
//	       
//		 AssinadorA1 assinadorA1 = new AssinadorA1("");
//		 assinadorA1.signA1("C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\original\\original.pdf", "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\assinado\\assinado_teste002.pdf");
//		 
//	    }  
//	
//}