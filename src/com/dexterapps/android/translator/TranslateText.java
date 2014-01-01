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

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 *
 * @author Pranay Airan
 * http://www.dexterapps.in/
 * pranay.airan@iiitb.net
 * 
 * This Class Calls the Azure Webservice for translating strings using Microsoft Translate JAVA API Library
 * Microsoft Translation API can take Single Strings or Group of Strings for translation
 */

public class TranslateText {

	
    String azureClientID;
    String azureClientSecret;
    
    public TranslateText(String clientID,String clientSecret)
    {
        azureClientID = clientID;
        azureClientSecret = clientSecret;
    }

     public String[] translateTextArrays(String[] sourceTexts, Language from, Language to) {

        // Set your Windows Azure Marketplace client info - See http://msdn.microsoft.com/en-us/library/hh454950.aspx

        //Translate.setClientId("thisisinsaneineedtogeneratemyownclientid");
        //Translate.setClientSecret("81+DZxvj/VGO4gPlfYpKSrbBHLdyB4F7BXGq++ttI9I=");

        Translate.setClientId(azureClientID);
        Translate.setClientSecret(azureClientSecret);

        // Call the translate.execute method, passing an array of source texts
        String[] translatedTexts;
        try {
            translatedTexts = Translate.execute(sourceTexts, from, to);
           // System.out.println("Translation Successful");
        } catch (Exception e) {
            translatedTexts = null;
            System.out.println(e.getMessage());
        }


        return translatedTexts;
    }
	
}
