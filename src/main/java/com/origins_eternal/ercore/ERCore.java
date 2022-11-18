package com.origins_eternal.ercore;

import com.origins_eternal.ercore.utils.proxy.CommonProxy;
import com.origins_eternal.ercore.content.tab.CreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = ERCore.MOD_ID, name = ERCore.MOD_NAME, version = ERCore.VERSION, dependencies = "after:tconstruct@[1.12-2.7.2.15,);")
public class ERCore {

	public static File config;

	public static final String MOD_ID = "ercore";
	public static final String MOD_NAME = "ER Core";
	public static final String VERSION = "1.0.0";
	
	@SidedProxy(clientSide = "com.origins_eternal.ercore.utils.proxy.ClientProxy", serverSide = "com.origins_eternal.ercore.utils.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs ERCORE = new CreativeTab();

	static { FluidRegistry.enableUniversalBucket(); }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) { proxy.preInit(event); }

	@EventHandler
	public void init(FMLInitializationEvent event) { proxy.init(event); }
}