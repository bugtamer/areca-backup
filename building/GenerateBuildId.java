package building;

import java.util.Random;


/**
 * Generates a random BUILD ID for VersionInfos.java
 * 
 * <p>Shell commands:<p>
 * <ul>
 *   <li>$ <code>javac building/GenerateBuildId.java</code></li>
 *   <li>$ <code>java building/GenerateBuildId</code></li>
 * </ul>
 * 
 * <p>Output example:<p>
 * <code>Next Areca's BUILD ID: -7610986468043976209L for src/com/application/areca/version/VersionInfos.java<code>
 * 
 * @see src/com/application/areca/version/VersionInfos.java
 * @since 8.0.0
 * @author bugtamer
 */
public class GenerateBuildId {

    public static void main(String[] args) {
        final long buildId = new Random().nextLong();
        final String versionInfos = "src/com/application/areca/version/VersionInfos.java";
        System.out.println("Next Areca's BUILD ID: " + buildId + "L for " + versionInfos);
    }

}
