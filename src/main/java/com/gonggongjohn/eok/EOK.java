package com.gonggongjohn.eok;

import org.apache.logging.log4j.Logger;

import com.gonggongjohn.eok.handlers.CapabilityHandler;
import com.gonggongjohn.eok.handlers.CommandHandler;
import com.gonggongjohn.eok.handlers.MetaItemHandler;
import com.gonggongjohn.eok.handlers.WorldGenHandler;
import com.gonggongjohn.eok.network.PacketGUIMerchant;
import com.gonggongjohn.eok.network.PacketGuiButton;
import com.gonggongjohn.eok.network.PacketGuiScreen;
import com.gonggongjohn.eok.network.PacketInverseReseachData;
import com.gonggongjohn.eok.network.PacketPlayerState;
import com.gonggongjohn.eok.network.PacketResearchData;
import com.gonggongjohn.eok.network.PacketSlotChange;
import com.gonggongjohn.eok.network.PacketTestGuiScreen;
import com.gonggongjohn.eok.tweakers.TweakersMain;
import com.gonggongjohn.eok.utils.BluePrintDict;
import com.gonggongjohn.eok.utils.InspirationDict;
import com.gonggongjohn.eok.utils.MathUtils;
import com.gonggongjohn.eok.utils.MultiBlockDict;
import com.gonggongjohn.eok.utils.ResearchDict;

import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ReportedException;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = EOK.MODID, name = EOK.NAME, dependencies = EOK.DEPENDENCIES, useMetadata = true)
public class EOK {
	public static final String MODID = "eok";
	public static final String NAME = "Evolution Of Knowledge";
	public static final String DEPENDENCIES = "required-after:tmc@[1.3.1.1,);required-after:gregtech@[1.8.13.465,)";

	@Mod.Instance
	public static EOK instance;

	@SidedProxy(clientSide = "com.gonggongjohn.eok.ClientProxy", serverSide = "com.gonggongjohn.eok.CommonProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs tabEOK = new EOKTab();
	private static Logger logger;

	private SimpleNetworkWrapper network;

	public static MathUtils mathUtils = new MathUtils();
	public static ResearchDict researchDict = new ResearchDict();
	public static InspirationDict inspirationDict = new InspirationDict();
	public static MultiBlockDict multiBlockDict;
	public static BluePrintDict bluePrintDict;
	
	public EOK() {
		ProgressBar progress = ProgressManager.push("Initializing MetaItem", 0);
		MetaItemHandler.setup();
		ProgressManager.pop(progress);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		if (Loader.isModLoaded("torcherino") || Loader.isModLoaded("projecte")) {
			CrashReport report = CrashReport.makeCrashReport(new IllegalAccessError(),
					String.format("You have ENRAGED the FOREST BAT because some mods are loaded"));
			throw new ReportedException(report);
		}
		proxy.preInit(event);
		researchDict.initName();
		researchDict.initRelation();
		inspirationDict.initName();
		CapabilityHandler.setupCapabilities();
		TweakersMain.preInit();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class, 0, Side.SERVER);
		network.registerMessage(new PacketPlayerState.Handler(), PacketPlayerState.class, 1, Side.CLIENT);
		network.registerMessage(new PacketResearchData.Handler(), PacketResearchData.class, 3, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 4, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 5, Side.SERVER);
		network.registerMessage(new PacketInverseReseachData.Handler(), PacketInverseReseachData.class, 6, Side.SERVER);
		network.registerMessage(new PacketTestGuiScreen.Handler(), PacketTestGuiScreen.class, 7, Side.CLIENT);
		network.registerMessage(new PacketSlotChange.Handler(), PacketSlotChange.class, 8, Side.SERVER);
		network.registerMessage(new PacketGuiScreen.Handler(), PacketGuiScreen.class, 9, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		multiBlockDict = new MultiBlockDict();
		multiBlockDict.initStructure();
		multiBlockDict.initDict();
		bluePrintDict = new BluePrintDict();
		bluePrintDict.init();
		new WorldGenHandler();
		TweakersMain.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		TweakersMain.postInit();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler.registerCommands(event);
	}

	public static CommonProxy getProxy() {
		return proxy;
	}

	public static SimpleNetworkWrapper getNetwork() {
		return instance.network;
	}

	public static Logger getLogger() {
		return logger;
	}
}