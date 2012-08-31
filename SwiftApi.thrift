namespace java org.phybros.thrift

// Various codes used for catching errors
enum ErrorCode {
	INVALID_REQUEST = 0,
	INVALID_AUTHSTRING = 1
	NOT_FOUND = 2,
}

// This exception is thrown when something data-related went wrong
exception EDataException {
	1: ErrorCode code,
	2: string message,
}

// Thrown when authentication goes awry
exception EAuthException {
	1: ErrorCode code,
	2: string message,
}

// Represents a server plugin
struct Plugin {
	1: string name,
	2: string description,
	3: string version,
	4: string website,
	5: list<string> authors,
	6: bool enabled,
}

// The main service that provides all the methods
service SwiftApi {
	list<Plugin> getPlugins(1:string authString) throws (1:EAuthException aex),
	Plugin getPlugin(1:string authString, 2:string name) throws (1:EAuthException aex, 2:EDataException dex),
}