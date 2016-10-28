package jp.technoco.thefoursamurai.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;

public class SoundTest2 extends Thread {

    private static final int BUFFER_SIZE = 3200;
    //  private static final int DM_BUFFER_SIZE = 500; // 実験用ダミー

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

            for (;;) {

                //          // 実験用ダミー（今は無効）
                //          if(flag){
                //            sourceDataline.write(dm, 0, dm.length);
                //            //  flag=false; // 一度だけダミーを挟むとき
                //          }
                
                sourceDataline.write(au_data, 0, targetDataLine.read(au_data, 0, au_data.length));

            }

        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
}
