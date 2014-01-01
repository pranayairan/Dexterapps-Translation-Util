/*
Copyright 2014 Pranay Airan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.dexterapps.android.translator;

import com.dexterapps.android.translator.pojo.IOSStringMapping;
import com.memetix.mst.language.Language;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author Pranay Airan
 * http://www.dexterapps.in/
 * pranay.airan@iiitb.net
 * 
 * This Class parse the input Localizable.strings and converts them into String Array which then can be passed
 * to Microsoft Translate API for translation
 * 
 * We Use a Hash Map and a ArrayList to store the  Localizable.strings structure
 * this is used when we create the file thus avoiding parsing of XML again
 * 
 */


public class TranslateTextForIOS {

    /**
     * @param args
     * @throws Exception
     */
    HashMap<String, Integer> baseStringResourcesMapping;
    ArrayList<IOSStringMapping> iOSStringDOM;
    ArrayList<String> baseLanguageStringForTranslation;

    public void translateIOSText(String clientID, String clientSecret, String sourceFilePath, String outputFolder, String baseLanguage) {

        baseStringResourcesMapping = new HashMap<String, Integer>();
        iOSStringDOM = new ArrayList<IOSStringMapping>();
        baseLanguageStringForTranslation = new ArrayList<String>();

        // Parse The XML
        parseStringAndGenerateDom(sourceFilePath);

        //Now we got the Strings from Localizable.strings lets call the translation method and get the data back

        //Let's Get the list of all languages

        List<String> supportedLanguage = Arrays.asList(SupportedLanguage.supportedLanguageCode);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        System.out.println("Translating Resources");
        for (String languageCodeForTranslation : supportedLanguage) {

            Language from = Language.fromString(baseLanguage);
            if (!languageCodeForTranslation.equalsIgnoreCase(baseLanguage)) {

                String[] sourceText = baseLanguageStringForTranslation.toArray(new String[baseLanguageStringForTranslation.size()]);

               Runnable worker = new TranslateAndSaveiOS(clientID, clientSecret, baseStringResourcesMapping, iOSStringDOM, sourceText, from, languageCodeForTranslation, outputFolder);
                executor.execute(worker);

            }

        }
    }

    private void parseStringAndGenerateDom(String sourceFile) {

        File file = new File(sourceFile);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String text = "";

            int i = 0;
            while ((text = reader.readLine()) != null) {
                IOSStringMapping stringMapping = new IOSStringMapping();
                //Extract the iOS String token line by line
                StringTokenizer iosToken = new StringTokenizer(text, "=");

                //Check if the token count is 2 this is to ignore comments     
                if (iosToken.countTokens() == 2) {
                    String key = iosToken.nextToken().replace("\"", "").trim();
                    String value = iosToken.nextToken().replace("\"", "");
                    value = value.replace(";", "").trim();
                    stringMapping.setKeyName(key);
                    stringMapping.setKeyValue(value);
                    baseLanguageStringForTranslation.add(value);
                    baseStringResourcesMapping.put(value, i);
                    iOSStringDOM.add(stringMapping);
                    i++;
                }
            }
        } catch (Exception io) {
            System.out.println(io.getMessage());
        }
    }
}
