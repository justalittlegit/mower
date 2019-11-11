package fr.mowitnow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import fr.mowitnow.enums.Orientation;
import fr.mowitnow.exception.InvalidFormatException;
import fr.mowitnow.exception.InvalidNumberOfLinesException;
import fr.mowitnow.model.Mower;
import fr.mowitnow.service.MowerService;

public class MowerTest {
	
	@Test
	public void process_Success_Case() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/success-case.txt");
		List<Mower> mowers = MowerService.process(path);        
        assertEquals(2, mowers.size());
        assertEquals(new Mower(1, 3, Orientation.N, new Point(5, 5)), mowers.get(0));
        assertEquals(new Mower(5, 1, Orientation.E, new Point(5, 5)), mowers.get(1));
	}
	
	@Test(expected = InvalidNumberOfLinesException.class)
	public void validate_IfIncorrectNumberOfLines_ShouldThrowException() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/incorrect-number-lines-case.txt");
		MowerService.process(path);
	}
	
	@Test(expected = InvalidNumberOfLinesException.class)
	public void validate_IfNoAtLeaseOneMower_ShouldThrowException() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/no-mower-case.txt");
		MowerService.process(path);
	}
	
	@Test(expected = InvalidFormatException.class)
	public void validate_IfIncorrectLimitPoint_ShouldThrowException() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/incorrect-limit-point-case.txt");
		MowerService.process(path);
	}
	
	@Test(expected = InvalidFormatException.class)
	public void validate_IfIncorrectOneOfMowersPosition_ShouldThrowException() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/incorrect-mower-position-case.txt");
		MowerService.process(path);
	}
	
	@Test(expected = InvalidFormatException.class)
	public void validate_IfIncorrectOneOfMowersMovements_ShouldThrowException() throws IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/incorrect-mower-movement-case.txt");
		MowerService.process(path);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void process_IfMowerPositionIsOutOfRange_ShouldThrowException() throws IllegalArgumentException, IOException, InvalidNumberOfLinesException, InvalidFormatException {
		Path path = Paths.get("test-files/mower-out-of-range-case.txt");
		MowerService.process(path);
	}
}
