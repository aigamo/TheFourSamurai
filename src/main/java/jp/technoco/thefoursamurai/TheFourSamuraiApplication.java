package jp.technoco.thefoursamurai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jp.technoco.thefoursamurai.sound.SoundThreadService;

@SpringBootApplication
public class TheFourSamuraiApplication implements CommandLineRunner {

    @Autowired
    public SoundThreadService soundThreadService;

    public static void main(String[] args) {
        SpringApplication.run(TheFourSamuraiApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        soundThreadService.run();
    }
}
