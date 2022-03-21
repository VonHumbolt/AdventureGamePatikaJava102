package AdventureGame;

import java.util.Random;

public class Snake extends Obstacle{

	Random random = new Random();
	int randomDamage;
	
	
	public Snake() {
		super("Yýlan", 0, 12, 0, 5);
		
		this.randomDamage = random.nextInt(3) + 3;
		this.setDamage(this.randomDamage);
		
	}
	

}
