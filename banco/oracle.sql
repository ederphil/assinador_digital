DECLARE
  l_output DBMS_OUTPUT.chararr;
  l_lines  INTEGER := 1000;
BEGIN
  DBMS_OUTPUT.enable(1000000);
  DBMS_JAVA.set_output(1000000);

  --host_command('dir C:\');
  host_command('/bin/java -jar ''/opt/arquivos/legis/AssinadorA1/jar/assinatura_digital-7.1.14-SNAPSHOT-jar-with-dependencies.jar'' ' || ' original.pdf ' || ' ' || ' 1001461546.pfx ' || '120224'||' /opt/arquivos/legis/AssinadorA1' );

  DBMS_OUTPUT.get_lines(l_output, l_lines);

  FOR i IN 1 .. l_lines LOOP
    -- Do something with the line.
    -- Data in the collection - l_output(i)
    DBMS_OUTPUT.put_line(l_output(i));
  END LOOP;
END;
/
CREATE OR REPLACE PROCEDURE sp_assinador_digital(p_nome_documento    VARCHAR2 DEFAULT NULL
                                                ,p_nome_certificado  VARCHAR2 DEFAULT NULL
                                                ,p_senha_certificado VARCHAR2 DEFAULT NULL
                                                ,p_origem            VARCHAR2 DEFAULT NULL
                                                ,p_destino           VARCHAR2 DEFAULT NULL) IS
BEGIN

  host_command('/bin/java -jar /opt/arquivos/legis/AssinadorA1/jar/assinatura_digital-7.1.14-SNAPSHOT-jar-with-dependencies.jar');

END sp_assinador_digital;

CREATE OR REPLACE AND COMPILE JAVA SOURCE NAMED "Host" AS

import java.io.*;

public class Host {
  public static void executeCommand(String command) {
    try {
      String[] finalCommand;
      if (isWindows()) {
        finalCommand = new String[4];
        finalCommand[0] = "C:\\windows\\system32\\cmd.exe"; // Windows XP/2003
        finalCommand[1] = "/y";
        finalCommand[2] = "/c";
        finalCommand[3] = command;
      } else {
        finalCommand = new String[3];
        finalCommand[0] = "/bin/sh";
        finalCommand[1] = "-c";
        finalCommand[2] = command;
      }

      final Process pr = Runtime.getRuntime().exec(finalCommand);
      pr.waitFor();

      new Thread(new Runnable() {
        public void run() {
          BufferedReader br_in = null;
          try {
            br_in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String buff = null;
            while ((buff = br_in.readLine()) != null) {
              System.out.println("Process out :" + buff);
              try {
                Thread.sleep(100);
              } catch (Exception e) {
              }
            }
            br_in.close();
          } catch (IOException ioe) {
            System.out.println("Exception caught printing process output.");
            ioe.printStackTrace();
          } finally {
            try {
              br_in.close();
            } catch (Exception ex) {
            }
          }
        }
      }).start();

      new Thread(new Runnable() {
        public void run() {
          BufferedReader br_err = null;
          try {
            br_err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String buff = null;
            while ((buff = br_err.readLine()) != null) {
              System.out.println("Saída :" + buff);
              try {
                Thread.sleep(100);
              } catch (Exception e) {
              }
            }
            br_err.close();
          } catch (IOException ioe) {
            System.out.println("Exception caught printing process error.");
            ioe.printStackTrace();
          } finally {
            try {
              br_err.close();
            } catch (Exception ex) {
            }
          }
        }
      }).start();
    } catch (Exception ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }

  public static boolean isWindows() {
    if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
      return true;
    else
      return false;
  }

};
DECLARE
  l_schema VARCHAR2(30) := 'LEGIS'; -- Adjust as required.
BEGIN
  DBMS_JAVA.grant_permission(l_schema, 'java.io.FilePermission', '<<ALL FILES>>', 'read ,write, execute, delete');
  DBMS_JAVA.grant_permission(l_schema, 'SYS:java.lang.RuntimePermission', 'writeFileDescriptor', '');
  DBMS_JAVA.grant_permission(l_schema, 'SYS:java.lang.RuntimePermission', 'readFileDescriptor', '');
  DBMS_JAVA.grant_permission(l_schema, 'SYS:java.io.FilePermission', '/bin/sh', 'execute' );
END;
/

CREATE OR REPLACE PROCEDURE host_command (p_command  IN  VARCHAR2)
AS LANGUAGE JAVA
NAME 'Host.executeCommand (java.lang.String)';
