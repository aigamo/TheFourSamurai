package jp.technoco.thefoursamurai.sound;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import jp.technoco.thefoursamurai.sound.model.SoundData;

@Component
public class SoundThreadService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SoundInputService soundInputService;

    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Callable<String>() {

            public String call() {
                SimpleDateFormat sdf = new SimpleDateFormat("mmss");

                while (true) {
                    SoundData soundData = new SoundData();

                    int volume = 0;
                    for (int i = 0; i < 10; i++) {
                        volume = volume + soundInputService.getVolume();
                    }
                    volume = volume / 10;
                    soundData.setData(new Integer(volume).toString());
                    soundData.setNow(sdf.format(new Date()));
                    messagingTemplate.convertAndSend("/topic/sounddata", soundData,
                            Collections.singletonMap("content-type", "application/json;charset=UTF-8"));
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };

        });

    }
}
