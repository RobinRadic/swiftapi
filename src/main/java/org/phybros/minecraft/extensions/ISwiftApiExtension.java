package org.phybros.minecraft.extensions;

import org.apache.thrift.TProcessor;

/**
 * Created by radic on 7/20/14.
 */
public interface ISwiftApiExtension {
    public TProcessor getApiProcessor(String name);
}
