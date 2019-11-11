package fr.mowitnow.enums;

public enum Orientation {
	N, E, S, W;

	public static Orientation update(Operation op, Orientation orientation) {
		switch (op) {
		case D: {
			switch (orientation) {
			case N:
				return E;
			case E:
				return S;
			case S:
				return W;
			case W:
				return N;
			default:
				throw new AssertionError("unknown orientation " + orientation);
			}
		}
		case G: {
			switch (orientation) {
			case N:
				return W;
			case W:
				return S;
			case S:
				return E;
			case E:
				return N;
			default:
				throw new AssertionError("unknown orientation " + orientation);
			}
		}
		default:
			throw new AssertionError("unknown orientation " + orientation);
		}
	}
}
