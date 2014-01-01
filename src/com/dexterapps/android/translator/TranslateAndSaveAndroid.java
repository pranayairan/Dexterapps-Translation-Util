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

import com.dexterapps.android.translator.pojo.AndroidStringMapping;
import com.dexterapps.android.translator.pojo.AndroidStringPlurals;
import com.memetix.mst.language.Language;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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
 * This class is used for Generating Android String.xml
 */

public class TranslateAndSaveAndroid implements Runnable {

    String azureClientID;
    String azureClientSecret;
    HashMap<String, Integer> baseStringResourcesMapping;
    ArrayList<AndroidStringMapping> stringXmlDOM;
    String[] sourceText;
    String languageCodeForTranslation;
    Language from;
    String outputFolder;

    public TranslateAndSaveAndroid(String clientId, String clientSecret, HashMap<String, Integer> tempBaseStringResourcesMapping,
            ArrayList<AndroidStringMapping> tempStringXmlDOM, String[] tempSourceText, Language tempFrom, 
            String tempLanguageCodeForTranslation,String tempOutputFolder) {
       
        azureClientID = clientId;
        azureClientSecret = clientSecret;
        baseStringResourcesMapping = tempBaseStringResourcesMapping;
        stringXmlDOM = tempStringXmlDOM;
        sourceText = tempSourceText;
        from = tempFrom;
        languageCodeForTranslation = tempLanguageCodeForTranslation;
        outputFolder = tempOutputFolder;
    }

    @Override
    public void run() {
        
        //Create The Translate Text object passing your Client Secret and Client ID
        TranslateText translateAndroid = new TranslateText(azureClientID, azureClientSecret);
        String[] translatedText = translateAndroid.translateTextArrays(sourceText, from, Language.fromString(languageCodeForTranslation));

        //Check if the strings are translated, if yes save the strings in new file
        if(translatedText!=null)
        {
            //Now we got the translated Text let's create the xml for the country
            generateXMLForCountry(languageCodeForTranslation, Arrays.asList(translatedText), stringXmlDOM, baseStringResourcesMapping, outputFolder);
        }
    }

    //This method creates a new String.XML with appropriate folder for that locale
    private void generateXMLForCountry(String countryCode, List<String> translatedText, ArrayList<AndroidStringMapping> stringXmlDOM, HashMap<String, Integer> baseStringResourcesMapping, String outPutFolder) {
        Element resources = new Element("resources");
        Document doc = new Document(resources);

        //System.out.println("Generating XML");

        //int totalNumberOfStrings = 0;
        for (int i = 0; i < stringXmlDOM.size(); i++) {
            AndroidStringMapping stringMapping = stringXmlDOM.get(i);

            if (stringMapping.getType().equalsIgnoreCase("string")) {
                Element string = new Element("string");
                string.setAttribute(new Attribute("name", stringMapping.getAttributeName()));

                //To get the attribute value, use the hasmap and then string array
                int translatedTextIndex = baseStringResourcesMapping.get(stringMapping.getAttributeValue());
                string.setText(translatedText.get(translatedTextIndex));

                //Add element to root
                doc.getRootElement().addContent(string);
            } else if (stringMapping.getType().equalsIgnoreCase("string-array")) {
                Element stringArray = new Element("string-array");
                stringArray.setAttribute(new Attribute("name", stringMapping.getAttributeName()));

                //Since this is String array it will have a list of string items, get the list of string items

                ArrayList<String> stringArrayItems = (ArrayList<String>) stringMapping.getAttributeValue();

                for (int j = 0; j < stringArrayItems.size(); j++) {
                    int translatedTextIndex = baseStringResourcesMapping.get(stringArrayItems.get(j));
                    stringArray.addContent(new Element("item").setText(translatedText.get(translatedTextIndex)));
                }

                //Add element to root
                doc.getRootElement().addContent(stringArray);
            } else {
                Element stringArray = new Element("plurals");
                stringArray.setAttribute(new Attribute("name", stringMapping.getAttributeName()));

                //Since this is plurals it will have a list of string items with values, get the list of string items

                ArrayList<AndroidStringPlurals> stringPluralItems = (ArrayList<AndroidStringPlurals>) stringMapping.getAttributeValue();

                for (int j = 0; j < stringPluralItems.size(); j++) {

                    int translatedTextIndex = baseStringResourcesMapping.get(stringPluralItems.get(j).getAttributeValue());
                    Element pluralItem = new Element("item");
                    pluralItem.setAttribute("quantity", stringPluralItems.get(j).getAttributeName());
                    pluralItem.setText(translatedText.get(translatedTextIndex));

                    stringArray.addContent(pluralItem);
                }

                //Add element to root
                doc.getRootElement().addContent(stringArray);
            }
        }

        // new XMLOutputter().output(doc, System.out);
        XMLOutputter xmlOutput = new XMLOutputter();

        try {
           // System.out.println("Saving File");
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            xmlOutput.setFormat(format);


            File file = new File(outPutFolder + "/values-" + countryCode);
            if (!file.exists()) {
                file.mkdir();
            }

            file = new File(outPutFolder + "/values-" + countryCode + "/strings.xml");
            FileOutputStream fop = new FileOutputStream(file);
            xmlOutput.output(doc, fop);
             System.out.println("Translation Successful !!"); 

           // System.out.println("File Saved!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   
}
