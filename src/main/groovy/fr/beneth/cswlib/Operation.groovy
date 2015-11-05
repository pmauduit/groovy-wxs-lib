package fr.beneth.cswlib

class Operation {
    

    //    <ows:Operation name="GetCapabilities">
    //      <ows:DCP>
    //        <ows:HTTP>
    //          <ows:Get xlink:href="http://blah/geonetwork/srv/eng/csw"/>
    //          <ows:Post xlink:href="http://blah/geonetwork/srv/eng/csw"/>
    //        </ows:HTTP>
    //      </ows:DCP>
    //      <ows:Parameter name="sections">
    //        <ows:Value>ServiceIdentification</ows:Value>
    //        <ows:Value>ServiceProvider</ows:Value>
    //        <ows:Value>OperationsMetadata</ows:Value>
    //        <ows:Value>Filter_Capabilities</ows:Value>
    //      </ows:Parameter>
    //      <ows:Constraint name="PostEncoding">
    //        <ows:Value>XML</ows:Value>
    //        <ows:Value>SOAP</ows:Value>
    //      </ows:Constraint>
    //    </ows:Operation>
    String name
    ArrayList<Parameter> values = new ArrayList<Parameter>()
    
 
}
