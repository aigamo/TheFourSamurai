package jp.technoco.thefoursamurai.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.junit.Ignore;
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
    @Ignore
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

                // sourceDataline.write(au_data, 0, targetDataLine.read(au_data, 0, au_data.length));
                targetDataLine.read(au_data, 0, au_data.length);
                //System.out.println(au_data.length);

                // AudioInputStream linearStream = new AudioInputStream(targetDataLine);
                //  int mount = (int) (audioFormat.getSampleRate() * sec);
                //System.out.println("mount" + mount);
                //  double[] values = new double[mount];
                //    System.out.println((int) ByteBuffer.wrap(au_data).order(ByteOrder.LITTLE_ENDIAN).getShort());

                int data = (int) ByteBuffer.wrap(au_data).order(ByteOrder.LITTLE_ENDIAN).getShort();
                if (data < 0) {
                    data = data * -1;
                }
                System.out.println(data);
                //  for (int i = 0; i < mount; mount++) {
                // 音声データの取得

                //  values[i] = data;
                ///   System.out.println(data);

                //   System.out.println((int) ByteBuffer.wrap(au_data).order(ByteOrder.LITTLE_ENDIAN).getShort());
                //   }

                //  FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
                //   Complex[] complexs = fft.transform(values, TransformType.FORWARD);

                //     Arrays.asList(complexs).forEach(i -> {
                //  System.out.println(i.getReal());
                //     });

                // int data = linearStream.read(au_data);
                // int data = targetDataLine.read(au_data, 0, au_data.length);

                // System.out.println(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error:" + e);
        }
    }
}
