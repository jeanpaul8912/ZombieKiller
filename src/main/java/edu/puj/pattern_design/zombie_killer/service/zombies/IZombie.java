package edu.puj.pattern_design.zombie_killer.service.zombies;

public interface IZombie {
	
	Zombie inicialize(short nivel, Zombie atras, Zombie zombieAlFrente);
	
}