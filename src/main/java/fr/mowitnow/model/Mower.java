package fr.mowitnow.model;

import java.awt.Point;

import fr.mowitnow.enums.Operation;
import fr.mowitnow.enums.Orientation;

public class Mower {

	static final int MOVEMENT_LEGTH = 1;
	
	private Point position;
	private Orientation orientation;

	public Mower(int x, int y, Orientation orientation, Point limit) {
		if (x > limit.x || y > limit.y) {
			throw new IllegalArgumentException("the mower position is out of the limit range");	
		}
		Point position = new Point(x, y);
		this.position = position;
		this.orientation = orientation;
	}

	public void rotate(Operation op) {
		this.orientation = Orientation.update(op, this.orientation);
	}

	public void move() {
		switch (this.orientation) {
		case N:
			this.position.setLocation(this.position.x, this.position.y + MOVEMENT_LEGTH);
			break;
		case E:
			this.position.setLocation(this.position.x + MOVEMENT_LEGTH, this.position.y);
			break;
		case S:
			this.position.setLocation(this.position.x, this.position.y - MOVEMENT_LEGTH);
			break;
		case W:
			this.position.setLocation(this.position.x - MOVEMENT_LEGTH, this.position.y);
			break;
		}
	}

	public boolean canMove(Point limit) {
		switch (this.orientation) {
		case N:
			return limit.getY() > this.getPosition().y;
		case E:
			return limit.getX() > this.getPosition().x;
		case S:
			return this.getPosition().y > 0;
		case W:
			return this.getPosition().x > 0;
		default:
			throw new AssertionError("unknown orientation " + orientation);
		}
	}
	
	public void operate(Operation op, Point limit) {
		switch (op) {
		case G:
		case D:
			rotate(op);
			break;
		case A: {
			if (this.canMove(limit)) {
				this.move();
			}
			break;
		}
		}
	}

	@Override
	public boolean equals(Object mower) {
		return this.position.x == ((Mower) mower).position.x 
				&& this.position.y == ((Mower) mower).position.y 
				&& this.orientation == ((Mower) mower).orientation; 
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return this.position.x + " " + this.position.y + " " + this.orientation;
	}
}
