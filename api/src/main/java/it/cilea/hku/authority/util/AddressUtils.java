/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressUtils
{

    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
    
    public static boolean checkIsInRange(String from, String to, String ip) throws UnknownHostException {
        
        long ipLo = ipToLong(InetAddress.getByName(from));
        long ipHi = ipToLong(InetAddress.getByName(to));
        long ipToTest = ipToLong(InetAddress.getByName(ip));

        return ipToTest >= ipLo && ipToTest <= ipHi;
        
    }
}
