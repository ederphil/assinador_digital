package assinatura_digital;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Calendar;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.IExternalSignatureContainer;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;

public class AssinaturaDigital003 {

	public static String ORIGINAL = "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\original\\original.pdf";
	public static String SIGNED1 = "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\assinado\\assinado_teste001.pdf";
	public static String CAMINHO_ASSINADO = "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\assinado\\";

	public static final String[] RESULT_FILES = new String[] { "hello_siged.pdf" };

	public void signPdfFirstTime(String src, String dest) throws IOException, IOException, GeneralSecurityException {

		String path = "C:\\Temp\\ws-eclipse\\assinatura_digital\\certificados\\1001461546.pfx";
		String keystore_password = "120224";
		String key_password = "120224";
		KeyStore ks = KeyStore.getInstance("pkcs12", "BC");

		ks.load(new FileInputStream(path), keystore_password.toCharArray());
		String alias = ks.aliases().nextElement();
		PrivateKey key = (PrivateKey) ks.getKey(alias, key_password.toCharArray());

		Certificate[] chain = ks.getCertificateChain(alias);

		PdfReader reader = new PdfReader(src);
		FileOutputStream os = new FileOutputStream(dest);
		PdfSigner signer = new PdfSigner(reader, os, new StampingProperties());
		PdfSignatureAppearance appearance = signer.getSignatureAppearance();
		appearance.setPageRect(new Rectangle(36, 748, 200, 100)).setPageNumber(1).setCertificate(chain[0])
				.setLocation("Sistema Doc Digital").setReason("Assinatura Digital.");

		signer.setFieldName("sig");
		signer.setSignDate(Calendar.getInstance());

		IExternalSignatureContainer external = new MyExternalSignatureContainer(key, chain);

		// Signs a PDF where space was already reserved. The field must cover the whole
		// document.
		PdfSigner.signDeferred(signer.getDocument(), CAMINHO_ASSINADO + RESULT_FILES[0], os, external);

		/*
		 * PdfReader reader = new PdfReader(src); FileOutputStream os = new
		 * FileOutputStream(dest); PdfStamper stamper =
		 * PdfStamper.createSignature(reader, os, '\0', null, true);
		 * 
		 * PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		 * 
		 * appearance.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
		 * appearance.setReason("Assinatura Digital.");
		 * appearance.setLocation("Sistema Doc Digital");
		 * appearance.setSignDate(Calendar.getInstance());
		 * appearance.setVisibleSignature(new com.itextpdf.text.Rectangle(10, 0, 300,
		 * 30), 1, "Inicio"); stamper.close();
		 */
	}

	class MyExternalSignatureContainer implements IExternalSignatureContainer {

	        protected PrivateKey pk;
	        protected Certificate[] chain;

	        public MyExternalSignatureContainer(PrivateKey pk, Certificate[] chain) {
	            this.pk = pk;
	            this.chain = chain;
	        }

			@Override
			public void modifySigningDictionary(PdfDictionary arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public byte[] sign(InputStream arg0) throws GeneralSecurityException {
				// TODO Auto-generated method stub
				return null;
			}
	}
	
	public static void main(String[] args) {
		try {

			Security.addProvider(new BouncyCastleProvider());
			AssinaturaDigital003 signatures = new AssinaturaDigital003();
			signatures.signPdfFirstTime(ORIGINAL, SIGNED1);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
}