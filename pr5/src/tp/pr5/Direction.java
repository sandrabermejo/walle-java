package tp.pr5;

public enum Direction {
		EAST, NORTH, SOUTH, UNKNOWN, WEST;
		
		/**
		 * 
		 * @return La direccion opuesta a la dada
		 */
		public Direction opposite() {
			Direction opposite;
			switch (this) {
			case EAST:
				opposite = WEST;
				break;
			case WEST:
				opposite = EAST;
				break;
			case NORTH:
				opposite = SOUTH;
				break;
			case SOUTH:
				opposite = NORTH;
				break;
			default:
				opposite = UNKNOWN;
			}
			return opposite;
		}
		
		/**
		 * 
		 * @param rotation
		 * @return La nueva direccion
		 */
		public Direction changeDirection(Rotation rotation) {
			Direction direction;
			switch (this){ // Por defecto giramos a la izquierda
			case EAST:
				direction = NORTH;
				break;
			case WEST:
				direction = SOUTH;
				break;
			case NORTH:
				direction = WEST;
				break;
			case SOUTH:
				direction = EAST;
				break;
			default:
				direction = UNKNOWN;
			}
			if (rotation == Rotation.RIGHT) // Si el giro es a la derecha
				direction = direction.opposite();
			return direction;
		}
}


