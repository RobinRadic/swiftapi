struct Plugin {
	1: string name,
	2: string description,
	3: string version,
	4: string website,
	5: list<string> authors,
	6: bool enabled,
}

service PluginService {
	list<Plugin> getPlugins(),
	Plugin getPlugin(1:string name),
}