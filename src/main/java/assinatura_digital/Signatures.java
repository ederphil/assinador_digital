//package assinatura_digital;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.Security;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Properties;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.PdfAnnotation;
//import com.itextpdf.text.pdf.PdfFormField;
//import com.itextpdf.text.pdf.PdfName;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfSignatureAppearance;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.PdfString;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.security.PdfPKCS7;  
//  
//public class Signatures {  
//  
//    /** The resulting PDF */  
//    // public static String ORIGINAL = "/Users/jeremias/Downloads/hello.pdf";  
//    public static String ORIGINAL = "/Users/jeremias/Downloads/YouTube.pdf";  
//    /** The resulting PDF */  
//    // public static String SIGNED1 = "/Users/jeremias/Downloads/signature_1.pdf";  
//    public static String SIGNED1 = "/Users/jeremias/Downloads/signature_1.pdf";  
//    /** The resulting PDF */  
//    public static String SIGNED2 = "r/Users/jeremias/Downloads/signature_2.pdf";  
//    /** Info after verification of a signed PDF */  
//    public static String VERIFICATION = "/Users/jeremias/Downloads/verify.txt";  
//    /** The resulting PDF */  
//    public static String REVISION = "/Users/jeremias/Downloads/revision_1.pdf";  
//  
//    /** 
//     * A properties file that is PRIVATE. You should make your own properties file and adapt this line. 
//     */  
//    // TODO public static String PATH = "/home/jeremias/Downloads/key.properties";  
//    /** Some properties used when signing. */  
//    public static Properties properties = new Properties();  
//    /** One of the resources. */  
//    public static final String RESOURCE = "resources/img/logo.gif";  
//  
//    /** 
//     * Creates a PDF document. 
//     * 
//     * @param filename 
//     *            the path to the new PDF document 
//     * @throws DocumentException 
//     * @throws IOException 
//     */  
//    public void createPdf(String filename) throws IOException, DocumentException {  
//        Document document = new Document();  
//        PdfWriter.getInstance(document, new FileOutputStream(filename));  
//        document.open();  
//        document.add(new Paragraph("Hello World!"));  
//        document.close();  
//    }  
//  
//    /** 
//     * Manipulates a PDF file src with the file dest as result 
//     * 
//     * @param src 
//     *            the original PDF 
//     * @param dest 
//     *            the resulting PDF 
//     * @throws IOException 
//     * @throws DocumentException 
//     * @throws GeneralSecurityException 
//     */  
//    public void signPdfFirstTime(String src, String dest) throws IOException, DocumentException, GeneralSecurityException {  
//        // TODO properties.getProperty("PRIVATE");  
//        String path = "C:\\Temp\\ws-eclipse\\assinatura_digital\\certificados\\1001461546.pfx";
//        String keystore_password = "120224"; // TODO properties.getProperty("PASSWORD");  
//        String key_password = "120224"; // TODO properties.getProperty("PASSWORD");  
//        KeyStore ks = KeyStore.getInstance("pkcs12", "BC");  
//        ks.load(new FileInputStream(path), keystore_password.toCharArray());  
//        String alias = ks.aliases().nextElement();  
//        PrivateKey key = (PrivateKey) ks.getKey(alias, key_password.toCharArray());  
//        Certificate[] chain = ks.getCertificateChain(alias);  
//        PdfReader reader = new PdfReader(src);  
//        FileOutputStream os = new FileOutputStream(dest);  
//        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');  
//  
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();  
//        //appearance.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);  
//        // appearance.setImage(Image.getInstance(RESOURCE));  
//        appearance.setReason("Assinatura Digital.");  
//        appearance.setLocation("Sistema Doc Digital");  
//        appearance.setSignDate(Calendar.getInstance());  
//        // appearance.setVisibleSignature(new Rectangle(250, 732, 144, 780), 1, "first");  
//        appearance.setVisibleSignature(new Rectangle(10, 0, 300, 30), 1, "Inicio");  
//        //PdfDictionary pdfDictionary = null;
//        //appearance.preClose(null);
//        //close(pdfDictionary);
//        stamper.close();  
//    }  
//  
//    /** 
//     * Manipulates a PDF file src with the file dest as result 
//     * 
//     * @param src 
//     *            the original PDF 
//     * @param dest 
//     *            the resulting PDF 
//     * @throws IOException 
//     * @throws DocumentException 
//     * @throws GeneralSecurityException 
//     */  
//    public void signPdfSecondTime(String src, String dest) throws IOException, DocumentException, GeneralSecurityException {  
//        String path = "resources/encryption/.keystore";  
//        String keystore_password = "f00b4r";  
//        String key_password = "f1lmf3st";  
//        String alias = "foobar";  
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());  
//        ks.load(new FileInputStream(path), keystore_password.toCharArray());  
//        PrivateKey key = (PrivateKey) ks.getKey(alias, key_password.toCharArray());  
//        Certificate[] chain = ks.getCertificateChain(alias);  
//        PdfReader reader = new PdfReader(src);  
//        FileOutputStream os = new FileOutputStream(dest);  
//        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);  
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();  
////        appearance.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);  
//        appearance.setReason("I'm approving this.");  
//        appearance.setLocation("Foobar");  
//        appearance.setVisibleSignature(new Rectangle(160, 732, 232, 780), 1, "second");  
//        stamper.close();  
//  
//    }  
//  
//    /** 
//     * Verifies the signatures of a PDF we've signed twice. 
//     * 
//     * @throws GeneralSecurityException 
//     * @throws IOException 
//     */  
//    public void verifySignatures() throws GeneralSecurityException, IOException {  
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());  
//        ks.load(null, null);  
//        CertificateFactory cf = CertificateFactory.getInstance("X509");  
//        FileInputStream is1 = new FileInputStream(properties.getProperty("ROOTCERT"));  
//        X509Certificate cert1 = (X509Certificate) cf.generateCertificate(is1);  
//        ks.setCertificateEntry("cacert", cert1);  
//        FileInputStream is2 = new FileInputStream("resources/encryption/digitaldocs.cer");  
//        X509Certificate cert2 = (X509Certificate) cf.generateCertificate(is2);  
//        ks.setCertificateEntry("digitaldocs", cert2);  
//  
//        PrintWriter out = new PrintWriter(new FileOutputStream(VERIFICATION));  
//        PdfReader reader = new PdfReader(SIGNED2);  
//        AcroFields af = reader.getAcroFields();  
//        ArrayList<String> names = af.getSignatureNames();  
//        for (String name : names) {  
//            out.println("Signature name: " + name);  
//            out.println("Signature covers whole document: " + af.signatureCoversWholeDocument(name));  
//            out.println("Document revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());  
//            PdfPKCS7 pk = af.verifySignature(name);  
//            Calendar cal = pk.getSignDate();  
//            Certificate[] pkc = pk.getCertificates();  
//            //out.println("Subject: " + PdfPKCS7.getSubjectFields(pk.getSigningCertificate()));  
//            out.println("Revision modified: " + !pk.verify());  
//            //Object fails[] = pk.verifyCertificates(pkc, ks, null, cal);  
////            if (fails == null) {  
////                out.println("Certificates verified against the KeyStore");  
////            } else {  
////                out.println("Certificate failed: " + fails[1]);  
////            }  
//        }  
//        out.flush();  
//        out.close();  
//    }  
//  
//    /** 
//     * Extracts the first revision of a PDF we've signed twice. 
//     * 
//     * @throws IOException 
//     */  
//    public void extractFirstRevision() throws IOException {  
//        PdfReader reader = new PdfReader(SIGNED2);  
//        AcroFields af = reader.getAcroFields();  
//        FileOutputStream os = new FileOutputStream(REVISION);  
//        byte bb[] = new byte[1028];  
//        InputStream ip = af.extractRevision("first");  
//        int n = 0;  
//        while ((n = ip.read(bb)) > 0) {  
//            os.write(bb, 0, n);  
//        }  
//        os.close();  
//        ip.close();  
//    }  
//  
//    /** 
//     * Main method. 
//     * 
//     * @param args 
//     *            no arguments needed 
//     * @throws DocumentException 
//     * @throws IOException 
//     * @throws GeneralSecurityException 
//     */  
//    public static void main(String[] args) throws IOException, DocumentException, GeneralSecurityException {  
//        Security.addProvider(new BouncyCastleProvider());  
//        // TODO properties.load(new FileInputStream(PATH));  
//        Signatures signatures = new Signatures();  
//        // signatures.createPdf(ORIGINAL);  
//        // signatures.signPdfFirstTime(ORIGINAL, SIGNED1);  
//        // signatures.signPdfSecondTime(SIGNED1, SIGNED2);  
//        // signatures.verifySignatures();  
//        // signatures.extractFirstRevision();  
//        //signatures.assinarPdfTodasPaginas("C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\original\\original.pdf", "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\assinado\\assinado_teste002.pdf");
//        signatures.signPdfFirstTime("C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\original\\original.pdf", "C:\\Temp\\ws-eclipse\\assinatura_digital\\pdfs\\assinado\\assinado_teste002.pdf");
//    }  
//  
//    public void assinarPdfTodasPaginas(String src, String dest) throws IOException, DocumentException, GeneralSecurityException {  
//  
//        String path =  "C:\\Temp\\ws-eclipse\\assinatura_digital\\certificados\\1001461546.pfx";  
//        String keystore_password = "120224"; 
//        String key_password = "120224";  
//        // KeyStore ks = KeyStore.getInstance("pkcs12", "BC");  
//        // ks.load(new FileInputStream(path), keystore_password.toCharArray());  
//  
//        KeyStore ks = KeyStore.getInstance("PKCS12");  
//        ks.load(new ByteArrayInputStream(getBytesFromInputStream(new FileInputStream(path))), keystore_password.toCharArray());  
//        String alias = ks.aliases().nextElement();  
//        PrivateKey key = (PrivateKey) ks.getKey(alias, key_password.toCharArray());  
//        Certificate[] chain = ks.getCertificateChain(alias);  
//  
//        // Get the first time signed pdf  
//        PdfReader reader = new PdfReader(src);  
//        ByteArrayOutputStream out = new ByteArrayOutputStream();  
//  
//        reader = new PdfReader(reader);  
//        // work with new revision each time  
//        PdfStamper stp1 = new PdfStamper(reader, out, '\3', true);  
//  
//        PdfFormField sig = PdfFormField.createSignature(stp1.getWriter());  
//        sig.setWidget(new Rectangle(10, 0, 300, 30), null);  
//        sig.setFlags(PdfAnnotation.FLAGS_PRINT);  
//        sig.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));  
//        sig.setFieldName("Signature4");  
//        sig.setPage(1);  
//  
//        // Attached the blank signature field to the existing document  
//        // stp1.addAnnotation(sig, 1);  
//        // stp1.addAnnotation(sig, 2);  
//        for (int p = 1; p <= reader.getNumberOfPages(); p++) {  
//            stp1.addAnnotation(sig, p);  
//        }  
//  
//        stp1.close();  
//        out.close();  
//  
//        FileOutputStream fout = new FileOutputStream(dest);  
//  
//        reader = new PdfReader(out.toByteArray());  
//  
//        // Fill the new signature fields in the correct pdf revision  
//        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\3', null, true);  
//        PdfSignatureAppearance sap = stp.getSignatureAppearance();  
//        //sap.setCrypto(key, chain, null, "WINCER_SIGNED");  
//  
//        sap.setReason("RAZON");  
//        sap.setLocation("UBICACION");  
//        sap.setSignDate(Calendar.getInstance());  
//  
//        // sap.setLayer2Text("Texto");  
//        // sap.setAcro6Layers(true);  
//        // Set visible signature field  
//        sap.setVisibleSignature("Signature4");  
//  
//        // Close PdfStamper and output 
//        fout.close();
//        reader.close();
//        stp.close();  
//        fout.close();  
//    }  
//  
//    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {  
//        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {  
//            byte[] buffer = new byte[0xFFFF];  
//  
//            for (int len; (len = is.read(buffer)) != -1;) {  
//                os.write(buffer, 0, len);  
//            }  
//            os.flush();  
//            return os.toByteArray();  
//        }  
//    }  
//  
//}  
