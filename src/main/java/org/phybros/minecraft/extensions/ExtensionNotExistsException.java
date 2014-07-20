package org.phybros.minecraft.extensions;

public class ExtensionNotExistsException extends ArrayIndexOutOfBoundsException {

    public ExtensionNotExistsException() {
        super("Extension could not be found in ExtensionBag.");
    }

    public ExtensionNotExistsException(String name) {
        super("Extension " + name + " could not be found in ExtensionBag");
    }

}
