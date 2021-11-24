package gbc_rom_packer;


import gbc_framework.ByteBlock;
import gbc_framework.rom_addressing.BankAddress;

public class FixedBlock extends AllocBlock
{
	private BankAddress address;
	// The remote block (if needed), should be referred to in the DataBlock so no need to track it here
	
	public FixedBlock(ByteBlock code, int fixedStartAddress)
	{
		super(code);
		setFixedBlockCommonData(fixedStartAddress);
	}
	
	private void setFixedBlockCommonData(int fixedStartAddress)
	{
		address = new BankAddress(fixedStartAddress);
	}

	public BankAddress getFixedAddress() 
	{
		return new BankAddress(address);
	}
}
