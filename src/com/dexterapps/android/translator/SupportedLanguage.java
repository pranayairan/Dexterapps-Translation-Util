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

import java.util.HashMap;

/**
 *
 * @author Pranay Airan
 * http://www.dexterapps.in/
 * pranay.airan@iiitb.net
 * 
 * This Class contains list of all Supported Languages for translation
 * If you don't want to translate into all the languages you can remove that
 * language code
 */
public class SupportedLanguage {

    //39 Languages Support
    public static String[] supportedLanguageCode = {
        "en", "ar", "bg", "ca", "zh-CHS", "zh-CHT", "cs", "da", "nl", 
        "et", "fi", "fr", "de", "el","he", "hi", "hu", "id", "it", "ja", 
        "ko", "lv", "lt","ms", "no", "fa", "pl", "pt", "ro", "ru", 
        "sk", "sl", "es", "sv", "th", "tr", "uk", "ur", "vi"};
    
   
    public static String[] supportedLanguage = {
        "English", "Arabic", "Bulgarian", "Catalan", "Chinies Simplified", "Chinies Traditional",
        "Czech", "Danish", "Dutch (Standard)","Estonian", "Finnish", "French", "German", "Greek",
        "Hebrew", "Hindi", "Hungarian", "Indonesian", "Italian ", "Japanese", "Korean", "Latvian", "Lithuanian",
        "Malaysian", "Norwegian", "Farsi", "Polish", "Portuguese ", "Romanian", "Russian",
        "Slovak", "Slovenian", "Spanish", "Swedish", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese"};
   
    public static final HashMap<String, String> languageCodeMap = new HashMap<String, String>();

    static {
        languageCodeMap.put("English", "en");
        languageCodeMap.put("Arabic", "ar");
        languageCodeMap.put("Bulgarian", "bg");
        languageCodeMap.put("Catalan", "ca");
        languageCodeMap.put("Chinies Simplified", "zh-CHS");
        languageCodeMap.put("Chinies Traditional", "zh-CHT");
        languageCodeMap.put("Czech", "cs");
        languageCodeMap.put("Danish", "da");
        languageCodeMap.put("Dutch (Standard)", "nl");
        languageCodeMap.put("Estonian", "et");
        languageCodeMap.put("Finnish", "fu");
        languageCodeMap.put("French", "fr");
        languageCodeMap.put("German", "de");
        languageCodeMap.put("Greek", "el");
        languageCodeMap.put("Hebrew", "he");
        languageCodeMap.put("Hindi", "hi");
        languageCodeMap.put("Hungarian", "hu");
        languageCodeMap.put("Indonesian", "id");
        languageCodeMap.put("Italian ", "it");
        languageCodeMap.put("Japanese", "jp");
        languageCodeMap.put("Korean", "ko");
        languageCodeMap.put("Latvian", "lv");
        languageCodeMap.put("Lithuanian", "lt");
        languageCodeMap.put("Malaysian", "ms");
        languageCodeMap.put("Maltese", "mt");
        languageCodeMap.put("Norwegian", "no");
        languageCodeMap.put("Farsi", "fa");
        languageCodeMap.put("Polish", "pl");
        languageCodeMap.put("Portuguese", "pt");
        languageCodeMap.put("Romanian", "ro");
        languageCodeMap.put("Russian", "ru");
        languageCodeMap.put("Slovak", "sk");
        languageCodeMap.put("Slovenian", "sl");
        languageCodeMap.put("Spanish", "es");
        languageCodeMap.put("Swedish", "sv");
        languageCodeMap.put("Thai", "th");
        languageCodeMap.put("Turkish", "tr");
        languageCodeMap.put("Ukrainian", "uk");
        languageCodeMap.put("Urdu", "ur");
        languageCodeMap.put("Vietnamese", "vi");
    }
}
