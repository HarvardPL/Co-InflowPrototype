class BooleanOperations {
	public static boolean leakyMethod(boolean high) {
		boolean ret;
		ret = (high && true);
		return ret;
	}
	
	public static void main(String[] args){
	    BooleanOperations.leakyMethod(false);
	}
}
