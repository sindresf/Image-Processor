package image.to.mp4;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import my_classes.Folder;
import my_classes.ImageFolderHandler;

import org.jcodec.codecs.h264.H264Encoder;
import org.jcodec.codecs.h264.H264Utils;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.common.model.Size;
import org.jcodec.containers.mp4.Brand;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.TrackType;
import org.jcodec.containers.mp4.muxer.FramesMP4MuxerTrack;
import org.jcodec.containers.mp4.muxer.MP4Muxer;
import org.jcodec.scale.AWTUtil;
import org.jcodec.scale.RgbToYuv420;

public class SequenceEncoder {
	private SeekableByteChannel ch;
	private FramesMP4MuxerTrack outTrack;
	private int frameNo;
	private MP4Muxer muxer;
	private Size size;

	public SequenceEncoder(File out) throws IOException {
		this.ch = NIOUtils.writableFileChannel(out);

		// Muxer that will store the encoded frames
		muxer = new MP4Muxer(ch, Brand.MP4);

		// Add video track to muxer
		outTrack = muxer.addTrackForCompressed(TrackType.VIDEO, 25);
	}

	public void encodeImage(File png) throws IOException {
		if (size == null) {
			BufferedImage read = ImageIO.read(png);
			size = new Size(read.getWidth(), read.getHeight());
		}
		// Add packet to video track
		outTrack.addFrame(new MP4Packet(NIOUtils.fetchFrom(png), frameNo, 25,
				1, frameNo, true, null, frameNo, 0));

		frameNo++;
	}

	public void finish() throws IOException {
		// Push saved SPS/PPS to a special storage in MP4
		outTrack.addSampleEntry(MP4Muxer.videoSampleEntry("png ", size,
				"JCodec"));

		// Write MP4 header and finalize recording
		muxer.writeHeader();
		NIOUtils.closeQuietly(ch);
	}

	public static void main(String[] args) throws IOException {
		SequenceEncoder encoder = new SequenceEncoder(new File("res/video.mp4"));
		System.out.println("getting all files in the IMGFolderTest folder");
		ArrayList<File> imageFiles = Folder.getFiles("res/IMGFolderTest");
		System.out.println("making a video of them");
		int c = 1;
		System.out.print("adding image: ");
		for (File bi : imageFiles) {
			System.out.print(c + " ");
			encoder.encodeImage(bi);
			c++;
		}
		System.out.println("\nfinishing up");
		encoder.finish();
		System.out.println("done.");
	}
}