package com.company;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.screenrecorder.ScreenRecorder.ENCODING_BLACK_CURSOR;

import java.awt.*;
import java.nio.ByteOrder;

import org.monte.media.AudioFormatKeys;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import javax.sound.sampled.Mixer;

public class VideoRecorder {

    private ScreenRecorder screenRecorder;

    public void startRecording(Rectangle rect) {

        try {
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration();
            screenRecorder = new ScreenRecorder(gc,// the file format
                    rect, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
//
// the output format for screen capture
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_ANIMATION, CompressorNameKey,
                            COMPRESSOR_NAME_QUICKTIME_ANIMATION, DepthKey, 24, FrameRateKey, new Rational(5, 1)),
//
// the output format for mouse capture
null,//
// the output format for audio capture
                    new Format(MediaTypeKey, MediaType.AUDIO, EncodingKey, ENCODING_QUICKTIME_TWOS_PCM, FrameRateKey,
                            new Rational(48000, 1), SampleSizeInBitsKey, 16, ChannelsKey, 1, SampleRateKey, new Rational(48000, 1),
                            SignedKey, true, ByteOrderKey, ByteOrder.BIG_ENDIAN), null);
            this.screenRecorder.start();

        } catch (Exception e) {
            System.out.println(e+" startrecording");
        }
    }

    public void stopRecording() {
        try {
            this.screenRecorder.stop();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Mixer getMixer(){
        return screenRecorder.getAudioMixer();
    }

}