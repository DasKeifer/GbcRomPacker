package rom_packer;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import gbc_framework.QueuedWriter;
import gbc_framework.SegmentedByteBlock;
import gbc_framework.rom_addressing.AssignedAddresses;
import gbc_framework.rom_addressing.BankAddress;
import gbc_framework.rom_addressing.BankAddress.BankAddressLimitType;
import gbc_framework.rom_addressing.BankAddress.BankAddressToUseType;

public class AllocBlock
{
	public static final String END_OF_DATA_BLOCK_SUBSEG_LABEL = "__end_of_data_block__";
	
	protected SegmentedByteBlock block;
	// TODO: make const
	protected String endSegmentName; // Root name + "." + END_OF_DATA_BLOCK_SUBSEG_LABEL
	
	public AllocBlock(SegmentedByteBlock code) 
	{
		this.block = code;
		this.endSegmentName = block.getId() + END_OF_DATA_BLOCK_SUBSEG_LABEL;
		//endSegmentName = CompilerUtils.formSubsegmentName(END_OF_DATA_BLOCK_SUBSEG_LABEL, id);
	}

	public String getId()
	{
		return block.getId();
	}
	
	protected String getEndSegmentId()
	{
		return endSegmentName;
	}

	public int getWorstCaseSize()
	{
		return block.getWorstCaseSize();
	}

	public int getWorstCaseSize(AssignedAddresses assignedAddresses)
	{
		return block.getWorstCaseSize(assignedAddresses);
	}
	
	public boolean addAllIds(Set<String> usedIds)
	{		
		// Add the references for its segments
		for (String segmentId : block.getSegmentIds())
		{
			if (!usedIds.add(segmentId))
			{
				return false;
			}
		}
		
		// Now try to add the end segment
		return usedIds.add(endSegmentName);
	}

	public void assignBank(byte bank, AssignedAddresses assignedAddresses) 
	{
		block.assignBank(bank, assignedAddresses);
	}

	public void assignAddresses(BankAddress blockAddress, AssignedAddresses assignedAddresses) 
	{	
		BankAddress blockEnd = assignBlockAddressesOnly(blockAddress, assignedAddresses);
		
		// Now assign the end segment
		assignedAddresses.put(endSegmentName, blockEnd);
	}
	
	protected BankAddress assignBlockAddressesOnly(BankAddress blockAddress, AssignedAddresses assignedAddresses) 
	{	
		if (blockAddress == BankAddress.UNASSIGNED)
		{
			throw new IllegalArgumentException("TODO");
		}
		
		// Will throw if the addresses are invalid - means we can make some assumptions here
		AssignedAddresses relAddresses = new AssignedAddresses();
		
		// TODO: Doesn't capture the shrinking step below - refactor to capture
		BankAddress relativeSegEnd = block.getSegmentsRelativeAddresses(blockAddress, assignedAddresses, relAddresses);
		
		// For each segment relative address, offset it to the block address and add it to the
		// allocated indexes	
		// Note that these will be in the correct order and will have the end segment label
		Iterator<String> segItr = block.getSegmentIds().iterator();
		while (segItr.hasNext())
		{
			String segmentId = segItr.next();
			BankAddress relAddress = blockAddress.newSum(
					relAddresses.getThrow(segmentId), 
					BankAddressToUseType.ADDRESS_IN_BANK_ONLY, 
					BankAddressLimitType.WITHIN_BANK_OR_START_OF_NEXT);
			
			// If its null, we passed the bank. Otherwise if its the next bank, then we reached the end perfectly so if this
			// was the last segment then we are good. Otherwise we are in trouble
			if (relAddress == null || 
					((!relAddress.isSameBank(blockAddress)) && segItr.hasNext()))
			{
				throw new RuntimeException("assignBlockAndSegmentBankAddresses Passed the end of a bank "
						+ "while assigning addresses for segment (" + segmentId + "). Each data block "
						+ "should fit assuming the blocks were successfully packed in earlier stages");
			}
			// Otherwise just set the address to the calculated address in the bank
			assignedAddresses.put(segmentId, relAddress);
		}
		
		return blockAddress.newSum(relativeSegEnd, BankAddressToUseType.ADDRESS_IN_BANK_ONLY);
	}

	public void removeAddresses(AssignedAddresses assignedAddresses) 
	{
		block.removeAddresses(assignedAddresses);
	}
	
	public void write(QueuedWriter writer, AssignedAddresses assignedAddresses) throws IOException 
	{
		// Write the block
		BankAddress writeEnd = block.write(writer, assignedAddresses);
		
		// Now get the expected end address and check that it matches what we found
		// when we wrote
		BankAddress expectedEnd = assignedAddresses.getThrow(endSegmentName);
		block.checkAndFillSegmentGaps(writeEnd, expectedEnd, writer, getEndSegmentId());
	}
}
