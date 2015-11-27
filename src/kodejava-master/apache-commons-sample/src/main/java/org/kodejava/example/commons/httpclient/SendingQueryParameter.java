package org.kodejava.example.commons.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;

import java.io.IOException;

public class SendingQueryParameter {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://www.kodejava.org/browse.php");

        try {
            //
            // Set query string information for accessing the page using a
            // simple string information.
            //
            method.setQueryString(URIUtil.encodeQuery("catid=47&page=1"));

            //
            // Other cleaner alternative is to use the NameValuePair object to
            // define the parameters for a HTTP GET method.
            //
            NameValuePair param1 = new NameValuePair("catid", URIUtil.encodeQuery("47"));
            NameValuePair param2 = new NameValuePair("page", URIUtil.encodeQuery("1"));
            NameValuePair[] params = new NameValuePair[]{param1, param2};
            method.setQueryString(params);

            client.executeMethod(method);

        } catch (URIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
    }
}
