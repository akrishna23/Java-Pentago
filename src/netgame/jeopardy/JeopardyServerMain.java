package netgame.jeopardy;

public class JeopardyServerMain {
	
	public static void main(String[] args) {
		String[] categories = new String[] {"Music", "Capitals",  "Art", "Science"};
		String[]	[] questions = new String[][] { {"Her songs include Teardrops on my Guitar and You Belong With Me", "His songs include Stronger, Gold Digger, and Mercy",
			"The instrument of Yo-Yo Ma", "Frequency of Concert A (number only)" },
				 {"Capital of California", "Capital of Colorado", "Capital of New York", "Capital of Nevada"}, {"This sculpting medium is also the name of a small glass ball",
					 "Last name of the impressionist who painted Waterlilies", "This artist painted the ceiling of the Sistine Chapel", "Full name of the painter of The Scream"}, 
					 {"The name of the most common negatively charged particle", "Name of the North Star", "Amino acid that starts with the letter v", "The SI unit of capacitance"}};
		String[][] answers = new String[][] { {"Taylor Swift", "Kanye West", "cello", "440"},
				 {"Sacramento", "Denver", "Albany", "Carson City"}, {"marble", "Monet", "Michelangelo", "Edvard Munch"}, 
				 {"electron", "Polaris", "valine", "farad"}};

		JeopardyServerController serverController;
		try {
			serverController = new JeopardyServerController(
					37831, categories, questions, answers, null);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}

}
