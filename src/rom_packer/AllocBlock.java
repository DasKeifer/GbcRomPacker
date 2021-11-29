package rom_packer;


import java.util.Set;

import gbc_framework.ByteBlock;
import gbc_framework.rom_addressing.AssignedAddresses;
import gbc_framework.rom_addressing.BankAddress;

public class AllocBlock
{
	ByteBlock block;
	
	public AllocBlock(ByteBlock code) 
	{
		this.block = code;
	}

	public String getId()
	{
		return block.getId();
	}

	public int getWorstCaseSize(AssignedAddresses assignedAddresses)
	{
		return block.getWorstCaseSize(assignedAddresses);
	}
	
	public boolean addAllIds(Set<String> usedIds)
	{
		return block.addAllIds(usedIds);
	}

	public void assignBank(byte bank, AssignedAddresses assignedAddresses) 
	{
		block.assignBank(bank, assignedAddresses);
	}

	public void assignAddresses(BankAddress fixedAddress, AssignedAddresses assignedAddresses) 
	{
		block.assignAddresses(fixedAddress, assignedAddresses);
	}

	public void removeAddresses(AssignedAddresses assignedAddresses) 
	{
		block.removeAddresses(assignedAddresses);
	}
}
