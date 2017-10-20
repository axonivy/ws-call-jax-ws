Demonstrates how actual Webservice Client technologies call remote services. The projects are ready to use in a Designer 7.0.0 LTS and should give you a first impression how these stacks work with your WSDL. Even tough there is yet no tooling support in the IDE.

# ws-call-cxf
- Generates plain JAX_WS stubs via maven plugin. Run `mvn generate-sources` to inspect them. Visit the `ConnectivityDemosTest/pom.xml`for code generator details.
- Special configurations and features can be applied on specific CXF API by wrapping the service with `org.apache.cxf.frontend.ClientProxy.getClient(Object serviceInstance)`
- See the JUnit tests in `ConnectivityDemosTest/src_test` to inspect sample service calls.

## Troubleshooting
- Problem: JUnit Test executions may fail in the Designer with the root cause:
```
Caused by: java.lang.RuntimeException: Cannot create a secure XMLInputFactory
	at org.apache.cxf.staxutils.StaxUtils.createXMLInputFactory(StaxUtils.java:338)
	at org.apache.cxf.staxutils.StaxUtils.getXMLInputFactory(StaxUtils.java:278)
	at org.apache.cxf.staxutils.StaxUtils.createXMLStreamReader(StaxUtils.java:1796)
	at org.apache.cxf.staxutils.StaxUtils.createXMLStreamReader(StaxUtils.java:1695)
	at org.apache.cxf.wsdl11.WSDLManagerImpl.loadDefinition(WSDLManagerImpl.java:230)
	... 33 more
```
- Solution: modify the runConfiguration of the Unit Test so that the Maven ClasspathContainer is before the Project.
![runConfig](https://raw.githubusercontent.com/ivy-samples/ws-call-jax-ws/master/cxf/ConnectivityDemosTest/src_test/com/axonivy/connectivity/ws/putMvnClasspathBeforeProject.png)
