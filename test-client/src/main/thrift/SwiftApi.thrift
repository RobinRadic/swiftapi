namespace java nl.radic.swiftapi.thrift

/**
 * Various codes used for catching errors
 */
enum ErrorCode {
/**
 * If a parameter was invalid
 */
	INVALID_REQUEST = 0,
/**
 * Authentication failed
 */
	INVALID_AUTHSTRING = 1,
/**
 * Requested data could not be found
 */
	NOT_FOUND = 2,
/**
 *	Something went wrong during a download operation
 */
 	DOWNLOAD_ERROR = 3,
/**
 *	Something went wrong during a file operation
 */
 	FILE_ERROR = 4,
/**
 *	Could not read a file
 */
 	NO_READ = 5,
}

/**
 * This exception is thrown when something data-related went wrong
 */
exception EDataException {
/**
 * Detailed reason for the exception
 */
	1: ErrorCode code,
/**
 * A message that describes the exception
 */
	2: string errorMessage,
}

/**
 * Thrown when authentication fails, this is thrown
 */
exception EAuthException {
/**
 * Detailed reason for the exception
 */
	1: ErrorCode code,
/**
 * A message that describes the exception
 */
	2: string errorMessage,
}


service SwiftApi {

/**
 * Broadcasts a message to all players on the server
 *
 * @param authString
 *            The authentication hash
 *
 * @param message
 *            The message to send
 *
 * @return boolean true on success false on serious failure
 *
 * @throws EAuthException
 *             If the method call was not correctly authenticated
 *
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool announce(1:string authString, 2:string message)
	throws (1:EAuthException aex),
}