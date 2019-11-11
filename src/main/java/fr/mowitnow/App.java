package fr.mowitnow;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import fr.mowitnow.exception.InvalidFormatException;
import fr.mowitnow.exception.InvalidNumberOfLinesException;
import fr.mowitnow.model.Mower;
import fr.mowitnow.service.MowerService;

public class App {

	public static void main(String[] args) throws IOException {
		
        Path path = Paths.get("test-files/success-case.txt");
        List<Mower> mowers = null;
        
        try {
        	mowers = MowerService.process(path);
        }
        catch (IOException | InvalidNumberOfLinesException | InvalidFormatException ex) {
        	System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
        	System.exit(0);
        }
        
        mowers.stream().forEach(m -> System.out.println(m));
    }
}
