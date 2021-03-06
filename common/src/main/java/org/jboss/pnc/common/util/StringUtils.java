/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.common.util;

import org.jboss.util.StringPropertyReplacer;

import java.util.Map;
import java.util.Properties;

/**
 * Created by <a href="mailto:matejonnet@gmail.com">Matej Lazar</a> on 2015-01-01.
 */
public class StringUtils {
    
    /**
     * Replace environment variables in string.
     * Environment variables are expected in format ${env.ENV_PROPERTY}, 
     * where "env" is static prefix and ENV_PROPERTY is name of environment property.
     * 
     * @param configString String with environment variables
     * @return String with replaced environment variables
     */
    public static String replaceEnv(String configString) {
        Properties properties = new Properties();
        
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            properties.put("env." + entry.getKey(), entry.getValue());
        }
        return StringPropertyReplacer.replaceProperties(configString, properties);
    }

    /**
     * Check if the given string is null or contains only whitespace characters.
     * 
     * @param string String to check for non-whitespace characters
     * @return boolean True if the string is null, empty, or contains only whitespace (empty when trimmed).  
     * Otherwise return false.
     */
    public static boolean isEmpty(String string) {
        if (string == null ) {
            return true;
        }
        return string.trim().isEmpty();
    }
}
