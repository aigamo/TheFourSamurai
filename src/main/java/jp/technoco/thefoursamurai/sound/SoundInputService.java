package jp.technoco.thefoursamurai.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.springframework.stereotype.Component;

@Component
public class SoundInputService {

    private static final int BUFFER_SIZE = 3200;

    private AudioFormat audioFormat;

    private DataLine.Info targetInfo;

    private TargetDataLine targetDataLine;

    @PostConstruct
    public void init() {
        // 22 kHz, 16 bit, ステレオでオーディオ形式を生成
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050.0F, 16, 2, 4, 22050.0F, false);

        targetInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            // ラインを開く
            targetDataLine.open(audioFormat);

            targetDataLine.start();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int getVolume() {
        byte[] au_data = new byte[BUFFER_SIZE];
        int data = 0;
        try {

            targetDataLine.read(au_data, 0, au_data.length);

            // 負数の場合は正の整数に変換
            data = (int) ByteBuffer.wrap(au_data).order(ByteOrder.LITTLE_ENDIAN).getShort();
            if (data < 0) {
                data = data * -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error:" + e);
        }
        return data;
    }

}
