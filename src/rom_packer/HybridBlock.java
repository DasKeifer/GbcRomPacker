package rom_packer;

import java.io.IOException;

import gbc_framework.SegmentedWriter;
import gbc_framework.rom_addressing.AssignedAddresses;

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

	public void write(SegmentedWriter writer, AssignedAddresses assignedAddresses) throws IOException
	{
		movable.write(writer, assignedAddresses);
	}
}
