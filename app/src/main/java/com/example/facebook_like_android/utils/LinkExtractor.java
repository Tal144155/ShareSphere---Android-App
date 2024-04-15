package com.example.facebook_like_android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor {
    public static void main(String[] args) {
        String input = "Here are some links: https://www.example.com, http://anotherexample.com, and www.example.org";

        extractLinks(input);
    }

    public static List<String> extractLinks(String input) {
        List<String> links = null;

        // Regular expression pattern to match URLs
        Pattern pattern = Pattern.compile("/^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$/");

        Matcher matcher = pattern.matcher(input);

        // Find all URLs and parse them as URIs
        while (matcher.find()) {
            links = new ArrayList<>();
            String url = matcher.group();
            links.add(url);
//            try {
//                URI uri = new URI(url);
//                System.out.println("Found URL: " + uri.toString());
//            } catch (URISyntaxException e) {
//                // Not a valid URI, ignore it
//            }
        }

        return links;
    }
}
