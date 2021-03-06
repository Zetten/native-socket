package org.mjd.nativesocket;

import org.joda.time.Duration;
import org.mjd.nativesocket.internal.CBool;


/**
 * {@link NativeSocket} instances provide functionality not exposed by the JDK due to platform/JVM/and
 * JDK API reasons.
 * 
 * The Factory will provide a safe implementation of {@link NativeSocket} if one exists. Use of the
 * code is non-portable.
 */
public interface NativeSocket
{
    /**
     * Enables the keep alive mechanism on this {@link NativeSocket} with the given interval.
     * The keep alive interval is the interval between sequential keep alive probes, regardless of 
     * what the connection has exchanged in the meantime
     * <p>
     * Note, this enables keep alive on this socket if not already enabled.
     * 
     * @param interval
     *            keep alive interval
     */
    void setKeepAliveInterval(Duration interval);
    
    /**
     * Enables the keep alive mechanism on this {@link NativeSocket} with the given idle time.
     * The keep alive idle time is the  interval between the last data packet sent (simple ACKs 
     * are not considered data) and the first keep alive probe; after the connection is 
     * marked to need keep alive, this counter is not used any further.
     * <p>
     * Note, this enables keep alive on this socket if not already enabled.
     * 
     * @param idleTime
     *            keep time interval
     */
    void setKeepAliveIdle(Duration idleTime);
    
    
    /**
     * Data class for keep alive information specific to a {@link NativeSocket} 
     */
    public static final class KeepAliveData
    {
        private final boolean enabled;
        private final int interval;
        private final int idle;
        private final int probes;
        
        /**
         * Constructs a new immutable {@link KeepAliveData}
         * @param enabled whether keep alive is enabled.
         * @param probeCount the number of probes sent before pronouncing this socket link dead.
         * @param intervalSeconds the interval duration in seconds.
         * @param idleTimeSeconds the idle time in seconds.
         */
        public KeepAliveData(CBool enabled, int probeCount, int intervalSeconds, int idleTimeSeconds)
        {
            this.enabled = enabled.toBoolean();
            this.probes = probeCount;
            this.interval = intervalSeconds;
            this.idle = idleTimeSeconds;
        }
        
        /** @return true if keep alive is enabled */
        public boolean isEnabled() { return enabled; }
        
        /** @return the keep alive interval in seconds */
        public int getInterval() { return interval; }
        
        /** @return the keep alive idle time in seconds */
        public int getIdleTime() { return idle; }
        
        /** @return the number of probes send before pronouncing this socket link dead */
        public int getProbeCount() { return probes; }
    }
    

    /**
     * Gathers all the keep alive data available for this {@link NativeSocket}
     * 
     * @return {@link KeepAliveData} data object representing all the available keep alive data.
     */
    KeepAliveData getKeepAliveData();

}
