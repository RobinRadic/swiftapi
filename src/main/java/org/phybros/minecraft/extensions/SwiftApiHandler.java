package org.phybros.minecraft.extensions;

import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.thrift.EAuthException;
import org.phybros.thrift.ErrorCode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by radic on 7/30/14.
 */
abstract public class SwiftApiHandler {

    /**
     * Authenticate a method call.
     *
     * @param authString The authentication hash
     * @param methodName The method that is being called.
     * @throws EAuthException This is thrown if the authString is invalid.
     */
    protected void authenticate(String authString, String methodName)
            throws EAuthException {
        String username = SwiftApiPlugin.getInstance().getConfig().getString("username");
        String password = SwiftApiPlugin.getInstance().getConfig().getString("password");
        String salt = SwiftApiPlugin.getInstance().getConfig().getString("salt");

        // build the pre-hashed string
        String myAuthString = username + methodName + password + salt;
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException algex) {
            SwiftApiPlugin.getInstance().getLogger().severe(algex.getMessage());
        }
        md.update(myAuthString.getBytes());
        String hash = byteToString(md.digest());
        // SwiftApiPlugin.getInstance().getLogger().info("Expecting: " + hash);
        // SwiftApiPlugin.getInstance().getLogger().info("Received:  " + authString);

        if (!hash.equalsIgnoreCase(authString)) {
            SwiftApiPlugin.getInstance().getLogger().warning(
                    String.format(
                            "Invalid Authentication received (method: %s)",
                            methodName)
            );

            EAuthException e = new EAuthException();
            e.code = ErrorCode.INVALID_AUTHSTRING;
            e.errorMessage = SwiftApiPlugin.getInstance().getConfig().getString(
                    "errorMessages.invalidAuthentication");
            throw e;
        }
    }

    protected String byteToString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            result += String.format("%02x", bytes[i]);
        }
        return result;
    }

    /**
     * Log an API call. If the config option logMethodCalls is false, this
     * method does nothing.
     *
     * @param methodName Name of the method that was called.
     */
    protected void logCall(String methodName) {
        if (SwiftApiPlugin.getInstance().getConfig().getBoolean("logMethodCalls")) {
            SwiftApiPlugin.getInstance().getLogger().info(
                    "SwiftApi method called: " + methodName + "()");
        }
    }


    protected String getAuthString(String methodName) {
        Configuration config = SwiftApiPlugin.getInstance().getConfiguration();
        String text = config.get("username") + methodName + config.get("password") + config.get("salt");
        return sha256(text);
    }

    protected String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Shorthand method that combines logCall and authenticate
     *
     * @param authString The authentication hash
     * @param methodName Name of the method that is called.
     * @throws EAuthException
     */
    protected void authenticateAndLog(String authString, String methodName) throws EAuthException {
        authenticate(authString, methodName);
        logCall(methodName);
    }
}
