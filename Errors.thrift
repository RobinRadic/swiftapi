namespace java org.phybros.thrift
namespace csharp org.phybros.thrift

/**
 * Various codes used for catching errors
 */
enum ErrorCode {
	INVALID_REQUEST = 0,
	INVALID_AUTHSTRING = 1
	NOT_FOUND = 2,
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