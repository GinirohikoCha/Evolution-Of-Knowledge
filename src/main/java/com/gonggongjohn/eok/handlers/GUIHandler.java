package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.client.gui.*;
import com.gonggongjohn.eok.inventory.ContainerBlueprintTable;
import com.gonggongjohn.eok.inventory.ContainerElementaryResearchTable;
import com.gonggongjohn.eok.inventory.ContainerMerchant;
import com.gonggongjohn.eok.inventory.ContainerRefractingTelescope;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	public static final int GUIEOKManual = 0;
    public static final int GUIRefractingTelescope = 1;
    public static final int GUIElementaryResearchTable = 2;
	public static final int GUIMerchant = 3;
	public static final int GUIContainerTest = 4;
    // public static final int GUIFirstMachine = 5;
    // public static final int GUIHayTorchBase = 6;
    // public static final int GUIHayTorchBaseLit = 7;
    public static final int GUIBluePrint = 8;
    public static final int GUITest2D = 9;
    public static final int GUIBlueprintTable = 10;

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {

        switch (ID) {
        	case GUIEOKManual:
        		return null;
            case GUIRefractingTelescope:
                return new ContainerRefractingTelescope(player);
            case GUIElementaryResearchTable:
                return new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z)));
            case GUIMerchant:
            	return new ContainerMerchant(player);
            case GUIBluePrint:
                return new GUIBluePrint();
            case GUITest2D:
                return new GUITest2D();
            case GUIBlueprintTable:
                return new ContainerBlueprintTable(player, world.getTileEntity(new BlockPos(x, y, z)));
            default:
				return null;
		}
	}

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {

        switch (ID){
        	case GUIEOKManual:
        		return null;
            case GUIRefractingTelescope:
                return new GUIRefractingTelescope(new ContainerRefractingTelescope(player));
            case GUIElementaryResearchTable:
                return new GUIElementaryResearchTable(new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z))));
            case GUIMerchant:
            	return new GUIMerchant(new ContainerMerchant(player));
            case GUIBluePrint:
                return new GUIBluePrint();
            case GUITest2D:
                return new GUITest2D();
            case GUIBlueprintTable:
                return new GUIBlueprintTable(new ContainerBlueprintTable(player, world.getTileEntity(new BlockPos(x, y, z))));
            default:
                return null;
        }
    }
}
