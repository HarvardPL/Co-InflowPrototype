class BooleanOperations {
	public static boolean leakyMethod(boolean high) {
		boolean ret;
		ret = (high || true) || (high || false);
		return ret;
	}
}
