import java.io.IOException;

public class PentagoServerMain {
	
	private static final int DEFAULT_PORT = 50000;
	
	public static void main(String[] args) {
		try {
			new PentagoServerController(DEFAULT_PORT);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
