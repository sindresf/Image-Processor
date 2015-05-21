package image.to.mp4;

import my_classes.Folder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.jcodec.common.*;
import org.jcodec.common.model.Size;
import org.jcodec.containers.mp4.*;
import org.jcodec.containers.mp4.muxer.*;

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