package fr.mowitnow.service;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import fr.mowitnow.enums.Operation;
import fr.mowitnow.enums.Orientation;
import fr.mowitnow.exception.InvalidFormatException;
import fr.mowitnow.exception.InvalidNumberOfLinesException;
import fr.mowitnow.model.Mower;

public class MowerService {

	// Pattern for first line
	static final String LIMIT_POINT_LINE_PATTERN = "^\\d+\\s\\d+$";
	
	// Pattern for mower first line
	static final String MOWER_INITIAL_POSITION_LINE_PATTERN = "^\\d+\\s\\d+\\s[NESW]$";
	
	// Pattern for mower second line
	static final String MOWER_MOVEMENT_LINE_PATTERN = "^[GDA]+$";
	
	static final String WHITESPACE_SEPARATOR = " ";

	public static List<Mower> process(Path path) throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		// Throws exception if the file has invalid format
		MowerService.validateEntryFile(path);
		
		List<Mower> mowers = new ArrayList<Mower>();
		BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
		
		// First line contains the limit point 
		String line = reader.readLine();
		Point limit = new Point(Integer.valueOf(line.split(WHITESPACE_SEPARATOR)[0]), 
				Integer.valueOf(line.split(WHITESPACE_SEPARATOR)[1]));
		
		// Go to the new line
		line = reader.readLine();
		
		// Here begins mowers lines
		AtomicInteger index = new AtomicInteger(0);
		Mower mower = null;
		while (line != null) {
			// Line for mower position
			if (index.getAndAdd(1) % 2 == 0) {
				mower = new Mower(Integer.valueOf(line.split(WHITESPACE_SEPARATOR)[0]), 
						Integer.valueOf(line.split(WHITESPACE_SEPARATOR)[1]), 
								Orientation.valueOf(line.split(WHITESPACE_SEPARATOR)[2]), limit);
			}
			// Line for mower movement
			else {
				for (int i = 0; i < line.length(); i++) {
					mower.operate(Operation.valueOf(String.valueOf(line.charAt(i))), limit);
				}
				mowers.add(mower);
			}
			line = reader.readLine();
		}
		reader.close();
		return mowers;
	}
	
	private static void validateEntryFile(Path path) throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		if (!Files.exists(path)) {
        	throw new FileNotFoundException("file " + path.toString() + " is inexistant");
        }
		
		// Number of lines should be odd not even
		long entryFileLinesNumber = Files.lines(path).count();
		if (entryFileLinesNumber % 2 == 0) {
			throw new InvalidNumberOfLinesException
			("incorrect number of lines in entry file (" + entryFileLinesNumber + ") - number of lines should be odd not even");
		}
		
		// Entry file should at least have 3 lines	 
		if (entryFileLinesNumber < 3) {
			throw new InvalidNumberOfLinesException
			("incorrect number of lines in entry file (" + entryFileLinesNumber + ") - entry file should have 3 lines at least");
		}
		
		// First line should respect the LIMIT_POINT_LINE_PATTERN pattern
		boolean isValidLimitPointLine = Files.lines(path).limit(1).allMatch(s -> s.matches(LIMIT_POINT_LINE_PATTERN));
		if (!isValidLimitPointLine) {
			throw new InvalidFormatException("first line doesn't match the correct syntax");
		}
			
		// All mowers initial position lines should respect the pattern MOWER_INITIAL_POSITION_LINE_PATTERN
		AtomicInteger index = new AtomicInteger(0);
		boolean areValidMowerPositionsLines = Files.lines(path)
				.skip(1)
				.filter(s -> index.getAndIncrement() % 2 == 0)
				.allMatch(s -> s.matches(MOWER_INITIAL_POSITION_LINE_PATTERN));
		if (!areValidMowerPositionsLines) {
			throw new InvalidFormatException("one of the mowers position line doesn't match the correct syntax");
		}
		
		// All mowers movements lines should respect the pattern MOWER_INITIAL_POSITION_LINE_PATTERN
		index.set(0);
		boolean areValidMowerMovementLines = Files.lines(path)
				.skip(1)
				.filter(s -> index.getAndIncrement() % 2 == 1)
				.allMatch(s -> s.matches(MOWER_MOVEMENT_LINE_PATTERN));
		if (!areValidMowerMovementLines) {
			throw new InvalidFormatException("one of the mowers movements line doesn't match the correct syntax");
		}
	}
}
