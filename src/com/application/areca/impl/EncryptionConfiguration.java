package com.application.areca.impl;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.application.areca.ResourceManager;
import com.myJava.object.EqualsHelper;
import com.myJava.object.HashHelper;
import com.myJava.util.log.Logger;

/**
 * Default parameters indexed by encryption algorithm
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 3675112183502703626
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class EncryptionConfiguration {

    public static final String KEYCONV_RAW = "RAW";
    public static final String KEYCONV_HASH = "HASH";
    public static final String KEYCONV_OLD = "OLD";
    
    private static Map DEFAULT_PARAMETERS;
    public static final String DEFAULT_ALGORITHM = "DESede"; // Default algorithm ... used for backward compatibility
    public static final String RECOMMENDED_ALGORITHM = "AES_HASH"; // Recommended algorithm
    
    private static void registerTripleDesConfiguration(int keySize, String id, String keyConvention) {
        EncryptionConfiguration tDesParam = new EncryptionConfiguration();
        tDesParam.setKeySize(keySize);
        tDesParam.setTransformation("DESede/CBC/PKCS5Padding");
        tDesParam.setAlgorithm("DESede");
        tDesParam.setId(id);
        tDesParam.setIV(new IvParameterSpec(new byte[] {0, 0, 0, 0, 0, 0, 0, 0}));
        tDesParam.setKeyConvention(keyConvention);
        registerConfiguration(tDesParam);
    }
    
    private static void registerAESConfiguration(int keySize, String id, String keyConvention) {
        EncryptionConfiguration AESParam = new EncryptionConfiguration();
        AESParam.setKeySize(keySize);
        AESParam.setTransformation("AES");
        AESParam.setAlgorithm("AES");
        AESParam.setId(id);
        AESParam.setIV(null);
        AESParam.setKeyConvention(keyConvention);
        registerConfiguration(AESParam);
    }
    
    private static void registerConfiguration(EncryptionConfiguration p) {
        if (p.isSupported()) {
            DEFAULT_PARAMETERS.put(p.getId(), p);
        }
    }
    
    static {
        DEFAULT_PARAMETERS = new HashMap();
        
        // Triple DES 192
        registerTripleDesConfiguration(24, "DESede", KEYCONV_OLD);
        registerTripleDesConfiguration(24, "DESede_HASH", KEYCONV_HASH);
        registerTripleDesConfiguration(24, "DESede_RAW", KEYCONV_RAW);
        
        // AES 128
        registerAESConfiguration(16, "AES", KEYCONV_OLD);
        registerAESConfiguration(16, "AES_HASH", KEYCONV_HASH);
        registerAESConfiguration(16, "AES_RAW", KEYCONV_RAW);

        // AES 256
        registerAESConfiguration(32, "AES256_HASH", KEYCONV_HASH);
        registerAESConfiguration(32, "AES256_RAW", KEYCONV_RAW);
    }
    
    private boolean isSupported() {
        try {
            Cipher cipher = Cipher.getInstance(this.transformation);
            byte[] b = new byte[this.keySize];
            Key key = new SecretKeySpec(b, this.algorithm);
            if (this.IV == null) {
                cipher.init(Cipher.ENCRYPT_MODE, key);                
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key, this.IV);
            }
            return true;
        } catch (Throwable e) {
            Logger.defaultLogger().info(this.id + " encryption configuration is not supported by your system (" + e.getMessage() + "). It will be removed from the available encryption configurations.");
            return false;
        }
    }
    
    public static boolean validateAlgorithmId(String id, boolean filterDeprecated) {
        return 
            DEFAULT_PARAMETERS.containsKey(id)
            && ((! filterDeprecated) || (! getParameters(id).isDeprecated()));
    }
    
    public static String[] getAvailableAlgorithms(boolean filterDeprecated) {
        Iterator algos = DEFAULT_PARAMETERS.keySet().iterator();
        ArrayList ret = new ArrayList();
        while (algos.hasNext()) {
            String k = (String)algos.next();
            EncryptionConfiguration alg = getParameters(k);
            if ((! filterDeprecated) || (! alg.isDeprecated())) {
                ret.add(k);
            }
        }
        
        String[] algs = (String[])ret.toArray(new String[ret.size()]);
        Arrays.sort(algs);
        return algs;
    }
    
    public static EncryptionConfiguration getParameters(String id) {
        return (EncryptionConfiguration)DEFAULT_PARAMETERS.get(id);
    }
    
    private int keySize;
    private String id;
    private String transformation;
    private String algorithm;
    private IvParameterSpec IV;
    private String fullName;
    private String keyConvention;
    
    private EncryptionConfiguration() {
        super();
    }

    public int getKeySize() {
        return keySize;
    }

    public String getId() {
        return id;
    }
    
    private void setId(String id) {
        this.id = id;
        this.setFullName(ResourceManager.instance().getLabel("targetedition.encryption." + id.toLowerCase() + ".label"));
    }
    
    private void setKeySize(int keySize) {
        this.keySize = keySize;
    }
    
    public String getTransformation() {
        return transformation;
    }
    
    private void setTransformation(String transformation) {
        this.transformation = transformation;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    private void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public IvParameterSpec getIV() {
        return IV;
    }
    
    private void setIV(IvParameterSpec iv) {
        IV = iv;
    }
    
    public String getFullName() {
        return fullName;
    }

    public String getKeyConvention() {
        return keyConvention;
    }

    public void setKeyConvention(String keyConvention) {
        this.keyConvention = keyConvention;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public boolean isDeprecated() {
        return this.keyConvention.equals(KEYCONV_OLD);
    }
    
    public int hashCode() {
        int h = HashHelper.initHash(this);
        h = HashHelper.hash(h, this.getAlgorithm());
        h = HashHelper.hash(h, this.getKeyConvention());
        return h;
    }
    
    public String toString() {
        return this.getFullName();
    }
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (! (obj instanceof EncryptionConfiguration)) {
            return false;
        } else {
            EncryptionConfiguration other = (EncryptionConfiguration)obj;
            return 
            	EqualsHelper.equals(this.getAlgorithm(), other.getAlgorithm()) &&
            	EqualsHelper.equals(this.getKeyConvention(), other.getKeyConvention())            	
            ;
        }
    }
}