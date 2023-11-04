# JWT-Generator
A JAVA project to generate JWTs (JSON Web Tokens)

**Note:** This code uses Apache Codec 1.16.0. The module is already present in the repository. If you want to use a new version then this Apache module should be deleted and new one should be installed manually.


**HOW TO RUN THIS PROJECT**
1. Download the repository on a local machine.
2. (Optional) Delete "Output" folder and "server.jks" file. (You should create a new JKS file)
3. (Optional) Install VS Code as this repository is already configured for a VS code project
4. Follow Step "A" to create a Private Key and Digital Certificate.
5. Follow Step "B" to convert the Certificate to JKS file (needed in the code).


**A. Create a Private Key and Self-Signed Digital Certificate**
To check whether OpenSSL is installed on your computer, run the _which_ command on macOS or Linux or the _where_ command on Windows.
> which openssl

(Optional) Create folder
> mkdir /Users/jdoe/JWT

> cd /Users/jdoe/JWT

Generate a private key, and store it in a file called server.key.
> openssl genpkey -des3 -algorithm RSA -pass pass:SomePassword -out server.pass.key -pkeyopt rsa_keygen_bits:2048

> openssl rsa -passin pass:SomePassword -in server.pass.key -out server.key

You can delete the server.pass.key file because you no longer need it.

Generate a certificate signing request using the server.key file. Store the certificate signing request in a file called server.csr. Enter information about your company when prompted.
> openssl req -new -key server.key -out server.csr

Generate a self-signed digital certificate from the server.key and server.csr files. Store the certificate in a file called server.crt.
> openssl x509 -req -sha256 -days 365 -in server.csr -signkey server.key -out server.crt

**Source:** https://developer.salesforce.com/docs/atlas.en-us.246.0.sfdx_dev.meta/sfdx_dev/sfdx_dev_auth_key_and_cert.htm?_ga=2.43114208.439795187.1656333652-1742453021.1655933163


**B. How to convert crt and key to jks file**

> openssl pkcs12 -export -in abc.crt -inkey abc.key -out abc.p12

> keytool -importkeystore -srckeystore abc.p12 \
        -srcstoretype PKCS12 \
        -destkeystore abc.jks \
        -deststoretype JKS

Default alias is 1
Password is what you set at the prompt

**Source:** https://community.datarobot.com/t5/best-practices-use-cases/how-to-convert-crt-and-key-to-jks-file/td-p/6342










