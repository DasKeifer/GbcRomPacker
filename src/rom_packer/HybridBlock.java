package rom_packer;

import java.io.IOException;

import gbc_framework.QueuedWriter;
import gbc_framework.rom_addressing.AddressRange;
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

	public void write(QueuedWriter writer, AssignedAddresses assignedAddresses) throws IOException
	{
		movable.write(writer, assignedAddresses);
	}
	
	public AddressRange createBlankedRangeForBlock(int size)
	{
		return fixed.createBlankedRangeForBlock(size);
	}
}
