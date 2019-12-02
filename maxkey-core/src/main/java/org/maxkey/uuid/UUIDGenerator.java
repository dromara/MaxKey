package org.maxkey.uuid;

/**
 * An interface representing an object that generates UUIDs.
 */
public interface UUIDGenerator
{
    /**
     * Generates a new unique UUID according to this generator's rules.
     */
    UUID nextUUID();
}