package fr.beneth.cswlib.metadata

import groovy.util.slurpersupport.NodeChild;

/**
 * A LinkedMetadata is a simple object containing a identifier (uuid) and a URL,
 * and allows to describe a "srv:operatesOn" element on a service metadata, e.g.:
 *
 * <pre>
 *  <srv:operatesOn uuidref="4a5ea17c" xlink:href="http://path.to/md?uuid=4a5ea17c"/>
 * </pre>
 *
 * @author pmauduit
 *
 */
class LinkedMetadata {

    String uuid, url

    public static LinkedMetadata mapFromXmlFragment(NodeChild xmlFragment) {
        def lm = new LinkedMetadata()

        lm.uuid = xmlFragment['@uuidref']
        lm.url = xmlFragment['@xlink:href']

        return lm
    }
}
