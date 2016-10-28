package jp.technoco.thefoursamurai.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;

public class SoundTest2 extends Thread {

    private static final int BUFFER_SIZE = 3200;
    //  private static final int DM_BUFFER_SIZE = 500; // 実験用ダミー

    private final double sec = 0.15; // チャートに表示する期間(s)

    public SoundTest2() {
    }

    public static void main(String[] args) {
        SoundTest2 m = new SoundTest2();
        m.init();
    }

    @Test
    public void init() {

        //   // 実験用ダミー
        //      byte[] dm = new byte[DM_BUFFER_SIZE];
        //
        //      for(int i=0;i < DM_BUFFER_SIZE-1;i++){
        //        dm[0] = 0;
        //         // 無音データは０じゃない？みたい
        //      }
        //      boolean flag = false; // true で実験， 今は無効

        byte[] au_data = new byte[BUFFER_SIZE];

        try {

            // 22 kHz, 16 bit, ステレオでオーディオ形式を生成
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050.0F, 16, 2, 4, 22050.0F,
                    false);

            // 読み出し情報を生成
            DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            // 読み込みライン（ターゲットライン）を生成
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);

            // ラインを開く
            targetDataLine.open(audioFormat);

            // 書き込み情報を生成
            DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

            // 書き出しライン（ソースライン）を生成
            SourceDataLine sourceDataline = (SourceDataLine) AudioSystem.getLine(sourceInfo);

            // ラインを開く
            sourceDataline.open(audioFormat);

            System.out.println("録音->再生 開始");

            sourceDataline.start();
            targetDataLine.start();
            int[] values = null;
            for (;;) {

                //          // 実験用ダミー（今は無効）
                //          if(flag){
                //            sourceDataline.write(dm, 0, dm.length);
                //            //  flag=false; // 一度だけダミーを挟むとき
                //          }

                // sourceDataline.write(au_data, 0, targetDataLine.read(au_data, 0, au_data.length));
                targetDataLine.read(au_data, 0, au_data.length);
                //System.out.println(au_data.length);

                AudioInputStream linearStream = new AudioInputStream(targetDataLine);
                int mount = (int) (audioFormat.getSampleRate() * sec);

                // 音声データの取得
                values = new int[mount];
                int data = (int) ByteBuffer.wrap(au_data).order(ByteOrder.LITTLE_ENDIAN).getShort();
                System.out.println(data);
               // int data = linearStream.read(au_data);

                // int data = targetDataLine.read(au_data, 0, au_data.length);

                // System.out.println(data);
            }

        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
}
