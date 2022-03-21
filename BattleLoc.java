package AdventureGame;

import java.util.Random;

public abstract class BattleLoc extends Location {
	protected Obstacle obstacle;
	protected String award;
	Random random = new Random();
	
	BattleLoc(Player player, String name, Obstacle obstacle, String award) {
		super(player);
		this.obstacle = obstacle;
		this.name = name;
		this.award = award;
	}

	public boolean getLocation() {
		int obsCount = obstacle.count();
		if (this.player.getInv().isFood() && this.getName() == "Ma�ara") {
			return true;
		}
		if (this.player.getInv().isFirewood() && this.getName() == "Orman") {
			return true;
		}
		if (this.player.getInv().isWater() && this.getName() == "Nehir") {
			return true;
		}
		System.out.println("�uan buradas�n�z : " + this.getName());
		System.out.println("Dikkatli ol! Burada " + obsCount + " tane " + obstacle.getName() + " ya��yor !");
		System.out.print("<S>ava� veya <K>a� :");
		String selCase = scan.nextLine();
		selCase = selCase.toUpperCase();
		if (selCase.equals("S")) {
			if (combat(obsCount)) {
				System.out.println(this.getName() + " b�lgesindeki t�m d��manlar� temizlediniz !");
				if (this.award.equals("Food") && player.getInv().isFood() == false) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setFood(true);
				} else if (this.award.equals("Water") && player.getInv().isWater() == false) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setWater(true);
				} else if (this.award.equals("Firewood") && player.getInv().isFirewood() == false) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setFirewood(true);
				}
				else {
					// Maden b�l�m�nde rastgele �d�l !
					this.randomAward();
				}
				return true;
			}
			
			if(player.getHealthy() <= 0) {
				System.out.println("�ld�n�z !");
				return false;
			}
		
		}
		return true;
	}

	public boolean combat(int obsCount) {
		for (int i = 0; i < obsCount; i++) {
			int defObsHealth = obstacle.getHealth();
			playerStats();
			enemyStats();
			boolean isFirstAttackerPlayer = this.firstAttacker();
			
			while (player.getHealthy() > 0 && obstacle.getHealth() > 0) {
				System.out.print("<V>ur veya <K>a� :");
				String selCase = scan.nextLine();
				selCase = selCase.toUpperCase();
				if (selCase.equals("V")) {
					
					// �lk kim sald�racak kontrol et !!!
					if (isFirstAttackerPlayer) {
						
						System.out.println("Siz vurdunuz !");
						obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
						afterHit();
						if (obstacle.getHealth() > 0) {
							System.out.println();
							System.out.println("Canavar size vurdu !");
							player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
							afterHit();
						}
					} else {
						System.out.println("Canavar size vurdu !");
						player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
						this.afterHit();
						if (player.getHealthy() > 0) {
							System.out.println();
							System.out.println("Siz vurdunuz !");
							obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
							afterHit();
						}
					}
				} else {
					return false;
				}
			}

			if (obstacle.getHealth() < player.getHealthy()) {
				System.out.println("D��man� yendiniz !");
				player.setMoney(player.getMoney() + obstacle.getAward());
				System.out.println("G�ncel Paran�z : " + player.getMoney());
				obstacle.setHealth(defObsHealth);
			} else {
				return false;
			}
			System.out.println("-------------------");
		}
		return true;
	}
	
	public boolean firstAttacker() {
		Random rand = new Random();
		int possibility = rand.nextInt(2);
		
		if (possibility == 0) {
			// �lk player sald�r�r!
			return true;
		}
		else {
			// ilk canavar sald�r�r!
			return false;
		}
	}

	// Maden lokasyonunda rastgele kazan�lacak �d�l�n belirlendi�i metot
	public void randomAward() {

		int possibility = this.random.nextInt(100);
		
		if (possibility <= 15) { // Silah gelme ihtimali ! 
			
			int possibilityForWeapon = this.random.nextInt(100);
			
			if (possibility <= 20) { // T�fek gelme ihtimali !
				
				System.out.println("T�fek kazand�n�z !");
				int newDamage = this.player.getDamage() + 7;
				this.player.setDamage(newDamage);
				this.player.getInv().setwName("T�fek");
			
			} else if (possibilityForWeapon > 20 && possibilityForWeapon <= 50) { // K�l�� kazanma ihtimali !
			
				System.out.println("K�l�� kazand�n�z !");
				int newDamage = this.player.getDamage() + 3;
				this.player.setDamage(newDamage);
				this.player.getInv().setwName("K�l��");
				
			
			} else { // Tabanca kazanma ihtimali !
				
				System.out.println("Tabanca kazand�n�z !");
				int newDamage = this.player.getDamage() + 2;
				this.player.setDamage(newDamage);
				this.player.getInv().setwName("Tabanca");
			}
			
			
		} else if (possibility > 15 && possibility <= 30) {
			// Z�rh kazanama ihtimali
			
			int possibilityForArmor = this.random.nextInt(100);
			
			
			if ( possibilityForArmor <= 20) { // A��r z�rh kazanma ihtimali
				
				System.out.println("A��r Z�rh kazand�n�z !");
				int newArmor = this.player.getInv().getArmor() + 5;
				this.player.getInv().setArmor(newArmor);
				this.player.getInv().setaName("A��r Z�rh");
				
			} else if (possibilityForArmor > 20 && possibility <= 50) { // Orta z�rh kazanma ihtimali
				
				System.out.println("Orta Z�rh kazand�n�z !");
				int newArmor = this.player.getInv().getArmor() + 3;
				this.player.getInv().setArmor(newArmor);
				this.player.getInv().setaName("Orta Z�rh");
				
			} else { // Hafif z�rh kazanma ihtimali
				
				System.out.println("Hafif Z�rh kazand�n�z !");
				int newArmor = this.player.getInv().getArmor() + 1;
				this.player.getInv().setArmor(newArmor);
				this.player.getInv().setaName("Hafif Z�rh");
				
			}
			
			
		} else if (possibility > 30 && possibility <= 55) {
			// Para kazanma ihtimali
			int possibilityForMoney = this.random.nextInt(100);
			
			if (possibilityForMoney <= 20) { // 10 para kazanma ihtimali
				
				System.out.println("10 para kazand�n�z !");
				int newMoney = this.player.getMoney() + 10;
				this.player.setMoney(newMoney);
				
			} else if (possibilityForMoney > 20 && possibilityForMoney <= 50) { // 5 para kazanma ihtimali
				
				System.out.println("5 para kazand�n�z !");
				int newMoney = this.player.getMoney() + 5;
				this.player.setMoney(newMoney);
				
			} else { // 1 para kazanma ihtimali
				
				System.out.println("1 para kazand�n�z !");
				int newMoney = this.player.getMoney() + 1;
				this.player.setMoney(newMoney);
				
			}
		} else {
			// Hi�bir �ey kazanmama ihtimali!
			System.out.println("Hi�bir �d�l kazanamad�n�z ! ");
			
		}
	}
	
	public void playerStats() {
		System.out.println("Oyuncu De�erleri\n--------------");
		System.out.println("Can:" + player.getHealthy());
		System.out.println("Hasar:" + player.getTotalDamage());
		System.out.println("Para:" + player.getMoney());
		if (player.getInv().getDamage() > 0) {
			System.out.println("Silah:" + player.getInv().getwName());
		}
		if (player.getInv().getArmor() > 0) {
			System.out.println("Z�rh:" + player.getInv().getaName());
		}
	}

	public void enemyStats() {
		System.out.println("\n" + obstacle.getName() + " De�erleri\n--------------");
		System.out.println("Can:" + obstacle.getHealth());
		System.out.println("Hasar:" + obstacle.getDamage());
		System.out.println("�d�l:" + obstacle.getAward());
	}

	public void afterHit() {
		System.out.println("Oyuncu Can�:" + player.getHealthy());
		System.out.println(obstacle.getName() + " Can�:" + obstacle.getHealth());
		System.out.println();
	}

}
