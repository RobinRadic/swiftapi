package org.phybros.minecraft.extensions;

import org.apache.thrift.TProcessor;

/**
 * Created by radic on 7/20/14.
 */
public interface ISwiftApiExtension {
    /**
     *
     * returns the processor of the thrift service
     *
     * @param name Name of the service
     * @return TProcessor the processor of the thrift service
     */
    public TProcessor getApiProcessor(String name);
}
