/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.o2.cms.gas.getpuk.GetPUKConstants
 *  com.o2.cms.gas.getpuk.GetPUKUtility
 *  com.o2.cms.gas.getpuk.babelfish.BabelfishCommunicator
 *  com.o2.cms.gas.getpuk.babelfish.BabelfishException
 *  com.o2.cms.gas.getpuk.babelfish.PUKNotFoundException
 *  com.o2.cms.gas.getpuk.business.GetPUKManagerImpl$1
 *  com.o2.cms.gas.getpuk.business.GetPUKManagerImpl$BabelfishAccessStats
 *  com.o2.cms.gas.getpuk.business.InvalidMPNException
 *  com.o2.cms.gas.getpuk.data.PUKCode
 *  org.apache.log4j.Logger
 */
package uk.co.o2.utility;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import uk.co.o2.utility.exception.InvalidMPNException;

/*
 * Exception performing whole class analysis ignored.
 */
public class GetPUKManagerImpl {
/*    private BabelfishCommunicator babelfishComm = null;
    private BabelfishAccessStats stats;
*/    private static Logger log = Logger.getLogger((Class)GetPUKManagerImpl.class);

    public GetPUKManagerImpl() {
    }

    public PUKCode getPUKCode(String inputMPN) {
        log.debug((Object)"GetPUKManagerImpl,getPUKCode,Entered method");
        PUKCode getPUKResult = new PUKCode();
        long getPUKAccessStartTime = 0;
        getPUKAccessStartTime = System.currentTimeMillis();
        String mpn = this.convertMPN2BableInputFormat(inputMPN);
        //getPUKResult.setPukCode(this.babelfishComm.getPUKCode(mpn));
        log.debug((Object)("PUKCode =>" + getPUKResult.getPukCode()));
        //BabelfishAccessStats.access$100((BabelfishAccessStats)this.stats, (long)(System.currentTimeMillis() - getPUKAccessStartTime));
        //log.debug((Object)("GetPUKManagerImpl,getPUKCode,Babelfish access stats " + (Object)this.stats));
        getPUKResult.setAccesstime(System.currentTimeMillis() - getPUKAccessStartTime);
        return getPUKResult;
    }

    public void validate(String userInputMPN) throws InvalidMPNException {
        log.debug((Object)("GetPUKManagerImpl,validate,Entered method with: " + userInputMPN));
        ArrayList<String> errors = new ArrayList<String>();
        if (userInputMPN != null) {
            int mpnLength = userInputMPN.length();
            if (mpnLength == 0) {
                errors.add("MPN_NULL");
                log.debug((Object)"GetPUKManagerImpl,validate,MPN is null");
                throw new InvalidMPNException("Invalid MPN", userInputMPN, errors);
            }
            userInputMPN = userInputMPN.replaceFirst("\\+", "");
            userInputMPN = userInputMPN.replaceAll("\\-", "");
            log.debug((Object)("GetPUKManagerImpl,validate,MPN is " + userInputMPN + " after replacing + and -"));
            if (!userInputMPN.matches("\\d+")) {
                errors.add("MPN_NOT_NUMERIC");
                log.debug((Object)("GetPUKManagerImpl,validate,MPN is not numeric " + userInputMPN));
                throw new InvalidMPNException("Invalid MPN", userInputMPN, errors);
            }
            if (mpnLength > 10 && GetPUKUtility.specialCharacterCheck((String)userInputMPN)) {
                if ((userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN)).matches("^(44|07)\\d+")) {
                    if (userInputMPN.startsWith("07") && userInputMPN.length() == 11 || userInputMPN.startsWith("447") && userInputMPN.length() == 12) {
                        return;
                    }
                    errors.add("MPN_INVALID_LENGTH");
                } else {
                    errors.add("MPN_INVALID_PREFIX");
                }
            } else {
                errors.add("MPN_TOO_SHORT");
            }
        } else {
            errors.add("MPN_NULL");
        }
        log.debug((Object)("GetPUKManagerImpl,validate,Invalid MPN '" + userInputMPN + "'"));
        throw new InvalidMPNException("Invalid MPN", userInputMPN, errors);
    }

    private String convertMPN2BableInputFormat(String userInputMPN) {
        if (log.isDebugEnabled()) {
            log.debug((Object)"GetPUKManagerImpl.convertMPN2BableInputFormat(String userInputMPN) Invoked");
        }
        try {
            this.validate(userInputMPN);
            userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN);
            if (userInputMPN.startsWith("07")) {
                userInputMPN = userInputMPN.replaceFirst("07", "447");
            }
        }
        catch (InvalidMPNException e) {
            // empty catch block
        }
        if (log.isDebugEnabled()) {
            log.debug((Object)("GetPUKManagerImpl.convertMPN2BableInputFormat(String userInputMPN) Converted userInputMPN " + userInputMPN));
        }
        return userInputMPN;
    }

    static {
    }
}
