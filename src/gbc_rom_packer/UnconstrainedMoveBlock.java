package gbc_rom_packer;


import java.util.List;
import java.util.SortedSet;

import gbc_framework.ByteBlock;
import gbc_framework.RomConstants;
import gbc_framework.rom_addressing.PrioritizedBankRange;

public class UnconstrainedMoveBlock extends MoveableBlock
{
	public UnconstrainedMoveBlock(ByteBlock code)
	{
		super(code);
	}
	
	public UnconstrainedMoveBlock(ByteBlock code, int priority, byte startBank, byte stopBank)
	{
		super(code, priority, startBank, stopBank);
	}
	
	public UnconstrainedMoveBlock(ByteBlock code, PrioritizedBankRange pref)
	{
		super(code, pref);
	}
	
	public UnconstrainedMoveBlock(ByteBlock code, List<PrioritizedBankRange> prefs)
	{
		super(code, prefs);
	}

	@Override
	public SortedSet<PrioritizedBankRange> getAllowableBankPreferences()
	{
		SortedSet<PrioritizedBankRange> toReturn = super.getAllowableBankPreferences();
		toReturn.add(new PrioritizedBankRange(Integer.MAX_VALUE, (byte) 0, (byte) (RomConstants.NUMBER_OF_BANKS - 1)));
		return toReturn;
	}
}
