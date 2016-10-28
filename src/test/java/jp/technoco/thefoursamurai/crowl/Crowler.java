package jp.technoco.thefoursamurai.crowl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class Crowler {

    @Test
    public void start() {
        // String url = "http://rss.rssad.jp/rss/gihyo/feed/rss1";
        String url = "http://owaraineta.com/feed/";
        // String url = "http://rss.rssad.jp/rss/gihyo/feed/rss2";
        // String url = "http://rss.rssad.jp/rss/gihyo/feed/atom";

        URL feedUrl = null;
        try {
            feedUrl = new URL(url);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        SyndFeedInput input = new SyndFeedInput();

        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FeedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // サイトのタイトル
        System.out.println(feed.getTitle());
        // サイトのURL
        System.out.println(feed.getLink());

        for (Object obj : feed.getEntries()) {
            SyndEntry entry = (SyndEntry) obj;
            // 記事タイトル
            System.out.println(entry.getTitle());
            // 記事のURL
            System.out.println(entry.getLink());
            // 記事の詳細
            System.out.println(entry.getDescription().getValue());
        }
    }

}
