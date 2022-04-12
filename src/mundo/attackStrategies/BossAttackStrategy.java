package mundo.attackStrategies;

import mundo.Boss;
import mundo.Enemigo;
import mundo.SurvivorCamp;

public class BossAttackStrategy extends AttackStrategy implements IAttackMovement{

	private Boss boss;

	@Override
	public void executeAttack(Enemigo enemy) {
		boss = (Boss) enemy;
		attack(boss);		
	}
	
	public void attack(Boss boss) {
		if (boss.getEstadoActual().equals(Boss.VOLANDO)) {
			if (boss.getPosY() > Boss.POS_ATAQUE) {
				boss.setEstadoActual(Boss.ATACANDO);
			}
			else {
				if(boss.getPosX()>SurvivorCamp.ANCHO_PANTALLA -Boss.ANCHO_IMAGEN || boss.getPosX()<0) {
					moverEnDireccion(boss);
					}
				boss.setPosX(boss.getPosX() + boss.getDireccionX());
				boss.setPosY(boss.getPosY() + boss.getDireccionY());
				if (boss.getFrameActual() < 13) {
					boss.setFrameActual((byte) (boss.getFrameActual() + 1));
				}
				else {
					boss.setFrameActual((byte) 0);
				}
			}
		}
		else if(boss.getEstadoActual().equals(Boss.ATACANDO)){
			if(boss.getFrameActual()<21)
				boss.setFrameActual((byte) (boss.getFrameActual()+1));
		}
	}

	@Override
	public void moverEnDireccion(Enemigo enemy) {

		Boss bossEnemy = (Boss)enemy;
		bossEnemy.setDireccionX((int) (Math.random() * 13) - 6);
		if(bossEnemy.getDireccionX() >0 && boss.getDireccionX()<6) {
			bossEnemy.setDireccionY(6 - bossEnemy.getDireccionX());
		}
			else if(bossEnemy.getDireccionX()<=0 && bossEnemy.getDireccionX()>-6) {
				bossEnemy.setDireccionY(6 + bossEnemy.getDireccionX());
			}
			else {
				bossEnemy.setDireccionY(2);
			}
		}
	
	public void terminaDeAtacar() {
		boss.setEstadoActual(Boss.VOLANDO);
		boss.setPosY(Boss.POS_INICIAL);
		moverEnDireccion(boss);
		boss.setPosX(boss.posAleatoriaX());
	}
}