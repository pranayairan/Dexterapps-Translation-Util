##About
DexterApps Translation Util is a simple utility that can take an Android project's string.xml or iOS Project Localizable.strings file and automatically translate it for multiple languages.

Dexterapps Translation Util uses Microsoft Translate API to translate text into 39 different languages. 

Inspired from : https://code.google.com/p/android-string-resource-translator/

##Supported Languages
English <br/>
Arabic <br/>
Bulgarian <br/>
Catalan <br/>
Chinese Simplified <br/>
Chinese Traditional<br/>
Czech <br/>
Danish <br/>
Dutch (Standard)<br/>
Estonian <br/>
Finnish <br/>
French <br/>
German <br/>
Greek<br/>
Hebrew <br/>
Hindi <br/>
Hungarian <br/>
Indonesian <br/>
Italian  <br/>
Japanese <br/>
Korean <br/>
Latvian <br/>
Lithuanian<br/>
Malaysian <br/>
Norwegian <br/>
Farsi <br/>
Polish <br/>
Portuguese  <br/>
Romanian <br/>
Russian<br/>
Slovak <br/>
Slovenian <br/>
Spanish <br/>
Swedish <br/>
Thai <br/>
Turkish <br/>
Ukrainian <br/>
Urdu <br/>
Vietnamese<br/>


## Registering For Microsoft Translate API
You need to register to microsoft azure marketplace to access these API. These API are free for 2,000,000 Characters/month.

1. To Register An Application at Azure MarketPlace https://datamarket.azure.com/developer/applications/register

2. To Add Microsoft Translate Service to your Azure App 
https://datamarket.azure.com/dataset/bing/microsofttranslator 

For More Information visit http://msdn.microsoft.com/en-us/library/hh454950.aspx

## Usage Instructions

* Double click on DexterappsTranslationUtil.jar, this will open up translation GUI
* Enter your Azure Client ID and Client Secret which you got from Azure Market place
* Choose your application platform (Android/iOS)
* Give the source file, String.xml for Android. Localizable.properties for iOS
* Choose Ouptut folder where you want to save the translated files (app will create new folders with appropriate locale)

Translation Util Application GUI is created using Netbeans. You can open the project and check the code as netbeans project.

## Known Issues

* Date, time and other formats getting translated
* for iOS Comments are getting Stripped in translated files

##Powered by: 

Microsoft Translator Java API by  Jonathan Griggs https://code.google.com/p/microsoft-translator-java-api/

Jdom for XML maNIPULATION http://www.jdom.org/

##License
Dexterapps Translation Utility is licensed under the Apache License, Version 2.0

    /*
     * Copyright 2014 Pranay Airan.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
Please note that while this license does not distinguish between personal, internal or commercial use, the Microsoft Translator API itself does in fact make this distinction:

If you intend to use the Microsoft Translator API for commercial or high volume purposes, you would need to sign a commercial license agreement and provide your appID to the Microsoft Translator team. For more details contact mtlic@microsoft.com. This allows the Microsoft Translator team to better tune the service to the needs of our many partners, and avoid abuse.
