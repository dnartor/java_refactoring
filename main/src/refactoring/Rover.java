package refactoring;

import java.util.HashMap;
import java.util.Map;

public class Rover {

	private Heading heading;
	private Position position;

	public Rover(String facing, int x, int y) {
		heading= Heading.of(facing);
		position = new Position(x,y);
	}

	public Rover(Heading heading, int x, int y) {
		this.heading = heading;
		this.position = new Position(x,y);
	}

	public Rover(Heading heading, Position position) {
		this.heading = heading;
		this.position = position;
	}

	public Heading heading() {
		return this.heading;
	}

	public Position position() {
		return position;
	}

	public void go(Order... orders){
		for (Order order : orders) actions.get(order).execute();
	}

	public void go (String orders){
		for(int i=0;i<orders.length();i++) {
			if(Order.of(orders.charAt(i)) != null) {
				actions.get(Order.of(orders.charAt(i))).execute();
			}
		}
	}

	public interface Action{
		void execute();
	}
	public static class Position {
		private final int x;
		private final int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Position forward(Heading heading) {
			if(heading.toString().equals("North")) return new Position(x,y+1);
			if(heading.toString().equals("South")) return new Position(x,y-1);
			if(heading.toString().equals("West")) return new Position(x-1,y);
			if(heading.toString().equals("East")) return new Position(x+1,y);
			return null;
		}

		@Override
		public boolean equals(Object object) {
			return isSameClass(object) && equals((Position) object);
		}

		private boolean equals(Position position) {
			return position == this || (x == position.x && y == position.y);
		}

		private boolean isSameClass(Object object) {
			return object != null && object.getClass() == Position.class;
		}

	}

	private final Map<Order, Action> actions = new HashMap<Order, Action>();
	{
		actions.put(Order.Left,() -> heading = heading.turnLeft());
		actions.put(Order.Right,() -> heading = heading.turnRight());
		actions.put(Order.Forward,() -> position = position.forward(this.heading));
		actions.put(Order.Backward,() -> position = position.forward(this.heading.turnLeft().turnLeft()));
	}

	public enum Heading {
		North, East, South, West;

		public static Heading of(String label) {
			return of(label.charAt(0));
		}

		public static Heading of(char label) {
			if (label == 'N') return North;
			if (label == 'S') return South;
			if (label == 'W') return West;
			if (label == 'E') return East;
			return null;
		}

		public Heading turnRight() {
			return values()[add(+1)];
		}

		public Heading turnLeft() {
			return values()[add(-1)];
		}

		private int add(int offset) {
			return (this.ordinal() + offset + values().length) % values().length;
		}

	}
	public enum Order {
		Left, Right, Forward, Backward;

		public static Order of(String label) {
			return of(label.charAt(0));
		}

		public static Order of(char label) {
			if (label == 'L') return Left;
			if (label == 'R') return Right;
			if (label == 'F') return Forward;
			if (label == 'B') return Backward;
			return null;
		}
	}


}

