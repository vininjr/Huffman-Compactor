package br.ufc.crateus.eda.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFile {

	public static void main(String[] args) {
		if (args.length == 3) {
			if (args[0].equals("-c")) {
				comprimir(args[1], args[2]);
			} else if (args[0].equals("-d")) {
				descomprimir(args[1], args[2]);
			} else {
				mensagem();
			}
		} else {
			mensagem();
		}

	}

	private static void mensagem() {
		System.out.println("Algum argumento esta errado");
	}

	@SuppressWarnings("unused")
	private static void comprimir(String entrada1, String saida1) {
		File f1 = new File(entrada1);
		File f2 = new File(saida1);

		try {

			FileInputStream fis1 = new FileInputStream(entrada1);
			FileInputStream fis2 = new FileInputStream(entrada1);
			FileOutputStream fos = new FileOutputStream(saida1);

			MontHuffman hc = new MontHuffman();
			hc.compress(fis1, fis2, fos);

			fis1.close();
			fis2.close();
			fos.close();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	@SuppressWarnings("unused")
	private static void descomprimir(String entrada2, String saida2) {
		File f1 = new File(entrada2);
		File f2 = new File(saida2);

		try {
			FileInputStream fis = new FileInputStream(entrada2);
			FileOutputStream fos = new FileOutputStream(saida2);

			// ---------------------------------------
			MontHuffman hc = new MontHuffman();
			hc.decompress(fis, fos);
			// ---------------------------------------

			fis.close();
			fos.close();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

}