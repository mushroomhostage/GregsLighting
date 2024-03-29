package net.minecraft.src.ic2.api;

/**
 * Interface implemented by the reactor chamber tile entity.
 */
public interface IReactorChamber {
	/**
	 * Get the chamber's reactor.
	 * 
	 * @return The reactor
	 */
	public IReactor getReactor();
}
