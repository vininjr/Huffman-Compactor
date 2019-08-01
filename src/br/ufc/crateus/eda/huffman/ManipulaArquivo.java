package br.ufc.crateus.eda.huffman;

import java.io.OutputStream;
import java.io.IOException;

public class ManipulaArquivo {
	int pos = 0;
	boolean[] buffer = new boolean[8];
	byte[] outStream = new byte[12288];
	int outLength = 0;
	OutputStream out;
	boolean booleandata = true;

	public ManipulaArquivo(OutputStream out, boolean bitdata) {
		this.out = out;
		booleandata = bitdata;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	public void write(boolean[] b) throws IOException {
		if (booleandata) {
			for (int i = 0; i < b.length; i++) {
				buffer[pos] = b[i];
				pos++;
				if (pos > 7) {
					byte character = Converte.booleanTobyte(buffer);
					pos -= 8;
					outStream[outLength] = character;
					outLength++;
				}
				if (outLength > 12200) {
					out.write(outStream, 0, outLength);
					outLength = 0;
				}
			}
		}
	}

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	public void write(byte[] b) throws IOException {
		if (!booleandata) {
			for (int i = 0; i < b.length; i++) {
				outStream[outLength] = b[i];
				outLength++;
				if (outLength > 12200) {
					out.write(outStream, 0, outLength);
					outLength = 0;
				}
			}
		}
	}

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	public void write(byte b) throws IOException {
		if (!booleandata) {
			outStream[outLength] = b;
			outLength++;
			if (outLength > 12200) {
				out.write(outStream, 0, outLength);
				outLength = 0;
			}
		}
	}

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	public void flush() throws IOException {
		if (booleandata) {
			boolean[] padding = new boolean[8 + (8 - pos)];
			int poscopy = pos;
			while (poscopy < 8) {
				padding[padding.length - (poscopy + 1)] = true;
				poscopy++;
			}
			write(padding);
		}
		out.write(outStream, 0, outLength);
		outLength = 0;
	}
}