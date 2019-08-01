package br.ufc.crateus.eda.huffman;

public class Converte {

	public static byte booleanTobyte(boolean[] array) {
		byte character = 0;
		if (array.length == 8) {
			if (array[0]) {
				character = (byte) (character | 0x80);
			}
			if (array[1]) {
				character = (byte) (character | 0x40);
			}
			if (array[2]) {
				character = (byte) (character | 0x20);
			}
			if (array[3]) {
				character = (byte) (character | 0x10);
			}
			if (array[4]) {
				character = (byte) (character | 0x08);
			}
			if (array[5]) {
				character = (byte) (character | 0x04);
			}
			if (array[6]) {
				character = (byte) (character | 0x02);
			}
			if (array[7]) {
				character = (byte) (character | 0x01);
			}
		}
		else {
	
		}
		return character;
	}

	public static boolean[] byteToBooleanArray(byte characterbyte) {
		boolean[] array = new boolean[8];
		array[0] = ((characterbyte & 0x80) != 0);
		array[1] = ((characterbyte & 0x40) != 0);
		array[2] = ((characterbyte & 0x20) != 0);
		array[3] = ((characterbyte & 0x10) != 0);
		array[4] = ((characterbyte & 0x08) != 0);
		array[5] = ((characterbyte & 0x04) != 0);
		array[6] = ((characterbyte & 0x02) != 0);
		array[7] = ((characterbyte & 0x01) != 0);
		return array;
	}
}