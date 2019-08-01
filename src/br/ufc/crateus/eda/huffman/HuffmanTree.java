package br.ufc.crateus.eda.huffman;

import java.util.ArrayList;

public class HuffmanTree implements Comparable<HuffmanTree>
{
	public int frequencia = -1;
	public byte caractere;
	public boolean nodeFolha = false;
	public HuffmanTree left = null;
	public HuffmanTree right = null;
	

	public HuffmanTree(byte item, HuffmanTree b1, HuffmanTree b2, int f) {
		frequencia = f;
		caractere = item;
		left = b1;
		right = b2;
		nodeFolha = true;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, int f) {
		frequencia = f;
		left = b1;
		right = b2;
	}
	
	public HuffmanTree() {
	}
	
	public boolean isLeaf() {
		return nodeFolha;
	}
	
	public byte getcaractere() {
		return caractere;
	}
	
	
	public HuffmanTree getLeft() {
		return left;
	}
	
	public HuffmanTree getRight() {
		return right;
	}
	
	public int compareTo(HuffmanTree bt) {
			return frequencia - bt.frequencia;
	}
	
	public boolean[] toBooleanArray(){
		boolean[] boolArray = new boolean[1024];
		byte[] caracteres = new byte[1024];
		ArrayList<HuffmanTree> queue = new ArrayList<HuffmanTree>();
		
		queue.add(this);
		boolArray[0] = false;
		int arraypos = 0;
		int position = 0;
		int caracterepos = 0;
		while (position < queue.size()) {
			boolArray[arraypos] = queue.get(position).isLeaf();
			if (queue.get(position).isLeaf()) {
				caracteres[caracterepos] = queue.get(position).getcaractere();
				caracterepos++;
			}
			else {
				queue.add(queue.get(position).getLeft());
				queue.add(queue.get(position).getRight());
			}
			position++;
			arraypos++;
		}
		
		boolean[] ret = new boolean[arraypos + 8 * caracterepos];
		for (int i = 0; i < arraypos; i++) {
			ret[i] = boolArray[i];
		}
		for (int i = 0; i < caracterepos; i++) {
			byte caractereByte = caracteres[i];
			boolean[] bits = Converte.byteToBooleanArray(caractereByte);
			for (int j = 0; j < 8; j++) {
				ret[arraypos + (8 * i) + j] = bits[j];
			}
		}
		
		return ret;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	public boolean[][] toArrayList() {
		boolean[][] ret = new boolean[256][];
		ArrayList<HuffmanTree> queue = new ArrayList<HuffmanTree>();
		ArrayList<boolean[]> binary = new ArrayList<boolean[]>();
		
		
		queue.add(this);
		binary.add(new boolean[0]);
		
		int position = 0;
		while (position < queue.size()) {
			boolean isLeaf = queue.get(position).isLeaf();
			if (!isLeaf) {
				boolean[] binaryString = binary.get(position);
				boolean[] binaryLeft = new boolean[binaryString.length + 1];
				boolean[] binaryRight = new boolean[binaryString.length + 1];
				for (int i = 0; i < binaryString.length; i++) {
					binaryLeft[i] = binaryString[i];
					binaryRight[i] = binaryString[i];
				}
				binaryLeft[binaryLeft.length - 1] = false;
				binaryRight[binaryRight.length - 1] = true;
				
				queue.add(queue.get(position).getLeft());
				binary.add(binaryLeft);
				queue.add(queue.get(position).getRight());
				binary.add(binaryRight);
			}
			else {
				int toAdd = (int) queue.get(position).getcaractere() + 128;
				ret[toAdd] = binary.get(position);
			}
			position++;
		}
		
		return ret;
	}
}