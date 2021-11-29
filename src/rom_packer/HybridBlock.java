package rom_packer;

public class HybridBlock 
{
	private FixedBlock fixed;
	private MovableBlock movable;
	
	public HybridBlock(MovableBlock block, int preferredStartAddress)
	{
		fixed = new FixedBlock(block.block, preferredStartAddress);
		movable = block;
	}
	
	public FixedBlock getFixedBlock() 
	{
		return fixed;
	}

	public MovableBlock getMovableBlock() 
	{
		return movable;
	}
}
