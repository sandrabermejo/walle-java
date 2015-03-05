package tp.pr5;

public enum Rotation {
		LEFT, RIGHT, UNKNOWN;
		
		public Rotation oppositeRotation(){
			if (this == Rotation.LEFT)
				return Rotation.RIGHT;
			else if (this == Rotation.RIGHT)
				return Rotation.LEFT;
			else
				return Rotation.UNKNOWN;
		}
}
