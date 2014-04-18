package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class RMIInputStream extends InputStream implements Serializable {
	RMIInputStreamImpl in;

	public RMIInputStream(RMIInputStreamImpl in) {
		this.in = in;
	}

	public int read() throws IOException {
		return in.lecture();
	}

	public int read(byte[] b, int off, int len) throws IOException {
		byte[] b2 = in.lectureBytes(len);
		if (b2 == null)
			return -1;
		int i = b2.length;
		System.arraycopy(b2, 0, b, off, i);
		return i;
	}

	public void close() throws IOException {
		super.close();
	}

}