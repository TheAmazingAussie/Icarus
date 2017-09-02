package org.alexdev.icarus.game.plugins;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PluginManager {

	private static Map<PluginEvent, List<Plugin>> registeredPlugins;

	public static void load() {
		registeredPlugins = Maps.newConcurrentMap();
		
		for (PluginEvent event : PluginEvent.values()) {
			registeredPlugins.put(event, Lists.newArrayList());
		}
		
		getPluginFiles();
	}

	private static void getPluginFiles() {

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile("plugin_registry.lua");
		chunk.call();

		LuaValue tableValue = globals.get("plugins");

		if (!tableValue.istable()) {
			return;

		}

		LuaTable table = (LuaTable) tableValue;

		if (table.len().toint() > 0) {
			Log.println();
		} else {
			return;
		}

		for (int i = 0; i < table.len().toint(); i++) {
			LuaValue value = table.get(i + 1);
			loadPlugin(value.toString());
		}
		
		Log.println("Loaded " + registeredPlugins.size() + " plugin(s)!");
	}

	private static void loadPlugin(String path) {

		Globals globals = JsePlatform.standardGlobals();
		registerGlobalVariables(globals);
		LuaValue chunk = globals.loadfile(path);
		chunk.call();

		LuaValue detailsValue = globals.get("plugin_details");

		if (!detailsValue.istable()) {
			return;

		}

		LuaValue eventsValue = globals.get("event_register");

		if (!eventsValue.istable()) {
			return;

		}

		LuaTable details = (LuaTable) detailsValue;
		LuaTable events = (LuaTable) eventsValue;

		Plugin plugin = new Plugin(
				details.get("name").toString(), 
				details.get("author").toString(),
				globals);

		LuaValue pluginEnable = globals.get("onEnable");
		pluginEnable.invoke(CoerceJavaToLua.coerce(plugin));
		//pluginEnable.invoke(LuaValue.varargsOf(new LuaValue[] { CoerceJavaToLua.coerce(plugin), LuaValue.valueOf("testing value") }));

		for (int i = 0; i < events.len().toint(); i++) {
			PluginEvent event = PluginEvent.valueOf(events.get(i + 1).toString());
			registeredPlugins.get(event).add(plugin);
		}

	}
	
	public static void callEvent(PluginEvent event, LuaValue[] values) {
		
		if (!registeredPlugins.containsKey(event)) {
			return;
		}
		
		for (Plugin plugin : registeredPlugins.get(event)) {
			
			LuaValue calledEvent = plugin.getGlobals().get(event.getFunctionName());
			Varargs variableArgs = calledEvent.invoke(LuaValue.varargsOf(values));
			
			//boolean isCancelled = variableArgs.arg1().toboolean();
			
		}
	}

	private static void registerGlobalVariables(Globals globals) {
		globals.set("log", CoerceJavaToLua.coerce(new Log()));
		globals.set("util", CoerceJavaToLua.coerce(new Util()));

	}
}
