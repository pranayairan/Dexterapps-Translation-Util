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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Pranay Airan
 * http://www.dexterapps.in/
 * pranay.airan@iiitb.net
 * 
 * This Class parse the input String.xml and converts them into String Array which then can be passed
 * to Microsoft Translate API for translation
 * 
 * We Use a Hash Map and a ArrayList to store the String.xml structure
 * this is used when we create the file thus avoiding parsing of XML again
 * 
 */

public class TranslateTextForAndroid {

	/**
	 * @param args
	 * @throws Exception 
	 */

	 HashMap<String, Integer> baseStringResourcesMapping ;

	 ArrayList<AndroidStringMapping> stringXmlDOM ;

	 ArrayList<String> baseLanguageStringForTranslation ;
         
        

	public void translateText(String clientID,String clientSecret,String sourceFilePath,String outputFolder,String baseLanguage) {

		baseStringResourcesMapping = new HashMap<String, Integer>();
		stringXmlDOM = new ArrayList<AndroidStringMapping>();
		baseLanguageStringForTranslation = new ArrayList<String>();
             
		// Parse The XML
		parseXMLAndGenerateDom(sourceFilePath);

		//Now we got the Strings from String.xml lets call the translation method and get the data back

		//Let's Get the list of all languages

		List<String> supportedLanguage =  Arrays.asList(SupportedLanguage.supportedLanguageCode);
                
                ExecutorService executor = Executors.newFixedThreadPool(5);
                
                System.out.println("Translating Resources"); 
		for (String languageCodeForTranslation : supportedLanguage) {

                        Language from = Language.fromString(baseLanguage);
			if(!languageCodeForTranslation.equalsIgnoreCase(baseLanguage))
			{
                            
                            String[] sourceText = baseLanguageStringForTranslation.toArray(new String[baseLanguageStringForTranslation.size()]);
                           
                            Runnable worker = new TranslateAndSaveAndroid(clientID, clientSecret, baseStringResourcesMapping, stringXmlDOM, sourceText, from, languageCodeForTranslation, outputFolder);
                            executor.execute(worker);
                            
			}

		}
                
	}

        //Parse the XML and Create String Array for Translation
	private  void parseXMLAndGenerateDom(String sourceFile)
	{
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(sourceFile);
		//System.out.println(xmlFile.getAbsolutePath());
		try {

			/*Navigate the XML DOM and populate the string array for translation
			  We also map the XML in java object so we can use to navigate it again for generating the xml back
			 */
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren();
			for (int i = 0; i < list.size(); i++) {

				AndroidStringMapping stringElement = new AndroidStringMapping();

				Element stringNode = (Element) list.get(i);
				if(stringNode.getName().equalsIgnoreCase("string"))
				{
					stringElement.setType("string");
					stringElement.setAttributeName(stringNode.getAttributeValue("name"));
					stringElement.setAttributeValue(stringNode.getText());

					baseLanguageStringForTranslation.add(stringNode.getText());
					baseStringResourcesMapping.put(stringNode.getText(), i);
				}
				else if (stringNode.getName().equalsIgnoreCase("string-array")) 
				{
					List stringArrayNodeList = stringNode.getChildren();
					ArrayList<String> stringArrayItems = new ArrayList<String>();

					for(int j = 0;j<stringArrayNodeList.size();j++)
					{
						Element stringArrayNode = (Element) stringArrayNodeList.get(j);

						baseLanguageStringForTranslation.add(stringArrayNode.getText());
						baseStringResourcesMapping.put(stringArrayNode.getText(), i+j);

						stringArrayItems.add(stringArrayNode.getText());
					}

					stringElement.setType("string-array");
					stringElement.setAttributeName(stringNode.getAttributeValue("name"));
					stringElement.setAttributeValue(stringArrayItems);

				}
				else
				{
					List stringPluralNodeList = stringNode.getChildren();
					ArrayList<AndroidStringPlurals> stringPluralsItems = new ArrayList<AndroidStringPlurals>();

					for(int j = 0;j<stringPluralNodeList.size();j++)
					{
						Element stringPluralNode = (Element) stringPluralNodeList.get(j);

						baseLanguageStringForTranslation.add(stringPluralNode.getText());
						baseStringResourcesMapping.put(stringPluralNode.getText(), i+j);

						AndroidStringPlurals pluralItem = new AndroidStringPlurals();
						
						pluralItem.setAttributeName(stringPluralNode.getAttributeValue("quantity"));
						pluralItem.setAttributeValue(stringPluralNode.getText());
						
						stringPluralsItems.add(pluralItem);
					}

					stringElement.setType("plurals");
					stringElement.setAttributeName(stringNode.getAttributeValue("name"));
					stringElement.setAttributeValue(stringPluralsItems);

				}

				stringXmlDOM.add(stringElement);


			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	
}
