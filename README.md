Demonstrates how actual Webservice Client technologies call remote services. The projects are ready to use in a Designer 7.0.0 and should give you a first impression how these stacks work with your WSDL. Even tough there is yet no tooling support in the IDE.

# ws-call-cxf
- Generates plain JAX_WS stubs via maven plugin. Run `mvn generate-sources` to inspect them. Visit the `ConnectivityDemosTest/pom.xml`for code generator details.
- Special configurations and features can be applied on specific CXF API by wrapping the service with `org.apache.cxf.frontend.ClientProxy.getClient(Object serviceInstance)`
- See the JUnit tests in `ConnectivityDemosTest/src_test` to inspect sample service calls.
