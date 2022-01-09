package rom_packer;


import gbc_framework.SegmentedByteBlock;
import gbc_framework.rom_addressing.BankAddress;

public class FixedBlock extends AllocBlock
{
	private BankAddress address;
	// The remote block (if needed), should be referred to in the DataBlock so no need to track it here
	
	public FixedBlock(SegmentedByteBlock code, int fixedStartAddress)
	{
		super(code);
		address = new BankAddress(fixedStartAddress);
	}
	
	@Override
	public boolean allowAssigningNonBlankAddressSpace()
	{
		return false;
	}

	public BankAddress getFixedAddress() 
	{
		return new BankAddress(address);
	}
}
