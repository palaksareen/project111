/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package uk.co.o2.utility;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class GetPUKConstants {
	//TODO Remove unnecessary constants
    private static Logger log = Logger.getLogger((Class)GetPUKConstants.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle("application");
    private static long babelfishTimeout = -1;
    private static final String BABELFISH_TIMEOUT_KEY = "babelfishTimeout";
    public static final String RID_KEY = "rID";
    public static final String RAT_KEY = "rAT";
    public static final String MSISDN_KEY = "MSISDN";
    public static final String USERID_KEY = "id";
    public static final String PASSWORD_KEY = "authData";
    public static final String DURATION_KEY = "duration";
    public static final String ADMINTOKEN_KEY = "adminToken";
    public static final String AUTH_METHOD_KEY = "authMethod";
    public static final String RESPONSE_EC_ERROR_NO = "EC_ErrorNo";
    public static final String BABELFISH_ERROR_TYPE = "BABELFISH_ERROR";
    public static final String RESPONSE_EC_MESSAGE = "EC_Message";
    public static final String ERROR_ATTRIBUTE = "ERROR";
    public static final String MPN_KEY = "Mpn";
    public static final String PUK_KEY = "PUK";
    public static final String OPERATORID_KEY = "operatorID";
    public static final String STATUS_KEY = "Status";
    public static final String BARRED_KEY = "Barred";
    public static final String[] REMOVABLE_SPECIAL_CHAR_FROM_MPN = new String[]{" ", "-", "+"};
    public static final int BABELFISH_ERROR_CODE = 101;
    public static final String USER_OBJ_KEY = "O2USER";
    public static final String INPUT_MPN_KEY = "MPN";
    public static final String BABELFISH_PARAM_RID_KEY = "BABELFISH_PARAM_RID";
    public static final String BABELFISH_PARAM_RAT_KEY = "BABELFISH_PARAM_RAT";
    public static final String XMLRPC_URL_KEY = "XMLRPC_URL";
    public static final String WELCOME_PAGE_KEY = "JSP_WELCOME";
    public static final String PUK_PAGE_KEY = "JSP_PUK";
    public static final String GETPUK_ERROR_PAGE_KEY = "JSP_PUK_ERROR";
    public static final String WELCOME_STYLESHEET_KEY = "XSL_WELCOME_STYLESHEET";
    public static final String PUK_STYLESHEET_KEY = "XSL_PUK_STYLESHEET";
    public static final String GETPUK_ERROR_STYLESHEET_KEY = "XSL_PUK_ERROR_STYLESHEET";
    public static final String HEADER_FILENAME_KEY = "HEADER_FILENAME";
    public static final String NAVIGATION_FILENAME_KEY = "NAVIGATION_FILENAME";
    public static final String FOOTER_FILENAME_KEY = "FOOTER_FILENAME";
    public static final String XSL_CACHE = "XSLT_CACHE";
    public static final String BABELFISH_RETRIES_KEY = "BABELFISH_RETRIES";

    public static final String getValue(String keyToken) {
        try {
            return bundle.getString(keyToken);
        }
        catch (MissingResourceException mre) {
            log.warn((Object)("Unable to find resource for '" + keyToken + "' this" + " has caused a missing resource exception"));
            throw mre;
        }
    }

    public static final String getXMLRPC_URL() {
        return GetPUKConstants.getValue("XMLRPC_URL");
    }

    public static final long getBabelfishTimeout() {
        if (babelfishTimeout < 0) {
            try {
                String timeoutString = bundle.getString("babelfishTimeout");
                babelfishTimeout = Long.parseLong(timeoutString) * 1000;
            }
            catch (NumberFormatException nfe) {
                long defaultBabelfishTimeout = 6000;
                log.warn((Object)"Babelfish timeout net set - defaulting 6000");
                babelfishTimeout = 6000;
            }
        }
        return babelfishTimeout;
    }

    public static String getBabelfishRetries() {
        return GetPUKConstants.getValue("BABELFISH_RETRIES");
    }
}
