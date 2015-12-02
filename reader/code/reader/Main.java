package reader;

public class Main {
	
	public static void main(String [] args) {
		
		String filename = args[0];
		String regFilename = args[1];
		String splitFilename = args[2];
		double bootstrapMultiplier = Double.parseDouble(args[3]);
		
		Reader reader = new Reader(filename);
		reader.createData(regFilename, splitFilename, bootstrapMultiplier);
	}

}
