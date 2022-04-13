package mundo.defenseStrategies;

public class DefenseStrategyContext {
	
	private final DefenseStrategy attackStrategy;

	public DefenseStrategyContext(DefenseStrategy attackStrategy) {
		this.attackStrategy = attackStrategy;
	}

	public void executeDefense() {
		this.attackStrategy.executeDefense();
	}

}
