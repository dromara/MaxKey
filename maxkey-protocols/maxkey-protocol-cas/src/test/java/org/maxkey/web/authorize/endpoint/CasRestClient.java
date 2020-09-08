package org.maxkey.web.authorize.endpoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class CasRestClient {
        
    
    public static void main(String... args) throws Exception
    {
        String username ="admin";
        String password ="maxkey";
        validateFromCAS(username,password);
    }
    
    public static boolean validateFromCAS(String username, String password) throws Exception
    {
        
        String url = "https://sso.maxkey.top/maxkey/authz/cas/v1/tickets";
        try 
        {
            org.maxkey.client.utils.HttpsTrusts.beforeConnection();
            HttpsURLConnection hsu = (HttpsURLConnection)openConn(url);
            String s =   URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8");
            s+="&" +URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
            
            System.out.println(s);
            OutputStreamWriter out = new OutputStreamWriter(hsu.getOutputStream());
            BufferedWriter bwr = new BufferedWriter(out); 
            bwr.write(s);
            bwr.flush();
            bwr.close();
            out.close();
            
            String tgt = hsu.getHeaderField("location");
            System.out.println( hsu.getResponseCode());
            if(tgt != null && hsu.getResponseCode() == 201)
            {
                System.out.println(tgt);
                
                System.out.println("Tgt is : " + tgt.substring( tgt.lastIndexOf("/") +1));
                tgt = tgt.substring( tgt.lastIndexOf("/") +1);
                bwr.close();
                closeConn(hsu);
                
                
                String serviceURL = "http://cas.demo.maxkey.top:8080/demo-cas/";
                String encodedServiceURL = URLEncoder.encode("service","utf-8") +"=" + URLEncoder.encode(serviceURL,"utf-8");
                System.out.println("Service url is : " + encodedServiceURL);
                
                
                
                String myURL = url+ "/"+ tgt ;
                System.out.println(myURL);
                hsu = (HttpsURLConnection)openConn(myURL);
                out = new OutputStreamWriter(hsu.getOutputStream());
                bwr = new BufferedWriter(out); 
                bwr.write(encodedServiceURL);
                bwr.flush();
                bwr.close();
                out.close();
                
                System.out.println("Response code is:  " + hsu.getResponseCode());
                
                BufferedReader isr = new BufferedReader(   new InputStreamReader(hsu.getInputStream()));
                String line;
                System.out.println( hsu.getResponseCode());
                while ((line = isr.readLine()) != null) {
                    System.out.println( line);
                }
                isr.close();
                hsu.disconnect();
                return true;
                
            }
            else
            {
                return false;
            }
            
            
        }
        catch(MalformedURLException mue)
        {
            mue.printStackTrace();
            throw mue; 
             
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            throw ioe;
        }
        
        
        
        
        
    }
    
    
    static URLConnection openConn(String urlk)  throws MalformedURLException, IOException
    {
        
        URL url = new URL(urlk);
        HttpsURLConnection hsu = (HttpsURLConnection) url.openConnection();
        hsu.setDoInput(true);
        hsu.setDoOutput(true);
        hsu.setRequestMethod("POST");
        return hsu;
        
        
    }
    
    
    static void closeConn(HttpsURLConnection c)
    {
        c.disconnect();
    }
    
    
    }

