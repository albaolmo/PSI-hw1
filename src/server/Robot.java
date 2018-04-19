package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Alba Olmo
 *
 */


public class Robot {
	private Integer x;
	private Integer y;
	private Orientation orientation;
	private String name;
	private List<Integer> brokenBlocks;

	public Robot(String name) {
		this.name = name;
		this.brokenBlocks = new ArrayList<Integer>();
		Random random = new Random();
		this.x = random.nextInt(36 - 1 + 1) + 1;
		this.y = random.nextInt(36 - 1 + 1) + 1;
		List<Orientation> orientations = Collections.unmodifiableList(Arrays.asList(Orientation.values()));
		this.orientation = orientations.get(random.nextInt(orientations.size())); 
	}
	
	public String getName(){
		return this.name;
	}
	
	public List<Integer> getBrokenBlocks(){
		return this.brokenBlocks;
	}
	
	public Integer getX(){
		return this.x;
	}
	
	public Integer getY(){
		return this.y;
	}

	public void addBrokenBlock(Integer block) {
		this.brokenBlocks.add(block);
	}

	public void removeBrokenBlock(Integer block) {
		this.brokenBlocks.remove(block);
	}

	public void setCoordinates(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	// returns an array with the coordinates {x,y}
	public List<Integer> turnLeft() {
		List<Integer> coordinates = new ArrayList<Integer>();
		coordinates.add(this.x);
		coordinates.add(this.y);
		if (this.orientation.equals(Orientation.NORTH)) {
			this.orientation = Orientation.EAST;
		} else if (this.orientation.equals(Orientation.EAST)) {
			this.orientation = Orientation.SOUTH;
		} else if (this.orientation.equals(Orientation.SOUTH)) {
			this.orientation = Orientation.WEST;
		} else if (this.orientation.equals(Orientation.WEST)) {
			this.orientation = Orientation.NORTH;
		}
		return coordinates;
	}

	// returns an array with the coordinates {x,y} or and empty array if the
	// robot got out of the city
	public List<Integer> step() {
		List<Integer> coordinates = new ArrayList<Integer>();
		if (!((this.x + 1 > 36) || (this.x - 1 < -36) || (this.y + 1 > 36) || (this.y - 1 < -36))) {

			if (this.orientation.equals(Orientation.NORTH)) {
				this.y += 1;
			} else if (this.orientation.equals(Orientation.EAST)) {
				this.x += 1;
			} else if (this.orientation.equals(Orientation.SOUTH)) {
				this.y -= 1;
			} else if (this.orientation.equals(Orientation.WEST)) {
				this.x -= 1;
			}
			
			coordinates.add(this.x);
			coordinates.add(this.y);
		}
		return coordinates;
	}

	
	

}
