package jp.technoco.thefoursamurai.sound;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import org.junit.Ignore;
import org.junit.Test;

public class SoundTest {

    @Test
    @Ignore
    public void test() {
        // リニアPCM 16bit 8000Hz × 10秒間 = 160000byte
        byte[] voiceData = new byte[160000];

        try {
            System.out.println("マイク入力開始...");

            // リニアPCM 8000Hz 16bit モノラル 符号付き リトルエンディアン
            AudioFormat linearFormat = new AudioFormat(8000, 16, 1, true, false);
            AudioFormat ulawFormat = new AudioFormat(AudioFormat.Encoding.ULAW, 8000, 8, 1, 1, 8000, false);

            // ターゲットデータラインを取得する
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, linearFormat);
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);

            // ターゲットデータラインをオープンする
            targetDataLine.open(linearFormat);

            // マイク入力開始
            targetDataLine.start();

            // ターゲットデータラインから入力ストリームを取得する
            AudioInputStream linearStream = new AudioInputStream(targetDataLine);
            AudioInputStream ulawStream = AudioSystem.getAudioInputStream(ulawFormat, linearStream);

            // 入力ストリームから音声データをByte配列へ取得する
            //linearStream.read(voiceData, 0, voiceData.length);
            AudioFormat audioFormat = linearStream.getFormat();
            for (int i = 0; i < (1000 / 20 * 10); i++) // 20msずつキャプチャーして送信
            {
                int data = ulawStream.read(voiceData, 0, voiceData.length);
                System.out.println(data);
            }

            // マイク入力停止
            targetDataLine.stop();

            // ターゲットデータラインをクローズする
            targetDataLine.close();

            System.out.println("マイク入力停止");

            // AUファイルへ書き出す
            File audioFile = new File("linear_pcm.au");
            ByteArrayInputStream baiStream = new ByteArrayInputStream(voiceData);
            AudioInputStream aiStream = new AudioInputStream(baiStream, linearFormat, voiceData.length);
            AudioSystem.write(aiStream, AudioFileFormat.Type.AU, audioFile);
            aiStream.close();
            baiStream.close();

            System.out.println("ファイルへ書き出し終了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
