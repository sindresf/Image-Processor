package image.to.mp4;

import my_classes.Folder;

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
	private int frameNo; // frame number = 0, 1, 2, 3, 4, 5, 6 ...
	private MP4Muxer muxer;
	private Size size;

	public ImageToMp4Encoder(File out) throws IOException {
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
		// outTrack.addFrame(new MP4Packet(NIOUtils.fetchFrom(png), frameNo, 25,
		// 1, frameNo, true, null, frameNo, 0));

		ByteBuffer data = NIOUtils.fetchFrom(png);// Bytebuffer that contains
													// encoded frame
		long pts = frameNo * 2; // PTS = 0, 2, 4, 6, 8, 10 ...
		long timescale = 1; // timescale = 1, so the values above are in
							// seconds ( 0/1, 2/1, 4/1, 6/1, 8/1 ... )
		long duration = 25;// duration = 2 / 1 = 2 seconds
		// frameno
		boolean iframe = true;// always iframe
		TapeTimecode tapeTimecode = null;
		long mediaPts = pts; // same as pts
		int entryNo = 0;
		MP4Packet mp4packet = new MP4Packet(data, pts, timescale, duration,
				frameNo, iframe, tapeTimecode, mediaPts, entryNo);
		outTrack.addFrame(mp4packet);
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