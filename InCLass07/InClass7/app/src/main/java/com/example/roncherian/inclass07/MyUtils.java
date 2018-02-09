package com.example.roncherian.inclass07;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roncherian on 23/10/17.
 */

public class MyUtils {

    static public class MyJSONParser {
        static List<Email> parseMovieTracks(String in) throws JSONException {

            ArrayList<Email> moviesArrayList = new ArrayList<Email>();
            JSONObject jsonObject = new JSONObject(in);

            if (jsonObject.length() == 0) {
                return moviesArrayList;
            }
            /*JSONObject resultJsonObject = jsonObject.getJSONObject("original_title");
            if (resultJsonObject.length() == 0){
                return moviesArrayList;
            }
            JSONObject trackMatchesJsonObject = resultJsonObject.getJSONObject("trackmatches");
            if (trackMatchesJsonObject.length() == 0){
                return moviesArrayList;
            }*/
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonMovieObject = jsonArray.getJSONObject(i);

                Email email = new Email();

                //movie.setMovieName(jsonMovieObject.getString("original_title"));
                //movie.setOverview(jsonMovieObject.getString("overview"));
                //movie.setReleaseDate(jsonMovieObject.getString("release_date"));
                //movie.setRating(jsonMovieObject.getString("vote_average"));
                //movie.setPopularity(jsonMovieObject.getString("popularity"));
                //movie.setPosterPath(jsonMovieObject.getString("poster_path"));

                moviesArrayList.add(email);
            }
            return moviesArrayList;
        }
    }

    static public class MyXmlParser{

        static public ArrayList<Email> parseItems(String xml ) throws XmlPullParserException, IOException {

            ArrayList<Email>recipesArrayList = new ArrayList<Email>();
            Email email = null;
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

            byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);
            //ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("email")){
                            email = new Email();
                            //person.setId(parser.getAttributeValue(null,"id"));//First one is name space

                        } else if (parser.getName().equals("title")){
                            //email.setTitle(parser.nextText().trim());
                        } else if (parser.getName().equals("href")){
                            //email.setUrl(parser.nextText().trim());
                        } else if (parser.getName().equals("ingredients")){
                            //email.setIngredients(parser.nextText().trim());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("email") && email!=null){
                            recipesArrayList.add(email);
                            email = null;
                        }
                }

                eventType = parser.next();
            }
            return  recipesArrayList;
        }
    }
}
