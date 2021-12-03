package rom_packer;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import gbc_framework.SegmentedWriter;
import gbc_framework.rom_addressing.AssignedAddresses;

public class Blocks 
{
	private Set<String> usedIds;
	private List<FixedBlock> fixedBlocks;
	private List<HybridBlock> hybridBlocks;
	private List<MovableBlock> movableBlocks;
	
	public Blocks()
	{
		usedIds = new HashSet<>();
		fixedBlocks = new LinkedList<>();
		hybridBlocks = new LinkedList<>();
		movableBlocks = new LinkedList<>();
	}
	
	public void addFixedBlock(FixedBlock block)
	{
		addIdsOfBlock(block);
		fixedBlocks.add(block);
	}
	
	public void addHybridBlock(HybridBlock block)
	{
		// We can use either since they both share the same code
		addIdsOfBlock(block.getFixedBlock());
		hybridBlocks.add(block);
	}
	
	public void addMovableBlock(MovableBlock block)
	{
		addIdsOfBlock(block);
		movableBlocks.add(block);
	}
	
	private void addIdsOfBlock(AllocBlock block)
	{
		if (!block.addAllIds(usedIds))
		{
			throw new IllegalArgumentException("Duplicate block/segment ID detected while adding ids for block \"" + block.getId() + "\"");
		}
	}
	
	public List<FixedBlock> getAllFixedBlocks()
	{
		return fixedBlocks;
	}

	public List<HybridBlock> getAllHybridBlocks() 
	{
		return hybridBlocks;
	}
	
	public List<MovableBlock> getAllMovableBlocks()
	{
		return movableBlocks;
	}
	
	public void writeBlocks(SegmentedWriter writer, AssignedAddresses assignedAddresses) throws IOException
	{
		for (AllocBlock block : fixedBlocks)
		{
			block.write(writer, assignedAddresses);
		}
		
		for (HybridBlock block : hybridBlocks)
		{
			block.write(writer, assignedAddresses);
		}
		
		for (AllocBlock block : movableBlocks)
		{
			block.write(writer, assignedAddresses);
		}
	}
}
