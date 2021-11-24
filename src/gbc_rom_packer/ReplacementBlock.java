package gbc_rom_packer;


import gbc_framework.ByteBlock;
import gbc_framework.rom_addressing.AssignedAddresses;

public class ReplacementBlock extends FixedBlock
{
	int replaceLength;
	
	// For write overs with unpredictable sizes including "minimal jumps" for extending/slicing existing code
	// Auto fill with nop (0x00) after
	public ReplacementBlock(ByteBlock code, int fixedStartAddress)
	{
		super(code, fixedStartAddress);
		setReplacementBlockCommonData(-1);
	}
	
	public ReplacementBlock(ByteBlock code, int fixedStartAddress, int replaceLength)
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
	public int getWorstCaseSize(AssignedAddresses unused)
	{
		return getSize();
	}
}
