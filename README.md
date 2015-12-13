# groovy-wxs-lib

a Groovy library to retrieve WxS data from remote servers and parse them.

# Usage

This library is the one used in the [Android App for geOrchestra](https://github.com/pmauduit/geor-android).
Here are some sample code that can be directly pasted into a groovy console for instance:

## Sample CSW GetRecords

```groovy
// Execute a getrecords operation querying for datasets
@GrabResolver(name='wxslib', root='http://download.qualitystreetmap.org/maven/')
@Grab(group='fr.beneth', module='wxslib', version='1.1-SNAPSHOT')

import fr.beneth.cswlib.GetRecords

def rec = GetRecords.getAllMetadatasFromEndpoint("http://sdi.georchestra.org/geonetwork/srv/eng/csw",
            GetRecords.DATASET)

rec.metadatas.each {
  println "title: ${it.title}"
}

println "${rec.metadatas.size()} metadata returned"

```

## CSW GetRecords & GetRecordById

```groovy
@GrabResolver(name='wxslib', root='http://download.qualitystreetmap.org/maven/')
@Grab(group='fr.beneth', module='wxslib', version='1.1-SNAPSHOT')

import fr.beneth.cswlib.GetRecords
import fr.beneth.cswlib.GetRecordById

def rec = GetRecords.getAllMetadatasFromEndpoint("http://geobretagne.fr/geonetwork/srv/eng/csw", GetRecords.SERVICE)
println "${rec.metadatas.size()} metadata returned"

def cg29 = rec.metadatas.find { it.fileIdentifier == "fr-org.geobretagne.cg29.wfs" }
if (cg29) {
  println "Service Metadata title: ${cg29.title}, identifier: ${cg29.fileIdentifier}"
  // Need to "fetch" the MD once again, see #2
  def fullcg29 = GetRecordById.getRecordById("http://geobretagne.fr/geonetwork/srv/eng/csw", cg29.fileIdentifier)
  
  fullcg29.operatesOn.each {
    println "\toperatesOn: ${it.uuid} url: ${it.url}"
  }
}
println "done"

```
