package gbc_rom_packer;


import java.util.Set;

import gbc_framework.ByteBlock;
import gbc_framework.rom_addressing.AssignedAddresses;
import gbc_framework.rom_addressing.BankAddress;

public class AllocBlock
{
	ByteBlock code;
	
	public AllocBlock(ByteBlock code) 
	{
		this.code = code;
	}

	public String getId()
	{
		return code.getId();
	}

	public int getWorstCaseSize(AssignedAddresses assignedAddresses)
	{
		return code.getWorstCaseSize(assignedAddresses);
	}
	
	public void addAllIds(Set<String> usedIds)
	{
		code.addAllIds(usedIds);
	}

	public void assignBank(byte bank, AssignedAddresses assignedAddresses) 
	{
		code.assignBank(bank, assignedAddresses);
	}

	public void assignAddresses(BankAddress fixedAddress, AssignedAddresses assignedAddresses) 
	{
		code.assignAddresses(fixedAddress, assignedAddresses);
	}

	public void removeAddresses(AssignedAddresses assignedAddresses) 
	{
		code.removeAddresses(assignedAddresses);
	}
}
