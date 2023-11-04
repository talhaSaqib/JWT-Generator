/*
 * Source: OAuth 2.0 JWT Bearer Flow for Server-to-Server Integration
 * Source URL: https://help.salesforce.com/s/articleView?id=sf.remoteaccess_oauth_jwt_flow.htm&type=5
 */


import org.apache.commons.codec.binary.Base64;
import java.io.*; 
import java.security.*; 
import java.text.MessageFormat;  

public class JWTGenerator {

  public static void main(String[] args) {

    String header = "{\"alg\":\"RS256\"}";
    String claimTemplate = "'{'\"iss\": \"{0}\", \"sub\": \"{1}\", \"aud\": \"{2}\", \"exp\": \"{3}\"}'";

    try {
      StringBuffer token = new StringBuffer();

      //Encode the JWT Header and add it to our string to sign
      token.append(Base64.encodeBase64URLSafeString(header.getBytes("UTF-8")));

      //Separate with a period
      token.append(".");

      //Create the JWT Claims Object
      String[] claimArray = new String[5];
      claimArray[0] = "3MVG96mGXeuuwTZin.Mbof4fu6pMuGECwt1iifnc2ObCqpLoaXl9MUTODiaY7FtzaJaNEBNnfIGCzzLIduip5";
      claimArray[1] = "partsfinder-development@bilsteingroup.com";
      claimArray[2] = "https://test.salesforce.com";
      claimArray[3] = "1721290019";     // 18-7-2024
      MessageFormat claims;
      claims = new MessageFormat(claimTemplate);
      String payload = claims.format(claimArray);

      //Add the encoded claims object
      token.append(Base64.encodeBase64URLSafeString(payload.getBytes("UTF-8")));

      //Load the private key from a keystore
      KeyStore keystore = KeyStore.getInstance("JKS");
      keystore.load(new FileInputStream("server.jks"), "Kerun123".toCharArray());   // Kerun123 = Keystore Password
      PrivateKey privateKey = (PrivateKey) keystore.getKey("1", "K3RUN".toCharArray());  // 1 = alias, SomePassword = Private Key Pasword

      //Sign the JWT Header + "." + JWT Claims Object
      Signature signature = Signature.getInstance("SHA256withRSA");
      signature.initSign(privateKey);
      signature.update(token.toString().getBytes("UTF-8"));
      String signedPayload = Base64.encodeBase64URLSafeString(signature.sign());

      //Separate with a period
      token.append(".");

      //Add the encoded signature
      token.append(signedPayload);

      System.out.println("JSON WEB TOKEN: ");
      System.out.println(token.toString());

    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}