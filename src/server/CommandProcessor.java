package server;

import java.util.List;
import java.util.Random;

/**
 * @author Alba Olmo
 *
 */

public class CommandProcessor {

	private Robot robot;
	private int stepCounter;

	CommandProcessor(Robot robot) {
		this.robot = robot;
		this.stepCounter = 0;
	}

	public String processStep() {
		String res = "";
		this.stepCounter = this.stepCounter + 1;

		if (!robot.getBrokenBlocks().isEmpty()) {// The robot's block was broken
													// but you tried to move it
													// anyway
			res = "572 ROBOT FELLS TO PIECES";

		} else { // The robot is not broken

			if (this.stepCounter >= 10) { // too much walked, the robot breaks
											// down
				this.stepCounter = 0; // start the counter again
				// generate a random broken block
				Random rn = new Random();
				int block = rn.nextInt(9 - 1 + 1) + 1;
				robot.addBrokenBlock(block);
				res = "580 FAILURE OF BLOCK " + block;

			} else {
				List<Integer> coordinates = this.robot.step();
				if (coordinates.isEmpty()) {
					res = "530 CRASHED";
				} else {
					Integer x = coordinates.get(0);
					Integer y = coordinates.get(1);
					res = "240 OK (" + x + "," + y + ")";
				}

			}
		}

		return res;
	}

	public String processLeft() {
		List<Integer> coordinates = this.robot.turnLeft();
		Integer x = coordinates.get(0);
		Integer y = coordinates.get(1);
		return "240 OK (" + x + "," + y + ")";
	}

	public String processRepair(int block) {
		String res = "";
		List<Integer> brokenBlocks = robot.getBrokenBlocks();
		Integer blockToRepair = new Integer(block);

		if (brokenBlocks.contains(blockToRepair)) {
			robot.removeBrokenBlock(blockToRepair);
			res = "240 OK (" + robot.getX() + "," + robot.getY() + ")";

		} else { // Attempt to repair a block that is OK
			res = "571 THIS BLOCK IS OK";
		}
		return res;
	}

	public String processPickUp() {
		String res = "";
		if (robot.getX() == 0 && robot.getY() == 0) {
			res = "260 SUCCESS Hello World";
		} else {
			res = "550 CANNOT PICK UP THE SIGN";
		}
		return res;
	}

}
