
public class simpleArraySize {
	
	public static void main(String[] args) {
		int value = 5;
		simpleArraySize.arraySizeLeak(value);
	}

	/**
	 * Returns the number that was given, by passing
	 * it trough an array size.
	 * @param h secret value
	 * @return value given
	 */
	public static int arraySizeLeak(int h) {
		int[] array = new int[h];
		return array.length;
	}
}
