package rom_packer;


import gbc_framework.SegmentedByteBlock;
import gbc_framework.rom_addressing.AssignedAddresses;
import gbc_framework.rom_addressing.BankAddress;

public class ReplacementBlock extends FixedBlock
{
	int replaceLength;
	
	// For write overs with unpredictable sizes including "minimal jumps" for extending/slicing existing code
	// Auto fill with nop (0x00) after
	public ReplacementBlock(SegmentedByteBlock code, int fixedStartAddress)
	{
		super(code, fixedStartAddress);
		setReplacementBlockCommonData(-1);
	}
	
	public ReplacementBlock(SegmentedByteBlock code, int fixedStartAddress, int replaceLength)
	{
		super(code, fixedStartAddress);
		setReplacementBlockCommonData(replaceLength);
	}
	
	private void setReplacementBlockCommonData(int replaceLength)
	{
		this.replaceLength = replaceLength;
	}
	
	public void setReplaceLength(int length)
	{
		replaceLength = length;
	}	
	
	public int getSize()
	{
		return replaceLength;
	}
	
	@Override 
	public int getWorstCaseSize()
	{
		return getSize();
	}
	
	@Override 
	public int getWorstCaseSize(AssignedAddresses unused)
	{
		return getSize();
	}

	@Override
	public void assignAddresses(BankAddress blockAddress, AssignedAddresses assignedAddresses) 
	{	
		BankAddress blockEnd = assignBlockAddressesOnly(blockAddress, assignedAddresses);
	
		// Ensure the block length is shorter than the length otherwise we need to throw - its too large to fit safely
		if (getSize() - blockAddress.getDifference(blockEnd) < 0)
		{
			throw new IllegalArgumentException("TODO");
		}
		
		// Now assign the end segment at the size instead of where it actually ends
		assignedAddresses.put(endSegmentName, blockAddress.newOffsettedWithinBank(getSize()));
	}
}
