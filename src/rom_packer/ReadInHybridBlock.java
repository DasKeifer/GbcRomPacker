package rom_packer;

import gbc_framework.rom_addressing.AddressRange;

public class ReadInHybridBlock extends HybridBlock
{
	private int sourceLength;
	
	public ReadInHybridBlock(MovableBlock block, int preferredStartAddress, int sourceLength)
	{
		super(block, preferredStartAddress);
		this.sourceLength = sourceLength;
	}
	
	public AddressRange determineNeededBlankingForMoving()
	{
		return new AddressRange(getFixedBlock().getFixedAddress(), sourceLength);
	}
}
