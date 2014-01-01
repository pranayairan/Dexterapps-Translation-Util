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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pranay Airan
 * http://www.dexterapps.in/
 * pranay.airan@iiitb.net
 * 
 * This Class Calls the Translate Method to get all the Strings Translated.
 * this class is also responsible to save the translated strings back in correct
 * file format and schema.
 * 
 * This class is used for Generating ios Localizable.string
 */

public class TranslateAndSaveiOS implements Runnable {

    String azureClientID;
    String azureClientSecret;
    HashMap<String, Integer> baseStringResourcesMapping;
    ArrayList<IOSStringMapping> iOSStringDOM;
    String[] sourceText;
    String languageCodeForTranslation;
    Language from;
    String outputFolder;

    public TranslateAndSaveiOS(String clientId, String clientSecret, HashMap<String, Integer> tempBaseStringResourcesMapping,
            ArrayList<IOSStringMapping> tempIOSStringDOM, String[] tempSourceText, Language tempFrom, 
            String tempLanguageCodeForTranslation,String tempOutputFolder) {
       
        azureClientID = clientId;
        azureClientSecret = clientSecret;
        baseStringResourcesMapping = tempBaseStringResourcesMapping;
        iOSStringDOM = tempIOSStringDOM;
        sourceText = tempSourceText;
        from = tempFrom;
        languageCodeForTranslation = tempLanguageCodeForTranslation;
        outputFolder = tempOutputFolder;
    }

    @Override
    public void run() {
        
        //Create The Translate Text object passing your Client Secret and Client ID
        TranslateText translateIOS = new TranslateText(azureClientID, azureClientSecret);
        String[] translatedText = translateIOS.translateTextArrays(sourceText, from, Language.fromString(languageCodeForTranslation));

        //Check if the strings are translated, if yes save the strings in new file
        if(translatedText!=null)
        {
            //Now we got the translated Text let's create the .string for the country
            generateStringForCountry(languageCodeForTranslation, Arrays.asList(translatedText), iOSStringDOM, baseStringResourcesMapping, outputFolder);
        }
    }

    //This method creates a new Localizable.strings with appropriate folder for that locale
    private void generateStringForCountry(String countryCode, List<String> translatedText, ArrayList<IOSStringMapping> iOSStringDOM, HashMap<String, Integer> baseStringResourcesMapping, String outPutFolder) {
       
        try {   
            
            File file = new File(outPutFolder + "/" + countryCode);
            if (!file.exists()) {
                file.mkdir();
            }
                FileWriter fileWriter = new FileWriter(outPutFolder + "/" + countryCode+"/Localizable.strings");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (int i = 0; i < iOSStringDOM.size(); i++) {
                    IOSStringMapping stringMapping = iOSStringDOM.get(i);

                        String key = stringMapping.getKeyName();

                        //To get the attribute value, use the hasmap and then string array
                        int translatedTextIndex = baseStringResourcesMapping.get(stringMapping.getKeyValue());
                        String value = translatedText.get(translatedTextIndex);

                        String finalString = "\""+key+"\" = \""+value+"\";";
                        
                        //Write to File
                        bufferedWriter.write(finalString);
                        bufferedWriter.newLine();
                }
                
                bufferedWriter.close();
                System.out.println("Translation Successful !!"); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   
}
