package src;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		Commands commands = new Commands();
		
			File file = new File("src/commands.txt");
			commands.fileInput(file);
	}

}
