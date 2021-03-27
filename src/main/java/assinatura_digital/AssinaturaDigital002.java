//package assinatura_digital;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.Certificate;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.Security;
//
//import javax.swing.text.Document;
//
//import com.itextpdf.kernel.geom.Rectangle;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.signatures.PdfSignatureAppearance;
//import com.itextpdf.signatures.PdfSigner.CryptoStandard;
//import com.itextpdf.signatures.PrivateKeySignature;
//import com.itextpdf.signatures.ProviderDigest;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.security.ExternalDigest;
//import com.itextpdf.text.pdf.security.ExternalSignature;
//import com.itextpdf.text.pdf.security.MakeSignature;
//
//public class AssinaturaDigital002 {
//	public void createPdf(String filename) throws IOException, DocumentException {
//		Document document = new Document();
//		Pdf Writer.getInstance(document, new FileOutputStream(filename));
//		document.open();
//		document.add(new Paragraph("Assinado com o Cart�o de Cidad�o!"));
//		document.close();
//	}
//
//	public void signPdf(String src, String dest) throws IOException, DocumentException, GeneralSecurityException {
//		KeyStore ks = KeyStore.getInstance(POReIDConfig.POREID);
//		ks.load(null);
//		PrivateKey pk = (PrivateKey) ks.getKey(POReIDConfig.AUTENTICACAO, null);
//		Certificate[] chain = ks.getCertificateChain(POReIDConfig.AUTENTICACAO);
//
//		// reader and stamper
//		PdfReader reader = new PdfReader(src);
//		FileOutputStream os = new FileOutputStream(dest);
//		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
//
//		// appearance
//		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//		appearance.setReason("qualquer motivo");
//		appearance.setLocation("qualquer localiza��o");
//		appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, "primeira assinatura");
//
//		// digital signature
//		ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", POReIDConfig.POREID);
//		ExternalDigest digest = new ProviderDigest(null); // find provider
//		MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
//	}
//
//	public static void main(String[] args) throws DocumentException, IOException, GeneralSecurityException {
//		Security.addProvider(new POReIDProvider());
//
//		AssinaturaDigital002 exemplo = new AssinaturaDigital002();
//		exemplo.createPdf("/home/quim/exemplo.pdf");
//		exemplo.signPdf("/home/quim/exemplo.pdf", "/home/quim/exemplo.assinado.pdf");
//	}
//}