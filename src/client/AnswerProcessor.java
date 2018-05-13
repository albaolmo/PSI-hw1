package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import server.Orientation;

/**
 * @author Alba Olmo
 *
 */

public class AnswerProcessor {

	private String robotName;
	private List<Integer> xList; // list with all the x coordinates where the
									// robot has been
	private List<Integer> yList; // list with all the y coordinates where the
									// robot has been
	private Orientation orientation;

	public AnswerProcessor() {
		xList = new ArrayList<Integer>();
		yList = new ArrayList<Integer>();
	}

	public String processGreeting(String name) {
		this.robotName = name;
		return robotName + " LEFT"; // to know the first coordinates of the
									// robot
	}

	public String processOk(Integer x, Integer y) { // these are the coordinae
													// sent by the server
		String res = "";
		// 1st case: the connection has just started and the client has just
		// sent the command STEP
		// (client has to send again the comand STEP to determine the direction)
		if (xList.isEmpty() && yList.isEmpty()) {
			res = robotName + " STEP";

		} else { // 2nd case: we already
			
			if(xList.size()==1){//We received just one the first ok (the coordinates of this ok are kept in the list) and we have just recived the second one 
				determineFirstOrientation(x,y);
			}else{
				determineOrientation(x, y);
			}
			
			if(x==0 && y==0){
				res = robotName + " PICK UP";
			}else if (rotate(x, y).equals(true)) { //trying to orientate the robot always to the 0,0
				res = robotName + " LEFT";
				if(this.orientation.equals(Orientation.WEST)){
					this.orientation = Orientation.NORTH;
				}else if(this.orientation.equals(Orientation.SOUTH)){
					this.orientation = Orientation.WEST;
				}else if(this.orientation.equals(Orientation.EAST)){
					this.orientation = Orientation.SOUTH;
				}else if(this.orientation.equals(Orientation.NORTH)){
					this.orientation = Orientation.EAST;
				}
			} else {
				res = robotName + " STEP";
			}
		}

		xList.add(x);
		yList.add(y);
		return res;

	}

	public String processUnknown() {
		//Send any of the commands
		List<String> commands = new ArrayList<String>();
		commands.add(robotName + " LEFT");
		commands.add(robotName + " STEP");
		commands.add(robotName+" REPAIR " + 3);
		commands.add(robotName + " PICK UP");
		Random random = new Random();
		return commands.get(random.nextInt(commands.size())); 
		
	}

	public String processFailure(Integer block) { // this is the number of broken block sent by the server
		return robotName+" REPAIR " + block;
	}

	private Boolean rotate(Integer x, Integer y) { // 0 -> move, 1 -> rotate
		Boolean res = false;
		if (x > 0 && y > 0) {
			if (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.NORTH)) {
				res = true;
			}
		} else if (x < 0 && y > 0) {
			if (orientation.equals(Orientation.WEST) || orientation.equals(Orientation.NORTH)) {
				res = true;
			}
		} else if (x < 0 && y < 0) {
			if (orientation.equals(Orientation.WEST) || orientation.equals(Orientation.SOUTH)) {
				res = true;
			}
		} else if (x > 0 && y < 0) {
			if (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.SOUTH)) {
				res = true;
			}
		} else if (x > 0 && y == 0) {
			if (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.NORTH)
					|| orientation.equals(Orientation.SOUTH)) {
				res = true;
			}
		} else if (x < 0 && y == 0) {
			if (orientation.equals(Orientation.WEST) || orientation.equals(Orientation.NORTH)
					|| orientation.equals(Orientation.SOUTH)) {
				res = true;
			}
		} else if (x == 0 && y > 0) {
			if (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.NORTH)
					|| orientation.equals(Orientation.WEST)) {
				res = true;
			}
		} else if (x == 0 && y < 0) {
			if (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.WEST)
					|| orientation.equals(Orientation.SOUTH)) {
				res = true;
			}
		}

		return res;
	}
	
	public void determineFirstOrientation(Integer x, Integer y){
		int lastX = this.xList.get(xList.size()-1);
		int lastY = this.yList.get(yList.size()-1);
			if(x > lastX){
				this.orientation = Orientation.EAST;
			}else if(x < lastX){
				this.orientation = Orientation.WEST;
			}else if(y > lastY){
				this.orientation = Orientation.NORTH;
			}else if(y < lastY){
				this.orientation = Orientation.SOUTH;
			}
		
	}

	public void determineOrientation(Integer newX, Integer newY) {
		Integer lastX = this.xList.get(this.xList.size()-1);
		Integer lastY = this.yList.get(this.yList.size()-1);
		if (!lastX.equals(newX) && !lastY.equals(newY))
			if (lastX < newX) {
				this.orientation = Orientation.EAST;
			}
		if (lastX > newX) {
			this.orientation = Orientation.WEST;
		}
		if (lastY < newY) {
			this.orientation = Orientation.NORTH;
		}
		if (lastY > newY) {
			this.orientation = Orientation.SOUTH;
		}

	}

}
