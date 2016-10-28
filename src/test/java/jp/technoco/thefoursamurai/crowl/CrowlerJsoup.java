package jp.technoco.thefoursamurai.crowl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jp.technoco.thefoursamurai.model.Add;
import jp.technoco.thefoursamurai.model.Adds;
import jp.technoco.thefoursamurai.model.Commit;
import jp.technoco.thefoursamurai.model.Doc;
import jp.technoco.thefoursamurai.model.WatsonData;

public class CrowlerJsoup {

    int passageId = 0;

    @Test
    public void crowl() {
        // 全部で27ページある
        //int pageNumber = 1;
        int pageNumber = 27;
        String baseUrl = "http://owaraineta.com/page/";

        WatsonData watsonData = new WatsonData();
        List<Adds> addss = new ArrayList<Adds>();
        for (int page = 1; page <= pageNumber; page++) {
            Document document = null;
            String targetUrl = baseUrl + page + "/";
            try {
                document = Jsoup.connect(targetUrl).get();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Elements elements1 = document.select("div.contents > p");

            Elements link = elements1.select("a.more-link");

            link.forEach(i -> {
                String linkUrl = i.attr("href");
                Document moreDocument = null;
                try {
                    moreDocument = Jsoup.connect(linkUrl).get();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Elements moreElements = moreDocument.select("div.contents > p");

                Doc doc = new Doc();

                Iterator<Element> iterator = moreElements.iterator();
                while (iterator.hasNext()) {
                    Element j = (Element) iterator.next();

                    Elements boke = j.select("p.neta1");
                    if (StringUtils.isNotEmpty(boke.text()) && StringUtils.contains(boke.text(), "：")) {
                        String[] bokes = boke.text().split("：");
                        if (bokes.length == 2) {
                            doc.setBoke_name(bokes[0]);
                            doc.setBoke(bokes[1]);
                        }
                    }
                    Elements tukkomi = j.select("p.neta2");

                    if (StringUtils.isNotEmpty(tukkomi.text()) && StringUtils.contains(tukkomi.text(), "：")) {
                        String[] tukkomis = tukkomi.text().split("：");
                        if (tukkomis.length == 2) {
                            doc.setTsukkomi_name(tukkomis[0]);
                            doc.setTsukkomi(tukkomis[1]);
                        }
                    }
                    if (StringUtils.isNotEmpty(doc.getBoke()) && StringUtils.isNotEmpty(doc.getTsukkomi())) {
                        passageId++;
                        doc.setId(new Integer(passageId).toString());
                        Add add = new Add();
                        add.setDoc(doc);
                        Adds adds = new Adds();
                        adds.setAdd(add);
                        addss.add(adds);
                        System.out.println(doc.getBoke() + doc.getTsukkomi());
                        doc = new Doc();
                    }
                }

            });

        }

        watsonData.setAddss(addss);
        Commit commit = new Commit();
        watsonData.setCommit(commit);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            //String json = mapper.writeValueAsString(watsonData);
            File file = new File("./src/test/resources/json/watsonData.json");
            //  Path outputFile = Paths.get("src","test","resouces", "json", "output.json");
            mapper.writeValue(file, watsonData);
            //  System.out.println(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
