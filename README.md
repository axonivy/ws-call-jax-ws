Demonstrates how actual Webservice Client technologies call remote services.

# ws-call-cxf
- Generates plain JAX_WS stubs via maven plugin. Run `mvn generate-sources` to inspect them. Visit the `ConnectivityDemosTest/pom.xml`for code generator details.
- Special configurations and features can be applied on specific CXF API by wrapping the service with `org.apache.cxf.frontend.ClientProxy.getClient(Object serviceInstance)`
- See the JUnit tests in `ConnectivityDemosTest/src_test` to inspect sampel service calls.
