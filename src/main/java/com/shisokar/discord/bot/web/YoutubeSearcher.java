package com.shisokar.discord.bot.web;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeSearcher {

    private final YouTube youtube;
    private final Search.List search;

    public YoutubeSearcher(String apikey){
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                                      (HttpRequest request) -> {}).setApplicationName("ShisouBot").build();
        Search.List tmp = null;
        try {
            tmp = youtube.search().list("id,snippet");
        } catch (IOException e) {
            SimpleLog.getLog("Youtube").fatal("failed to initialize search: "+e.toString());
        }
        search = tmp;
        if(search == null){
            return;
        }
        search.setKey(apikey);
        search.setType("video");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
    }

    public List<String> getResults(String query, int numResults){
        List<String> results = new ArrayList<>();
        search.setQ(query);
        search.setMaxResults((long)numResults);

        SearchListResponse searchResponse;
        try {
            searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            searchResultList.stream().forEach((sr) -> {
                results.add(sr.getId().getVideoId()+"|"+sr.getSnippet().getTitle());
            });
        } catch (IOException e) {
            SimpleLog.getLog("Youtube").fatal("Search failure: "+e.toString());
            return null;
        }
        return results;
    }

}
