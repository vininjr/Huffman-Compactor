package br.ufc.crateus.eda.huffman;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class MontHuffman {

	public String compress(InputStream in1, InputStream in2, OutputStream out) {
		try {
			ManipulaArquivo m = new ManipulaArquivo(out, true);
			byte[] buffer = new byte[10240];

			int searching = in1.read(buffer);
			int[] frequencia = new int[256];
			while (searching > 0) {
				for (int i = 0; i < searching; i++) {
					frequencia[(int) buffer[i] + 128]++;
				}
				searching = in1.read(buffer);
			}
			try {
				for (int i = 0; i < frequencia.length; i++) {
					if (frequencia[(int) i + 128] != 0) {
						System.out.print((char) i + " - ");
						System.out.println(frequencia[(int) i + 128]);
					}
				}
			} catch (Exception e) {
			}
			in1.close();

			PriorityQueue<HuffmanTree> ph = new PriorityQueue<HuffmanTree>();
			HuffmanTree null_tree = new HuffmanTree();
			for (int i = 0; i < 256; i++) {
				if (frequencia[i] > 0) {
					ph.offer(new HuffmanTree((byte) (i - 128), null_tree, null_tree, frequencia[i]));
				}
			}

			if (ph.size() == 1) {
				ph.offer(new HuffmanTree((byte) 0, null_tree, null_tree, 0));
			}

			while (ph.size() > 1) {
				HuffmanTree bt_get1 = ph.poll();
				HuffmanTree bt_get2 = ph.poll();
				HuffmanTree add = new HuffmanTree(bt_get1, bt_get2, bt_get1.frequencia + bt_get2.frequencia);
				ph.offer(add);
			}

			HuffmanTree htree = ph.poll();
			m.write(htree.toBooleanArray());
			boolean[][] huffmantreeArrayList = htree.toArrayList();

			searching = in2.read(buffer);
			while (searching > 0) {
				for (int i = 0; i < searching; i++) {
					byte caractereByte = buffer[i];
					boolean[] caractereBoolArray = huffmantreeArrayList[(int) caractereByte + 128];
					m.write(caractereBoolArray);
				}
				searching = in2.read(buffer);
			}
			m.flush();
			in2.close();
			out.close();
		} catch (IOException ex) {
		}
		return null;
	}

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	public String decompress(InputStream in, OutputStream out) {
		try {
			ManipulaArquivo wb = new ManipulaArquivo(out, false);

			byte[] buffer = new byte[12288];
			boolean[] bufferbool = new boolean[8];
			boolean[] treeleaf = new boolean[8];
			boolean treefound = false;
			int numLeavesThisLevel = 0;
			int numLeaves = 0;
			int nodesFoundThisLevel = 0;
			int nodesThisLevel = 1;
			boolean treemade = false;
			int numLeavesFound = 0;
			int treeposicFolha = 0;

			ArrayList<HuffmanTree> htreebuilder = new ArrayList<HuffmanTree>();
			ArrayList<HuffmanTree> leaves = new ArrayList<HuffmanTree>();
			htreebuilder.add(new HuffmanTree());
			HuffmanTree htree = htreebuilder.get(0);
			HuffmanTree currentTree = htree;
			int position = 0;

			int searching = in.read(buffer);
			while (searching > 0) {
				for (int i = 0; i < (searching - 2); i++) {
					bufferbool = Converte.byteToBooleanArray(buffer[i]);
					for (int j = 0; j < 8; j++) {
						if (!treefound) {
							if (bufferbool[j]) {
								HuffmanTree lookingat = htreebuilder.get(position);
								leaves.add(lookingat);

								numLeavesThisLevel++;
								numLeaves++;
							} else {
								HuffmanTree lookingat = htreebuilder.get(position);
								lookingat.left = new HuffmanTree();
								lookingat.right = new HuffmanTree();
								htreebuilder.add(lookingat.getLeft());
								htreebuilder.add(lookingat.getRight());

								nodesFoundThisLevel++;
							}
							position++;
							if ((nodesFoundThisLevel + numLeavesThisLevel) == nodesThisLevel) {
								nodesThisLevel = nodesFoundThisLevel * 2;
								nodesFoundThisLevel = 0;
								numLeavesThisLevel = 0;
								if (nodesThisLevel == 0) {
									treefound = true;
								}
							}
						} else if (!treemade) {
							treeleaf[treeposicFolha] = bufferbool[j];
							treeposicFolha++;
							if (treeposicFolha == 8) {
								HuffmanTree leaf = leaves.get(numLeavesFound);
								leaf.caractere = Converte.booleanTobyte(treeleaf);
								leaf.nodeFolha = true;
								treeposicFolha = 0;
								numLeavesFound++;

								if (numLeavesFound == numLeaves) {
									treemade = true;
								}
							}
						} else {
							if (!bufferbool[j]) {
								currentTree = currentTree.getLeft();
							} else {
								currentTree = currentTree.getRight();
							}
							if (currentTree.isLeaf()) {
								wb.write(currentTree.getcaractere());
								currentTree = htree;
							}
						}
					}
				}
				buffer[0] = buffer[searching - 2];
				buffer[1] = buffer[searching - 1];
				searching = in.read(buffer, 2, buffer.length - 2);
				if (searching != -1) {
					searching += 2;
				}
			}
			boolean[] paddingBits = Converte.byteToBooleanArray(buffer[0]);
			boolean[] lastBits = Converte.byteToBooleanArray(buffer[1]);
			int bytesToIgnore = 0;
			for (int i = 0; i < 8; i++) {
				if (lastBits[i]) {
					bytesToIgnore++;
				}
			}
			bufferbool = paddingBits;
			for (int j = 0; j < (8 - bytesToIgnore); j++) {
				if (!bufferbool[j]) {
					currentTree = currentTree.getLeft();
				} else {
					currentTree = currentTree.getRight();
				}
				if (currentTree.isLeaf()) {
					wb.write(currentTree.getcaractere());
					currentTree = htree;
				}
			}
			wb.flush();
			in.close();
			out.close();
		} catch (IOException ex) {
		}
		return null;
	}

	public static int decodific(boolean b[]) {
		boolean[] aux = new boolean[8];
		aux[5] = b[0];
		aux[6] = b[1];
		aux[7] = b[2];

		return (int) Converte.booleanTobyte(aux);
	}
}