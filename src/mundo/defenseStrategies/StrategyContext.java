package mundo.defenseStrategies;

public class StrategyContext {
	
	private final DefenseStrategy attackStrategy;

	public StrategyContext(DefenseStrategy attackStrategy) {
		this.attackStrategy = attackStrategy;
	}

	public void executeAttack() {
		this.attackStrategy.executeAttack();
	}

}
