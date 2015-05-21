package image.to.mp4;

import my_classes.files_n_folders.Folder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jcodec.common.*;
import org.jcodec.common.model.Size;
import org.jcodec.common.model.TapeTimecode;
import org.jcodec.containers.mp4.*;
import org.jcodec.containers.mp4.muxer.*;

public class ImageToMp4Encoder {
	private SeekableByteChannel ch;
	private FramesMP4MuxerTrack outTrack;
	private int frameNum;
	private int fps;
	private MP4Muxer muxer;
	private Size size;

	public ImageToMp4Encoder(File out) throws IOException {
		ch = NIOUtils.writableFileChannel(out);

		// Muxer that will store the encoded frames
		muxer = new MP4Muxer(ch, Brand.MP4);

		frameNum = 0;
		fps = 60;
		// Add video track to muxer
		outTrack = muxer.addTrackForCompressed(TrackType.VIDEO, fps);
	}

	public void encodeImage(File png) throws IOException {
		if (size == null) {
			BufferedImage read = ImageIO.read(png);
			size = new Size(read.getWidth(), read.getHeight());
		}

		ByteBuffer data = NIOUtils.fetchFrom(png);// contains encoded frame
		long pts = frameNum * 2 * fps;
		long duration = 2 * fps;
		boolean iframe = true;// always iframe!
		TapeTimecode tapeTimecode = null; // not really used anymore
		int entryNo = 0; // nobody talks about this one
		MP4Packet mp4packet = new MP4Packet(data, pts, fps, duration, pts,
				iframe, tapeTimecode, pts, entryNo);

		outTrack.addFrame(mp4packet);
		frameNum++;
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
		ImageToMp4Encoder encoder = new ImageToMp4Encoder(new File(
				"res/video.mp4"));
		System.out.println("getting all files in the MP4Test folder");
		ArrayList<File> imageFiles = Folder.getFiles("res/MP4Test");
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